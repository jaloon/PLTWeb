package com.tipray.test;

import com.tipray.util.OkHttpUtil;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author chenlong
 * @version 1.0 2018-07-20
 */
public class HttpTest {
    @Test
    public void test() throws IOException {
        // String url = "http://192.168.7.20:3002/manage/session/login.do";
        String url = "http://127.0.0.1:8080/manage/session/login.do";
        // String url = "https://127.0.0.1:8443/manage/session/login.do";
        // String param = "account=admin&password=admin";
        // String param = "{\"account\":\"admin\",\"password\":\"admin\"}";
        // System.out.println(OkHttpUtil.post(url, param));
        RequestBody requestBody = new FormBody.Builder()
                // .addEncoded("account", "{\"biz\":\"realtime\",\"bizType\":\"cancel\",\"session\":123,\"user\":{\"id\":1,\"account\":\"abc\",\"name\":\"甲乙丙\"}}")
                .add("account", "admin")
                .add("password", "admin")
                .build();
        System.out.println(OkHttpUtil.post(url, requestBody));
        // url = "http://127.0.0.1:8080/api/getAppInfo.do";
        // url = "https://127.0.0.1:8443/api/getAppInfo.do";
        // System.out.println(OkHttpUtil.get(url, param));
        // url = "https://127.0.0.1:8443/center/getCenterList.do";
        // param = "uuid=865712021058189&appid=pltone_e_seal&system=Android&model=vivo X3L&current_version=1.0.1";
        // System.out.println(OkHttpUtil.post(url, param));
        url = "http://192.168.7.20:3002/manage/device/findByType.do?deviceType=1";
        // System.out.println(get(url));
        url = "http://192.168.7.20:3002/manage/file/upload";
        File file = new File("d:/UDPServer.java");
        MultipartBody multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("biz", "4")//添加键值对参数
                .addFormDataPart("uploadFile", file.getName(), RequestBody.create(OkHttpUtil.MEDIA_TYPE_MARKDOWN, file))//添加文件
                .build();
        // System.out.println(OkHttpUtil.post(url, multipartBody));
        System.out.println();
        url = "http://192.168.7.20:3002/manage/file/download/driver_setup.rar";
        url = "http://127.0.0.1:8080/manage/file/download/driver_setup.rar";
        OkHttpUtil.download(url, "d:/ftp");
    }
}
