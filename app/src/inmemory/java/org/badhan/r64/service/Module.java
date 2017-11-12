package org.badhan.r64.service;

import org.badhan.r64.core.MyApplication;

public class Module {
    public static void register(MyApplication application){
        new InMemoryAccountService(application);
        new InMemoryContactService(application);
        new InMemoryMessageService(application);
        new InMemoryCadreService(application);
        new InMemoryTrainerService(application);
    }
}
