package wang.huaichao.utils;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 8/16/2016.
 */
public class StringUtils {
    public static String fromCharset(String str, String charset) {
        try {
            return new String(str.getBytes(charset), "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String replace(String template, Object... args) {
        int founds = 0;
        Pattern ptn = Pattern.compile("\\{ *}");
        Matcher matcher = ptn.matcher(template);
        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            matcher.appendReplacement(sb, args[founds++].toString());
        }

        return sb.toString();
    }

    public static String join(List<String> arr, String sep) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.size(); i++) {
            if (i != 0) sb.append(sep);
            sb.append(arr.get(i));
        }
        return sb.toString();
    }
}
