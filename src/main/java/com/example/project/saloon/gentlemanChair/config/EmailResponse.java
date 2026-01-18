package com.example.project.saloon.gentlemanChair.config;

import java.util.Set;

public class EmailResponse {
    public static String getBarberMail(String name, String time, String date, Set<String> req, Integer money) {
        return String.format("""
                Hello %s,
                
                You have a new client booking request.
                
                Booking Details:
                - Date: %s
                - Time: %s
                - Services Requested: %s
                - Total Amount: ₹%d
                
                Please review the booking and confirm or cancel the request at your earliest convenience so we can update the client accordingly.
                
                Thank you,
                Admin Team
                """, name, date, time, String.join(", ", req), money);

    }
}
