package com.company.javatest.ch24ClassLoader.ch24_2;

/**
 * ClassLoader.loadClass：不加载静态代码块
 * Class.forName：默认加载静态代码块，使用重载方法传入false，则不执行
 */
public class CLInitDemo {
    public static class Hello {
        static {
            System.out.println("hello");
        }
    }

    public static void main(String[] args) {
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        String className = CLInitDemo.class.getName() + "$Hello";
        try {
            cl.loadClass(className);//Hello的静态代码块未执行，其内部传入了false参数
//            Class.forName(className);//Hello的静态代码块被执行
            Class.forName(className, true, cl);//Hello的静态代码块被执行
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
