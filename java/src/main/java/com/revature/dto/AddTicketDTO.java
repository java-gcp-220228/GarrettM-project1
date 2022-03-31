package com.revature.dto;

import com.revature.model.User;

import java.io.InputStream;
import java.util.Objects;

public class AddTicketDTO {

    private String ticketName;
    private String description;
    private String type;
    private float amount;

    private InputStream image;
    public AddTicketDTO() {
    }

    public AddTicketDTO(String ticketName, String description, String type, float amount) {
        this.ticketName = ticketName;
        this.description = description;
        this.type = type;
        this.amount = amount;


    }

    public AddTicketDTO(String ticketName, String status, String description, String type, float amount, InputStream image) {
        this.ticketName = ticketName;
        this.description = description;
        this.type = type;
        this.amount = amount;
        this.image = image;
    }

    public String getTicketName() {
        return ticketName;
    }

    public void setTicketName(String ticketName) {
        this.ticketName = ticketName;
    }

    public InputStream getImage() {
        return image;
    }

    public void setImage(InputStream image) {
        this.image = image;
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

    @Override
    public int hashCode() {
        return Objects.hash(ticketName, description, type, image);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        AddTicketDTO that = (AddTicketDTO) obj;
        return Objects.equals(ticketName, that.ticketName) && Objects.equals(description, that.description)
                && Objects.equals(type, that.type) && Objects.equals(image, that.image);
    }

    @Override
    public String toString() {
        return "AddTicketDTO{" +
                "ticketName='" + ticketName + '\''+
                ", description='" + description + '\'' +
                ", image=" + image +
                '}';
    }
}
