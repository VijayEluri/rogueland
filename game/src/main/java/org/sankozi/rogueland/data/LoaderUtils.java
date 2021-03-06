package org.sankozi.rogueland.data;

import com.google.common.base.CaseFormat;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author sankozi
 */
class LoaderUtils {

    static <K extends Enum<K>> EnumMap<K, Float> toFloatMap(Map<?,?> map, Class<K> enumClass){
        EnumMap<K, Float> ret = new EnumMap<>(enumClass);
        for(Map.Entry entry : map.entrySet()){
            ret.put(Enum.valueOf(enumClass, CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_UNDERSCORE, entry.getKey().toString())), 
                    ((Number)entry.getValue()).floatValue());
        }
        return ret;
    }

    static <K extends Enum<K>> EnumMap<K, Integer> toIntMap(Map<?,?> map, Class<K> enumClass){
        EnumMap<K, Integer> ret = new EnumMap<>(enumClass);
        for(Map.Entry entry : map.entrySet()){
            ret.put(Enum.valueOf(enumClass, CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_UNDERSCORE, entry.getKey().toString())),
                    ((Number)entry.getValue()).intValue());
        }
        return ret;
    }
}
