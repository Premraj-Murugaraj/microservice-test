package com.spring.booking.stub;

import com.spring.booking.fake.model.TicketBooking;
import com.spring.booking.fake.repository.TicketBookRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StubRepository implements TicketBookRepository {
    @Override
    public List<TicketBooking> findAll() {
        return null;
    }

    @Override
    public void save(TicketBooking ticketBooking) {

    }

    @Override
    public List<TicketBooking> findOlderTicket(LocalDate date) {
        List<TicketBooking> tktLst = new ArrayList<>();

        TicketBooking maduraiBooking = TicketBooking.builder()
                .bookingId(1).busName("PR")
                .CustomerName("Thangarasu")
                .gender("Male")
                .timings("7:30 PM")
                .tktCost(200)
                .issueDate(LocalDate.now())
                .build();

        TicketBooking tirupatturBooking = TicketBooking.builder()
                .bookingId(2).busName("TAT")
                .CustomerName("Megammai")
                .gender("Female")
                .timings("7:30 PM")
                .tktCost(100)
                .issueDate(LocalDate.now().minusDays(10))
                .build();
        tktLst.add(maduraiBooking);
        tktLst.add(tirupatturBooking);
        return tktLst;
    }
}
