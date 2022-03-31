package com.revature.dao;

import com.revature.dto.AddTicketDTO;
import com.revature.model.Ticket;
import com.revature.model.User;
import com.revature.utility.ConnectionUtility;
import jdk.nashorn.internal.ir.Assignment;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDao {

    //TODO FM approve ticket
    public Ticket approveTicket(int ticketId, int status, int financeMangerId) throws SQLException{ //on FM
        try (Connection con = ConnectionUtility.getConnection()) {
            con.setAutoCommit(false);

            String sql = "update tickets \n" +
                    "set status = ?, resTime = current_timestamp, finance_manager = ?\n" +
                    "where id = ?";

            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, status);
            pstmt.setInt(2, financeMangerId);
            pstmt.setInt(3, ticketId);

            pstmt.executeUpdate();

            String sql2 ="select tickets.id as ticket_id, tickets.name, rs.status, tickets.description, rt.type, tickets.amount, tickets.create_time, tickets.resTime,\n" +
                    "tickets.employee ,users.username e_user, users.first_name as e_fname, users.last_name as e_lname, users.email as e_email,\n" +
                    "tickets.finance_manager, u.username, u.first_name, u.last_name, u.email  \n" +
                    "from tickets\n" +
                    "inner join users\n" +
                    "on tickets.employee = users.id \n" +
                    "inner join users u \n" +
                    "on tickets.finance_manager = u.id\n" +
                    "inner join reimbursement_status rs\n" +
                    "on tickets.status = rs.id \n" +
                    "inner join reimbursement_type rt \n" +
                    "on tickets.type = rt.id \n" +
                    "where tickets.id = ?";


            PreparedStatement pstmt2 = con.prepareStatement(sql2);
            pstmt2.setInt(1, ticketId);

            ResultSet rs2 = pstmt2.executeQuery();

            rs2.next();
            int ticketId2 = rs2.getInt("ticket_id");
                String ticketName = rs2.getString("name");
                String ticketStatus = rs2.getString("status");
                String ticketDescription = rs2.getString("description");
                String ticketType = rs2.getString("type");
                float ticketAmount = rs2.getFloat("amount");
            String createTime = rs2.getTimestamp("create_time").toString();
            String resTime;
            try {
                resTime = rs2.getTimestamp("restime").toString();
            } catch (Exception e) {
                resTime = " ";
            }


                int employeeId = rs2.getInt("employee");
                String employeeUN = rs2.getString("e_user");
                String employeeFN = rs2.getString("e_fname");
                String employeeLN = rs2.getString("e_lname");
                String employeeEm = rs2.getString("e_email");

                User employee = new User(employeeId, employeeUN, "redacted", "Employee", employeeFN, employeeLN, employeeEm);

                int fmId = rs2.getInt("finance_manager");
                String fmUN = rs2.getString("username");
                String fmFN = rs2.getString("first_name");
                String fmLN = rs2.getString("last_name");
                String fmEm = rs2.getString("email");

                User financeManager = new User(fmId, fmUN, "redacted", "Finance Manager", fmFN, fmLN, fmEm);

                Ticket ticket = new Ticket(ticketId, ticketName, ticketStatus, ticketDescription, ticketType, ticketAmount,createTime,resTime, employee, financeManager);

                con.commit();

                return ticket;
        }
    }

    //TODO employee adds ticket
    public Ticket addTicket(int employeeId, AddTicketDTO dto) throws SQLException{
        try(Connection con = ConnectionUtility.getConnection()) {
            con.setAutoCommit(false);
            int typeInt =Integer.parseInt(dto.getType());

            String sql = "insert into tickets (name, status, description, type, amount, picture, employee, create_time, finance_manager) " +
                    "values " +
                    "(?, 1, ?, ?, ?, ?, ?, current_timestamp, 1)";

            PreparedStatement pstmt1 = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt1.setString(1, dto.getTicketName());
            pstmt1.setString(2, dto.getDescription());
            pstmt1.setInt(3, typeInt);
            pstmt1.setFloat(4,dto.getAmount());
            pstmt1.setBinaryStream(5, dto.getImage());
            pstmt1.setInt(6, employeeId);

            System.out.println(dto.getTicketName());
            pstmt1.executeUpdate();

            ResultSet rs = pstmt1.getGeneratedKeys();
            rs.next();
            int ticketId = rs.getInt(1);

            String sqlTime = "select create_time  from tickets \n" +
                    "where id = ?";
            PreparedStatement pstmtTime = con.prepareStatement(sqlTime);
            pstmtTime.setInt(1, ticketId);
            ResultSet rsTime = pstmtTime.executeQuery();
            rsTime.next();
            String createTime = rsTime.getTimestamp("create_time").toString();
            String resTime=" ";

            String sql2 = "SELECT * FROM users WHERE id = ?";
            PreparedStatement pstmt2 = con.prepareStatement(sql2);
            pstmt2.setInt(1, employeeId);


            ResultSet rs2 = pstmt2.executeQuery();
            rs2.next();

            int eId = rs2.getInt("id");
            String employeeUsername = rs2.getString("username");
            String employeePassword = rs2.getString("password");
            String employeeRole = "Employee";

            User employee = new User(eId, employeeUsername, employeePassword, employeeRole);

            String sql3 = "select * from users where id = 1";
            PreparedStatement pstmt3 = con.prepareStatement(sql3);

            ResultSet rs3 = pstmt3.executeQuery();
            rs3.next();

            int fmId = rs3.getInt("id");
            String fmUsername = rs3.getString("username");
            String fmPassword = rs3.getString("password");
            String fmRole = "Finance Manager";

            User financeManager = new User(fmId, fmUsername, fmPassword, fmRole);


            String type;
            switch (typeInt){
                case 1:
                    type = "Lodging";
                break;
                case 2:
                    type = "Travel";
                    break;
                case 3:
                    type = "Food";
                    break;
                default:
                    type = "Other";
            }

            Ticket ticket = new Ticket(ticketId, dto.getTicketName(), "Pending", dto.getDescription(), type, dto.getAmount(),createTime,resTime,  employee, financeManager);

            con.commit();

            return ticket;
        }
    }

    //FM get all tickets
    public List<Ticket> getAllTickets() throws SQLException {
        //try catch return data as sql query
        try (Connection con = ConnectionUtility.getConnection()) {
            List<Ticket> tickets = new ArrayList<>();

            String sql = "select tickets.id as ticket_id, tickets.name, rs.status, tickets.description, rt.type ,tickets.amount, tickets.create_time, tickets.resTime," +
                    "tickets.employee ,users.username e_user, users.first_name as e_fname, users.last_name as e_lname, users.email as e_email, " +
                    "tickets.finance_manager, u.username, u.first_name, u.last_name, u.email  " +
                    "from tickets " +
                    "inner join users " +
                    "on tickets.employee = users.id " +
                    "inner join users u " +
                    "on tickets.finance_manager = u.id " +
                    "inner join reimbursement_status rs " +
                    "on tickets.status = rs.id " +
                    "inner join reimbursement_type rt " +
                    "on tickets.type = rt.id ";

            PreparedStatement pstmt = con.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()){
                int ticketId = rs.getInt("ticket_id");
                String ticketName = rs.getString("name");
                String ticketStatus = rs.getString("status");
                String ticketDescription = rs.getString("description");
                String ticketType = rs.getString("type");
                Float ticketAmount = rs.getFloat("amount");
                String createTime = rs.getTimestamp("create_time").toString();
                String resTime;
                try {
                    resTime = rs.getTimestamp("restime").toString();
                } catch (Exception e) {
                    resTime = " ";
                }


                int employeeId = rs.getInt("employee");
                String employeeUN = rs.getString("e_user");
                String employeeFN = rs.getString("e_fname");
                String employeeLN = rs.getString("e_lname");
                String employeeEm = rs.getString("e_email");

                User employee = new User(employeeId, employeeUN, "redacted", "Employee", employeeFN, employeeLN, employeeEm);

                int fmId = rs.getInt("finance_manager");
                String fmUN = rs.getString("username");
                String fmFN = rs.getString("first_name");
                String fmLN = rs.getString("last_name");
                String fmEm = rs.getString("email");

                User financeManager = new User(fmId, fmUN, "redacted", "Finance Manager", fmFN, fmLN, fmEm);

                Ticket t = new Ticket(ticketId, ticketName, ticketStatus, ticketDescription, ticketType, ticketAmount, createTime, resTime, employee, financeManager);

                tickets.add(t);

            }

            return tickets;


        }
        /*Ticket ticTok = new Ticket();
        List<Ticket> ticsToks = new ArrayList<>();
        ticsToks.add(ticTok);
        return ticsToks;*/
    }

    //TODO FM get a ticket
    public List<Ticket> getPendingTickets() throws SQLException {
        try (Connection con = ConnectionUtility.getConnection()) {
            List<Ticket> tickets = new ArrayList<>();

            String sql = "select tickets.id as ticket_id, tickets.name, rs.status, tickets.description, rt.type, tickets.amount, tickets.create_time, tickets.resTime, \n" +
                    "tickets.employee ,users.username e_user, users.first_name as e_fname, users.last_name as e_lname, users.email as e_email,\n" +
                    "tickets.finance_manager, u.username, u.first_name, u.last_name, u.email  \n" +
                    "from tickets\n" +
                    "inner join users\n" +
                    "on tickets.employee = users.id \n" +
                    "inner join users u \n" +
                    "on tickets.finance_manager = u.id\n" +
                    "inner join reimbursement_status rs\n" +
                    "on tickets.status = rs.id \n" +
                    "inner join reimbursement_type rt \n" +
                    "on tickets.type = rt.id \n" +
                    "where tickets.status = 1";

            PreparedStatement pstmt = con.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int ticketId = rs.getInt("ticket_id");
                String ticketName = rs.getString("name");
                String ticketStatus = rs.getString("status");
                String ticketDescription = rs.getString("description");
                String ticketType = rs.getString("type");
                float ticketAmount = rs.getFloat("amount");
                String createTime = rs.getTimestamp("create_time").toString();
                String resTime;
                try {
                    resTime = rs.getTimestamp("restime").toString();
                } catch (Exception e) {
                    resTime = " ";
                }

                int employeeId = rs.getInt("employee");
                String employeeUN = rs.getString("e_user");
                String employeeFN = rs.getString("e_fname");
                String employeeLN = rs.getString("e_lname");
                String employeeEm = rs.getString("e_email");

                User employee = new User(employeeId, employeeUN, "redacted", "Employee", employeeFN, employeeLN, employeeEm);

                int fmId = rs.getInt("finance_manager");
                String fmUN = rs.getString("username");
                String fmFN = rs.getString("first_name");
                String fmLN = rs.getString("last_name");
                String fmEm = rs.getString("email");

                User financeManager = new User(fmId, fmUN, "redacted", "Finance Manager", fmFN, fmLN, fmEm);

                Ticket t = new Ticket(ticketId, ticketName, ticketStatus, ticketDescription, ticketType,ticketAmount, createTime, resTime, employee, financeManager);

                tickets.add(t);
            }
            return tickets;
        }
    }

    //TODO employee get all tickets its by their user ID
    public List<Ticket> getAllTickets(int userId) throws SQLException {
        try(Connection con = ConnectionUtility.getConnection()) {
            List<Ticket> tickets = new ArrayList<>();

            String sql = "select tickets.id as ticket_id, tickets.name, rs.status, tickets.description, rt.type, tickets.amount, tickets.create_time, tickets.resTime,\n" +
                    "tickets.employee ,users.username e_user, users.first_name as e_fname, users.last_name as e_lname, users.email as e_email,\n" +
                    "tickets.finance_manager, u.username, u.first_name, u.last_name, u.email  \n" +
                    "from tickets \n" +
                    "inner join reimbursement_status rs\n" +
                    "on tickets.status = rs.id\n" +
                    "inner join reimbursement_type rt\n" +
                    "on tickets.type = rt.id\n" +
                    "inner join users \n" +
                    "on tickets.employee = users.id\n" +
                    "inner join user_role \n" +
                    "on users.user_role_id = user_role.id\n" +
                    "inner join users u \n" +
                    "on tickets.finance_manager = u.id\n" +
                    "where users.id = ?";

            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, userId);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int ticketId = rs.getInt("ticket_id");
                String ticketName = rs.getString("name");
                String ticketStatus = rs.getString("status");
                String ticketDescription = rs.getString("description");
                String ticketType = rs.getString("type");
                float ticketAmount = rs.getFloat("amount");
                String createTime = rs.getTimestamp("create_time").toString();
                String resTime;
                try {
                    resTime = rs.getTimestamp("restime").toString();
                } catch (Exception e) {
                    resTime = " ";
                }

                int employeeId = rs.getInt("employee");
                String employeeUN = rs.getString("e_user");
                String employeeFN = rs.getString("e_fname");
                String employeeLN = rs.getString("e_lname");
                String employeeEmail = rs.getString("e_email");

                User employee = new User(employeeId, employeeUN, "redacted", "Employee", employeeFN, employeeLN, employeeEmail);

                int fmId = rs.getInt("finance_manager");
                String fmUN = rs.getString("username");
                String fmFN = rs.getString("first_name");
                String fmLN = rs.getString("last_name");
                String fmEm = rs.getString("email");

                User financeManager = new User(fmId, fmUN, "redacted", "Finance Manager", fmFN, fmLN, fmEm);


                Ticket ticket = new Ticket(ticketId, ticketName, ticketStatus, ticketDescription, ticketType, ticketAmount,createTime,resTime, employee, financeManager);

                tickets.add(ticket);
            }
            return tickets;
        }
    }
    //TODO FM gets image from ticket
    public InputStream getTicketImage(int tId) throws SQLException {

        try(Connection con = ConnectionUtility.getConnection()){
            String sql = "select picture from tickets\n" +
                    "where id= ?";

            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setInt(1, tId);

                ResultSet rs = pstmt.executeQuery();

                if(rs.next()) {
                    InputStream is = rs.getBinaryStream("picture");

                    return is;
                } else {
                    return null;
                }
            }
        }
    }
}
