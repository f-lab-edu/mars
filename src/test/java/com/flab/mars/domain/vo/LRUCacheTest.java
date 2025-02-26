package com.flab.mars.domain.vo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LRUCacheTest {

    private LRUCache lruCache;

    @BeforeEach
    void setup() {
        lruCache = LRUCache.createWithCapacity(3);
    }

    @Test
    void testCacheHit() {
        lruCache.add("A", "value1");
        lruCache.add("B", "value2");
        lruCache.add("A", "value3");

        assertEquals("value3", lruCache.getIfPresent("A"));
        assertEquals("value2", lruCache.getIfPresent("B"));
    }

    @Test
     void testCacheEvictionOnCapacityLimit() {
        lruCache.add("A", "value1");
        lruCache.add("B", "value2");
        lruCache.add("C", "value3");

        // 가장 오랜된 항목의 A 가 삭제되어야 함
        lruCache.add("D", "value4");

        assertNull(lruCache.getIfPresent("A"));
        assertEquals("value2", lruCache.getIfPresent("B"));
        assertEquals("value3", lruCache.getIfPresent("C"));
        assertEquals("value4", lruCache.getIfPresent("D"));

    }

}