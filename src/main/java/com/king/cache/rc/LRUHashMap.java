package com.king.cache.rc;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author 金龙
 * @date 2018/5/6 at 上午9:45
 */
public class LRUHashMap<K,V> {
    /**
     * 线程池
     */
    private static ScheduledExecutorService expireExecutor = new ScheduledThreadPoolExecutor();
}
