package com.spring.booking.tests;

import com.spring.booking.dummy.EmailService;
import com.spring.booking.dummy.TicketBkEmailService;
import com.spring.booking.dummy.TicketBkService;
import com.spring.booking.fake.model.TicketBooking;
import com.spring.booking.fake.repository.FakeTicketTicketBookRepository;
import com.spring.booking.fake.service.TicketBookService;
import com.spring.booking.mock.MockRepository;
import com.spring.booking.spy.SpyRepository;
import com.spring.booking.stub.StubRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class TicketBookingTest {

    @Test
    void should_return_all_tkts_booked(){
        //arrange
        FakeTicketTicketBookRepository fakeTicketTicketBookRepository = new FakeTicketTicketBookRepository();
        TicketBookService ticketBookService = new TicketBookService(fakeTicketTicketBookRepository);

        TicketBooking maduraiBooking = TicketBooking.builder()
                .bookingId(1).busName("PR")
                .CustomerName("Thangarasu")
                .gender("Male")
                .timings("7:30 PM")
                .tktCost(200)
                .build();

        TicketBooking tirupatturBooking = TicketBooking.builder()
                .bookingId(2).busName("TAT")
                .CustomerName("Megammai")
                .gender("Female")
                .timings("7:30 PM")
                .tktCost(500)
                .build();

        ticketBookService.addTickets(maduraiBooking);
        ticketBookService.addTickets(tirupatturBooking);

        //act
        List<TicketBooking> allTickets = ticketBookService.getAllTickesBooked();

        //assert
        Assertions.assertEquals(2,allTickets.size());
        Assertions.assertTrue(allTickets.stream().anyMatch(name ->name.getCustomerName().contains("Megammai")));
    }

    @Test
    void should_return_all_tkts_booked_with_dummy(){
        //arrange
        FakeTicketTicketBookRepository fakeTicketBookRepository = new FakeTicketTicketBookRepository();
        EmailService emailService = new TicketBkEmailService();
        TicketBkService ticketBkService = new TicketBkService(fakeTicketBookRepository,emailService);

        TicketBooking maduraiBooking = TicketBooking.builder()
                .bookingId(1).busName("PR")
                .CustomerName("Thangarasu")
                .gender("Male")
                .timings("7:30 PM")
                .build();

        TicketBooking TirupattueBooking = TicketBooking.builder()
                .bookingId(2).busName("TAT")
                .CustomerName("Megammai")
                .gender("Female")
                .timings("7:30 PM")
                .build();

        ticketBkService.addTickets(maduraiBooking);
        ticketBkService.addTickets(TirupattueBooking);

        //act
        List<TicketBooking> allTickets = ticketBkService.getAllTickets();

        //assert
        Assertions.assertEquals(2,allTickets.size());
        Assertions.assertTrue(allTickets.stream().anyMatch(name ->name.getCustomerName().contains("Megammai")));
    }

    @Test
    void should_update_price_per_discount_stub(){
        StubRepository stubRepository = new StubRepository();
        TicketBookService ticketBookService = new TicketBookService(stubRepository);

        List<TicketBooking> tickets = ticketBookService.applyDiscountTickets(LocalDate.now(),20);
        Assertions.assertEquals(160,tickets.get(0).getTktCost());
    }

    @Test
    void should_save_book_mock(){
        MockRepository mockRepository = new MockRepository();
        TicketBookService ticketBookService = new TicketBookService(mockRepository);

        TicketBooking maduraiBooking = TicketBooking.builder()
                .bookingId(1).busName("PR")
                .CustomerName("Thangarasu")
                .gender("Male")
                .timings("7:30 PM")
                .tktCost(1000)
                .build();

        TicketBooking tirupatturBooking = TicketBooking.builder()
                .bookingId(2).busName("TAT")
                .CustomerName("Megammai")
                .gender("Female")
                .timings("7:30 PM")
                .tktCost(300)
                .build();

        ticketBookService.addTickets(maduraiBooking);

        Assertions.assertEquals(1, mockRepository.getTimesCalled());
        Assertions.assertEquals(maduraiBooking, mockRepository.getRecentlyCalledWith());
        Assertions.assertTrue(mockRepository.getTimesCalled() > 0);

    }

    @Test
    void should_save_book_spy(){
        SpyRepository spyRepository = new SpyRepository();
        TicketBookService ticketBookService = new TicketBookService(spyRepository);

        TicketBooking maduraiBooking = TicketBooking.builder()
                .bookingId(1).busName("PR")
                .CustomerName("Thangarasu")
                .gender("Male")
                .timings("7:30 PM")
                .tktCost(100)
                .build();

        TicketBooking tirupatturBooking = TicketBooking.builder()
                .bookingId(2).busName("TAT")
                .CustomerName("Megammai")
                .gender("Female")
                .timings("7:30 PM")
                .tktCost(99)
                .build();

        ticketBookService.addTickets(maduraiBooking);

        spyRepository.verify(1,maduraiBooking);

        //this shouldnt call / store the book object
        ticketBookService.addTickets(tirupatturBooking);

        spyRepository.verify(1,maduraiBooking);



    }


}
