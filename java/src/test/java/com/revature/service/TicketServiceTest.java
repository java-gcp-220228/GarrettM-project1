package com.revature.service;

import com.revature.dao.TicketDao;
import com.revature.dto.AddTicketDTO;
import com.revature.dto.ResponseTicketDTO;
import com.revature.exception.InvalidImageException;
import com.revature.model.Ticket;
import com.revature.model.User;
import javafx.application.Application;
import org.eclipse.jetty.util.DateCache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.omg.CORBA.portable.ApplicationException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TicketServiceTest {

    @Test
    public void testAddTicket_positive() throws SQLException, InvalidImageException, IOException {

        ResponseTicketDTO mockResponseDTO = mock(ResponseTicketDTO.class);
        TicketDao mockTicketDAO = mock(TicketDao.class);

        AddTicketDTO mockAddDTO = new AddTicketDTO("Name", "description", "1",57);

        User employee = new User(5, "Joseph", "xyz", "Employee");
        User FM = new User(3, "Stpfen", "123", "Finance Manager");

        Ticket mockTicket = new Ticket(1, "Name", "Pending", "description", "Lodging",57, employee, FM);

        TicketService ticketTest = new TicketService(mockTicketDAO);

        when(mockTicketDAO.addTicket(5, mockAddDTO)).thenReturn(mockTicket);

        ResponseTicketDTO actual = ticketTest.addTicket(5, mockAddDTO);

        ResponseTicketDTO expected = new ResponseTicketDTO(1, mockAddDTO.getTicketName(), "Pending",
                mockAddDTO.getDescription(), "Lodging", 57, employee.getUsername(), FM.getUsername());

        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testAddTicket_Negative() throws SQLException, InvalidImageException, IOException {

        ResponseTicketDTO mockResponseDTO = mock(ResponseTicketDTO.class);
        TicketDao mockTicketDAO = mock(TicketDao.class);

        AddTicketDTO mockAddDTO = new AddTicketDTO("Name", "description", "1",57);

        User employee = new User(5, "Joseph", "xyz", "Employee");
        User FM = new User(3, "Stpfen", "123", "Finance Manager");

        Ticket mockTicket = new Ticket(1, "Name", "Pending", "description", "Lodging",57, employee, FM);

        TicketService ticketTest = new TicketService(mockTicketDAO);

        when(mockTicketDAO.addTicket(5, mockAddDTO)).thenReturn(mockTicket);

        ResponseTicketDTO actual = ticketTest.addTicket(5, mockAddDTO);
        //different employee id in expected.
        ResponseTicketDTO expected = new ResponseTicketDTO(3, mockAddDTO.getTicketName(), "Pending",
                mockAddDTO.getDescription(), "Lodging", 57, employee.getUsername(), FM.getUsername());

        Assertions.assertNotEquals(actual, expected);
    }

    @Test
    public void testGetAllTickets_Negative() throws SQLException {
        TicketDao mockTicket= mock(TicketDao.class);
        ResponseTicketDTO mockResponse = mock(ResponseTicketDTO.class);
        User employee = new User(5, "Joseph", "xyz", "Employee");
        User FM = new User(3, "Stpfen", "123", "Finance Manager");

        List<Ticket> fakeTicket = new ArrayList<>();
        fakeTicket.add(new Ticket(1, "Beep", "Pending", "yelp", "Ugly",57, employee, FM));
        fakeTicket.add(new Ticket(2, "pen", "Approved", "writing", "cost",27, employee, FM));

        List<ResponseTicketDTO> fakeResponse = new ArrayList<>();
        fakeResponse.add(new ResponseTicketDTO(1, "Beep", "Pending", "yelp", "Ugly", 57, employee.getUsername(), FM.getUsername()));
        fakeResponse.add(new ResponseTicketDTO(2, "pen", "Approved", "Writing", "cost", 27, employee.getUsername(), FM.getUsername()));
        fakeResponse.add(new ResponseTicketDTO(3, "Three", "Three", "Writing", "Pain", 5.5f, employee.getUsername(), FM.getUsername()));

        when(mockTicket.getAllTickets()).thenReturn(fakeTicket);

        TicketService ticketService = new TicketService(mockTicket);

        List<ResponseTicketDTO> actual = ticketService.getAllTickets();

        List<ResponseTicketDTO> expected = new ArrayList<>(fakeResponse);

        Assertions.assertNotEquals(expected, actual);

    }

    @Test
    public void testGetAllTickets_Positive() throws SQLException {
        TicketDao mockTicket= mock(TicketDao.class);
        ResponseTicketDTO mockResponse = mock(ResponseTicketDTO.class);
        User employee = new User(5, "Joseph", "xyz", "Employee");
        User FM = new User(3, "Stpfen", "123", "Finance Manager");

        List<Ticket> fakeTicket = new ArrayList<>();
        fakeTicket.add(new Ticket(1, "Beep", "Pending", "yelp", "Ugly",57, employee, FM));
        fakeTicket.add(new Ticket(2, "pen", "Approved", "writing", "cost",27, employee, FM));

        List<ResponseTicketDTO> fakeResponse = new ArrayList<>();
        fakeResponse.add(new ResponseTicketDTO(1, "Beep", "Pending", "yelp", "Ugly", 57, employee.getUsername(), FM.getUsername()));
        fakeResponse.add(new ResponseTicketDTO(2, "pen", "Approved", "writing", "cost", 27, employee.getUsername(), FM.getUsername()));

        when(mockTicket.getAllTickets()).thenReturn(fakeTicket);

        TicketService ticketService = new TicketService(mockTicket);

        List<ResponseTicketDTO> actual = ticketService.getAllTickets();

        List<ResponseTicketDTO> expected = new ArrayList<>(fakeResponse);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testPendingTickets_Positive() throws  SQLException {
        TicketDao mockTicket= mock(TicketDao.class);
        ResponseTicketDTO mockResponse = mock(ResponseTicketDTO.class);
        User employee = new User(5, "Joseph", "xyz", "Employee");
        User FM = new User(3, "Stpfen", "123", "Finance Manager");

        List<Ticket> fakeTicket = new ArrayList<>();
        fakeTicket.add(new Ticket(1, "Beep", "Pending", "yelp", "Ugly",57, employee, FM));
        //fakeTicket.add(new Ticket(2, "pen", "Approved", "writing", "cost", employee, FM));

        List<ResponseTicketDTO> fakeResponse = new ArrayList<>();
        fakeResponse.add(new ResponseTicketDTO(1, "Beep", "Pending", "yelp", "Ugly",57, employee.getUsername(), FM.getUsername()));
        //fakeResponse.add(new ResponseTicketDTO(2, "pen", "Approved", "writing", "cost", employee.getUsername(), FM.getUsername()));

        when(mockTicket.getPendingTickets()).thenReturn(fakeTicket);

        TicketService ticketService = new TicketService(mockTicket);

        List<ResponseTicketDTO> actual = ticketService.getPendingTickets();

        List<ResponseTicketDTO> expected = new ArrayList<>(fakeResponse);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testPendingTickets_Negative() throws  SQLException {
        TicketDao mockTicket= mock(TicketDao.class);
        ResponseTicketDTO mockResponse = mock(ResponseTicketDTO.class);
        User employee = new User(5, "Joseph", "xyz", "Employee");
        User FM = new User(3, "Stpfen", "123", "Finance Manager");

        List<Ticket> fakeTicket = new ArrayList<>();
        fakeTicket.add(new Ticket(1, "Beep", "Pending", "yelp", "Ugly",57, employee, FM));
        fakeTicket.add(new Ticket(2, "pen", "Approved", "writing", "cost",27, employee, FM));

        List<ResponseTicketDTO> fakeResponse = new ArrayList<>();
        fakeResponse.add(new ResponseTicketDTO(1, "Beep", "Pending", "yelp", "Ugly",57, employee.getUsername(), FM.getUsername()));
        //fakeResponse.add(new ResponseTicketDTO(2, "pen", "Approved", "writing", "cost", employee.getUsername(), FM.getUsername()));

        when(mockTicket.getPendingTickets()).thenReturn(fakeTicket);

        TicketService ticketService = new TicketService(mockTicket);

        List<ResponseTicketDTO> actual = ticketService.getPendingTickets();

        List<ResponseTicketDTO> expected = new ArrayList<>(fakeResponse);

        Assertions.assertNotEquals(expected, actual);
    }

    @Test
    public void getAllTicketsUserIdTest_positive() throws SQLException {
        TicketDao mockTicket= mock(TicketDao.class);
        ResponseTicketDTO mockResponse = mock(ResponseTicketDTO.class);
        User employee = new User(5, "Joseph", "xyz", "Employee");
        User FM = new User(3, "Stpfen", "123", "Finance Manager");

        List<Ticket> fakeTicket = new ArrayList<>();
        fakeTicket.add(new Ticket(1, "Beep", "Pending", "yelp", "Ugly",57, employee, FM));
        fakeTicket.add(new Ticket(2, "pen", "Approved", "writing", "cost",27, employee, FM));
        //fakeTicket.add(new Ticket(3,"beboop", "beep", "bop", "tick", FM, FM));

        List<ResponseTicketDTO> fakeResponse = new ArrayList<>();
        fakeResponse.add(new ResponseTicketDTO(1, "Beep", "Pending", "yelp", "Ugly",57, employee.getUsername(), FM.getUsername()));
        fakeResponse.add(new ResponseTicketDTO(2, "pen", "Approved", "writing", "cost",27, employee.getUsername(), FM.getUsername()));

        when(mockTicket.getAllTickets(5)).thenReturn(fakeTicket);

        TicketService ticketService = new TicketService(mockTicket);

        List<ResponseTicketDTO> actual = ticketService.getAllTickets(5);

        List<ResponseTicketDTO> expected = new ArrayList<>(fakeResponse);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getAllTicketsUserIdTest_negative() throws SQLException {
        TicketDao mockTicket= mock(TicketDao.class);
        ResponseTicketDTO mockResponse = mock(ResponseTicketDTO.class);
        User employee = new User(5, "Joseph", "xyz", "Employee");
        User FM = new User(3, "Stpfen", "123", "Finance Manager");

        List<Ticket> fakeTicket = new ArrayList<>();
        fakeTicket.add(new Ticket(1, "Beep", "Pending", "yelp", "Ugly",57, employee, FM));
        fakeTicket.add(new Ticket(2, "pen", "Approved", "writing", "cost",23, employee, FM));
        fakeTicket.add(new Ticket(3,"beboop", "beep", "bop", "tick",48, FM, FM));

        List<ResponseTicketDTO> fakeResponse = new ArrayList<>();
        fakeResponse.add(new ResponseTicketDTO(1, "Beep", "Pending", "yelp", "Ugly", 57, employee.getUsername(), FM.getUsername()));
        fakeResponse.add(new ResponseTicketDTO(2, "pen", "Approved", "writing", "cost", 23, employee.getUsername(), FM.getUsername()));

        when(mockTicket.getAllTickets(3)).thenReturn(fakeTicket);

        TicketService ticketService = new TicketService(mockTicket);

        List<ResponseTicketDTO> actual = ticketService.getAllTickets(5);

        List<ResponseTicketDTO> expected = new ArrayList<>(fakeResponse);

        Assertions.assertNotEquals(expected, actual);
    }

    @Test
    public void approveTicketTest_positive() throws SQLException {
        TicketDao mockTicket= mock(TicketDao.class);
        ResponseTicketDTO mockResponse = mock(ResponseTicketDTO.class);
        User employee = new User(5, "Joseph", "xyz", "Employee");
        User FM = new User(3, "Stpfen", "123", "Finance Manager");

        Ticket fakeTicket = new Ticket(1, "Beep", "Pending", "yelp", "Ugly",57,"now", "later", employee, FM);



        when(mockTicket.approveTicket(1, 2, FM.getId())).thenReturn(fakeTicket);

        TicketService ticketService = new TicketService(mockTicket);

        ResponseTicketDTO actual = ticketService.approveTicket("1", "approved", FM.getId());

        ResponseTicketDTO expected = new ResponseTicketDTO(fakeTicket.getId(), fakeTicket.getTicketName(), fakeTicket.getStatus()
        ,fakeTicket.getDescription(), fakeTicket.getType(), fakeTicket.getAmount(), fakeTicket.getCreationTime(), fakeTicket.getResolutionTime(), fakeTicket.getEmployee().getUsername(), fakeTicket.getFinanceManger().getUsername());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void approveTicketTest_negative() throws SQLException {
        TicketDao mockTicket= mock(TicketDao.class);
        ResponseTicketDTO mockResponse = mock(ResponseTicketDTO.class);
        User employee = new User(5, "Joseph", "xyz", "Employee");
        User FM = new User(3, "Stpfen", "123", "Finance Manager");

        Ticket fakeTicket = new Ticket(1, "Beep", "Pending", "yelp", "Ugly",57, "now", "later", employee, FM);


        System.out.println("Return before when return");
        when(mockTicket.approveTicket(1, 2, FM.getId())).thenReturn(fakeTicket);

        TicketService ticketService = new TicketService(mockTicket);

        ResponseTicketDTO actual = ticketService.approveTicket("1", "approved", FM.getId());


        ResponseTicketDTO expected = new ResponseTicketDTO(2, fakeTicket.getTicketName(), fakeTicket.getStatus()
                ,fakeTicket.getDescription(), fakeTicket.getType(), fakeTicket.getAmount(), fakeTicket.getCreationTime(), fakeTicket.getResolutionTime(), fakeTicket.getEmployee().getUsername(), fakeTicket.getFinanceManger().getUsername());
        System.out.println("Expected: " + expected.toString());
        System.out.println("Actual: "+ actual.toString());

        Assertions.assertNotEquals(expected, actual);

    }

    @Test
    public void approveTicketTest_intException() throws SQLException {
        TicketDao mockTicket= mock(TicketDao.class);
        ResponseTicketDTO mockResponse = mock(ResponseTicketDTO.class);
        User employee = new User(5, "Joseph", "xyz", "Employee");
        User FM = new User(3, "Stpfen", "123", "Finance Manager");

        Ticket fakeTicket = new Ticket(1, "Beep", "Pending", "yelp", "Ugly", 57, employee, FM);



        when(mockTicket.approveTicket(1, 2, FM.getId())).thenReturn(fakeTicket);

        TicketService ticketService = new TicketService(mockTicket);
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ResponseTicketDTO actual = ticketService.approveTicket("one", "Approved", FM.getId());
        });

        Assertions.assertEquals("Ticket ID and Status must be a int value", thrown.getMessage());
    }

    @Test
    public void testNoArgConstructor(){
        TicketService mockService = new TicketService();

        Assertions.assertNotNull(mockService);
    }


}
