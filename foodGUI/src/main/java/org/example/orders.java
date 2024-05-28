package org.example;
import java.util.Date;

public class orders
{
    private int order_id;
    private int client_id;
    private int couriers_id;
    private Date datetime;
    private Status status;
    private double price;

    public enum Status {
        created, delivery, in_progress, delivered
    }

    public orders(int order_id, int client_id, int couriers_id, Date datetime, Status status, double price) {
        this.order_id = order_id;
        this.client_id = client_id;
        this.couriers_id = couriers_id;
        this.datetime = datetime;
        this.status = status;
        this.price = price;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public int getCouriers_id() {
        return couriers_id;
    }

    public void setCouriers_id(int couriers_id) {
        this.couriers_id = couriers_id;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
