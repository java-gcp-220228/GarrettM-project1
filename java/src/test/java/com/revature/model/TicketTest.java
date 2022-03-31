package com.revature.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TicketTest {
    private User mockEmployee = new User(2, "JimSplosion", "angryGamer", "Employee", "Jim", "Nuclear", "tnt@boom.com");
    private User mockFinanceManager = new User(1, "Tomato", "plantman", "Finance Manager", "Thomas", "Ato", "seeds@fruit.com");
    @Test
    public void testEquals_Positive_Variables(){
        int id = 1;
        String tN = "Splouch";
        String sts = "missing";
        String dsc = "painful";
        String typ = "loving";
        float amount = 57;

        Ticket mockTicket1 = new Ticket(id, tN, sts, dsc, typ, amount, mockEmployee, mockFinanceManager);
        Ticket mockTicket2 = new Ticket(id, tN, sts, dsc, typ, amount, mockEmployee, mockFinanceManager);

        Assertions.assertEquals(mockTicket1, mockTicket2);
    }

    @Test
    public void testEquals_Positive_Object(){
        int id = 1;
        String tN = "Splouch";
        String sts = "missing";
        String dsc = "painful";
        String typ = "loving";
        float amount = 57;

        Ticket mockTicket1 = new Ticket(id, tN, sts, dsc, typ, amount, mockEmployee, mockFinanceManager);

        Assertions.assertEquals(mockTicket1, mockTicket1);
    }

    @Test
    public void testEquals_Negative_Null(){
        int id = 1;
        String tN = "Splouch";
        String sts = "missing";
        String dsc = "painful";
        String typ = "loving";
        float amount = 57;

        Ticket mockTicket1 = new Ticket(id, tN, sts, dsc, typ, amount, mockEmployee, mockFinanceManager);

        Assertions.assertNotEquals(mockTicket1, null);
    }

    @Test
    public void testToString_Positive(){
        int id = 1;
        String tN = "Splouch";
        String sts = "missing";
        String dsc = "painful";
        String typ = "loving";
        float amount = 57;

        Ticket mockTicket1 = new Ticket(1, tN, sts, dsc, typ, amount, mockEmployee, mockFinanceManager);

        String actual = mockTicket1.toString();
        System.out.println(actual);

        String expected = "Ticket{" +
                "id="+ id +
                ", ticketName='" + tN + '\'' +
                ", approval=" + sts + '\'' +
                ", description=" + dsc + '\'' +
                ", type=" + typ + '\'' +
                ", employee=" + mockEmployee.getUsername() + '\'' +
                ", financeManager=" + mockFinanceManager.getUsername() + '\'' +
                '}';

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testHackCode(){
        int id = 1;
        String tN = "Splouch";
        String sts = "missing";
        String dsc = "painful";
        String typ = "loving";
        float amount = 57;

        Ticket mockTicket1 = new Ticket(id, tN, sts, dsc, typ, amount, mockEmployee, mockFinanceManager);
        Ticket mockTicket2 = new Ticket(id, tN, sts, dsc, typ, amount, mockEmployee, mockFinanceManager);

        int hash1 = mockTicket1.hashCode();
        int hash2 = mockTicket2.hashCode();

        Assertions.assertEquals(hash1, hash2);
    }

    @Test
    public void testGetSetId(){
        int id = 1;
        Ticket mockTicket = new Ticket();
        mockTicket.setId(id);

        Assertions.assertEquals(id, mockTicket.getId());
    }

    @Test
    public void testGetSetName(){
        String tN = "Splouch";
        Ticket mockTicket = new Ticket();
        mockTicket.setTicketName(tN);

        Assertions.assertEquals(tN, mockTicket.getTicketName());
    }

    @Test
    public void testGetSetStatus(){
        String sts = "missing";
        Ticket mockTicket = new Ticket();
        mockTicket.setStatus(sts);
        Assertions.assertEquals(sts, mockTicket.getStatus());
    }

    @Test
    public void testGetSetDescription(){
        String dsc = "painful";
        Ticket mockTicket = new Ticket();
        mockTicket.setDescription(dsc);
        Assertions.assertEquals(dsc, mockTicket.getDescription());
    }

    @Test
    public void testGetSetType(){
        String typ = "loving";
        Ticket mockTicket = new Ticket();
        mockTicket.setType(typ);
        Assertions.assertEquals(typ, mockTicket.getType());
    }

    @Test
    public void testGetSetEmployee(){
        Ticket mockTicket = new Ticket();
        mockTicket.setEmployee(mockEmployee);
        Assertions.assertEquals(mockEmployee, mockTicket.getEmployee());
    }

    @Test
    public void testGetSetFinanceManager(){
        Ticket mockTicket = new Ticket();
        mockTicket.setFinanceManger(mockFinanceManager);
        Assertions.assertEquals(mockFinanceManager, mockTicket.getFinanceManger());
    }


}
