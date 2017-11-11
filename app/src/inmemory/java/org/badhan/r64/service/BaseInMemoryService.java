package org.badhan.r64.service;

import android.os.Handler;

import com.squareup.otto.Bus;

import org.badhan.r64.core.MyApplication;

import java.util.Random;

public abstract class BaseInMemoryService {
    protected final MyApplication application;
    protected final Bus bus;
    protected final Handler handler;
    protected final Random random;

    protected BaseInMemoryService(MyApplication application){
        this.application = application;
        bus = application.getBus();
        handler = new Handler();
        random = new Random();
        bus.register(this);
    }

    public void invokeDelayed(Runnable runnable, long mSecondMin, long mSecondMax){
        if (mSecondMin > mSecondMax)
            throw new IllegalArgumentException("min must be smaller then max");

        long delay = (long) (random.nextDouble()*(mSecondMax - mSecondMin)) + mSecondMin;
        handler.postDelayed(runnable, delay);
    }

    protected void postDelayed(final Object event, long mSecondMin, long mSecondMax){
        invokeDelayed(new Runnable() {
            @Override
            public void run() {
                bus.post(event);
            }
        }, mSecondMin, mSecondMax);
    }

    protected void postDelayed(Object event, long mSecond){
        postDelayed(event, mSecond, mSecond);
    }

    protected void postDelayed(Object event){
        postDelayed(event, 600, 1200);
    }
}
