package wang.huaichao.utils.velocity;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 1/5/2017.
 */
public class EscapeToolCustom {
    public static Map<Character, String> MapCharToEntity = new HashMap<>();

    static {
        MapCharToEntity.put('"', "quot");
        MapCharToEntity.put('\'', "apos");
        MapCharToEntity.put('<', "lt");
        MapCharToEntity.put('>', "gt");
        MapCharToEntity.put('&', "amp");
    }

    public String xml(Object string) {
        if (string == null) return null;
        char[] chars = string.toString().toCharArray();
        StringWriter sw = new StringWriter();
        for (char c : chars) {
            if (MapCharToEntity.containsKey(c)) {
                sw.write('&');
                sw.write(MapCharToEntity.get(c));
                sw.write(';');
            } else {
                sw.write(c);
            }
        }
        return sw.toString();
    }
}
