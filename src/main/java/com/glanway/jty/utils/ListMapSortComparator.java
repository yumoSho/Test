package com.glanway.jty.utils;

import java.util.Comparator;
import java.util.Map;

public abstract class ListMapSortComparator implements Comparator<Map<String, Object>> {
	 
    private String key;
 
    private String order;
 
    public ListMapSortComparator(String key,String order) {
        this.key = key;
        this.order = order;
    }

    @Override
    public int compare(Map<String, Object> o1, Map<String, Object> o2){
        if (order.equals("asc")) {
            return o1.get(key).toString().compareTo(o2.get(key).toString());
        }else {
            return o2.get(key).toString().compareTo(o1.get(key).toString());
        }
    }
 
}