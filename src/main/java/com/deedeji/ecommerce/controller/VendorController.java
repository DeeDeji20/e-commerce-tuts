package com.deedeji.ecommerce.controller;

import com.deedeji.ecommerce.data.dto.request.UpdateVendorProfileRequest;
import com.deedeji.ecommerce.data.dto.request.VendorRegisterRequest;
import com.deedeji.ecommerce.exception.EcommerceExpressException;
import com.deedeji.ecommerce.exception.UserNotFoundException;
import com.deedeji.ecommerce.services.VendorService;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/admin")
@AllArgsConstructor
public class VendorController {

    private final VendorService vendorService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid VendorRegisterRequest request){
        try{
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(vendorService.register(request));
        } catch (MailjetSocketTimeoutException | MailjetException | EcommerceExpressException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PutMapping()
    public ResponseEntity<?> updateProfile(@RequestBody UpdateVendorProfileRequest request){
        try{
            return ResponseEntity.status(HttpStatus.OK)
                    .body(vendorService.updateVendorProfile(request));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVendorById(@PathVariable(value = "id") Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK)
                    .body(vendorService.findById(id));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @PutMapping("/all")
    public ResponseEntity<?> getAllVendors(){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(vendorService.getAllVendors());
    }
}
