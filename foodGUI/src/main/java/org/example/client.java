package org.example;

public class client
{
    private int client_id;
    private int document;
    private String name;
    private String surname;
    private String patronymics;
    private String phone_number;
    private String city;
    private String street;
    private String apartments;

    public client(int client_id, int document, String name, String surname, String patronymics, String phoneNumber, String city, String street, String apartments) {
        this.client_id = client_id;
        this.document = document;
        this.name = name;
        this.surname = surname;
        this.patronymics = patronymics;
        this.phone_number = phoneNumber;
        this.city = city;
        this.street = street;
        this.apartments = apartments;
    }


    public int getClientId() {
        return client_id;
    }

    public int getDocument() {
        return document;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPatronymics() {
        return patronymics;
    }

    public String getPhoneNumber() {
        return phone_number;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getApartments() {
        return apartments;
    }

    public void setClientId(int clientId) {
        this.client_id = clientId;
    }

    public void setDocument(int document) {
        this.document = document;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPatronymics(String patronymics) {
        this.patronymics = patronymics;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phone_number = phoneNumber;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setApartments(String apartments) {
        this.apartments = apartments;
    }
}
