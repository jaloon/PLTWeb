package com.tipray.core.listener;

import com.tipray.core.watcher.BasicAuthAccountWatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 文件变化监听
 *
 * @author chenlong
 * @version 1.0 2018-07-04
 */
public class FileChangeListener implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(FileChangeListener.class);
    private BasicAuthAccountWatcher basicAuthAccountWatcher;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        basicAuthAccountWatcher = new BasicAuthAccountWatcher();
        basicAuthAccountWatcher.start();
        logger.info("basic auth file watchers started.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        basicAuthAccountWatcher.close();
        logger.info("basic auth file watchers stopped.");
    }
}
