package com.deedeji.ecommerce.services;

import com.deedeji.ecommerce.data.models.VerificationToken;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
public class VerificationTokenImplTest {

    private VerificationToken verificationToken;

    @Autowired
    private VerificationTokenService verificationTokenService;

    @BeforeEach
    void setUp(){
        verificationToken = verificationTokenService
                .createToken("test@gmail.com");
    }

    @Test
    void createTokenTest(){
        log.info("Verification token object {}", verificationToken);
        assertThat(verificationToken).isNotNull();
        assertThat(verificationToken.getUserEmail()).isEqualTo("test@gmail.com");
        log.info("Token--> {}", verificationToken.getToken());
        assertThat(verificationToken.getToken().length()).isEqualTo(5);
    }
}
