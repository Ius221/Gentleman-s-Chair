package com.example.project.saloon.gentlemanChair.controller;

import com.example.project.saloon.gentlemanChair.entity.ServiceWithPrice;
import com.example.project.saloon.gentlemanChair.payload.admin.AllBarberResponseDto;
import com.example.project.saloon.gentlemanChair.service.BarberService;
import com.example.project.saloon.gentlemanChair.service.ClientService;
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

    @GetMapping("/all-barbers")
    public ResponseEntity<AllBarberResponseDto> showAllBarber() {
        AllBarberResponseDto responseDto = barberService.fetchAllBarber();
        return new ResponseEntity<>(responseDto, HttpStatus.FOUND);
    }

    @GetMapping("/prices")
    public ResponseEntity<?> showPrices() {
        return new ResponseEntity(ServiceWithPrice.getServiceAndPrices(), HttpStatus.FOUND);
    }
}
