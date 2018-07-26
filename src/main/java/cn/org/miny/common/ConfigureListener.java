package cn.org.miny.common;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 初始化配置listener
 * Created by limingyang on 2018/7/19.
 */
public class ConfigureListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Configure.init();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

}
