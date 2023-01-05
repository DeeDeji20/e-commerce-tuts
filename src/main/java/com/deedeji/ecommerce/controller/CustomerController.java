package com.deedeji.ecommerce.controller;

import com.deedeji.ecommerce.data.dto.request.CustomerRegistrationRequest;
import com.deedeji.ecommerce.data.dto.request.UpdateCustomerDetails;
import com.deedeji.ecommerce.exception.EcommerceExpressException;
import com.deedeji.ecommerce.exception.UserNotFoundException;
import com.deedeji.ecommerce.services.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid CustomerRegistrationRequest request){
        try{
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(customerService.register(request));
        } catch (EcommerceExpressException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PutMapping()
    public ResponseEntity<?> updateProfile(@RequestBody UpdateCustomerDetails request){
        try{
            return ResponseEntity.status(HttpStatus.OK)
                    .body(customerService.updateCustomerProfile(request));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllCustomers(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerService.getAllCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable(value = "id") Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK)
                    .body(customerService.findById(id));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

//    TODO move this to admin service
    @PutMapping("/suspend/{id}")
    public ResponseEntity<?> suspendCustomer(@PathVariable(value = "id") Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK)
                    .body(customerService.suspendCustomer(id));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}
