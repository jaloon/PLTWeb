package com.tipray.service.impl;

import com.tipray.bean.Session;
import com.tipray.bean.User;
import com.tipray.core.exception.LoginException;
import com.tipray.dao.SessionDao;
import com.tipray.service.SessionService;
import com.tipray.service.UserService;
import com.tipray.util.MD5Util;
import com.tipray.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;

/**
 * @author chends
 */
@Transactional
@Service("sessionService")
public class SessionServiceImpl implements SessionService {
    Logger logger = LoggerFactory.getLogger(SessionService.class);
    @Autowired
    private UserService userService;
    @Resource
    private SessionDao sessionDao;

    @Override
    public Session getSessionByUser(Long userId) {
        if (userId == null) {
            return null;
        }
        return sessionDao.getByUser(userId);
    }

    @Override
    public Session getSessionByUUID(String uuid) {
        if (StringUtil.isEmpty(uuid)) {
            return null;
        }
        return sessionDao.getByUUID(uuid);
    }

    @Override
    public Session getSessionByOldUuid(String uuid) {
        if (StringUtil.isEmpty(uuid)) {
            return null;
        }
        return sessionDao.getByOldUUID(uuid);
    }

    @Override
    public void deleteSessionByUUID(String uuid) {
        if (StringUtil.isEmpty(uuid)) {
            return;
        }
        sessionDao.deleteByUUID(uuid);
    }

    @Override
    public void updateOperateDate(String uuid) {
        if (StringUtil.isEmpty(uuid)) {
            return;
        }
        Session session = new Session();
        session.setUuid(uuid);
        session.setOperateDate(new Date());
        sessionDao.updateOperateDate(session);
    }

    @Override
    public void updateSession(Session session) {
        if (session == null) {
            return;
        }
        sessionDao.update(session);
    }

    @Override
    public void addSession(Session session) {
        if (session == null) {
            return;
        }
        Session oldSession = getSessionByUser(session.getUser().getId());
        if (oldSession == null) {
            sessionDao.add(session);
        } else {
            session.setOldUuid(oldSession.getUuid());
            sessionDao.update(session);
        }
    }

    @Override
    public void deleteSessionByUser(Long userId) {
        if (userId != null) {
            sessionDao.deleteByUser(userId);
        }
    }

    @Override
    public Session login(User user, Session session) throws LoginException {
        User userDb = userService.getUserByAccount(user.getAccount());
        if (userDb == null) {
            throw new LoginException("账号错误");
        }
        String newPassword = "";
        try {
            newPassword = MD5Util.md5Encode(user.getPassword());
        } catch (NoSuchAlgorithmException e) {
            throw new LoginException("");
        }
        if (!newPassword.equals(userDb.getPassword())) {
            throw new LoginException("密码错误");
        }
        session.setUser(userDb);
        addSession(session);
        return session;
    }

    @Override
    public void logout(String sessionId) {
        if (StringUtil.isNotEmpty(sessionId)) {
            sessionDao.deleteByUUID(sessionId);
        }
    }

    @Override
    public void deleteTimeOutSession(Date timeoutDate) {
        sessionDao.deleteTimeOutSession(timeoutDate);
    }

    @Override
    public Map<String, Session> findSessions() {
        return sessionDao.findSessions();
    }
}
