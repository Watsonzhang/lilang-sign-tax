package com.zhangwei.until;

import java.net.URI;
import java.net.URL;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 对象工具类，包括判空、克隆、序列化等操作
 */
public class ObjectUtil {

    /**
     * 是否为基本类型，包括包装类型和非包装类型
     *
     * @param object 被检查对象
     * @return 是否为基本类型
     */
    public static boolean isBasicType(Object object) {
        return isBasicType(object.getClass());
    }

    /**
     * 是否为包装类型
     *
     * @param clazz 类
     * @return 是否为包装类型
     */
    public static boolean isPrimitiveWrapper(Class<?> clazz) {
        if (null == clazz) {
            return false;
        }
        return BasicType.wrapperPrimitiveMap.containsKey(clazz);
    }

    /**
     * 是否为基本类型（包括包装类和原始类）
     *
     * @param clazz 类
     * @return 是否为基本类型
     */
    public static boolean isBasicType(Class<?> clazz) {
        if (null == clazz) {
            return false;
        }
        return (clazz.isPrimitive() || isPrimitiveWrapper(clazz));
    }

    /**
     * 是否简单值类型或简单值类型的数组<br>
     * 包括：原始类型,、String、other CharSequence, a Number, a Date, a URI, a URL, a Locale or a Class及其数组
     *
     * @param clazz 属性类
     * @return 是否简单值类型或简单值类型的数组
     */
    public static boolean isSimpleTypeOrArray(Class<?> clazz) {
        if (null == clazz) {
            return false;
        }
        return isSimpleValueType(clazz) || (clazz.isArray() && isSimpleValueType(clazz.getComponentType()));
    }

    /**
     * 是否为简单值类型<br>
     * 包括：
     * <pre>
     *     原始类型
     *     String、other CharSequence
     *     Number
     *     Date
     *     URI
     *     URL
     *     Locale
     *     Class
     * </pre>
     *
     * @param clazz 类
     * @return 是否为简单值类型
     */
    public static boolean isSimpleValueType(Class<?> clazz) {
        if(clazz == null) {
            return false;
        }
        return isBasicType(clazz) //
                || clazz.isEnum() //
                || CharSequence.class.isAssignableFrom(clazz) //
                || Number.class.isAssignableFrom(clazz) //
                || Date.class.isAssignableFrom(clazz) //
                || clazz.equals(URI.class) //
                || clazz.equals(URL.class) //
                || clazz.equals(Locale.class) //
                || clazz.equals(Class.class)//
                // jdk8 date object
                || TemporalAccessor.class.isAssignableFrom(clazz); //
    }

    /**
     * @param clazz 类
     * @return 是否为简单值类型
     * 判断是否是基本数据类型、String类型、other CharSequence、Number、Date
     */
    public static boolean isSimpleTypeOrString(Class<?> clazz) {
        if(clazz == null) {
            return false;
        }
        return isBasicType(clazz) //
                || clazz.isEnum() //
                || CharSequence.class.isAssignableFrom(clazz) //
                || Number.class.isAssignableFrom(clazz) //
                || Date.class.isAssignableFrom(clazz);
    }

    enum BasicType {
        BYTE, SHORT, INT, INTEGER, LONG, DOUBLE, FLOAT, BOOLEAN, CHAR, CHARACTER, STRING;

        /**
         * 包装类型为Key，原始类型为Value，例如： Integer.class =》 int.class.
         */
        public static final Map<Class<?>, Class<?>> wrapperPrimitiveMap = new ConcurrentHashMap<>(8);
        /**
         * 原始类型为Key，包装类型为Value，例如： int.class =》 Integer.class.
         */
        public static final Map<Class<?>, Class<?>> primitiveWrapperMap = new ConcurrentHashMap<>(8);

        static {
            wrapperPrimitiveMap.put(Boolean.class, boolean.class);
            wrapperPrimitiveMap.put(Byte.class, byte.class);
            wrapperPrimitiveMap.put(Character.class, char.class);
            wrapperPrimitiveMap.put(Double.class, double.class);
            wrapperPrimitiveMap.put(Float.class, float.class);
            wrapperPrimitiveMap.put(Integer.class, int.class);
            wrapperPrimitiveMap.put(Long.class, long.class);
            wrapperPrimitiveMap.put(Short.class, short.class);

            for (Map.Entry<Class<?>, Class<?>> entry : wrapperPrimitiveMap.entrySet()) {
                primitiveWrapperMap.put(entry.getValue(), entry.getKey());
            }
        }

        /**
         * 原始类转为包装类，非原始类返回原类
         *
         * @param clazz 原始类
         * @return 包装类
         */
        public static Class<?> wrap(Class<?> clazz) {
            if (null == clazz || false == clazz.isPrimitive()) {
                return clazz;
            }
            Class<?> result = primitiveWrapperMap.get(clazz);
            return (null == result) ? clazz : result;
        }

        /**
         * 包装类转为原始类，非包装类返回原类
         *
         * @param clazz 包装类
         * @return 原始类
         */
        public static Class<?> unWrap(Class<?> clazz) {
            if (null == clazz || clazz.isPrimitive()) {
                return clazz;
            }
            Class<?> result = wrapperPrimitiveMap.get(clazz);
            return (null == result) ? clazz : result;
        }
    }


}

