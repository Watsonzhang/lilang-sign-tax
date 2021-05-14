package com.zhangwei.until;

import com.zhangwei.anon.SignIgnore;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * 签名工具类
 */
@Slf4j
public class SignUtil {

    private static final String CHARSET = "utf-8";

    /**
     * 校验签名timestamp与当前时间是否超过300s
     * 注：true未过期;false过期
     *
     * @param timestamp
     * @return
     */
    public static boolean validateTimestamp(Long timestamp) {
        if (timestamp == null) {
            return false;
        }

        long current = System.currentTimeMillis();
        long diffTime = Math.abs(current - timestamp);
        return diffTime <= 600000;
    }

    /**
     * 签名算法
     *
     * @param o 要参与签名的数据对象
     * @return 签名，使用sha256
     * @throws IllegalAccessException
     */
    public static String getSignByObj(Object o, String key) {
        String result = "";

        try {
            ArrayList<String> list = new ArrayList<>();
            Class cls = o.getClass();
            /**
             * 由于class.getDeclaredFields()无法获取父类的字段，因此通过循环的方式获取其所有父类，并排除掉object类字段
             */
            while (cls != null && !cls.isAssignableFrom(Object.class)) {
                Field[] fields = cls.getDeclaredFields();
                for (Field f : fields) {
                    f.setAccessible(true);
                    if (f.isAnnotationPresent(SignIgnore.class)) {
                        continue;
                    }

                    if (ObjectUtil.isSimpleTypeOrString(f.getType())) {
                        if (f.get(o) != null && !f.get(o).equals("")) {
                            list.add(f.getName() + "=" + f.get(o) + "&");
                        }
                    }
                }
                cls = cls.getSuperclass();
            }

            int size = list.size();
            String[] arrayToSort = list.toArray(new String[size]);
            Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < size; i++) {
                sb.append(arrayToSort[i]);
            }

            result = sb.toString();
            result += key;
            result = SHA256Util.getSHA256StrJava(result, CHARSET);
        } catch (Exception e) {
            log.error("getSignByObject Exception", e);
        }

        return result;
    }

    /**
     * 验证签名 服务器端使用
     *
     * @param parametersMap 参数
     * @param secretkey     密钥
     * @param sign          签名
     * @return
     */
    public static boolean validateSign(Map<String, Object> parametersMap, String secretkey, String sign) {
        boolean flag = false;
        if (parametersMap.isEmpty()) {
            return flag;
        }
        //去除parametersMap中的sign
        parametersMap.remove("sign");
        String mySign = signRequest(parametersMap, secretkey);
        log.info("服务端计算签名为：" + mySign);
        // 验证签名是否一致
        if (mySign.equals(sign)) {
            flag = true;
        }
        return flag;
    }

    /**
     * 签名加密 客户端使用
     *
     * @param parametersMap 参数
     * @param secretkey     密钥
     * @return
     */
    public static String signRequest(Map<String, Object> parametersMap, String secretkey) {
        ArrayList<String> list = new ArrayList<>();
        for (Map.Entry<String, Object> entry : parametersMap.entrySet()) {
            String key = entry.getKey();
            Object val = entry.getValue();
            if (StringUtils.isBlank(key) || val == null) {
                continue;
            }

            if (ObjectUtil.isSimpleTypeOrString(val.getClass())) {
                if (null != val && !"".equals(val)) {
                    list.add(key + "=" + val + "&");
                }
            }
        }
        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += secretkey;
        result = SHA256Util.getSHA256StrJava(result, CHARSET);
        return result;
    }
}
