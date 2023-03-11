package com.spring.booking.fake.repository;

import com.spring.booking.fake.model.TicketBooking;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FakeTicketTicketBookRepository implements TicketBookRepository {

    List<TicketBooking> ticketBookingLst = new ArrayList<>();
    @Override
    public List<TicketBooking> findAll() {
        return ticketBookingLst;
    }

    @Override
    public void save(TicketBooking ticketBooking) {
        ticketBookingLst.add(ticketBooking);
    }

    @Override
    public List<TicketBooking> findOlderTicket(LocalDate date) {
        return null;
    }
}
