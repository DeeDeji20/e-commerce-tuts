package com.deedeji.ecommerce.services;

import com.deedeji.ecommerce.data.dto.request.AdminRegisterRequest;
import com.deedeji.ecommerce.data.dto.request.UpdateAdminProfileRequest;
import com.deedeji.ecommerce.data.dto.response.AdminRegisterResponse;
import com.deedeji.ecommerce.data.dto.response.SuspendUserResponse;
import com.deedeji.ecommerce.data.dto.response.UpdateAdminProfileResponse;
import com.deedeji.ecommerce.data.models.Admin;
import com.deedeji.ecommerce.exception.UserNotFoundException;

import java.util.List;
import java.util.Optional;

public interface AdminService {

    AdminRegisterResponse register(AdminRegisterRequest request);

    UpdateAdminProfileResponse updateAdminProfile(UpdateAdminProfileRequest response);

    List<Admin> getAllAdmins();

    Optional<Admin> findById(Long id);

    SuspendUserResponse suspendCustomer(Long id) throws UserNotFoundException;
}
