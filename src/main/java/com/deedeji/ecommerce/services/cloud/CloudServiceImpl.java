package com.deedeji.ecommerce.services.cloud;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class CloudServiceImpl implements CloudService{

    private final Cloudinary cloudinary;
    @Override
    public String upload(byte[] imageBytes, Map<?, ?> map) throws IOException {
        Map<?, ?> uploadResponse =
                cloudinary.uploader()
                        .upload(imageBytes, ObjectUtils.emptyMap());
        return uploadResponse.get("url").toString();
    }
}
