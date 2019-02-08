package com.company.javatest.ch24ClassLoader.ch24_4;

/**
 * 目标：不同的类加载器加载相同的一个class文件，生成不同的class对象
 * <p>
 * 该Demo执行流程：
 * 1，在本文件夹下创建HelloService类
 * 2，编译HelloService,将其放入指定文件夹： javac -d ~/IdeaProjects/JavaLogic/data/classloader/ HelloService.java
 * 3，删除HelloService类。重要，若未删除，编译期会在out文件夹生成class文件，默认系统类加载器即可加载，如此自定义类加载器就不会执行了
 * 4，执行
 */
public class MyClassLoaderDemo {
    public static void main(String[] args) throws ClassNotFoundException {
        String pkgName = MyClassLoaderDemo.class.getPackage().getName();
        String className = pkgName + ".HelloService";

        MyClassLoader cl1 = new MyClassLoader();
        Class<?> class1 = cl1.loadClass(className);
        MyClassLoader cl2 = new MyClassLoader();
        Class<?> class2 = cl2.loadClass(className);

        System.out.println(class1 == class2);
    }
}
