package com.djsz.rxjava2demo.bus;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * author: Shawn
 * time  : 2017/4/14 09:36
 * desc  :
 */
public final class RxBus2 {

    private static volatile RxBus2 mDefaultInstance;

    private RxBus2() {
    }

    public static RxBus2 getDefault() {
        if (mDefaultInstance == null) {
            synchronized (RxBus2.class) {
                if (mDefaultInstance == null) {
                    mDefaultInstance = new RxBus2();
                }
            }
        }
        return mDefaultInstance;
    }

    private final PublishSubject<Object> bus = PublishSubject.create();

    public void send(final Object event) {
        bus.onNext(event);
    }

    public Observable<Object> toObservable() {
        return bus;
    }

    public boolean hasObservers() {
        return bus.hasObservers();
    }
}
