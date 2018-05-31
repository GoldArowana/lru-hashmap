package com.king.cache.rc;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.lang.StringUtils;

import java.util.concurrent.*;

/**
 * @author 金龙
 * @date 2018/5/4 at 下午3:02
 */
public class ThreadPoolHelper {
    private static final ConcurrentHashMap<String, ExecutorService> threadMap = new ConcurrentHashMap<>();

    public static ExecutorService getThreadPool(String name, int size) {
        if (threadMap.contains(name)) {
            ExecutorService pool = Executors.newFixedThreadPool(size);
            ExecutorService old = threadMap.putIfAbsent(name, pool);
            if (old != null) {
                pool.shutdown();
            }
        }
        return threadMap.get(name);
    }

    public static ExecutorService getThreadPool(String name) {
        return getThreadPool(name, Runtime.getRuntime()
                .availableProcessors());
    }

    public static ExecutorService getThreadPool(String name, String threadNamePrefix, int maxPoolSize) {
//        new ThreadFactoryBuilder().setNameFormat("lru-schedule-pool-%d");

        if (StringUtils.isEmpty(threadNamePrefix)) {
            threadNamePrefix = name + "_%d";
        } else {
            if (!threadNamePrefix.contains("%d")) {
                threadNamePrefix += "_%d";
            }
        }
        if (!threadMap.containsKey(name)) {
            ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                    .setNameFormat(threadNamePrefix).build();
//            ThreadFactory namedThreadFactory = Executors.defaultThreadFactory();
            ExecutorService pool = new ThreadPoolExecutor(1,
                    maxPoolSize < 1 ? 1 : maxPoolSize,
                    0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(),
                    namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
            ExecutorService old = threadMap.putIfAbsent(name, pool);
            if (old != null) {
                pool.shutdown();
            }
        }
        return threadMap.get(name);
    }
}
