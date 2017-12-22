package com.blog.convert;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 数据类型转换工具，目前没有考虑char（Character）类型。
 *
 * @author lijinzhou
 * @since 2017/12/20 19:10
 */
public final class ConvertUtils {


    /**
     * {@link String}转换为{@link Boolean}类型。
     * 转换不成功时返回defaultValue
     * 字符串的"true"、"false"值（忽略大小写）会转化为boolean类型的true、false值；
     * value为null或者空字符串，会返回defaultValue；
     *
     * @param value        要转换成boolean值的字符串。
     * @param defaultValue 转换不成功时的默认值（可以为null，这种情况下转换不成功时将返回null值）
     */
    private static Boolean toBoolean(String value, Boolean defaultValue) {
        if (value == null || value.length() <= 0) {
            return defaultValue;
        }
        value = value.trim().toLowerCase();
        if (value.length() <= 0) {
            return defaultValue;
        }
        if ("false".equals(value)) {
            return false;
        }
        if ("true".equals(value)) {
            return true;
        }
        return defaultValue;
    }

    /**
     * {@link Object}转换为{@link Boolean}类型。
     * 转换不成功时返回defaultValue
     * value等于null时，返回defaultValue；
     * value为{@link Boolean}类型时，不进行类型转换，直接返回value值；
     * value为{@link String}类型时，参考{@link #toBoolean(String, Boolean)}；
     * 其他情况均返回defaultValue；
     *
     * @param value        要转换成boolean值的对象。
     * @param defaultValue 转换不成功时的默认值（可以为null，这种情况下转换不成功时将返回null值）
     */
    public static Boolean toBoolean(Object value, Boolean defaultValue) {
        if (value == null)
            return defaultValue;
        Class<?> clazz = value.getClass();
        // 最可能出现的类型放在最前面判断
        if (Boolean.class.equals(clazz))
            return (Boolean) value;
        if (String.class.equals(clazz))
            return toBoolean(String.valueOf(value), defaultValue);
        return defaultValue;
    }

    /**
     * {@link String}转换为{@link Integer}类型。
     * <p>转换不成功时返回defaultValue并记录info或者warn日志，不会抛出任何异常。
     * <p>value等于null或者为空字符串，或者在转换过程中发生异常，均返回defaultValue，否则返回转换后的{@link Integer}值。
     *
     * @param value        要转换成{@link Integer}值的字符串。
     * @param defaultValue 转换不成功时的默认值（可以为null，这种情况下转换不成功时将返回null值）。
     * @return
     */
    private static Integer toInteger(String value, Integer defaultValue) {

        if (value == null || value.trim().length() <= 0) {
            return defaultValue;
        }

        // 字符串中包含小数点，Integer.valueOf会报异常，先转换为Double，返回其整数部分
        if (value.indexOf('.') >= 0) {
            try {
                Double d = Double.valueOf(value);
                return d.intValue();
            } catch (Exception e) {
                return defaultValue;
            }
        }

        // 当做整数字符串处理
        try {
            return Integer.valueOf(value);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * {@link Object}转换为{@link Integer}类型。
     * 转换不成功时返回defaultValue
     * value等于null时返回defaultValue；
     * value为{@link Integer}类型时，不进行类型转换，直接返回value值；
     * value为{@link String}类型时，参考{@link #toInteger(String, Integer)}；
     * 其他情况均返回defaultValue；
     *
     * @param value        要转换成{@link Integer}值的对象。
     * @param defaultValue 转换不成功时的默认值（可以为null，这种情况下转换不成功时将返回null值）
     */
    public static Integer toInteger(Object value, Integer defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        Class<?> cls = value.getClass();
        if (Integer.class.equals(cls)) {
            return (Integer) value;
        }
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        if (String.class.equals(cls)) {
            return toInteger((String) value, defaultValue);
        }
        return defaultValue;
    }

    /**
     * {@link String}转换为{@link Long}类型。
     * <p>转换不成功时返回defaultValue并记录info或者warn日志，不会抛出任何异常。
     * <p>value等于null或者为空字符串，或者在转换过程中发生异常，均返回defaultValue，否则返回转换后的{@link Long}值。
     *
     * @param value        要转换成{@link Long}值的字符串。
     * @param defaultValue 转换不成功时的默认值（可以为null，这种情况下转换不成功时将返回null值）。
     * @return
     */
    private static Long toLong(String value, Long defaultValue) {
        if (value == null || value.trim().length() <= 0)
            return defaultValue;

        // 字符串中包含小数点，Long.valueOf会报异常，先转换为BigDecimal，返回其整数部分
        if (value.indexOf('.') >= 0) {
            try {
                Double d = Double.valueOf(value);
                return d.longValue();
            } catch (Exception e) {
                return defaultValue;
            }
        }

        // 当做整数字符串处理
        try {
            return Long.valueOf(value);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * {@link Object}转换为{@link Long}类型。
     * 转换不成功时返回defaultValue
     * value等于null时返回defaultValue；
     * value为{@link Long}类型时，不进行类型转换，直接返回value值；
     * value为{@link String}类型时，参考{@link #toLong(String, Long)}；
     * 其他情况均返回defaultValue；
     *
     * @param value        要转换成{@link Long}值的对象。
     * @param defaultValue 转换不成功时的默认值（可以为null，这种情况下转换不成功时将返回null值）
     */
    public static Long toLong(Object value, Long defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        Class<?> cls = value.getClass();
        if (Long.class.equals(cls)) {
            return (Long) value;
        }
        if (value instanceof Number) {
            return ((Number) value).longValue();
        }
        if (String.class.equals(cls)) {
            return toLong((String) value, defaultValue);
        }
        return defaultValue;
    }

    /**
     * {@link String}转换为{@link Float}类型。
     * <p>转换不成功时返回defaultValue
     * <p>value等于null或者为空字符串，或者在转换过程中发生异常，均返回defaultValue，否则返回转换后的{@link Float}值。
     *
     * @param value        要转换成{@link Float}值的字符串。
     * @param defaultValue 转换不成功时的默认值（可以为null，这种情况下转换不成功时将返回null值）。
     */
    private static Float toFloat(String value, Float defaultValue) {
        if (value == null || value.trim().length() <= 0) {
            return defaultValue;
        }

        try {
            return Float.valueOf(value);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * {@link Object}转换为{@link Float}类型。
     * <p>转换不成功时返回defaultValue
     * <p>
     * 1) value等于null时返回defaultValue；
     * <br /> 2) value为{@link Float}类型时，不进行类型转换，直接返回value值；
     * <br /> 3) value为{@link String}类型时，参考{@link #toFloat(String, Float)}；
     * <br /> 5) 其他情况均返回defaultValue；
     *
     * @param value        要转换成{@link Float}值的对象。
     * @param defaultValue 转换不成功时的默认值（可以为null，这种情况下转换不成功时将返回null值）
     */
    public static Float toFloat(Object value, Float defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        Class<?> cls = value.getClass();
        if (Float.class.equals(cls)) {
            return (Float) value;
        }
        if (value instanceof Number) {
            return ((Number) value).floatValue();
        }
        if (String.class.equals(cls)) {
            return toFloat((String) value, defaultValue);
        }
        return defaultValue;
    }

    /**
     * {@link String}转换为{@link Double}类型。
     * <p>转换不成功时返回defaultValue
     * <p>value等于null或者为空字符串，或者在转换过程中发生异常，均返回defaultValue，否则返回转换后的{@link Double}值。
     *
     * @param value        要转换成{@link Double}值的字符串。
     * @param defaultValue 转换不成功时的默认值（可以为null，这种情况下转换不成功时将返回null值）。
     */
    private static Double toDouble(String value, Double defaultValue) {
        if (value == null || value.trim().length() <= 0) {
            return defaultValue;
        }

        try {
            return Double.valueOf(value);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * {@link Object}转换为{@link Double}类型。
     * <p>转换不成功时返回defaultValue
     * value等于null时返回defaultValue；
     * value为{@link Double}类型时，不进行类型转换，直接返回value对象；
     * value为{@link String}类型时，参考{@link #toDouble(String, Double)}；
     * 其他情况均返回defaultValue；
     *
     * @param value        要转换成{@link Double}值的对象。
     * @param defaultValue 转换不成功时的默认值（可以为null，这种情况下转换不成功时将返回null值）
     */
    public static Double toDouble(Object value, Double defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        Class<?> cls = value.getClass();
        if (Double.class.equals(cls)) {
            return (Double) value;
        }
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        if (String.class.equals(cls)) {
            return toDouble((String) value, defaultValue);
        }
        return defaultValue;
    }

    /**
     * {@link String}转换为{@link BigDecimal}类型。
     * <p>转换不成功时返回defaultValue
     * <p>value等于null或者为空字符串，或者在转换过程中发生异常，均返回defaultValue，否则返回转换后的{@link BigDecimal}值。
     *
     * @param value        要转换成{@link BigDecimal}值的字符串。
     * @param defaultValue 转换不成功时的默认值（可以为null，这种情况下转换不成功时将返回null值）。
     */
    private static BigDecimal toDecimal(String value, BigDecimal defaultValue) {
        if (value == null || value.trim().length() <= 0) {
            return defaultValue;
        }
        try {
            return new BigDecimal(value);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * {@link Object}转换为{@link BigDecimal}类型。
     * <p>转换不成功时返回defaultValue
     * <p>
     * 1) value等于null时返回defaultValue；
     * <br /> 2) value为{@link BigDecimal}类型时，不进行类型转换，直接返回value对象；
     * <br /> 3) value为{@link String}类型时，参考{@link #toDecimal(String, BigDecimal)}；
     * <br /> 4) value为{@link Byte}、{@link Short}、{@link Integer}、{@link Long}、{@link Float}、{@link Double}、{@link BigInteger}
     * 等类型时，返回其{@link BigDecimal}值；
     * <br /> 5) 其他情况均返回defaultValue；
     *
     * @param value        要转换成{@link BigDecimal}值的对象。
     * @param defaultValue 转换不成功时的默认值（可以为null，这种情况下转换不成功时将返回null值）
     */
    public static BigDecimal toDecimal(Object value, BigDecimal defaultValue) {
        if (value == null) {
            return defaultValue;
        }

        Class clazz = value.getClass();
        // 最可能出现的类型放在最前面判断
        if (BigDecimal.class.equals(clazz)) {
            return ((BigDecimal) value);
        }
        if (Double.class.equals(clazz)) {
            // Double转BigDecimal，需要使用new Decimal(doubleValue.toString())
            // 如果使用new Decimal(doubleValue)，像89.7会变成89.70000000000000012之类值
            return toDecimal(value.toString(), defaultValue);
        }

        if (Float.class.equals(clazz)) {
            // 同double
            return toDecimal(value.toString(), defaultValue);
        }
        if (String.class.equals(clazz)) {
            return toDecimal((String) value, defaultValue);
        }
        if (Integer.class.equals(clazz)) {
            return new BigDecimal((Integer) value);
        }
        if (Short.class.equals(clazz)) {
            return new BigDecimal((Short) value);
        }
        if (Byte.class.equals(clazz)) {
            return new BigDecimal((Byte) value);
        }
        if (Long.class.equals(clazz)) {
            return new BigDecimal((Long) value);
        }
        return defaultValue;
    }
}