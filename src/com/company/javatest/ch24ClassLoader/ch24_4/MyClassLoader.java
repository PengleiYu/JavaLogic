package com.company.javatest.ch24ClassLoader.ch24_4;

import java.io.IOException;

/**
 * 自定义classLoader
 */
public class MyClassLoader extends ClassLoader {
    // 类文件所在文件夹
    private static final String BASE_DIR = "data/classloader/";

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String fileName = name.replaceAll("\\.", "/");
        fileName = BASE_DIR + fileName + ".class";
        try {
            byte[] bytes = BinaryUtils.redFileToBinary(fileName);
            System.out.println("findClass: " + bytes.hashCode());
            return defineClass(name, bytes, 0, bytes.length);
        } catch (IOException e) {
            throw new ClassNotFoundException("failed to load class " + name, e);
        }
    }
}
