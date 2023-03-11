package com.spring.booking.fake.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketBooking {

    private Integer bookingId;
    private String  CustomerName;
    private String  busName;
    private String gender;
    private String timings;
    private LocalDate issueDate;
    private double tktCost;

}
