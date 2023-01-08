package com.deedeji.ecommerce.services;

import com.deedeji.ecommerce.data.dto.request.AdminRegisterRequest;
import com.deedeji.ecommerce.data.dto.request.UpdateAdminProfileRequest;
import com.deedeji.ecommerce.data.dto.response.AdminRegisterResponse;
import com.deedeji.ecommerce.data.dto.response.SuspendUserResponse;
import com.deedeji.ecommerce.data.dto.response.UpdateAdminProfileResponse;
import com.deedeji.ecommerce.data.models.Admin;
import com.deedeji.ecommerce.exception.EcommerceExpressException;
import com.deedeji.ecommerce.exception.UserNotFoundException;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;

import java.util.List;
import java.util.Optional;

public interface AdminService {

    AdminRegisterResponse register(AdminRegisterRequest request) throws EcommerceExpressException, MailjetSocketTimeoutException, MailjetException;

    UpdateAdminProfileResponse updateAdminProfile(UpdateAdminProfileRequest response) throws UserNotFoundException;

    List<Admin> getAllAdmins();

    Optional<Admin> findById(Long id);

    SuspendUserResponse suspendCustomer(Long id) throws UserNotFoundException;
}
