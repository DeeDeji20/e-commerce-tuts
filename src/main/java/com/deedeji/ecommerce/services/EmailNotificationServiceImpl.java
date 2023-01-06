package com.deedeji.ecommerce.services;

import com.deedeji.ecommerce.data.dto.request.EmailNotificationRequest;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.resource.Emailv31;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
@Slf4j
@AllArgsConstructor
public class EmailNotificationServiceImpl implements EmailNotificationService{
//    @Value("${api.key}")
//    private String apiKeyValue;

    private final JavaMailSender javaMailSender;

//    @Value("mj.api.key")
//    private final String apiKey;
//
//    @Value("mj.api.secret")
//    private final String apiSecret;

    private Environment env;

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

    @Override
    public String sendMailJetMessage(EmailNotificationRequest emailNotificationRequest) throws MailjetSocketTimeoutException, MailjetException {
        MailjetClient client;
        MailjetRequest request;
        MailjetResponse response;

        String apiKey = env.getProperty("mj.api.key");
        String apiSecret = env.getProperty("mj.api.secret");        System.out.println(apiKey);

        client = new MailjetClient(apiKey, apiSecret, new ClientOptions("v3.1"));
        request = new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray()
                        .put(new JSONObject()
                                .put(Emailv31.Message.FROM, new JSONObject()
                                        .put("Email", "deolaoladeji@gmail.com")
                                        .put("Name", "Deji"))
                                .put(Emailv31.Message.TO, new JSONArray()
                                        .put(new JSONObject()
                                                .put("Email", emailNotificationRequest.getUserEmail())
                                                .put("Name", "Adeola")))
                                .put(Emailv31.Message.SUBJECT, "Greetings from Mailjet.")
                                .put(Emailv31.Message.TEXTPART, "My first Mailjet email")
                                .put(Emailv31.Message.HTMLPART, "<h3>Dear passenger Dee, welcome to <a href='https://www.google.com/'>Mailjet</a>!</h3><br />")
                                .put(Emailv31.Message.CUSTOMID, "AppGettingStartedTest")));
        response = client.post(request);
        System.out.println(response.getStatus());
        System.out.println(response.getData());
        return "Email sent successfully";
    }

//    public MessageResponse sendSimpleMessage() {
//        MailgunMessagesApi mailgunMessagesApi = MailgunClient.config(API_KEY)
//                .createApi(MailgunMessagesApi.class);
//
//        Message message = Message.builder()
//                .from("Excited User <USER@YOURDOMAIN.COM>")
//                .to("artemis@example.com")
//                .subject("Hello")
//                .text("Testing out some Mailgun awesomeness!")
//                .build();
//
//        return mailgunMessagesApi.sendMessage(YOUR_DOMAIN_NAME, message);
//    }
}
