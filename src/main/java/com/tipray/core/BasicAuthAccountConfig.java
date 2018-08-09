package com.tipray.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * HTTP基本认证账户配置
 *
 * @author chenlong
 * @version 1.0 2018-08-02
 */
public class BasicAuthAccountConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(BasicAuthAccountConfig.class);
    private static final List<String> ACCOUNT_LIST = new ArrayList<>();

    /**
     * 校验基本认证账户
     * @param account 基本认证账户
     * @return
     */
    public static boolean checkAccount(String account) {
        if (account == null || account.trim().isEmpty()) {
            return false;
        }
        synchronized (ACCOUNT_LIST) {
            if (ACCOUNT_LIST.contains(account)) {
                return true;
            }
        }
        return false;
    }

    public static List<String> getAccountList() {
        return ACCOUNT_LIST;
    }

    /**
     * 更新基本认证账户列表
     *
     * @return
     */
    public static boolean upAccountList(File file) {
        if (file.exists()) {
            synchronized (ACCOUNT_LIST) {
                ACCOUNT_LIST.clear();
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String mac = null;
                    while ((mac = br.readLine()) != null) { // 使用readLine方法，一次读一行
                        ACCOUNT_LIST.add(mac);
                    }
                    return true;
                } catch (Exception e) {
                    LOGGER.error("更新基本认证账户列表异常！", e);
                }
            }
        }
        return false;
    }
}
