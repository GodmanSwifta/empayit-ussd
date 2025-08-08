package com.swifta.ussd.serviceClient;

import com.swifta.ussd.dto.CustomerData;
import com.swifta.ussd.entity.cache.UssdSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.concurrent.CompletableFuture;

import static com.swifta.ussd.constant.PropertyKeys.PAYMENT_SHORT_CODE;

@Component
public class SmsServiceImpl implements SmsService {

    private static final String SHORT_CODE_MESSAGE = "Dear {0}, in case your payment for {1}" +
            "failed. Dial *yourbankcode*{2} to pay or use your selected bank {3}.Expires in 5 mins";

    private final EmpayItOnboardingServiceImpl empayItOnboardingService;
    private final SmsApiClient smsApiClient;
    private final String serviceShortCode;
    private final String serviceSenderId;

    public SmsServiceImpl(EmpayItOnboardingServiceImpl empayItOnboardingService, SmsApiClient smsApiClient,
                          @Value("${service.short-code}") String serviceShortCode, @Value("${service.sender-id}") String serviceSenderId) {
        this.empayItOnboardingService = empayItOnboardingService;
        this.smsApiClient = smsApiClient;
        this.serviceShortCode = serviceShortCode;
        this.serviceSenderId = serviceSenderId;
    }

    @Override
    public CompletableFuture<Void> sendPaymentSms(UssdSession session) {
        return CompletableFuture.runAsync(() -> {
            CustomerData customer = empayItOnboardingService.validateCustomer(session.getMsisdn());
            String starCode = session.getData(PAYMENT_SHORT_CODE);
//            smsApiClient.sendSms(session.getMsisdn(),
//                    retrieveShortCodeMessage(customer, starCode, session.getSelectedProduct().getProductName()), serviceSenderId,
//                    serviceShortCode);
            smsApiClient.sendSms(session.getMsisdn(),
                    retrieveShortCodeMessage(customer, starCode, "Ticket"), serviceSenderId,
                    serviceShortCode);
        });
    }

    private String retrieveShortCodeMessage(CustomerData customer, String paymentShortCode, String productName) {
        String subString = paymentShortCode.substring(paymentShortCode.indexOf("*", 1) + 1);
        return MessageFormat.format(SHORT_CODE_MESSAGE, customer.getFirstName(), productName, subString,
                paymentShortCode);
    }
}
