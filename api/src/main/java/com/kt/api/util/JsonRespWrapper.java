package com.kt.api.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.validation.FieldError;

public class JsonRespWrapper extends HashMap<String, Object> {

    public static JsonRespWrapper uploadSuccess(String url) {
        return json().json("err", "").json("msg", url);
    }

    public static JsonRespWrapper uploadError(String message) {
        return json().json("err", message).json("msg", "");
    }


    public static JsonRespWrapper uploadSuccess(String name, String url) {
        return success().json("name", name).json("url", url);
    }

    public static JsonRespWrapper denied() {
        return error("没有权限.");
    }

    /**
     * 只返回成功标志
     * @return
     */
    public static JsonRespWrapper success() {
        return json(true);
    }
    
    public static JsonRespWrapper success(String message) {
        return json("successMessage",message,true);
    }
    
    /**
     * 提示成功消息
     * @return
     */
    public static JsonRespWrapper successAlert(String message) {
        return success(message, null);
    }

    /**
     * 直接跳转
     * @param redirectUrl 可以是http打头，或是绝对路径以/开头
     * @return
     */
    public static JsonRespWrapper successJump(String redirectUrl) {
        return success(null, redirectUrl);
    }

    /**
     * 成功，提示且跳转，如果message为空，则直接跳转。如果redirectUrl为空，则不跳转
     * @param message 提示信息
     * @param redirectUrl 可以是http打头，或是绝对路径以/开头
     * @return
     */
    public static JsonRespWrapper success(String message, String redirectUrl) {
        return success().json("message", message).json("url", redirectUrl);
    }

    /**
     * 成功，且传入键值对
     * @param key
     * @param value
     * @return
     */
    public static JsonRespWrapper success(List<String> key, List<Object> value) {
        return json(key, value, true);
    }

    public static JsonRespWrapper error(String errorMessage) {
        return json("errorMessage", errorMessage, false);
    }

    public static JsonRespWrapper error(Map<String, String> errors) {
        return json("errors", errors, false);
    }

   /* public static JsonRespWrapper errorField(String field, String msg) {
        return error(Lists.newArrayList(new FieldError(field, field, msg)));
    }

    public static JsonRespWrapper error(List<FieldError> errors) {
        Map<String, String> er = Maps.newLinkedHashMap();
        for (FieldError fe : errors) {
            er.put(fe.getField(), fe.getDefaultMessage());
        }
        return json("errors", er, false);
    }*/

    public JsonRespWrapper json(String key, Object value) {
        this.put(key, value);
        return this;
    }

    public JsonRespWrapper json(List<String> keys, List<Object> values) {
        if (keys == null) return this;
        for (int i = 0; i < keys.size(); i++) {
            this.put(keys.get(i), values.get(i));
        }
        return this;
    }

    public static JsonRespWrapper json() {
        JsonRespWrapper map = new JsonRespWrapper();
        return map;
    }

    public static JsonRespWrapper json(boolean success) {
        return json().json("success", success);
    }

    public static JsonRespWrapper json(String key, Object value, boolean success) {
        return json(success).json(key, value);
    }

    public static JsonRespWrapper json(List<String> keys, List<Object> values, boolean success) {
        JsonRespWrapper obj = json(success);
        return obj.json(keys, values);
    }

}
