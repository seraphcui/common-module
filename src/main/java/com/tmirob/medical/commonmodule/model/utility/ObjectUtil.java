/*
 * Copyright (c) 2017. All rights reserved by Taimi Robot.
 * Created by yaocui on 17-8-21 下午2:27.
 */

package com.tmirob.medical.commonmodule.model.utility;

import org.springframework.util.ClassUtils;

import java.lang.reflect.Field;

public class ObjectUtil {
    /**
     * 1.null == null
     * 2.null != Object
     * 3.object.equals(other) is true,return
     * true 4.compare all Fields
     */
    public static boolean equals(Object obj, Object other) {
        if (obj == null && other == null) {
            return true;
        }

        if (obj != null && other == null) {
            return false;
        }
        if (obj == null) {
            return false;
        }

        return compareNotNull(obj, other);
    }

    private static boolean compareNotNull(Object obj, Object other) {
        if (obj.equals(other)) {
            return true;
        }
        Class type = obj.getClass();
        if (type != other.getClass()) {
            return false;
        }

        if (ClassUtils.isPrimitiveOrWrapper(type)) {
            return comparePrimitiveType(obj, other);
        }

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object tmp1 = field.get(obj);
                Object tmp2 = field.get(other);
                if (!tmp1.equals(tmp2)) {
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

        }
        return true;
    }

    private static boolean comparePrimitiveType(Object obj, Object other) {
        if (obj.getClass().isPrimitive()) {
            return obj == other;
        } else {
            return obj.equals(other);
        }
    }

}
