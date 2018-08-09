package com.tipray.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tipray.bean.Permission;
import com.tipray.init.impl.PermissionXmlInit;
import com.tipray.service.PermissionService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml" })
public class PermissionTest {
	@Resource
	private PermissionService permissionService;

	@Test
	public void init() {
		try {
			new PermissionXmlInit().init();
		} catch (Exception e) {
			e.printStackTrace();
		}
		;
	}

	@Test
	public void add() {
		Permission p = new Permission();
		p.setCname("人员管理");
		p.setEname("pem");
		p.setPermissionType(1);
		p.setEnable(true);
		p.setParentId(0l);
		p.setDescription("");
		p.setIndexId(2l);
		p.setGridUrl("");
		permissionService.addPermission(p);
	}

	@Test
	public void findAllPermissions() {
		List<Permission> plist = permissionService.findAllPermissions();
		System.out.println();
		for (Permission permission : plist) {
			System.out.println(permission.getId() + "：" + permission);
		}
		System.out.println();
	}
}
