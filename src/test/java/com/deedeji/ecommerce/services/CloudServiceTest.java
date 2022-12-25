package com.deedeji.ecommerce.services;

import com.cloudinary.utils.ObjectUtils;
import com.deedeji.ecommerce.services.cloud.CloudService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
public class CloudServiceTest {

    @Autowired
    private CloudService cloudService;

    private MultipartFile file;

    @BeforeEach
    void setUp() throws IOException {
        Path path = Paths
                .get("C:\\Users\\CROWN_STAFF.DESKTOP-R8GJQ3F\\Pictures\\swit-c9.jpeg");
        file =
                new MockMultipartFile("swit-c9",
                        Files.readAllBytes(path));
    }

    @Test
    @DisplayName("cloudinary upload test")
    void uploadImageTest(){
        try {
            String response = cloudService.upload(file.getBytes(),
                    ObjectUtils.emptyMap());
            log.info("Cloud upload response {}", response);
            assertThat(response).isNotNull();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
