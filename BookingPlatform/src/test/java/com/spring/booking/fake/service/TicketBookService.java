package com.spring.booking.fake.service;

import com.spring.booking.fake.model.TicketBooking;
import com.spring.booking.fake.repository.TicketBookRepository;

import java.time.LocalDate;
import java.util.List;

public class TicketBookService {
    private TicketBookRepository ticketBookRepository;

    public TicketBookService(TicketBookRepository ticketBookRepository){
        this.ticketBookRepository = ticketBookRepository;
    }

    public List<TicketBooking> getAllTickesBooked(){
        return ticketBookRepository.findAll();
    }

    public void addTickets(TicketBooking ticketBooking){
        if (ticketBooking.getTktCost()<100)
            return;
        ticketBookRepository.save(ticketBooking);
    }

    public List<TicketBooking> applyDiscountTickets(LocalDate date, double percentage){
        //find the ticket older than issued date
        List<TicketBooking> olderTkt = ticketBookRepository.findOlderTicket(date);

        //apply discount and set new price
        olderTkt.stream().forEach( x -> x.setTktCost(x.getTktCost() - x.getTktCost() * percentage/100));
        return olderTkt;
    }
}
