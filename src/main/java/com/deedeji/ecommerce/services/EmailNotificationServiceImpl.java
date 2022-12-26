package com.deedeji.ecommerce.services;

import com.deedeji.ecommerce.data.dto.request.EmailNotificationRequest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import sendinblue.ApiClient;
import sendinblue.ApiException;
import sendinblue.Configuration;
import sendinblue.auth.*;
import sibApi.ContactsApi;
import sibModel.CreateContact;
import sibModel.CreateModel;
import sibModel.CreateUpdateContactModel;

import javax.mail.internet.MimeMessage;

@Service
@Slf4j
@AllArgsConstructor
public class EmailNotificationServiceImpl implements EmailNotificationService{
//    @Value("${api.key}")
//    private String apiKeyValue;

    private final JavaMailSender javaMailSender;

    @Override
    public void sendHtmlMail(EmailNotificationRequest emailNotificationRequest) {
        // Java SDK: https://github.com/sendinblue/APIv3-java-library
// Import classes:
//import sendinblue.ApiClient;
//import sendinblue.ApiException;
//import sendinblue.Configuration;
//import sendinblue.auth.*;
//import sibApi.ContactsApi;

//        ApiClient defaultClient = Configuration.getDefaultApiClient();
//        // Configure API key authorization: api-key
//        ApiKeyAuth apiKey = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
//        apiKey.setApiKey(apiKeyValue);
//        log.info("API key {}", apiKeyValue);
//        log.info("API key from them {}", apiKey.getApiKey());
//
//        ContactsApi apiInstance = new ContactsApi();
//
//        CreateContact createContact = new CreateContact(); // CreateContact | Values to create a contact
//        createContact.email("deolaoladeji@gmail.com");
//
//        try {
//            CreateUpdateContactModel result = apiInstance.createContact(createContact);
//            System.out.println(result);
//        } catch (ApiException e) {
//            System.err.println("Exception when calling ContactsApi#createContact");
//            e.printStackTrace();
//        }
    }

    @Override
    public String sendGmail(EmailNotificationRequest emailNotificationRequest) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper=
                new MimeMessageHelper(mimeMessage);

        try {
            mimeMessageHelper.setFrom("no-reply@gmail.com");
            mimeMessageHelper.setTo(emailNotificationRequest.getUserEmail());
            mimeMessageHelper.setText(emailNotificationRequest.getMailContent());
            javaMailSender.send(mimeMessage);
            return String.format("Email sent successfully to %s",
                    emailNotificationRequest.getUserEmail());
        }catch (Exception e){
            e.printStackTrace();
        }
        return String.format("Email not sent to %s", emailNotificationRequest.getUserEmail());
    }
}
