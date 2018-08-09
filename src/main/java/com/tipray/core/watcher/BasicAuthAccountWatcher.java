package com.tipray.core.watcher;

import com.tipray.core.BasicAuthAccountConfig;

/**
 * 中心配置文件监听
 *
 * @author chenlong
 * @version 1.0 2018-07-04
 */
public class BasicAuthAccountWatcher extends FileWatcher {
    private static final String BASIC_AUTH_FILE_NAME = "basic-auth";

    public BasicAuthAccountWatcher() {
        super(BASIC_AUTH_FILE_NAME);
    }

    public BasicAuthAccountWatcher(String filename) {
        super(filename);
    }

    @Override
    protected void doOnChange() {
        BasicAuthAccountConfig.upAccountList(file);
    }
}
