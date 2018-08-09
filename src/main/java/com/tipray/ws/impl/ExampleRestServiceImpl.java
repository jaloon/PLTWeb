package com.tipray.ws.impl;

import java.io.File;

import javax.annotation.Resource;
import javax.ws.rs.core.Response;
import javax.xml.ws.WebServiceContext;

import org.springframework.stereotype.Component;

import com.tipray.ws.ExampleRestService;

@Component("exampleRestService")
public class ExampleRestServiceImpl implements ExampleRestService {
	@Resource
	private WebServiceContext context;
	
	@Override
	public String getInfo(String params) throws Exception {
		System.out.println(params);
		return "success";
	}

	@Override
	public Response downloadFile(String params) throws Exception {
		String file = "test";
		String path = null;
		File downloadFile = new File(path);
		if(downloadFile.exists()){
			return Response.ok(downloadFile)
					.header("Content-Disposition", "attachment; filename="+ file)
					.header("Content-Length", Long.toString(downloadFile.length())).build();
		}
		return null;
	}


	@Override
	public void uploadFile(String params, File file)
			throws Exception {
		
	}
}
