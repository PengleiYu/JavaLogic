package com.company.javatest.ch24ClassLoader.ch24_5;

import com.company.javatest.ch24ClassLoader.ch24_4.MyClassLoader;

import java.io.File;

/**
 * 动态部署： 在程序运行期间，动态更换类对象
 * <p>
 * Demo执行步骤：
 * 1，创建多个版本的HelloImpl的class文件：
 * 1.1 新建HelloImpl实现IHelloService，然后编译到指定文件夹
 * javac -d data/classloader/ src/com/company/javatest/ch24ClassLoader/ch24_5/IHelloService.java src/com/company/javatest/ch24ClassLoader/ch24_5/HelloImpl.java
 * 1.2 将HelloImpl.class重命名为HelloImpl_origin.class
 * 1.3 修改HelloImpl的实现，然后再次编译，并重命名为新版本HelloImpl_revised.class
 * 1.4 生成足够多的实现类版本
 * 2，运行demo
 * 3，将各个版本的实现类复制为HelloImpl.class文件
 * 4，实现类会被动态加载并执行
 */
public class HotDeployDemo {
    private static final String CLASS_NAME = "com.company.javatest.ch24ClassLoader.ch24_5.HelloImpl";
    private static final String FILE_NAME = "data/classloader/" + CLASS_NAME.replaceAll("\\.", "/") + ".class";
    private static volatile IHelloService sIHelloService;

    public static void main(String[] args) {
        monitor();
        client();
    }

    private static void reloadHelloService() {
        sIHelloService = createHelloService();
    }

    private static void monitor() {
        Thread t = new Thread() {
            private long lastModified = new File(FILE_NAME).lastModified();

            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(1000);
                        long now = new File(FILE_NAME).lastModified();
                        if (now != lastModified) {
                            lastModified = now;
                            reloadHelloService();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }

    private static void client() {
        Thread t = new Thread(() -> {
            try {
                while (true) {
                    IHelloService helloService = getHelloService();
                    helloService.sayHello();
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t.start();
    }

    private static IHelloService getHelloService() {
        if (sIHelloService != null) return sIHelloService;
        synchronized (HotDeployDemo.class) {
            if (sIHelloService == null) sIHelloService = createHelloService();
            return sIHelloService;
        }
    }

    private static IHelloService createHelloService() {
        try {
            MyClassLoader cl = new MyClassLoader();
            Class<?> aClass = cl.loadClass(CLASS_NAME);
            if (aClass != null) return (IHelloService) aClass.newInstance();
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
