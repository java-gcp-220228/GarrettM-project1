package com.revature.model;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Objects;

public class Ticket {

    private int id;
    private String ticketName;
    private String status;
    private String description;
    private String type;
    private float amount;

    private User employee;

    private String creationTime;

    private User financeManger;

    private String resolutionTime;

    public Ticket() {
    }

    /*public Ticket(int id, String ticketName, String status, String description, String type, User employee) {
        this.id = id;
        this.ticketName = ticketName;
        this.status = status;
        this.description = description;
        this.type = type;
        this.employee = employee;
    }*/

    public Ticket(int id, String ticketName, String status, String description, String type, float amount,
                  String creationTime, String resolutionTime, User employee, User financeManger) {
        this.id = id;
        this.ticketName = ticketName;
        this.status = status;
        this.description = description;
        this.type = type;
        this.amount = amount;
        this.employee = employee;
        this.creationTime = creationTime;
        this.financeManger = financeManger;
        this.resolutionTime = resolutionTime;
    }

    public Ticket(int id, String ticketName, String status, String description, String type, float amount, User employee, User financeManger) {
        this.id = id;
        this.ticketName = ticketName;
        this.status = status;
        this.description = description;
        this.type = type;
        this.amount = amount;
        this.employee = employee;
        this.financeManger = financeManger;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTicketName() {
        return ticketName;
    }

    public void setTicketName(String ticketName) {
        this.ticketName = ticketName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public User getEmployee() {
        return employee;
    }

    public void setEmployee(User employee) {
        this.employee = employee;
    }

    public User getFinanceManger() {
        return financeManger;
    }

    public void setFinanceManger(User financeManger) {
        this.financeManger = financeManger;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getResolutionTime() {
        return resolutionTime;
    }

    public void setResolutionTime(String resolutionTime) {
        this.resolutionTime = resolutionTime;
    }


    public int hashCode() {
        return Objects.hash(id, ticketName, status, amount, creationTime, resolutionTime, employee, financeManger);
    }


    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Ticket that = (Ticket) obj;
        return id == that.id && Objects.equals(status, that.status) && Objects.equals(ticketName, that.ticketName) && Objects.equals(employee, that.employee)
                && Objects.equals(financeManger, that.financeManger) && Objects.equals(description, that.description) && Objects.equals(type, that.type)
                && Objects.equals(creationTime, that.creationTime) && Objects.equals(resolutionTime, that.resolutionTime) && amount == that.amount;
    }


    public String toString() {
        return "Ticket{" +
                "id="+ id +
                ", ticketName='" + ticketName + '\'' +
                ", approval=" + status + '\'' +
                ", description=" + description + '\'' +
                ", type=" + type+ '\'' +
                ", amount=" + amount + '\'' +
                ", Creation time=" + creationTime +'\'' +
                ", Resolution time="+ resolutionTime + '\'' +
                ", employee=" + employee.getUsername() + '\'' +
                ", financeManager=" + financeManger.getUsername() + '\'' +
                '}'; //wait this is Json
    }
}
