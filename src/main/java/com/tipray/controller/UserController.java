package com.tipray.controller;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tipray.bean.GridPage;
import com.tipray.bean.Message;
import com.tipray.bean.Page;
import com.tipray.bean.User;
import com.tipray.constant.LogDescriptionEnum;
import com.tipray.core.annotation.LogAnno;
import com.tipray.core.annotation.PermissionAnno;
import com.tipray.core.base.BaseAction;
import com.tipray.core.exception.ServiceException;
import com.tipray.service.UserService;
import com.tipray.util.MD5Util;

@Controller
@RequestMapping("/manage/user")
/* @Scope("prototype") */
public class UserController extends BaseAction {
	@Resource
	private UserService userService;

	@RequestMapping(value = "dispatch.do")
	public String dispatch(String mode, Long id, ModelMap modelMap) {
		modelMap.put("mode", mode);
		User user = new User();
		if (id > 0) {
			user = userService.getUserById(id);
			user.setPassword(null);
		}
		modelMap.put("user", user);
		return "normal/user/userEdit.jsp";
	}

	@LogAnno(LogDescriptionEnum.USER_ADD)
	@PermissionAnno("userModule")
	@RequestMapping(value = "add.do", method = RequestMethod.POST)
	@ResponseBody
	public Message addUser(@ModelAttribute User user, Long roleId) {
		try {
			userService.addUser(user, roleId);
			return Message.success();
		} catch (ServiceException | NoSuchAlgorithmException e) {
			return Message.error(e);
		}
	}

	@LogAnno(LogDescriptionEnum.USER_ALTER)
	@PermissionAnno("userModule")
	@RequestMapping(value = "update.do", method = RequestMethod.POST)
	@ResponseBody
	public Message updateUser(User user, Long roleId) {
		try {
			userService.updateUser(user, roleId);
			return Message.success();
		} catch (ServiceException e) {
			return Message.error(e);
		}
	}

	@LogAnno(LogDescriptionEnum.USER_DELETE)
	@PermissionAnno("userModule")
	@RequestMapping(value = "delete.do", method = RequestMethod.POST)
	@ResponseBody
	public Message deleteUser(Long id) {
		try {
			userService.deleteUserById(id);
			return Message.success();
		} catch (ServiceException e) {
			return Message.error(e);
		}
	}

	@LogAnno(LogDescriptionEnum.PWD_RESET)
	@PermissionAnno("userModule")
	@RequestMapping(value = "reset.do", method = RequestMethod.POST)
	@ResponseBody
	public Message resetPassword(User user) {
		try {
			user.setPassword("123456");
			userService.updatePassword(user);
			return Message.success();
		} catch (ServiceException | NoSuchAlgorithmException e) {
			return Message.error(e);
		}
	}

	@LogAnno(LogDescriptionEnum.PWD_ALTER)
	@PermissionAnno("userModule")
	@RequestMapping(value = "updatePwd.do", method = RequestMethod.POST)
	@ResponseBody
	public Message updatePassword(User user, String oldPwd) {
		try {
			User u = userService.getUserById(user.getId());
			String up=u.getPassword();
			String op=MD5Util.md5Encode(oldPwd);
			if (!up.equals(op)) {
				return Message.error("oldPwdError");
			}
			userService.updatePassword(user);
			return Message.success();
		} catch (ServiceException | NoSuchAlgorithmException e) {
			return Message.error(e);
		}
	}

	@RequestMapping("isExist.do")
	@ResponseBody
	public Boolean isUserExist(@ModelAttribute User user) {
		User bean = userService.getUserByAccount(user.getAccount());
		return bean != null && user.getId() != bean.getId();
	}

	@PermissionAnno("userModule")
	@RequestMapping(value = "ajaxFindForPage.do")
	@ResponseBody
	public GridPage<User> ajaxFindUsersForPage(@ModelAttribute User user, @ModelAttribute Page page) {
		GridPage<User> gridPage = userService.findUsersForPage(user, page);
		return gridPage;
	}

	@PermissionAnno("userModule")
	@RequestMapping(value = "findForPage.do")
	@ResponseBody
	public String findUsersForPage(User user, Page page, ModelMap modelMap) {
		modelMap.put("user", user);
		modelMap.put("gridPage", userService.findUsersForPage(user, page));
		return "normal/user/userList.jsp";
	}

	@RequestMapping(value = "findAllUsers.do")
	@ResponseBody
	public Object findAllUsers() {
		List<Object> list = new ArrayList<Object>();
		for (User user : userService.findAllUsers()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("label", user.getAccount());
			map.put("value", user.getId());
			list.add(map);
		}
		return list;
	}

}
