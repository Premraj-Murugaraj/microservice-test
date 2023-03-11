package com.spring.booking.spy;

import com.spring.booking.fake.model.TicketBooking;
import com.spring.booking.fake.repository.TicketBookRepository;
import lombok.Getter;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Getter
public class SpyRepository implements TicketBookRepository {
    int timesCalled=0;
    TicketBooking recentlyCalledWith=null;

    List<TicketBooking> ticketBookingList = new ArrayList<>();

    // method being called
    //method was called with right object

    @Override
    public void save(TicketBooking ticketBooking) {
        timesCalled++;
        recentlyCalledWith = ticketBooking;
        ticketBookingList.add(ticketBooking);
    }

    public void verify(int times, TicketBooking ticketBooking){
        Assertions.assertEquals(times,timesCalled);
        Assertions.assertEquals(ticketBooking,recentlyCalledWith);
    }


    @Override
    public List<TicketBooking> findAll() {
        return null;
    }



    @Override
    public List<TicketBooking> findOlderTicket(LocalDate date) {
        return null;
    }
}
