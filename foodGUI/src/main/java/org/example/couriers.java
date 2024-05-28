package org.example;

public class couriers
{
    private int courier_id;
    private int courier_document;
    private long phone;
    private Status status;
    private String name;
    private String surname;
    private String patronymics;
    private String experience;
    private TypeOfCourier type_of_courier;
    private double delivery_price;

    public enum Status
    {
        busy, free, delivering
    }

    public enum TypeOfCourier
    {
        walking, bicycling, by_car
    }

    public couriers(int courierId, int courierDocument, long phone, Status status, String name,
                   String surname, String patronymics, String experience, TypeOfCourier typeOfCourier,
                   double deliveryPrice) {
        this.courier_id = courierId;
        this.courier_document = courierDocument;
        this.phone = phone;
        this.status = status;
        this.name = name;
        this.surname = surname;
        this.patronymics = patronymics;
        this.experience = experience;
        this.type_of_courier = typeOfCourier;
        this.delivery_price = deliveryPrice;
    }


    public int getCourierId() {
        return courier_id;
    }

    public void setCourierId(int courierId) {
        this.courier_id = courierId;
    }

    public int getCourierDocument() {
        return courier_document;
    }

    public void setCourierDocument(int courierDocument) {
        this.courier_document = courierDocument;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymics() {
        return patronymics;
    }

    public void setPatronymics(String patronymics) {
        this.patronymics = patronymics;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public TypeOfCourier getTypeOfCourier() {
        return type_of_courier;
    }

    public void setTypeOfCourier(TypeOfCourier typeOfCourier) {
        this.type_of_courier = typeOfCourier;
    }

    public double getDeliveryPrice() {
        return delivery_price;
    }

    public void setDeliveryPrice(double deliveryPrice) {
        this.delivery_price = deliveryPrice;
    }
}
