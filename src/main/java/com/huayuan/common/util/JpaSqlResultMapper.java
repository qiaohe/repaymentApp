package com.huayuan.common.util;

import javax.persistence.Query;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Johnson on 5/18/14.
 */
public class JpaSqlResultMapper {
    public static <T> List<T> list(Query q, Class<T> clazz)
            throws IllegalArgumentException {
        Constructor<?> ctor = clazz.getDeclaredConstructors()[0];
        List<T> result = new ArrayList<T>();
        @SuppressWarnings("unchecked")
        List<Object[]> list = q.getResultList();
        for (Object obj : list) {
            if (ctor.getParameterTypes().length == 1) {
                obj = new Object[]{obj};
            }
            createAndAddBean(ctor, (Object[]) obj, result);
        }
        return result;
    }

    private static <T> void createAndAddBean(
            Constructor<?> ctor, Object[] args, List<T> result) {
        try {
            @SuppressWarnings("unchecked")
            T obj = (T) ctor.newInstance(args);
            result.add(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}