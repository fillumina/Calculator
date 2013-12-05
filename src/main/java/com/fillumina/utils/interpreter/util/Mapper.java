package com.fillumina.utils.interpreter.util;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class Mapper {
    @SuppressWarnings("unchecked")
    public static <T> Map<String, T> create(Object... objects) {
        final Map<String,T> map = new HashMap<>();
        for (int i=0; i<objects.length; i+=2) {
            map.put((String)objects[i], (T) objects[i+1]);
        }
        return map;
    }
}
