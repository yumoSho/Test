
package com.glanway.jty.common;


import com.glanway.jty.entity.product.Goods;
import com.glanway.jty.entity.product.SpecValue;
import com.glanway.jty.utils.HtmlUtil;
import org.vacoor.mux.common.json.Jacksons;
import org.vacoor.mux.common.reflect.Mates;
import org.vacoor.mux.common.util.StringUtils;

import java.util.*;

/**
 * @author vacoor
 */

public abstract class Functions {

    public static boolean contains(Iterable<?> it, Object target) {
        if (null == it) {
            return false;
        }
        for (Object e : it) {
            if (e == target || (e != null && e.equals(target))) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasEqPropObj(Iterable<?> it, Object target, String prop) {
        if (null == it || null == target) {
            return false;
        }
        Object value = get(target, prop);

        for (Object el : it) {
            if (null != el && value.equals(get(el, prop))) {
                return true;
            }
        }
        return false;
    }

    private static <V> V get(Object o, String property) {
        Mates<Object> coat = Mates.from(o);
        // Coat#get bug
        return (V) coat.invoke("get" + StringUtils.capitalize(property));
    }

    public static String concat(String a, String b) {
        a = null != a ? a : "";
        b = null != b ? b : "";
        return a + b;
    }

    public static Object find(Iterable<?> it, String prop, Object value) {
        if (null == it) {
            return null;
        }

        String[] path = prop.split("\\.");
        for (Object o : it) {
            final Object obj = o;
            Object val = null;
            for (int i = 0; i < path.length; i++) {
                val = get(o, path[i]);
                if (null == val) {
                    return null;
                }
                o = val;
            }
            if (val == value || (null != val && val.equals(value))) {
                return obj;
            }
        }

        return null;
    }

    public static String toJson(Object obj) {
        return Jacksons.serialize(obj);
    }

    public static boolean hasSpecValue(Iterable<Goods> goodsList, String id) {
        if (null == goodsList) {
            return false;
        }

        for (Goods goods : goodsList) {
            List<SpecValue> values = goods.getSpecValues();
            if (null == values) {
                continue;
            }
            for (SpecValue value : values) {
                Long vid = null;
                if (null == value || null == (vid = value.getId())) {
                    continue;
                }
                if (vid.equals(id)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static String delHTMLTag(String htmlStr){
        if(null == htmlStr){
            return null;
        }

        return HtmlUtil.delHTMLTag(htmlStr);
    }

    public static String appendParam(Map<String, String[]> params, String p, Object v) {
        v = null == v ? "" : v;
        Map<String, String[]> clone = new HashMap<String, String[]>(params);

        String[] newValues = clone.get(p);
        newValues = null == newValues ? new String[1] : Arrays.copyOf(newValues, newValues.length + 1);
        newValues[newValues.length - 1] = String.valueOf(v);

        clone.put(p, newValues);
        return toQs(clone);
    }

    public static String removeParam(Map<String, String[]> params, String p) {
        Map<String, String[]> clone = new HashMap<String, String[]>(params);
        clone.remove(p);
        return toQs(clone);
    }

    public static String removeParamTwo(Map<String, String[]> params, String p,String e) {
        Map<String, String[]> clone = new HashMap<String, String[]>(params);
        clone.remove(p);
        clone.remove(e);
        return toQs(clone);
    }

    public static String removeParamValue(Map<String, String[]> params, String p, String v) {
        Map<String, String[]> clone = new HashMap<String, String[]>(params);
        String[] values = clone.get(p);
        List<String> newValues = new ArrayList<String>();

        if (null != values) {
            for (String value : values) {
                if (v == value || (null != v && v.equals(value))) {
                    continue;
                }
                newValues.add(value);
            }
            clone.put(p, newValues.toArray(new String[newValues.size()]));
        }
        return toQs(clone);
    }

    public static String replaceParam(Map<String, String[]> params, String p, String value) {
        Map<String, String[]> clone = new HashMap<String, String[]>(params);
        clone.put(p, new String[]{value});
        return toQs(clone);
    }

    public static String toQs(Map<String, String[]> params) {
        StringBuilder buff = new StringBuilder();
        for (Map.Entry<String, String[]> param : params.entrySet()) {
            String key = param.getKey();
            // ....
            if ("p".equals(key)) {
                continue;
            }
            String[] values = param.getValue();

            for (String value : values) {
                buff.append("&").append(key).append("=").append(value);
            }
        }
        return buff.replace(0, 1, "?").toString();
    }
}
