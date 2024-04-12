package com.crazy.rain.usercenter.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Set;

/**
 * @ClassName: JsonUtils
 * @Description: json解析
 * @author: CrazyRain
 * @date: 2024/4/2 20:41
 */

public class JsonUtils {
    public static Set<String> getJson(String userTags) {
        Gson gson = new Gson();
        Set<String> tempTagNameS = gson.fromJson(userTags, new TypeToken<Set<String>>() {
        }.getType());
        return tempTagNameS;
    }

    public static List<String> getJsonToList(String userTags) {
        Gson gson = new Gson();
        List<String> tempTagNameS = gson.fromJson(userTags, new TypeToken<List<String>>() {
        }.getType());
        return tempTagNameS;
    }
}
