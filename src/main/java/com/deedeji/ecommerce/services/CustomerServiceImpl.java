package com.deedeji.ecommerce.services;

import com.deedeji.ecommerce.data.dto.request.CustomerRegistrationRequest;
import com.deedeji.ecommerce.data.dto.request.EmailNotificationRequest;
import com.deedeji.ecommerce.data.dto.request.UpdateCustomerDetails;
import com.deedeji.ecommerce.data.dto.response.CustomerRegisterResponse;
import com.deedeji.ecommerce.data.dto.response.SuspendUserResponse;
import com.deedeji.ecommerce.data.dto.response.UpdateResponse;
import com.deedeji.ecommerce.data.models.*;
import com.deedeji.ecommerce.data.repository.CustomerRepository;
import com.deedeji.ecommerce.exception.EcommerceExpressException;
import com.deedeji.ecommerce.exception.UserNotFoundException;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
//@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    VerificationTokenService verificationTokenService;

    @Autowired
    EmailNotificationService emailNotificationService;

    private final ModelMapper mapper = new ModelMapper();

    @Override
    public CustomerRegisterResponse register(CustomerRegistrationRequest request) throws EcommerceExpressException, MailjetSocketTimeoutException, MailjetException {
        Optional<Customer> foundCustomer = customerRepository.findByEmail(request.getEmail());
        if (foundCustomer.isPresent()) throw new EcommerceExpressException(
                String.format("Email %s already exist", request.getEmail())
        );

        Customer customer = mapper.map(request, Customer.class);
        customer.setCart(new Cart());
        setCustomerAddress(request, customer);
        customer.getAuthorities().add(Authority.BUY);
        Customer savedCustomer = customerRepository.save(customer);
        log.info("customer saved in db::{}", savedCustomer);
        VerificationToken verificationToken = verificationTokenService
                .createToken(savedCustomer.getEmail());

        var msg =emailNotificationService.sendMailJetMessage(buildEmailNotificationRequest(verificationToken, savedCustomer.getFirstName()));
        return registrationCustomerBuilder(savedCustomer);
    }

    private EmailNotificationRequest buildEmailNotificationRequest(VerificationToken verificationToken,
                                                                   String customerName) {
        var message = getEmailTemplate();
        String mail = null;
        if (message != null){
            var verificationUrl = verificationToken;
            mail = String.format(message, customerName, verificationUrl);
            log.info("mailed url--> {}", verificationUrl);
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

    @Override
    public UpdateResponse updateCustomerProfile(UpdateCustomerDetails details) throws UserNotFoundException {
        Customer customerToUpdate = customerRepository.findById(details.getCustomerId())
                .orElseThrow(()-> new UserNotFoundException(
                        String.format("Customer with id %d, not found", details.getCustomerId())
                ));
        mapper.map(details, customerToUpdate);
        Set<Address> customerAddressList = customerToUpdate.getAddress();
        Optional<Address> foundAddress = customerAddressList.stream().findFirst();
        if (foundAddress.isPresent()){
            applyAddressUpdate(foundAddress.get(), details);
        }
        customerToUpdate.getAddress().add(foundAddress.get());
        customerToUpdate.setEnabled(true);
        Customer updatedCustomer = customerRepository.save(customerToUpdate);
        return UpdateResponse.builder()
                .code(200)
                .message(String.format("%s detail was updated successfully", customerToUpdate.getFirstName()))
                .build();
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> findById(Long id) throws UserNotFoundException {
        Customer customer = customerRepository.findById(id).orElseThrow(()->
                new UserNotFoundException(String.format(
                        "No customer with such Id %d ", id)));
        return Optional.of(customer);
    }

    private void applyAddressUpdate(Address foundAddress, UpdateCustomerDetails details) {
        foundAddress.setCity(details.getCity());
        foundAddress.setBuildingNumber(details.getBuildingNumber());
        foundAddress.setStreet(details.getStreet());
        foundAddress.setState(details.getState());
    }

    private CustomerRegisterResponse registrationCustomerBuilder(Customer savedCustomer) {
        return CustomerRegisterResponse.builder()
                .message("Success")
                .userId(savedCustomer.getId())
                .code(201)
                .build();
    }

    private void setCustomerAddress(CustomerRegistrationRequest request, Customer customer) {
        Address customerAddress = new Address();
        customerAddress.setCountry(request.getCountry());
        customer.getAddress().add(customerAddress);
    }

//    Add item to cart
}
