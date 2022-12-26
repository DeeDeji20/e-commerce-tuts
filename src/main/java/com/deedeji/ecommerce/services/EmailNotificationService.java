package com.deedeji.ecommerce.services;

import com.deedeji.ecommerce.data.dto.request.EmailNotificationRequest;

public interface EmailNotificationService {

    void sendHtmlMail(EmailNotificationRequest emailNotificationRequest);
    String sendGmail(EmailNotificationRequest emailNotificationRequest);
}
