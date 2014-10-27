package com.fillumina.calculator.util;

import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class MapperTest {

    @Test
    public void shouldCreateAnEmptyMap() {
        final Map<String, Double> map = Mapper.create();

        assertTrue(map.isEmpty());
    }

    @Test
    public void shouldCreateASingleValueMap() {
        final Map<String, Double> map = Mapper.create("one", 1.0);

        assertEquals(1, map.size());
        assertEquals(1.0, map.get("one"), 0.1);
    }

    @Test
    public void shouldCreateATwoValuesMap() {
        final Map<String, Double> map = Mapper.create("one", 1.0, "two", 2.0);

        assertEquals(2, map.size());
        assertEquals(1.0, map.get("one"), 0.1);
        assertEquals(2.0, map.get("two"), 0.1);
    }

    @Test(expected=ArrayIndexOutOfBoundsException.class)
    public void shouldFireAnExceptionIfNoValue() {
        final Map<String, Double> map = Mapper.create("one", 1.0, "two");

        assertEquals(2, map.size());
        assertEquals(1.0, map.get("one"), 0.1);
        assertEquals(2.0, map.get("two"), 0.1);
    }

    @Test(expected=NullPointerException.class)
    public void shouldFireAnExceptionIfNoKey() {
        final Map<String, Double> map = Mapper.create("one", 1.0, null, 2.0);

        assertEquals(2, map.size());
        assertEquals(1.0, map.get("one"), 0.1);
        assertEquals(2.0, map.get("two"), 0.1);
    }
}
