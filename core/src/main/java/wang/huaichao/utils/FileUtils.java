package wang.huaichao.utils;

import java.io.*;

/**
 * Created by Administrator on 2015/5/20.
 */
public class FileUtils {
    public static String readFromClassPath(String path) throws IOException {
        return readFromClassPath(path, "utf-8");
    }

    public static String readFromClassPath(String path, String encoding) throws IOException {
        InputStream is = FileUtils.class.getResourceAsStream(path);
        return read(is);
    }

    public static String read(String path) throws IOException {
        return read(new FileInputStream(path));
    }

    public static String read(InputStream is) throws IOException {
        return read(is, "utf-8");
    }

    public static String read(InputStream is, String encoding) throws IOException {
        ByteArrayOutputStream baos = readRaw(is);
        return baos.toString(encoding);
    }

    public static ByteArrayOutputStream readRaw(InputStream is) throws IOException {
        int len;
        final byte[] buff = new byte[2048];
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        while ((len = is.read(buff)) != -1) {
            baos.write(buff, 0, len);
        }
        is.close();
        return baos;
    }

    public static void write(byte[] bytes, String file) throws IOException {
        write(new ByteArrayInputStream(bytes), new FileOutputStream(file));
    }

    public static void write(InputStream is, File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        write(is, fos);
    }

    public static void write(InputStream is, OutputStream os) throws IOException {
        int len;
        byte[] buffer = new byte[2048];
        while ((len = is.read(buffer)) != -1) {
            os.write(buffer, 0, len);
        }
        os.close();
        is.close();
    }

}
