package com.deedeji.ecommerce.services;

import com.deedeji.ecommerce.data.dto.request.EmailNotificationRequest;
import com.deedeji.ecommerce.data.dto.request.UpdateVendorProfileRequest;
import com.deedeji.ecommerce.data.dto.request.VendorRegisterRequest;
import com.deedeji.ecommerce.data.dto.response.UpdateVendorProfileResponse;
import com.deedeji.ecommerce.data.dto.response.VendorRegisterResponse;
import com.deedeji.ecommerce.data.models.Address;
import com.deedeji.ecommerce.data.models.Authority;
import com.deedeji.ecommerce.data.models.Vendor;
import com.deedeji.ecommerce.data.models.VerificationToken;
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

@AllArgsConstructor
@Service
@Slf4j
public class VendorServiceImpl implements VendorService{

    @Autowired
    VendorRepository vendorRepository;

    @Autowired
    VerificationTokenService verificationTokenService;

    @Autowired
    EmailNotificationService emailNotificationService;

    private final ModelMapper mapper = new ModelMapper();
    @Override
    public VendorRegisterResponse register(VendorRegisterRequest request) throws EcommerceExpressException, MailjetSocketTimeoutException, MailjetException {
        Optional<Vendor> foundVendor = vendorRepository.findByEmail(request.getEmail());
        if (foundVendor.isPresent()){
            throw new EcommerceExpressException(
                    String.format("Email %s already exist", request.getEmail())
            );
        }
        Vendor vendor = mapper.map(foundVendor, Vendor.class);
        vendor.getAuthorities().add(Authority.SELL);
        Address address = new Address();
        address.setCountry(request.getCountry());
        vendor.getAddress().add(address);
        Vendor savedVendor = vendorRepository.save(vendor);
        log.info("Vendor saved in db::{}", savedVendor);
        VerificationToken verificationToken =
                verificationTokenService.createToken(savedVendor.getEmail());

        var message = getEmailTemplate();
        String mail = null;
        if (message != null){
            var verificationUrl = verificationToken;
            mail = String.format(message, savedVendor.getFirstName(), verificationUrl);
            log.info("mailed url--> {}", verificationUrl);
        }
        EmailNotificationRequest e = EmailNotificationRequest.builder()
                .userEmail(verificationToken.getUserEmail())
                .mailContent(mail)
                .build();
        emailNotificationService.sendMailJetMessage(e);
        return VendorRegisterResponse.builder()
                .message("Success")
                .userId(savedVendor.getId())
                .code(201)
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

    @Override
    public UpdateVendorProfileResponse updateVendorProfile(UpdateVendorProfileRequest request) throws UserNotFoundException {
        Vendor vendorToUpdate = vendorRepository.findById(request.getAdminId())
                .orElseThrow(
                        ()-> new UserNotFoundException(
                                String.format("Vendor with id %d, not found", request.getAdminId())
                        )
                );
        mapper.map(request, vendorToUpdate);
        vendorToUpdate.setEnabled(true);
        Vendor updatedVendor =  vendorRepository.save(vendorToUpdate);
        return UpdateVendorProfileResponse.builder()
                .code(200)
                .message(String.format("%s detail was updated successfully", updatedVendor.getFirstName()))
                .build();
    }

    @Override
    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }

    @Override
    public Vendor findById(Long id) throws UserNotFoundException {
        return vendorRepository.findById(id).orElseThrow(
                ()->
                        new UserNotFoundException(String.format(
                                "No admin with Id::: %d ", id)));
    }
}
