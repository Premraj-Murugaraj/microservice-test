package com.spring.booking.fake.repository;

import com.spring.booking.fake.model.TicketBooking;

import java.time.LocalDate;
import java.util.List;

public interface TicketBookRepository {
    //get All ticket
    public List<TicketBooking> findAll();
    //save ticket in DB
    public void save(TicketBooking ticketBooking);

    List<TicketBooking> findOlderTicket(LocalDate date);
}
