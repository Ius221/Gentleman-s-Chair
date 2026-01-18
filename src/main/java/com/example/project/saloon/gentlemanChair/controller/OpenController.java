package com.example.project.saloon.gentlemanChair.controller;

import com.example.project.saloon.gentlemanChair.entity.ServiceWithPrice;
import com.example.project.saloon.gentlemanChair.payload.admin.AllBarberResponseDto;
import com.example.project.saloon.gentlemanChair.service.BarberService;
import com.example.project.saloon.gentlemanChair.service.ClientService;
import com.example.project.saloon.gentlemanChair.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("")
public class OpenController {

    @Autowired
    private BarberService barberService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private EmailService emailService;

    @GetMapping("/all-barbers")
    public ResponseEntity<AllBarberResponseDto> showAllBarber() {
        AllBarberResponseDto responseDto = barberService.fetchAllBarber();
        return new ResponseEntity<>(responseDto, HttpStatus.FOUND);
    }

    @GetMapping("/prices")
    public ResponseEntity<?> showPrices() {
        return new ResponseEntity(ServiceWithPrice.getServiceAndPrices(), HttpStatus.FOUND);
    }

    @GetMapping("/send")
    public String triggerMail() {
        emailService.sendSimpleEmail(
                "ayushg6996@gmail.com",
                "Hello from spring boot",
                "THis is a test mail. Is it working fine??"
        );
        return "Email Send Successfully";
    }
}
