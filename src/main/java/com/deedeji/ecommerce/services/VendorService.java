package com.deedeji.ecommerce.services;

import com.deedeji.ecommerce.data.dto.request.UpdateVendorProfileRequest;
import com.deedeji.ecommerce.data.dto.request.VendorRegisterRequest;
import com.deedeji.ecommerce.data.dto.response.UpdateVendorProfileResponse;
import com.deedeji.ecommerce.data.dto.response.VendorRegisterResponse;
import com.deedeji.ecommerce.data.models.Vendor;
import com.deedeji.ecommerce.exception.EcommerceExpressException;
import com.deedeji.ecommerce.exception.UserNotFoundException;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.List;

public interface VendorService {
    VendorRegisterResponse register(VendorRegisterRequest request) throws EcommerceExpressException, MailjetSocketTimeoutException, MailjetException, IOException, TemplateException;

    UpdateVendorProfileResponse updateVendorProfile(UpdateVendorProfileRequest request) throws UserNotFoundException;

    List<Vendor> getAllVendors();

    Vendor findById(Long id) throws UserNotFoundException;

//    SuspendVendorResponse
}
