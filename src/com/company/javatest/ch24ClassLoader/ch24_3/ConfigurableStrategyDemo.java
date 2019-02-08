package com.company.javatest.ch24ClassLoader.ch24_3;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 手动加载class的场景
 * 使用配置改变程序行为，策略模式
 */
public class ConfigurableStrategyDemo {
    private static IService createService() throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        Properties properties = new Properties();
        String fileName = new File("").getAbsolutePath() + "/data/config.properties";
        properties.load(new FileInputStream(fileName));
        String className = ConfigurableStrategyDemo.class.getPackage().getName() + "." + properties.getProperty("service");
        Class<?> aClass = Class.forName(className);
        return (IService) aClass.newInstance();
    }

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        IService service = createService();
        service.action();
    }
}
