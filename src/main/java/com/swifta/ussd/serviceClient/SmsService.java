package com.swifta.ussd.serviceClient;


import com.swifta.ussd.entity.cache.UssdSession;

import java.util.concurrent.CompletableFuture;

public interface SmsService {
    CompletableFuture<Void> sendPaymentSms(UssdSession session);

}
