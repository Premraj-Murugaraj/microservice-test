package com.spring.booking.dummy;

import com.spring.booking.fake.model.TicketBooking;
import com.spring.booking.fake.repository.TicketBookRepository;

import java.util.List;

public class TicketBkService {

    private TicketBookRepository ticketBookRepository;
    private EmailService emailService;

    public TicketBkService(TicketBookRepository ticketBookRepository, EmailService emailService){
        this.ticketBookRepository=ticketBookRepository;
        this.emailService=emailService;
    }

    public List<TicketBooking> getAllTickets(){
        return ticketBookRepository.findAll();
    }

    public void addTickets(TicketBooking ticketBooking){
        ticketBookRepository.save(ticketBooking);
    }
}
