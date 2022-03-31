package com.revature.dto;

import java.util.Objects;

public class ResponseTicketDTO {

    private int id;
    private String ticketName;
    private String Status;
    private String description;
    private String type;
    private float amount;
    private String createTime;
    private String resolutionTime;

    private String employeeUserName;
    private String financeManagerUserName;

    public ResponseTicketDTO() {
    }

    public ResponseTicketDTO(int id, String ticketName, String status, String description, String type, float amount,String createTime, String resolutionTime, String employeeUserName, String financeManagerUserName) {
        this.id = id;
        this.ticketName = ticketName;
        Status = status;
        this.description = description;
        this.type = type;
        this.amount = amount;
        this.createTime = createTime;
        this.resolutionTime = resolutionTime;

        this.employeeUserName = employeeUserName;
        this.financeManagerUserName = financeManagerUserName;
    }

    public ResponseTicketDTO(int id, String ticketName, String status, String description, String type, float amount, String employeeUserName, String financeManagerUserName) {
        this.id = id;
        this.ticketName = ticketName;
        Status = status;
        this.description = description;
        this.type = type;
        this.amount = amount;
        this.employeeUserName = employeeUserName;
        this.financeManagerUserName = financeManagerUserName;
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
        return Status;
    }

    public void setStatus(String status) {
        this.Status = status;
    }

    public String getEmployeeUserName() {
        return employeeUserName;
    }

    public void setEmployeeUserName(String employeeUserName) {
        this.employeeUserName = employeeUserName;
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

    public String getFinanceManagerUserName() {
        return financeManagerUserName;
    }

    public void setFinanceManagerUserName(String financeManagerUserName) {
        this.financeManagerUserName = financeManagerUserName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getResolutionTime() {
        return resolutionTime;
    }

    public void setResolutionTime(String resolutionTime) {
        this.resolutionTime = resolutionTime;
    }

    @Override //todo update overrides to include resolution and create times
    public int hashCode() {
        return Objects.hash(id, ticketName, Status, description, type, employeeUserName, financeManagerUserName);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ResponseTicketDTO that = (ResponseTicketDTO) obj;
        return id == that.id && Objects.equals(ticketName, that.ticketName) && Objects.equals(Status, that.Status)
                && Objects.equals(description, that.description)  && Objects.equals(type, that.type)
                && Objects.equals(employeeUserName, that.employeeUserName) && Objects.equals(financeManagerUserName, that.financeManagerUserName);
    }

    @Override
    public String toString() {
        return "GetAssignmentDTO{" +
                "id=" + id +
                ", ticketName='" + ticketName + '\'' +
                ", status=" + Status +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", employeeUsername='" + employeeUserName + '\'' +
                ", financeManagerUsername='" + financeManagerUserName + '\'' +
                '}';
    }
}
