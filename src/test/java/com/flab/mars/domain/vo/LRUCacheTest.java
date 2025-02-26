package com.flab.mars.domain.vo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LRUCacheTest {

    private LRUCache<String> lruCache;


    @Test
    void 음수Capacity생성시_예외발생() {
        assertThrows(IllegalArgumentException.class, () -> lruCache = LRUCache.createWithCapacity(-1));
    }

    @Test
    void zeroCapacity생성시_IllegalArgumentException발생() {
        assertThrows(IllegalArgumentException.class, () -> lruCache = LRUCache.createWithCapacity(0));
    }

    @Test
    void 캐시가Capacity사이즈가초과하면가장오래된아이템을교체한다(){
        lruCache = LRUCache.createWithCapacity(2);
        lruCache.add("A", "value1");
        lruCache.add("B", "value2");
        lruCache.add("C", "value2");

        assertNull(lruCache.getIfPresent("A"));
    }

    @Test
    void 캐시가Capacity사이즈가초과하면가장오랫동안참조가안된아이템을교체한다(){
        lruCache = LRUCache.createWithCapacity(2);
        lruCache.add("A", "value1");
        lruCache.add("B", "value2");

        lruCache.getIfPresent("A");
        lruCache.add("C", "value2");

        assertNotNull(lruCache.getIfPresent("A"));
        assertNull(lruCache.getIfPresent("B"));
    }

    @Test
    void 캐싱된데이터_조회가가능하다(){
        lruCache = LRUCache.createWithCapacity(1);
        lruCache.add("A", "value1");
        Object a = lruCache.getIfPresent("A");
        assertEquals("value1", a);
    }


    @Test
    void testCacheHit() {
        lruCache = LRUCache.createWithCapacity(3);
        lruCache.add("A", "value1");
        lruCache.add("B", "value2");
        lruCache.add("A", "value3");

        assertEquals("value3", lruCache.getIfPresent("A"));
        assertEquals("value2", lruCache.getIfPresent("B"));
    }

    @Test
     void testCacheEvictionOnCapacityLimit() {
        lruCache = LRUCache.createWithCapacity(3);
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