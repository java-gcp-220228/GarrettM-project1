package com.revature.service;

import com.revature.dao.TicketDao;
import com.revature.dto.AddTicketDTO;
import com.revature.dto.ResponseTicketDTO;
import com.revature.exception.ImageNotFoundException;
import com.revature.exception.InvalidImageException;
import com.revature.model.Ticket;

import jdk.nashorn.internal.ir.Assignment;
import org.apache.tika.Tika;


import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketService {

    private TicketDao ticketDao;

    public TicketService() {
        this.ticketDao = new TicketDao();
    }

    public TicketService(TicketDao mockDao) {
        this.ticketDao = mockDao;
    }
    //Test coverage, positive, negative, and exception
    public ResponseTicketDTO approveTicket(String ticketId, String status, int financeManagerId) throws SQLException {
        try {
            int intTicketId = Integer.parseInt(ticketId);
            //String stringStatus = Integer.parseInt(status);
            status = status.toLowerCase();
            int intStatus;
            switch (status){
                case "approved": intStatus = 2; break;
                default: intStatus = 3; break;
            }

            Ticket ticket = this.ticketDao.approveTicket(intTicketId, intStatus, financeManagerId);

            return new ResponseTicketDTO(ticket.getId(), ticket.getTicketName(), ticket.getStatus(), ticket.getDescription(),
                    ticket.getType(),ticket.getAmount(),ticket.getCreationTime(), ticket.getResolutionTime(),
                    ticket.getEmployee().getUsername(), ticket.getFinanceManger().getUsername());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Ticket ID and Status must be a int value");
        }
    }
    //Test coverage, positive and negative
    public List<ResponseTicketDTO> getAllTickets(int userId) throws SQLException {
        List<Ticket> tickets = this.ticketDao.getAllTickets(userId);

        List<ResponseTicketDTO> ticketDTOS = new ArrayList<>();
        for(Ticket t : tickets) {
            ticketDTOS.add(new ResponseTicketDTO(t.getId(), t.getTicketName(),
                    t.getStatus(), t.getDescription(), t.getType(), t.getAmount(), t.getCreationTime(),
                    t.getResolutionTime(), t.getEmployee().getUsername(), t.getFinanceManger().getUsername()));
        }
        return ticketDTOS;
    }
    //test coverage, positive and negative.
    public List<ResponseTicketDTO> getPendingTickets() throws SQLException {
        List<Ticket> tickets = this.ticketDao.getPendingTickets();

        List<ResponseTicketDTO> ticketDTOs = new ArrayList<>();

        for(Ticket ticket : tickets){
            ticketDTOs.add(new ResponseTicketDTO(ticket.getId(), ticket.getTicketName(),
                    ticket.getStatus(), ticket.getDescription(), ticket.getType(), ticket.getAmount(), ticket.getCreationTime(),
                    ticket.getTicketName(), ticket.getEmployee().getUsername(), ticket.getFinanceManger().getUsername()));
        }
        return ticketDTOs;
    }
    //test coverage, positive and negative.
    public List<ResponseTicketDTO> getAllTickets() throws SQLException {
        List<Ticket> tickets = this.ticketDao.getAllTickets();

        List<ResponseTicketDTO> ticketDTOs = new ArrayList<>();

        for(Ticket ticket : tickets){
            ticketDTOs.add(new ResponseTicketDTO(ticket.getId(), ticket.getTicketName(),
                    ticket.getStatus(), ticket.getDescription(), ticket.getType(), ticket.getAmount(), ticket.getCreationTime(),
                    ticket.getResolutionTime(), ticket.getEmployee().getUsername(), ticket.getFinanceManger().getUsername()));
        }
        return ticketDTOs;
    }
    //test coverage, positive and negative
    public ResponseTicketDTO addTicket(int employeeId, AddTicketDTO dto) throws SQLException, InvalidImageException, IOException {
        Tika tika = new Tika();
        String mimeType = tika.detect(dto.getImage());
        /*
        if(!mimeType.equals("image/jpeg") && !mimeType.equals("image/png") && !mimeType.equals("image/gif")) {
            throw new InvalidImageException("Image must be a JPEG, PNG, or GIF");
        }*/

        Ticket ticket = this.ticketDao.addTicket(employeeId, dto);

        return new ResponseTicketDTO(ticket.getId(), ticket.getTicketName(), ticket.getStatus(), ticket.getDescription(),
                ticket.getType(), ticket.getAmount(), ticket.getCreationTime(), ticket.getResolutionTime(),
                ticket.getEmployee().getUsername(), ticket.getFinanceManger().getUsername());
    }

    public InputStream getTricketImage(String ticketId) throws SQLException, ImageNotFoundException {
        try {
            int tId = Integer.parseInt(ticketId);

            InputStream is = this.ticketDao.getTicketImage(tId);

            if(is == null){
                throw new ImageNotFoundException("Ticket id " + ticketId + " does not have an image");
            }

            return is;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Ticket and/or user id must be an int value");
        }
    }
}
