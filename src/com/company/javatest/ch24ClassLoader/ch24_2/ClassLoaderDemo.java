package com.company.javatest.ch24ClassLoader.ch24_2;

/**
 * 打印ClassLoader
 */
public class ClassLoaderDemo {
    public static void main(String[] args) {
        test2();
    }

    /**
     * 打印委派链上的类加载器
     */
    private static void test1() {
        ClassLoader cl = ClassLoaderDemo.class.getClassLoader();
        while (cl != null) {//打印所有的类加载器
            System.out.println(cl.getClass().getName());
            cl = cl.getParent();
            //sun.misc.Launcher$AppClassLoader
            //sun.misc.Launcher$ExtClassLoader
        }
        System.out.println(String.class.getClassLoader());//BootstrapClassLoader会返回null
        System.out.println(ClassLoader.getSystemClassLoader());
    }

    /**
     * classLoader.loadClass方法
     * 和Class.forName不同在于：不会加载静态语句
     */
    private static void test2() {
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        try {
            Class<?> cls = cl.loadClass("java.util.ArrayList");
            ClassLoader actualLoader = cls.getClassLoader();
            System.out.println(actualLoader);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
