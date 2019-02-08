package com.company.javatest.ch24ClassLoader.ch24_4;

import java.io.*;

public class BinaryUtils {
    public static void copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bytes = new byte[1024 * 4];
        int len;
        while ((len = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, len);
        }
    }

    public static byte[] redFileToBinary(String fileName) throws IOException {
        InputStream inputStream = new FileInputStream(fileName);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            copy(inputStream, outputStream);
            outputStream.close();
            return outputStream.toByteArray();
        } finally {
            inputStream.close();
        }
    }
}
