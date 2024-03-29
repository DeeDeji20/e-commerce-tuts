package com.deedeji.ecommerce.services;

import com.deedeji.ecommerce.data.dto.request.AdminRegisterRequest;
import com.deedeji.ecommerce.data.dto.request.EmailNotificationRequest;
import com.deedeji.ecommerce.data.dto.request.UpdateAdminProfileRequest;
import com.deedeji.ecommerce.data.dto.response.AdminRegisterResponse;
import com.deedeji.ecommerce.data.dto.response.SuspendUserResponse;
import com.deedeji.ecommerce.data.dto.response.UpdateAdminProfileResponse;
import com.deedeji.ecommerce.data.models.*;
import com.deedeji.ecommerce.data.repository.AdminRepository;
import com.deedeji.ecommerce.data.repository.CustomerRepository;
import com.deedeji.ecommerce.data.repository.VendorRepository;
import com.deedeji.ecommerce.exception.EcommerceExpressException;
import com.deedeji.ecommerce.exception.UserNotFoundException;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class AdminServiceImpl implements AdminService{
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    VerificationTokenService verificationTokenService;

    @Autowired
    EmailNotificationService emailNotificationService;

    @Autowired
    VendorRepository vendorRepository;

    private final ModelMapper mapper = new ModelMapper();

    @Override
    public AdminRegisterResponse register(AdminRegisterRequest request) throws EcommerceExpressException, MailjetSocketTimeoutException, MailjetException {
        Optional<Admin> foundCustomer = adminRepository.findByEmail(request.getEmail());
        if (foundCustomer.isPresent()) throw new EcommerceExpressException(
                String.format("Email %s already exist", request.getEmail())
        );

        Admin admin = mapper.map(request, Admin.class);
        admin.getAuthorities().add(Authority.MODERATE);
        Admin savedAdmin = adminRepository.save(admin);
        log.info("Admin saved in db::{}", savedAdmin.toString());
        VerificationToken verificationToken = verificationTokenService
                .createToken(savedAdmin.getEmail());

        var msg =emailNotificationService.sendMailJetMessage(buildEmailNotificationRequest(verificationToken, savedAdmin.getFirstName()));
        return registrationCustomerBuilder(savedAdmin);
    }

    private EmailNotificationRequest buildEmailNotificationRequest(VerificationToken verificationToken, String customerName) {
        var message = getEmailTemplate();
        String mail = null;
        if (message != null){
            var verificationUrl = verificationToken;
            mail = String.format(message, customerName, verificationUrl);
            log.info("mailed url--> {}", verificationUrl);
        }
        return EmailNotificationRequest.builder()
                .userEmail(verificationToken.getUserEmail())
                .mailContent(mail)
                .build();
    }

    private String getEmailTemplate() {
        try(BufferedReader bufferedReader=
                    new BufferedReader(new FileReader("C:\\Users\\CROWN_STAFF.DESKTOP-R8GJQ3F\\IdeaProjects\\e-commerce\\src\\main\\resources\\welcome.txt"))){
            return bufferedReader.lines().collect(Collectors.joining());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private AdminRegisterResponse registrationCustomerBuilder(Admin savedAdmin) {
        return AdminRegisterResponse.builder()
                .message("Success")
                .userId(savedAdmin.getId())
                .code(201)
                .build();
    }


    @Override
    public UpdateAdminProfileResponse updateAdminProfile(UpdateAdminProfileRequest request) throws UserNotFoundException {
        Admin adminToUpdate = adminRepository.findById(request.getAdminId())
                .orElseThrow(()-> new UserNotFoundException(
                        String.format("Admin with id %d, not found", request.getAdminId())
                ));
        mapper.map(request, adminToUpdate);
        adminToUpdate.setEnabled(true);
        Admin updatedAdmin = adminRepository.save(adminToUpdate);
        return UpdateAdminProfileResponse.builder()
                .code(200)
                .message(String.format("%s detail was updated successfully", updatedAdmin.getFirstName()))
                .build();
    }

    @Override
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @Override
    public Admin findById(Long id) throws UserNotFoundException {
        return adminRepository.findById(id).orElseThrow(()->
                        new UserNotFoundException(String.format(
                                "No admin with Id::: %d ", id)));
    }

    @Override
    public SuspendUserResponse suspendUser(String email) throws UserNotFoundException {
        Optional<Customer> foundCustomer = customerRepository.findByEmail(email);
        if (foundCustomer.isPresent()){
            foundCustomer.get().setEnabled(false);
            customerRepository.save(foundCustomer.get());
            return buildSuspendResponse(foundCustomer.get().getFirstName());
        }

        Optional<Vendor> foundVendor = vendorRepository.findByEmail(email);
        if (foundVendor.isPresent()){
            foundVendor.get().setEnabled(false);
            vendorRepository.save(foundVendor.get());
            return buildSuspendResponse(foundVendor.get().getFirstName());
        }

        throw new UserNotFoundException(String.format(
                        "User with the id %s not available", email));
    }

    private SuspendUserResponse buildSuspendResponse(String firstName) {
        return SuspendUserResponse.builder()
                .code(200)
                .message(String.format("%s's details suspended successfully",
                        firstName))
                .build();
    }

}
