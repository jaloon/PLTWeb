package com.tipray.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tipray.bean.Session;
import com.tipray.service.SessionService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml" })
public class SessionTest {
	@Resource
	private SessionService sessionService;

	@Test
	public void getSessionByUser() {
		Session session = sessionService.getSessionByUser(5l);
		System.out.println();
		System.out.println(session);
		System.out.println();
	}
}
