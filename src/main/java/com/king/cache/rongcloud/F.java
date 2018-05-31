package com.king.cache.rongcloud;

/**
 * @author 金龙
 * @date 2018/5/4 at 上午10:14
 */
public class F {

    public static interface Action1<T> {
        public void invoke(T arg);
    }

    public static interface Action2<T1,T2> {
        public void invoke(T1 arg1, T2 arg2);
    }

    public static interface Function0<R> {
        public R invoke();
    }

    public static interface Function1<T,R> {
        public R invoke(T arg);
    }

    public static interface Promise<V> extends Action1<V>{

        public void invokeWithThrowable(Throwable e);
    }
}

