package com.flab.mars.domain.vo;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

// capacity 를 초과할 시 가장 오랫동안 사용되지 않은 데이터 삭제
@Component
public class LRUCache {
    private final int capacity;
    private final int DEFAULT_CAPACITY  = 10;

    private final Map<String, Object> map = new HashMap<>(); // LinkedList 를 경우 키의 검색의 경우 O(n) 를 보완하기 위해서 map 생성-> 키의 유무 판단 O(1)

    private final LinkedList<String> order = new LinkedList<>(); // 키의 삽입 삭제 O(1)

    private LRUCache() {
        this.capacity = DEFAULT_CAPACITY;
    }
    private LRUCache(int capacity) {
        this.capacity = capacity;
    }

    public static LRUCache createWithDefaultCapacity() {
        return new LRUCache();
    }

    public static LRUCache createWithCapacity(int capacity) {
        return new LRUCache(capacity);
    }

    public void add(String key, Object value) {
        if(map.containsKey(key)) { // cache hit
            order.remove(key); // 데이터 순서 앞으로 이동
        } else { // cache miss
            if (capacity == order.size()) {
                String removedKey = order.removeLast();// 가장 오랫동안 사용되지않은 데이터 삭제
                map.remove(removedKey);
            }
        }
        map.put(key, value);
        order.addFirst(key);
    }

    public Object getIfPresent(String key) {
        if(map.containsKey(key)) {
            order.remove(key);
            order.addFirst(key);
            return map.get(key);
        }
        return null;
    }
}
