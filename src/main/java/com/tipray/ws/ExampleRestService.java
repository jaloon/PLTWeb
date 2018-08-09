package com.tipray.ws;

import java.io.File;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/RSExample")
public interface ExampleRestService {
	@GET
	@Path("/getInfo")
	@Consumes({"application/json"})
	public String getInfo(String params) throws Exception;
	/**
	 * 下载文件
	 */
	@POST
	@Path("/downloadFile")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response downloadFile(String filePath) throws Exception;
	/**
	 * 上传文件
	 */
	@POST
	@Path("/uploadFile/{params}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public void uploadFile(@PathParam("params")String params,File file)throws Exception;
}
