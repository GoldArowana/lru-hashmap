package com.king.cache.rongcloud;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.lang.StringUtils;

import java.util.concurrent.*;

/**
 * 增加一些接口,用来显示的创建线程池.
 */
public class ThreadPoolHelper {
    private static ConcurrentHashMap<String, ExecutorService> threadMap = new ConcurrentHashMap<>();
    public static final String DataPersistence = "dataPersistence";
    public static final String Common = "common";

    public static ExecutorService getThreadPool(String name, int size) {
        ExecutorService pool = Executors.newFixedThreadPool(size);
        ExecutorService old = threadMap.putIfAbsent(name, pool);
        if (old != null) {
            pool.shutdown();
        }
        return threadMap.get(name);
    }

    public static ExecutorService getThreadPool(String name) {
        return getThreadPool(name, Runtime.getRuntime()
                .availableProcessors());
    }

    public static ExecutorService getCommonThreadPool() {
        return getThreadPool(Common, Runtime.getRuntime()
                .availableProcessors());
    }

    public static ExecutorService getDataPersistenceThreadPool() {
        int size = Runtime.getRuntime()
                .availableProcessors() / 2;
        if (size <= 0) size = 1;
        return getThreadPool(DataPersistence, size);
    }

    /**
     * @param name             线程池的名字,
     * @param threadNamePrefix 线程名称前缀.
     * @param maxPoolSize      最大数量. 必须> 1
     * @return
     */
    public static ExecutorService getThreadPool(String name, String threadNamePrefix, int maxPoolSize) {
//        new ThreadFactoryBuilder().setNameFormat("lru-schedule-pool-%d");

        if (StringUtils.isEmpty(threadNamePrefix)) {
            threadNamePrefix = name + "_%d";
        } else {
            if (!threadNamePrefix.contains("%d")) {
                threadNamePrefix += "_%d";
            }
        }
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat(threadNamePrefix).build();
        ExecutorService pool = new ThreadPoolExecutor(1,
                maxPoolSize < 1 ? 1 : maxPoolSize,
                0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(),
                namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
        ExecutorService old = threadMap.putIfAbsent(name, pool);
        if (old != null) {
            pool.shutdown();
        }
        return threadMap.get(name);
    }
}

