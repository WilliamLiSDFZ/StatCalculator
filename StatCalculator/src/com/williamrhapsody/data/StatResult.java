package com.williamrhapsody.data;

import java.util.LinkedHashMap;
import java.util.Set;

/**
 * @author WilliamLi
 * @version 1.0
 * @date 2022/3/5 18:42
 */
public class StatResult extends LinkedHashMap<String,String> {

    public StatResult(String type){
        super(5);
        this.put("标题", type);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Set<String> keySet = this.keySet();
        for (String key:keySet){
            stringBuilder.append("|\"");
            stringBuilder.append(key);
            stringBuilder.append("\"\t");
            stringBuilder.append("\"");
            stringBuilder.append(this.get(key));
            stringBuilder.append("\"|");
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
