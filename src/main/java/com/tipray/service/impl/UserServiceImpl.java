package com.tipray.service.impl;

import com.tipray.bean.GridPage;
import com.tipray.bean.Page;
import com.tipray.bean.User;
import com.tipray.core.exception.ServiceException;
import com.tipray.dao.RoleDao;
import com.tipray.dao.UserDao;
import com.tipray.service.UserService;
import com.tipray.util.MD5Util;
import com.tipray.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Transactional
@Service("userService")
public class UserServiceImpl implements UserService {
	@Resource
	private UserDao userDao;
	@Resource
	private RoleDao roleDao;

	@Override
	public User addUser(User user, Long roleId) throws ServiceException, NoSuchAlgorithmException {
		if (user != null) {
			String account = user.getAccount();
			if (account == null || account.trim().isEmpty()) {
				throw new IllegalArgumentException("账号为空！");
			}
			user.setRole(roleDao.getById(roleId));
			String password = MD5Util.md5Encode("123456");
			if (StringUtil.isNotEmpty(user.getPassword())) {
				password = MD5Util.md5Encode(user.getPassword());
			}
			user.setPassword(password);
			Integer count = userDao.countByAccount(account);
			if (count == null || count == 0) {
				userDao.add(user);
			} else if (count == 1) {
				userDao.updateByAccount(user);
			} else {
				userDao.deleteByAccount(account);
				userDao.add(user);
			}
		}
		return user;
	}

	@Override
	public User updateUser(User user, Long roleId) throws ServiceException {
		if (user != null) {
			user.setRole(roleDao.getById(roleId));
			userDao.update(user);
		}
		return user;
	}

	@Override
	public void updatePassword(User user) throws ServiceException, NoSuchAlgorithmException{
		if (user != null) {
			if (StringUtil.isNotEmpty(user.getPassword())) {
				String password = MD5Util.md5Encode(user.getPassword());
				user.setPassword(password);
				userDao.updatePassword(user);
			}
		}

	}

	@Override
	public void deleteUserById(Long id) throws ServiceException{
		userDao.delete(id);
	}

	@Override
	public User getUserById(Long id) {
		return id == null ? null : userDao.getById(id);
	}

	@Override
	public User getUserByAccount(String account) {
		return StringUtil.isEmpty(account) ? null : userDao.getByAccount(account);
	}

	@Override
	public User getByIDCard(String identityCard) {
		return StringUtil.isEmpty(identityCard) ? null : userDao.getByIDCard(identityCard);
	}

	@Override
	public List<User> findByAccount(String account) {
		return StringUtil.isEmpty(account) ? null : userDao.findByAccount(account);
	}

	@Override
	public List<User> findByName(String name) {
		return StringUtil.isEmpty(name) ? null : userDao.findByName(name);
	}

	@Override
	public List<User> findAllUsers() {
		return userDao.findAll();
	}

	@Override
	public long countUser(User user) {
		return userDao.count(user);
	}

	@Override
	public List<User> findByPage(User user, Page page) {
		return userDao.findByPage(user, page);
	}

	@Override
	public GridPage<User> findUsersForPage(User user, Page page) {
		long records = countUser(user);
		List<User> list = findByPage(user, page);
		return new GridPage<User>(list, records, page.getPageId(), page.getRows(), list.size(), user);
	}
}
