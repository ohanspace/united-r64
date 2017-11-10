package org.badhan.blooddonor.service;

import org.badhan.blooddonor.core.MyApplication;

public class Module {
    public static void register(MyApplication application){
        new InMemoryAccountService(application);
        new InMemoryContactService(application);
        new InMemoryMessageService(application);
    }
}
