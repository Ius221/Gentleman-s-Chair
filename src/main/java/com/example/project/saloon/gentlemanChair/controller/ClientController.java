package com.example.project.saloon.gentlemanChair.controller;

import com.example.project.saloon.gentlemanChair.payload.client.BookAppointmentRequestDto;
import com.example.project.saloon.gentlemanChair.payload.client.BookAppointmentResponseDto;
import com.example.project.saloon.gentlemanChair.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/book-appointment")
    public ResponseEntity<BookAppointmentResponseDto> bookAnAppointment(@Valid @RequestBody BookAppointmentRequestDto requestDto) {

        BookAppointmentResponseDto responseDto = clientService.bookAppointment( requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);

    }
}
