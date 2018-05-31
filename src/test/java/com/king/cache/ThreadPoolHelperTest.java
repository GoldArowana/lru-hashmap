package com.king.cache;

import com.king.cache.rongcloud.ThreadPoolHelper;
import org.junit.Before;
import org.junit.Test;

/**
 * @author 金龙
 * @date 2018/5/6 at 上午12:28
 */
public class ThreadPoolHelperTest {
    private static final int cycle = 99999;
    private static final int size = 10;

    @Before
    public void t2() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < cycle; i++) {
            ThreadPoolHelper.getThreadPool(String.valueOf(i), "123", size);
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void t1() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < cycle; i++) {
            ThreadPoolHelper.getThreadPool(String.valueOf(i), "123", size);
        }
        System.out.println(System.currentTimeMillis() - start);
    }
}
