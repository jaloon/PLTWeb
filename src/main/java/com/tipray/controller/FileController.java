package com.tipray.controller;

import com.tipray.constant.SystemPropertyConst;
import com.tipray.core.base.BaseAction;
import com.tipray.core.exception.FileException;
import com.tipray.util.StringUtil;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * SpringMVC文件上传/下载控制器
 *
 * @author chenlong
 * @version 1.0 2018-02-02
 */
@Controller
@RequestMapping("/manage/file")
public class FileController extends BaseAction {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(FileController.class);

    /**
     * 文件下载
     *
     * @param filename 下载文件名
     */
    // 此处必须写成value = "download/{filename:.+}"。
    // 若写为value = "download/{filename}"，则接收的参数filename若包含特殊字符会从特殊字符处截断
    @RequestMapping(value = "download/{filename:.+}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseEntity<byte[]> download(@PathVariable(value = "filename") String filename) {
        logger.info("downloadUpgradeFile file, filename={}", filename);
        try {
            if (StringUtil.isEmpty(filename)) {
                logger.warn("file name is null!");
                throw new FileException(FileException.FILE_NAME_NULL_EXCEPTION);
            }
            // 服务器文件存放路径
            String downloadFilePath = new StringBuffer().append(SystemPropertyConst.CATALINA_HOME)
                    .append("/download/").append(filename).toString();
            File file = new File(downloadFilePath);
            if (!file.exists()) {
                logger.warn("file is not existed!");
                throw new FileException(FileException.FILE_NOT_FOUND_EXCEPTION);
            }
            // http头信息
            HttpHeaders headers = new HttpHeaders();
            // 下载文件名就是此处设置的filename的值，如果包含中文，浏览器的下载文件名会乱码，
            // 可以采用 filename = URLEncoder.encode(filename,"UTF-8"); 对原文件名编码；
            // 若Content-Disposition中filename的值设置为空字符串""或null，各浏览器会将参数前的最后一段路径作为文件名。
            headers.setContentDispositionFormData("attachment", URLEncoder.encode(filename,"UTF-8"));
            // MediaType:互联网媒介类型 contentType：具体请求中的媒体类型信息
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            // 设为HttpStatus.CREATED时，IE无法下载文件
            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
        } catch (IOException e) {
            logger.error("文件下载失败！", e);
            throw new FileException(FileException.FILE_DOWNLOAD_EXCEPTION, e);
        }
    }

}
