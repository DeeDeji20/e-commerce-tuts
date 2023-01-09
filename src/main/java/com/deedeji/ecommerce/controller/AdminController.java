package com.deedeji.ecommerce.controller;

import com.deedeji.ecommerce.exception.UserNotFoundException;
import com.deedeji.ecommerce.services.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(("/api/v1/admin"))
@AllArgsConstructor
public class AdminController {

    private final AdminService adminService;

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
