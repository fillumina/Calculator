package com.fillumina.calculator.util;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class Mapper {
    /**
     * Helper to easily create a map.
     * <code><pre>
     * Map<String,Double> map = Mapper.create("a", 1.2, "b", 2.3, "c", 3.4);
     * </pre></code>
     * @param <T>     the type of the values in the map
     * @param objects the objects (must conform to types in pairs)
     * @return        an {@link HashMap} filled with the given couples.
     */
    @SuppressWarnings("unchecked")
    public static <T> Map<String, T> create(Object... objects) {
        final Map<String,T> map = new HashMap<>();
        for (int i=0; i<objects.length; i+=2) {
            map.put((String)objects[i], (T) objects[i+1]);
        }
        return map;
    }
}
