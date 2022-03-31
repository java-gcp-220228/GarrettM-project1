package com.revature.controller;

import com.revature.dto.AddTicketDTO;
import com.revature.dto.ResponseTicketDTO;
import com.revature.service.JWTService;
import com.revature.service.TicketService;
import io.javalin.Javalin;
import io.javalin.http.Handler;
import io.javalin.http.UnauthorizedResponse;
import io.javalin.http.UploadedFile;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.apache.tika.Tika;

import java.io.InputStream;
import java.util.List;


public class TicketController implements Controller{

    private JWTService jwtService; //handles the encription key stuff
    private TicketService ticketService;

    public TicketController() {
        this.jwtService = JWTService.getInstance();
        this.ticketService = new TicketService();
    }

    private Handler getAllTickets = (ctx) -> {
        String jwt = ctx.header("Authorization");
        jwt = jwt.split(" ")[1];
        System.out.println(jwt);
        Jws<Claims> token = this.jwtService.parseJwt(jwt);

        if(!token.getBody().get("user_role").equals("Finance Manager")){
            throw new UnauthorizedResponse("You must be a Finance Manager to access this endpoint");
        }

        List<ResponseTicketDTO> tickets = this.ticketService.getAllTickets();

        ctx.json(tickets);
    };

    //TODO approve/deny ticket for FM Handler, may require some work after front end work

    private Handler ticketJudgement = (ctx) -> {
        String jwt = ctx.header("Authorization").split(" ")[1];
        Jws<Claims> token = this.jwtService.parseJwt(jwt);

        if(!token.getBody().get("user_role").equals("Finance Manager")) {
            throw new UnauthorizedResponse("You must be logged in as a Finance Manager");
        }
        String ticketId = ctx.pathParam("ticket_id");
        String status = ctx.queryParam("status");
        int userId = token.getBody().get("user_id", Integer.class);

        if(status == null) {
            throw new IllegalArgumentException("You need to provide a status query parameter when attempting to judge the ticket");
        }

        ResponseTicketDTO ticket = this.ticketService.approveTicket(ticketId, status, userId);
        ctx.json(ticket);
    };

    //Todo get all pending tickets, update with image feature one that feature is added
    //get/tickets/pending
    private Handler getPendingTickets = (ctx) -> {
        System.out.println(ctx.header("Authorization").split(" ")[1]);
        String jwt = ctx.header("Authorization");

        jwt = jwt.split(" ")[1];
        System.out.println(jwt);
        Jws<Claims> token = this.jwtService.parseJwt(jwt);

        if(!token.getBody().get("user_role").equals("Finance Manager")){
            throw new UnauthorizedResponse("You must be a Finance Manager to access this endpoint");
        }

        List<ResponseTicketDTO> tickets = this.ticketService.getPendingTickets();

        ctx.json(tickets);
    };



    //TODO get all tickets employee, needs image feature once feature is done
    //get/user/userID/tickets, getTickets
    private Handler getSpecificEmployeeTickets = (ctx) -> {
        String jwt = ctx.header("Authorization").split(" ")[1];
        Jws<Claims> token = this.jwtService.parseJwt(jwt);

        if (!token.getBody().get("user_role").equals("Employee")) {
            throw new UnauthorizedResponse("You must be a standard employee to access this endpoint");
        }

        String userId = ctx.pathParam("user_id");
        int id = Integer.parseInt(userId);
        if (!token.getBody().get("user_id").equals(id)) {
            throw new UnauthorizedResponse("You cannot obtain tickets that don't belong to yourself");
        }


        List<ResponseTicketDTO> dtos = this.ticketService.getAllTickets(id);
        ctx.json(dtos);
    };

    //TODO add ticket for employee Handler, wip in dao, add image feature
    private Handler addTicket = (ctx) -> {
        String jwt = ctx.header("Authorization").split(" ")[1];

        Jws<Claims> token = this.jwtService.parseJwt(jwt);

        if(!token.getBody().get("user_role").equals("Employee")) {
            throw new UnauthorizedResponse("You must be a Employee to access this endpoint");
        }

        String userId = ctx.pathParam("user_id");
        int id = Integer.parseInt(userId);
        if(!token.getBody().get("user_id").equals(id)) {
            throw new UnauthorizedResponse("You cannot add an assignment for a student other than yourself");
        }

        String ticketName = ctx.formParam("ticketName");
        String ticketDescription = ctx.formParam("description");
        String ticketAmount = ctx.formParam("amount");
        String ticketType = ctx.formParam("type");
        float amount = Float.parseFloat(ticketAmount);

        AddTicketDTO dto = new AddTicketDTO();
        dto.setTicketName(ticketName);
        dto.setDescription(ticketDescription);
        dto.setAmount(amount);
        dto.setType(ticketType);


        UploadedFile file = ctx.uploadedFile("image");
        InputStream is = file.getContent();
        dto.setImage(is);

        ResponseTicketDTO getDto = this.ticketService.addTicket(id, dto);
        ctx.status(201);
        ctx.json(getDto);

    };

    //TODO get specific Ticket Image FM, wip in dao, can't make until system is finished.
    //get/tickets/ticketID/image
    private Handler getTicketImage = ctx -> {
        String ticketId = ctx.pathParam("ticket_id");
        InputStream image = this.ticketService.getTricketImage(ticketId);
        Tika tika = new Tika();
        String mimeType = tika.detect(image);
        ctx.header("Content-Type", mimeType);
        ctx.result(image);
    };

    @Override
    public void mapEndpoints(Javalin app) {
        app.get("/user/{user_id}/tickets", getSpecificEmployeeTickets); //fm
        app.get("/tickets", getAllTickets); //emp
        app.get("/tickets/pending", getPendingTickets); //fm
        app.patch("/tickets/{ticket_id}", ticketJudgement); //fm
        app.post("/users/{user_id}/tickets", addTicket); //emp
        app.get("/tickets/{ticket_id}/image", getTicketImage); //emp
    }
}
