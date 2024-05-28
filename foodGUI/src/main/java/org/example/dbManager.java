package org.example;

import java.awt.*;
import java.sql.Connection;
import org.example.couriers.Status;
import org.example.couriers.TypeOfCourier;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
public class dbManager
{
    private String jdbcURL;
    private String username;
    private String password;
    private Connection connection;

    public dbManager(String jdbcURL, String username, String password) {
        this.jdbcURL = jdbcURL;
        this.username = username;
        this.password = password;
    }
    public void connect() throws SQLException {
        connection = DriverManager.getConnection(jdbcURL, username, password);
        System.out.println("Successfully connected to the database.");
    }
    public void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Disconnected from the database.");
        }
    }

    public List<client> getAllClients() {
        List<client> clients = new ArrayList<>();
        String sql = "SELECT * FROM client";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int client_id = resultSet.getInt("client_id");
                int document = resultSet.getInt("document");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String patronymics = resultSet.getString("patronymics");
                String phone_number = resultSet.getString("phone_number");
                String city = resultSet.getString("city");
                String street = resultSet.getString("Street");
                String apartments = resultSet.getString("Apartments");

                clients.add(new client(client_id, document, name, surname, patronymics, phone_number, city, street, apartments));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }
    public List<couriers> getAllCouriers() {
        List<couriers> couriersList = new ArrayList<>();
        String sql = "SELECT * FROM couriers";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int courier_id = resultSet.getInt("courier_id");
                int courier_document = resultSet.getInt("courier_document");
                long phone = resultSet.getLong("phone");
                couriers.Status status = couriers.Status.valueOf(resultSet.getString("status"));
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String patronymics = resultSet.getString("patronymics");
                String experience = resultSet.getString("expirience");
                couriers.TypeOfCourier type_of_courier = couriers.TypeOfCourier.valueOf(resultSet.getString("type_of_courier").replace(" ", "_"));
                double delivery_price = resultSet.getDouble("delivery_price");

                couriersList.add(new couriers(courier_id, courier_document, phone, status, name, surname, patronymics, experience, type_of_courier, delivery_price));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return couriersList;
    }

    public List<menu> getAllMenu() {
        List<menu> menuItems = new ArrayList<>();
        String sql = "SELECT * FROM menu";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int position_id = resultSet.getInt("position_id");
                int restaurants_id = resultSet.getInt("restaraunts_id");
                int positionTypes_id = resultSet.getInt("position_types_id");
                double price = resultSet.getDouble("price");
                String foodName = resultSet.getString("food_name");

                menuItems.add(new menu(position_id,restaurants_id,positionTypes_id,price,foodName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menuItems;
    }
    public List<ordered_food> getAllOrderedFood() {
        List<ordered_food> orderedFoodItems = new ArrayList<>();
        String sql = "SELECT * FROM ordered_food";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int order_id = resultSet.getInt("order_id");
                int menu_position_id = resultSet.getInt("menu_position_id");
                int count = resultSet.getInt("count");

                orderedFoodItems.add(new ordered_food(order_id, menu_position_id, count));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderedFoodItems;
    }
    public List<orders> getAllOrders() {
        List<orders> ordersList = new ArrayList<>();
        String sql = "SELECT * FROM orders";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int order_id = resultSet.getInt("order_id");
                int client_id = resultSet.getInt("client_id");
                int couriers_id = resultSet.getInt("couriers_id");
                Date datetime = resultSet.getDate("datetime");
                orders.Status status = orders.Status.valueOf(resultSet.getString("status").replace(" ", "_"));
                double price = resultSet.getDouble("price");

                ordersList.add(new orders(order_id, client_id, couriers_id, datetime, status, price));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ordersList;
    }

    public List<position_types> getAllPositionTypes() {
        List<position_types> positionTypesList = new ArrayList<>();
        String sql = "SELECT * FROM position_types";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int position_type_id = resultSet.getInt("position_type_id");
                String food_type = resultSet.getString("food_type");

                positionTypesList.add(new position_types(position_type_id, food_type));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return positionTypesList;
    }

    public List<reports> getAllReports() {
        List<reports> reportsList = new ArrayList<>();
        String sql = "SELECT * FROM reports";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int order_id = resultSet.getInt("order_id");
                Date compl_datetime = resultSet.getDate("compl_datetime");
                short grade = resultSet.getShort("grade");
                String comment_cl = resultSet.getString("comment_cl");
                String result = resultSet.getString("result");

                reportsList.add(new reports(order_id, compl_datetime, grade, comment_cl, result));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reportsList;
    }
    public List<restaraunts> getAllRestaraunts() {
        List<restaraunts> restarauntsList = new ArrayList<>();
        String sql = "SELECT * FROM restaraunts";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int restaraunt_id = resultSet.getInt("restaraunt_id");
                String address = resultSet.getString("address");
                String name = resultSet.getString("name");

                restarauntsList.add(new restaraunts(restaraunt_id, address, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return restarauntsList;
    }

    public void insertClient(int clientId, int document, String name, String surname, String patronymics, String phoneNumber, String city, String street, String apartments) throws SQLException {
        String sql = "INSERT INTO client (client_id, document, name, surname, patronymics, phone_number, City, Street, Apartments) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, clientId);
        statement.setInt(2, document);
        statement.setString(3, name);
        statement.setString(4, surname);
        statement.setString(5, patronymics);
        statement.setString(6, phoneNumber);
        statement.setString(7, city);
        statement.setString(8, street);
        statement.setString(9, apartments);

        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("A new client was inserted successfully!");
        }
        statement.close();
    }
    public void insertCourier(int courierId, int courierDocument, long phone, Status status, String name, String surname, String patronymics, String experience, TypeOfCourier typeOfCourier, double deliveryPrice) throws SQLException {
        String sql = "INSERT INTO couriers (courier_id, courier_document, phone, status, name, surname, patronymics, expirience, type_of_courier, delivery_price) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, courierId);
        statement.setInt(2, courierDocument);
        statement.setLong(3, phone);
        statement.setString(4, status.toString());
        statement.setString(5, name);
        statement.setString(6, surname);
        statement.setString(7, patronymics);
        statement.setString(8, experience);
        statement.setString(9, typeOfCourier.toString());
        statement.setDouble(10, deliveryPrice);

        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("A new courier was inserted successfully!");
        }
        statement.close();
    }

    public void insertMenuItem(int positionId, int restarauntsId, int positionTypesId, double price, String foodName) throws SQLException {
        String sql = "INSERT INTO menu (position_id, restaraunts_id, position_types_id, price, food_name) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, positionId);
        statement.setInt(2, restarauntsId);
        statement.setInt(3, positionTypesId);
        statement.setDouble(4, price);
        statement.setString(5, foodName);

        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("A new menu item was inserted successfully!");
        }
        statement.close();
    }
    public void insertOrderedFood(int orderId, int menuPositionId, int count) throws SQLException {
        String sql = "INSERT INTO ordered_food (order_id, menu_position_id, count) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, orderId);
        statement.setInt(2, menuPositionId);
        statement.setInt(3, count);

        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("New ordered food item was inserted successfully!");
        }
        statement.close();
    }

    public void insertOrder(int orderId, int clientId, int courierId, Date datetime, String status, double price) throws SQLException {
        String sql = "INSERT INTO orders (order_id, client_id, couriers_id, datetime, status, price) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, orderId);
        statement.setInt(2, clientId);
        statement.setInt(3, courierId);
        statement.setDate(4, datetime);
        statement.setString(5, status);
        statement.setDouble(6, price);

        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("New order was inserted successfully!");
        }
        statement.close();
    }

    public void insertPositionType(int positionTypeId, String foodType) throws SQLException {
        String sql = "INSERT INTO position_types (position_type_id, food_type) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, positionTypeId);
        statement.setString(2, foodType);

        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("New position type was inserted successfully!");
        }
        statement.close();
    }
    public void insertReport(int orderId, Date complDatetime, int grade, String comment, String result) throws SQLException {
        String sql = "INSERT INTO reports (order_id, compl_datetime, grade, comment_cl, result) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, orderId);
        statement.setDate(2, complDatetime);
        statement.setInt(3, grade);
        statement.setString(4, comment);
        statement.setString(5, result);

        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("New report was inserted successfully!");
        }
        statement.close();
    }
    public void insertRestaurant(int restarauntId, String address, String name) throws SQLException {
        String sql = "INSERT INTO restaraunts (restaraunt_id, address, name) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, restarauntId);
        statement.setString(2, address);
        statement.setString(3, name);

        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("New restaurant was inserted successfully!");
        }
        statement.close();
    }


    public void deleteClient(int clientId) throws SQLException {
        String sql = "DELETE FROM client WHERE client_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, clientId);
        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("Client with ID " + clientId + " was deleted successfully!");
        }
        statement.close();
    }

    public void deleteCourier(int courierId) throws SQLException {
        String sql = "DELETE FROM couriers WHERE courier_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, courierId);
        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("Courier with ID " + courierId + " was deleted successfully!");
        }
        statement.close();
    }

    public void deleteMenuItem(int positionId) throws SQLException {
        String sql = "DELETE FROM menu WHERE position_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, positionId);
        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("Menu item with ID " + positionId + " was deleted successfully!");
        }
        statement.close();
    }

    public void deleteOrderedFood(int orderId, int menuPositionId) throws SQLException {
        String sql = "DELETE FROM ordered_food WHERE order_id = ? AND menu_position_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, orderId);
        statement.setInt(2, menuPositionId);
        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("Ordered food with Order ID " + orderId + " and Menu Position ID " + menuPositionId + " was deleted successfully!");
        }
        statement.close();
    }
    public void deleteOrder(int orderId) throws SQLException {
        String sql = "DELETE FROM orders WHERE order_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, orderId);
        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("Order with ID " + orderId + " was deleted successfully!");
        }
        statement.close();
    }

    public void deletePositionType(int positionTypeId) throws SQLException {
        String sql = "DELETE FROM position_types WHERE position_type_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, positionTypeId);
        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("Position type with ID " + positionTypeId + " was deleted successfully!");
        }
        statement.close();
    }
    public void deleteReport(int orderId) throws SQLException {
        String sql = "DELETE FROM reports WHERE order_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, orderId);
        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("Report with Order ID " + orderId + " was deleted successfully!");
        }
        statement.close();
    }

    public void deleteRestaraunt(int restarauntId) throws SQLException {
        String sql = "DELETE FROM restaraunts WHERE restaraunt_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, restarauntId);
        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("Restaraunt with ID " + restarauntId + " was deleted successfully!");
        }
        statement.close();
    }
}