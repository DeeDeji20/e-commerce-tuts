package com.deedeji.ecommerce.services;

import com.deedeji.ecommerce.data.dto.request.EmailNotificationRequest;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;

public interface EmailNotificationService {

    void sendHtmlMail(EmailNotificationRequest emailNotificationRequest);
    String sendGmail(EmailNotificationRequest emailNotificationRequest);

    String sendMailJetMessage(EmailNotificationRequest emailNotificationRequest) throws MailjetSocketTimeoutException, MailjetException;
}
