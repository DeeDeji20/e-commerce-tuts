package com.deedeji.ecommerce.controller;

import com.deedeji.ecommerce.data.dto.request.AdminRegisterRequest;
import com.deedeji.ecommerce.data.dto.request.UpdateAdminProfileRequest;
import com.deedeji.ecommerce.exception.EcommerceExpressException;
import com.deedeji.ecommerce.exception.UserNotFoundException;
import com.deedeji.ecommerce.services.AdminService;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(("/api/v1/admin"))
@AllArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid AdminRegisterRequest request){
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(adminService.register(request));
        } catch (MailjetSocketTimeoutException | EcommerceExpressException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        } catch (MailjetException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping()
    public ResponseEntity<?> updateProfile(@RequestBody UpdateAdminProfileRequest request){
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(adminService.updateAdminProfile(request));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllAdmins(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(adminService.getAllAdmins());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAdminById(@PathVariable(value = "id") Long id){
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(adminService.findById(id));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }


    @PutMapping("/suspendCustomer/{email}")
    public ResponseEntity<?> suspendCustomer(@PathVariable(value = "email") String email){
        try{
            return ResponseEntity.status(HttpStatus.OK)
                    .body(adminService.suspendUser(email));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}
