package com.tipray.service;

import com.tipray.bean.Session;
import com.tipray.bean.User;
import com.tipray.core.exception.LoginException;

import java.util.Date;
import java.util.Map;

public interface SessionService {
	void addSession(Session session);
	void updateSession(Session session);
	void updateOperateDate(String uuid);
	void deleteSessionByUUID(String uuid);
	void deleteSessionByUser(Long userId);
	Session getSessionByUUID(String uuid);
	Session getSessionByUser(Long userId);
	/**
	 * 根据上一次登录的sessionId，获取本次登录的sessionId(同一用户)
	 * @param sessionId
	 * @return
	 */
	Session getSessionByOldUuid(String sessionId);
	/**
	 * 登录操作
	 * @param user,request,response
	 * @return
	 * @throws LoginException 
	 */
	Session login(User user, Session session) throws LoginException;
	/**
	 * 退出当前登录账号
	 * @param sessionId
	 */
	void logout(String sessionId) throws LoginException;
	/**
	 * 删除过期session
	 * @param timeoutDate  过期临界时间
	 */
	void deleteTimeOutSession(Date timeoutDate);

    /**
     * 获取session列表
     * @return
     */
    Map<String, Session> findSessions();
}
