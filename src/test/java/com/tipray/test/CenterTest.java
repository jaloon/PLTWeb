package com.tipray.test;

import com.tipray.bean.AppInfo;
import com.tipray.bean.Center;
import com.tipray.core.exception.ServiceException;
import com.tipray.service.AppdevService;
import com.tipray.service.CenterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml" })
public class CenterTest {
	@Resource
	private CenterService centerService;
	@Resource
    private AppdevService appdevService;

    @Test
	public void appinfo() {
        AppInfo appInfo = appdevService.getCenterWebAddr(3L);
        if (appInfo == null) {
            appInfo = new AppInfo();
        }
        System.out.println(appInfo);
    }

	@Test
	public void addCenter() {
		Center center = new Center();
		center.setName("广西中心");
		center.setIp("192.126.3.223");
		center.setTcpPort(12345);
		center.setPhone("123456789");
		center.setAddress("广西南宁");
		
		Center center1 = new Center();
		center1.setName("福建中心");
		center1.setIp("192.126.1.112");
		center1.setTcpPort(25005);
		center1.setPhone("1234567892");
		center1.setAddress("福建厦门");
		
		Center center2 = new Center();
		center2.setName("江苏中心");
		center2.setIp("192.168.4.5");
		center2.setTcpPort(38045);
		center2.setPhone("123456789");
		center2.setAddress("江苏南京");
		
		Center center3 = new Center();
		center3.setName("广东中心");
		center3.setIp("192.168.3.223");
		center3.setTcpPort(12345);
		center3.setPhone("123456789");
		center3.setAddress("广东广州");
		try {
			centerService.addCenter(center);
			centerService.addCenter(center1);
			centerService.addCenter(center2);
			centerService.addCenter(center3);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void updateCenter() {
		Center center = new Center();
		center.setId(4l);
		center.setName("广东中心1");
		center.setIp("192.126.3.188");
		center.setTcpPort(14568);
		center.setPhone("1234566789");
		center.setAddress("广东广州白云区");
		try {
			centerService.updateCenter(center);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void findAllCenters() {
		List<Center> clist = centerService.findAllCenters();
		System.out.println();
		for (Center center : clist) {
			System.out.println(center.getId() + "：" + center);
		}
		System.out.println();
	}

	@Test
	public void deleteCenter() {
		try {
			centerService.deleteCenterById(5l);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getById() {
		System.out.println(centerService.getCenterById(3l));
	}

	@Test
	public void getByName() {
		System.out.println(centerService.getByName("广东中心"));
	}
}
