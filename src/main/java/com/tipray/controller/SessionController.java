package com.tipray.controller;

import com.tipray.bean.Message;
import com.tipray.bean.Session;
import com.tipray.bean.User;
import com.tipray.core.base.BaseAction;
import com.tipray.core.exception.LoginException;
import com.tipray.service.SessionService;
import com.tipray.util.HttpServletUtil;
import com.tipray.util.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Controller
@RequestMapping("/manage/session")
public class SessionController extends BaseAction {
	Logger logger = LoggerFactory.getLogger(SessionController.class);
	
	@Autowired
	private SessionService sessionService;
    
	@RequestMapping(value = "login.do", method = RequestMethod.POST)
	@ResponseBody
	public Message login(User user, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		try {
			Session session = new Session();
			session.setLoginDate(new Date());
			session.setOperateDate(new Date());
			// session.setUuid(request.getSession().getId());
			session.setUuid(SessionUtil.getLoginSessionId(request));
			session.setIp(HttpServletUtil.getHost(request));
			session = sessionService.login(user,session);
			SessionUtil.cacheSession(request, response, session);
			modelMap.put("isLogined", session!=null);
			return Message.success();
		} catch (LoginException e){
			return Message.error(e);
		}
	}
	
	@RequestMapping(value = "logout.do", method = RequestMethod.POST)
	@ResponseBody
	public Message logout(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		try {
			// String sessionId = request.getSession().getId();
			String sessionId = SessionUtil.getLogoutSessionId(request);
			sessionService.logout(sessionId);
            SessionUtil.removeSessionFromList(sessionId);
			return Message.success();
		} catch (LoginException e){
			return Message.error(e);
		}
	}
	
	@RequestMapping(value = "isAlive.do")
	@ResponseBody
	public Message isAlive() {
		return new Message(SUCCESS);
	}
	
}
