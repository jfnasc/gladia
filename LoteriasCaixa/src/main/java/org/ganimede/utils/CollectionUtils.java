package org.ganimede.utils;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CollectionUtils {

    public static void addAll(Integer[] p, Collection<Integer> t) {
        if (p != null && p.length != 0) {
            if (t != null) {
                for (Integer i : p) {
                    t.add(i);
                }
            }
        }
    }

    /**
     * 
     * @param p
     * @return
     */
    public static Integer[] toArray(Collection<Integer> p) {
        if (p == null || p.size() == 0) {
            return null;
        }

        Integer[] result = new Integer[p.size()];
        p.toArray(result);

        return result;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <K extends Comparable, V extends Comparable> Map<K, V> sortByKeys(Map<K, V> map) {
        List<K> keys = new LinkedList<K>(map.keySet());
        Collections.sort(keys);

        // LinkedHashMap will keep the keys in the order they are inserted
        // which is currently sorted on natural ordering
        Map<K, V> sortedMap = new LinkedHashMap<K, V>();
        for (K key : keys) {
            sortedMap.put(key, map.get(key));
        }

        return sortedMap;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <K extends Comparable, V extends Comparable> Map<K, V> sortByValues(Map<K, V> map) {
        List<Map.Entry<K, V>> entries = new LinkedList<Map.Entry<K, V>>(map.entrySet());

        Collections.sort(entries, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Entry<K, V> o1, Entry<K, V> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        // LinkedHashMap will keep the keys in the order they are inserted
        // which is currently sorted on natural ordering
        Map<K, V> sortedMap = new LinkedHashMap<K, V>();

        for (Map.Entry<K, V> entry : entries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }
}
