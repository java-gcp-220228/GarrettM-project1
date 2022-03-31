package com.revature.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    public void largeConstructor_Test_Positive(){
        int id = 1;
        String uN= "Blah";
        String pW = "its a secret to everyone";
        String uR = "janitor";
        String fN = "Strong";
        String lN = "Bad";
        String eM = "last@place.com";
        User mockUser = new User (id, uN, pW, uR, fN, lN, eM);

        String actual = mockUser.toString();
        String expected = "User{" +
                    "id=" + id +
                    ", username='" + uN + '\'' +
                    ", password='" + pW + '\'' +
                    ", userRole='" + uR + '\'' +
                    ", firstName='" + fN + '\'' +
                    ", lastName='" + lN + '\'' +
                    ", email='" + eM + '\'' +
                    '}';

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testHackCode_positive(){
        int id = 1;
        String uN= "Blah";
        String pW = "its a secret to everyone";
        String uR = "janitor";
        String fN = "Strong";
        String lN = "Bad";
        String eM = "last@place.com";

        User user1 = new User(1, uN, pW, uR, fN, lN, eM);
        User user2 = new User(1, uN, pW, uR, fN, lN, eM);

        int u1 = user1.hashCode();
        int u2 = user2.hashCode();

        Assertions.assertEquals(u1, u2);
    }

    @Test
    public void testGetSetId_positive(){
        int id = 1;
        User mockUser = new User();

        mockUser.setId(id);

        Assertions.assertEquals(id, mockUser.getId());
    }

    @Test
    public void testGetSetId_negative(){
        int id = 1;
        User mockUser = new User();

        mockUser.setId(id);

        Assertions.assertNotEquals(10, mockUser.getId());
    }

    @Test
    public void testGetSetUserName_positive(){
        String uN= "Blah";
        User mockUser = new User();

        mockUser.setUsername(uN);

        Assertions.assertEquals(uN, mockUser.getUsername());
    }

    @Test
    public void testGetSetUserName_negative(){
        String uN= "Blah";
        User mockUser = new User();

        mockUser.setUsername(uN);

        Assertions.assertNotEquals(uN + "pof", mockUser.getUsername());
    }

    @Test
    public void testGetSetPassword_positive(){
        String pW = "its a secret to everyone";
        User mockUser = new User();

        mockUser.setPassword(pW);

        Assertions.assertEquals(pW, mockUser.getPassword());
    }

    @Test
    public void testGetSetPassword_negative(){
        String pW = "its a secret to everyone";
        User mockUser = new User();

        mockUser.setPassword(pW);

        Assertions.assertNotEquals(pW + "ad", mockUser.getPassword());
    }

    @Test
    public void testGetSetRole_positive(){
        String uR = "janitor";
        User mockUser = new User();

        mockUser.setUserRole(uR);

        Assertions.assertEquals(uR, mockUser.getUserRole());
    }

    @Test
    public void testGetSetRole_negative(){
        String uR = "janitor";
        User mockUser = new User();

        mockUser.setUserRole(uR);

        Assertions.assertNotEquals(uR + "asdf", mockUser.getUserRole());
    }

    @Test
    public void testGetSetFirstName_positive(){
        String fN = "Strong";
        User mockUser = new User();

        mockUser.setFirstName(fN);

        Assertions.assertEquals(fN,mockUser.getFirstName());
    }

    @Test
    public void testGetSetFirstName_negative(){
        String fN = "Strong";
        User mockUser = new User();

        mockUser.setFirstName(fN);

        Assertions.assertNotEquals(fN +"fe",mockUser.getFirstName());
    }

    @Test
    public void testGetSetLastName_positive(){
        String lN = "Bad";
        User mockUser = new User();

        mockUser.setLastName(lN);

        Assertions.assertEquals(lN, mockUser.getLastName());
    }

    @Test
    public void testGetSetLastName_negative(){
        String lN = "Bad";
        User mockUser = new User();

        mockUser.setLastName(lN);

        Assertions.assertNotEquals(lN + "en", mockUser.getLastName());
    }

    @Test
    public void testGetSetEmail_positive(){
        String eM = "last@place.com";
        User mockUser = new User();

        mockUser.setEmail(eM);

        Assertions.assertEquals(eM, mockUser.getEmail());
    }

    @Test
    public void testGetSetEmail_negative(){
        String eM = "last@place.com";
        User mockUser = new User();

        mockUser.setEmail(eM);

        Assertions.assertNotEquals(eM + "ad", mockUser.getEmail());
    }


}
