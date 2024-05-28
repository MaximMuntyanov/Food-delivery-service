package org.example;

import com.mysql.cj.xdevapi.Client;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;
import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.stream.Collectors;
public class Main extends Application
{
    public dbManager dbFoodAPP;
    public TableView<client> clientTable;
    public TableView<couriers> couriersTable;
    public TableView<menu> menuTable;
    public TableView<ordered_food> ordered_foodTable;
    public TableView<orders> ordersTable;
    public TableView<position_types> position_typesTable;
    public TableView<reports> reportsTable;
    public TableView<restaraunts> restarauntsTable;

    public ObservableList<client> client;
    public ObservableList<couriers> couriers;
    public ObservableList<menu> menu;
    public ObservableList<ordered_food> ordered_food;
    public ObservableList<orders> orders;
    public ObservableList<position_types> position_types;
    public ObservableList<reports> reports;
    public ObservableList<restaraunts> restaraunts;


    @Override
    public void start(Stage primaryStage)
    {
        dbFoodAPP = new dbManager("jdbc:mysql://127.0.0.1:3306/fooddeliveryservice", "root", "ERROR348");
        try {
            dbFoodAPP.connect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        VBox root = new VBox();


        client = FXCollections.observableArrayList(dbFoodAPP.getAllClients());
        couriers = FXCollections.observableArrayList(dbFoodAPP.getAllCouriers());
        menu = FXCollections.observableArrayList(dbFoodAPP.getAllMenu());
        ordered_food = FXCollections.observableArrayList(dbFoodAPP.getAllOrderedFood());
        orders = FXCollections.observableArrayList(dbFoodAPP.getAllOrders());
        position_types = FXCollections.observableArrayList(dbFoodAPP.getAllPositionTypes());
        reports = FXCollections.observableArrayList(dbFoodAPP.getAllReports());
        restaraunts = FXCollections.observableArrayList(dbFoodAPP.getAllRestaraunts());

        //тут все необходимые кнопки
        RadioButton clientRadioButton = new RadioButton("client");
        RadioButton couriersRadioButton = new RadioButton("couriers");
        RadioButton menuRadioButton = new RadioButton("menu");
        RadioButton ordered_foodRadioButton = new RadioButton("ordered food");
        RadioButton ordersRadioButton = new RadioButton("orders");
        RadioButton position_typesRadioButton = new RadioButton("position types");
        RadioButton reportsRadioButton = new RadioButton("reports");
        RadioButton restarauntsRadioButton = new RadioButton("restaraunts");
        RadioButton test = new RadioButton("testInsert");


        clientRadioButton.setOnAction(event -> {
            if (clientRadioButton.isSelected())
            {
                if (clientTable != null)
                {
                    clientTable = null;
                    root.getChildren().remove(test);
                }
                initializeClientTable(client);
                root.getChildren().addAll(clientTable,test);

            }
            else
            {
                root.getChildren().remove(clientTable);
                clientTable.refresh();
            }
        });

        couriersRadioButton.setOnAction(event -> {
            if (couriersRadioButton.isSelected())
            {
                initializeCourierTable(couriers);
                root.getChildren().addAll(couriersTable);
            }
            else
            {
                root.getChildren().remove(couriersTable);
            }
        });

        menuRadioButton.setOnAction(event -> {
            if (menuRadioButton.isSelected())
            {
                initializeMenuTable(menu);
                root.getChildren().addAll(menuTable);
            }
            else
            {
                root.getChildren().remove(menuTable);
            }
        });

        ordered_foodRadioButton.setOnAction(event -> {
            if (ordered_foodRadioButton.isSelected())
            {
                initializeOrderedFoodTable(ordered_food);
                root.getChildren().addAll(ordered_foodTable);
            }
            else
            {
                root.getChildren().remove(ordered_foodTable);
            }
        });

        ordersRadioButton.setOnAction(event -> {
            if (ordersRadioButton.isSelected())
            {
                initializeOrdersTable(orders);
                root.getChildren().addAll(ordersTable);
            }
            else
            {
                root.getChildren().remove(ordersTable);
            }
        });

        position_typesRadioButton.setOnAction(event -> {
            if (position_typesRadioButton.isSelected())
            {
                initializePositionTypesTable(position_types);
                root.getChildren().addAll(position_typesTable);
            }
            else
            {
                root.getChildren().remove(position_typesTable);
            }
        });

        reportsRadioButton.setOnAction(event -> {
            if (reportsRadioButton.isSelected())
            {
                initializeReportsTable(reports);
                root.getChildren().addAll(reportsTable);
            }
            else
            {
                root.getChildren().remove(reportsTable);
            }
        });

        restarauntsRadioButton.setOnAction(event -> {
            if(restarauntsRadioButton.isSelected())
            {
                initializeRestarauntsTable(restaraunts);
                root.getChildren().addAll(reportsTable);
            }
            else
            {
                root.getChildren().remove(reportsTable);
            }
        });
        test.setOnAction(event -> {
            showInsertClientWindow(primaryStage);
        });

        //При создание новой кнопки вписывай сюда её название
        root.getChildren().addAll(clientRadioButton,couriersRadioButton,menuRadioButton,ordered_foodRadioButton,ordersRadioButton,position_typesRadioButton,reportsRadioButton);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Food Delivery Service");
        primaryStage.show();

    }

    public void initializeClientTable(ObservableList<client> clientData) {
        clientTable = new TableView<>(clientData);

        TableColumn<client, Integer> clientIdColumn = new TableColumn<>("client_id");
        clientIdColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getClientId()));

        TableColumn<client, Integer> documentColumn = new TableColumn<>("Document");
        documentColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDocument()));

        TableColumn<client, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        TableColumn<client, String> surnameColumn = new TableColumn<>("Surname");
        surnameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSurname()));

        TableColumn<client, String> patronymicsColumn = new TableColumn<>("Patronymics");
        patronymicsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPatronymics()));

        TableColumn<client, String> phoneNumberColumn = new TableColumn<>("Phone Number");
        phoneNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhoneNumber()));

        TableColumn<client, String> cityColumn = new TableColumn<>("City");
        cityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCity()));

        TableColumn<client, String> streetColumn = new TableColumn<>("Street");
        streetColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStreet()));

        TableColumn<client, String> apartmentsColumn = new TableColumn<>("Apartments");
        apartmentsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApartments()));

        clientTable.getColumns().addAll(clientIdColumn, documentColumn, nameColumn, surnameColumn,
                patronymicsColumn, phoneNumberColumn, cityColumn, streetColumn, apartmentsColumn);

    }
    public void initializeCourierTable(ObservableList<couriers> courierData) {
        couriersTable = new TableView<>(courierData);

        TableColumn<couriers, Integer> courierIdColumn = new TableColumn<>("courier_id");
        courierIdColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCourierId()));

        TableColumn<couriers, Integer> courierDocumentColumn = new TableColumn<>("Courier Document");
        courierDocumentColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCourierDocument()));

        TableColumn<couriers, Long> phoneColumn = new TableColumn<>("Phone");
        phoneColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPhone()));

        TableColumn<couriers, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus().toString()));

        TableColumn<couriers, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        TableColumn<couriers, String> surnameColumn = new TableColumn<>("Surname");
        surnameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSurname()));

        TableColumn<couriers, String> patronymicsColumn = new TableColumn<>("Patronymics");
        patronymicsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPatronymics()));

        TableColumn<couriers, String> experienceColumn = new TableColumn<>("Experience");
        experienceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getExperience()));

        TableColumn<couriers, String> typeOfCourierColumn = new TableColumn<>("Type of Courier");
        typeOfCourierColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTypeOfCourier().toString()));

        TableColumn<couriers, Double> deliveryPriceColumn = new TableColumn<>("Delivery Price");
        deliveryPriceColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDeliveryPrice()));

        couriersTable.getColumns().addAll(courierIdColumn, courierDocumentColumn, phoneColumn, statusColumn,
                nameColumn, surnameColumn, patronymicsColumn, experienceColumn, typeOfCourierColumn, deliveryPriceColumn);
    }


    public void initializeMenuTable(ObservableList<menu> menuData) {
        menuTable = new TableView<>(menuData);

        TableColumn<menu, Integer> positionIdColumn = new TableColumn<>("position_id");
        positionIdColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPositionId()));

        TableColumn<menu, Integer> restarauntsIdColumn = new TableColumn<>("restaraunts_id");
        restarauntsIdColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getRestaurantsId()));

        TableColumn<menu, Integer> positionTypesIdColumn = new TableColumn<>("position_types_id");
        positionTypesIdColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPositionTypesId()));

        TableColumn<menu, Double> priceColumn = new TableColumn<>("price");
        priceColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPrice()));

        TableColumn<menu, String> foodNameColumn = new TableColumn<>("food_name");
        foodNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFoodName()));

        menuTable.getColumns().addAll(positionIdColumn, restarauntsIdColumn, positionTypesIdColumn, priceColumn, foodNameColumn);
    }

    public void initializeOrderedFoodTable(ObservableList<ordered_food> orderedFoodData) {
        ordered_foodTable = new TableView<>(orderedFoodData);

        TableColumn<ordered_food, Integer> orderIdColumn = new TableColumn<>("order_id");
        orderIdColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getOrderId()));

        TableColumn<ordered_food, Integer> menuPositionIdColumn = new TableColumn<>("menu_position_id");
        menuPositionIdColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getMenuPositionId()));

        TableColumn<ordered_food, Integer> countColumn = new TableColumn<>("count");
        countColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCount()));

        ordered_foodTable.getColumns().addAll(orderIdColumn, menuPositionIdColumn, countColumn);
    }

    public void initializeOrdersTable(ObservableList<orders> ordersData) {
        ordersTable = new TableView<>(ordersData);

        TableColumn<orders, Integer> orderIdColumn = new TableColumn<>("order_id");
        orderIdColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getOrder_id()));

        TableColumn<orders, Integer> clientIdColumn = new TableColumn<>("client_id");
        clientIdColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getClient_id()));

        TableColumn<orders, Integer> couriersIdColumn = new TableColumn<>("couriers_id");
        couriersIdColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCouriers_id()));

        TableColumn<orders, String> datetimeColumn = new TableColumn<>("datetime");
        datetimeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDatetime().toString()));

        TableColumn<orders, String> statusColumn = new TableColumn<>("status");
        statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus().toString()));

        TableColumn<orders, Double> priceColumn = new TableColumn<>("price");
        priceColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPrice()));

        ordersTable.getColumns().addAll(orderIdColumn, clientIdColumn, couriersIdColumn, datetimeColumn, statusColumn, priceColumn);
    }

    public void initializePositionTypesTable(ObservableList<position_types> positionTypesData) {
        position_typesTable = new TableView<>(positionTypesData);

        TableColumn<position_types, Integer> positionTypeIdColumn = new TableColumn<>("position_type_id");
        positionTypeIdColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPosition_type_id()));

        TableColumn<position_types, String> foodTypeColumn = new TableColumn<>("food_type");
        foodTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFood_type()));

        position_typesTable.getColumns().addAll(positionTypeIdColumn, foodTypeColumn);
    }

    public void initializeReportsTable(ObservableList<reports> reportsData) {
        reportsTable = new TableView<>(reportsData);

        TableColumn<reports, Integer> orderIdColumn = new TableColumn<>("order_id");
        orderIdColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getOrder_id()));

        TableColumn<reports, Date> complDatetimeColumn = new TableColumn<>("compl_datetime");
        complDatetimeColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCompl_datetime()));

        TableColumn<reports, Short> gradeColumn = new TableColumn<>("grade");
        gradeColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getGrade()));

        TableColumn<reports, String> commentClColumn = new TableColumn<>("comment_cl");
        commentClColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getComment_cl()));

        TableColumn<reports, String> resultColumn = new TableColumn<>("result");
        resultColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getResult()));

        reportsTable.getColumns().addAll(orderIdColumn, complDatetimeColumn, gradeColumn, commentClColumn, resultColumn);
    }

    public void initializeRestarauntsTable(ObservableList<restaraunts> restarauntsData) {
        restarauntsTable = new TableView<>(restarauntsData);

        TableColumn<restaraunts, Integer> restarauntIdColumn = new TableColumn<>("restaraunt_id");
        restarauntIdColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getRestaraunt_id()));

        TableColumn<restaraunts, String> addressColumn = new TableColumn<>("address");
        addressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));

        TableColumn<restaraunts, String> nameColumn = new TableColumn<>("name");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        restarauntsTable.getColumns().addAll(restarauntIdColumn, addressColumn, nameColumn);
    }
    private void showInsertClientWindow(Stage primaryStage) {
        Stage insertClientStage = new Stage();
        insertClientStage.setTitle("Insert Client");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        TextField clientIdField = new TextField();
        clientIdField.setPromptText("Enter client ID");
        gridPane.add(new Label("Client ID:"), 0, 0);
        gridPane.add(clientIdField, 1, 0);

        TextField documentField = new TextField();
        documentField.setPromptText("Enter document");
        gridPane.add(new Label("Document:"), 0, 1);
        gridPane.add(documentField, 1, 1);

        TextField nameField = new TextField();
        nameField.setPromptText("Enter name");
        gridPane.add(new Label("Name:"), 0, 2);
        gridPane.add(nameField, 1, 2);

        TextField surnameField = new TextField();
        surnameField.setPromptText("Enter surname");
        gridPane.add(new Label("Surname:"), 0, 3);
        gridPane.add(surnameField, 1, 3);

        TextField patronymicsField = new TextField();
        patronymicsField.setPromptText("Enter patronymics");
        gridPane.add(new Label("Patronymics:"), 0, 4);
        gridPane.add(patronymicsField, 1, 4);

        TextField phoneNumberField = new TextField();
        phoneNumberField.setPromptText("Enter phone number");
        gridPane.add(new Label("Phone Number:"), 0, 5);
        gridPane.add(phoneNumberField, 1, 5);

        TextField cityField = new TextField();
        cityField.setPromptText("Enter city");
        gridPane.add(new Label("City:"), 0, 6);
        gridPane.add(cityField, 1, 6);

        TextField streetField = new TextField();
        streetField.setPromptText("Enter street");
        gridPane.add(new Label("Street:"), 0, 7);
        gridPane.add(streetField, 1, 7);

        TextField apartmentsField = new TextField();
        apartmentsField.setPromptText("Enter apartments");
        gridPane.add(new Label("Apartments:"), 0, 8);
        gridPane.add(apartmentsField, 1, 8);

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            int clientId = Integer.parseInt(clientIdField.getText());
            int document = Integer.parseInt(documentField.getText());
            String name = nameField.getText();
            String surname = surnameField.getText();
            String patronymics = patronymicsField.getText();
            String phoneNumber = phoneNumberField.getText();
            String city = cityField.getText();
            String street = streetField.getText();
            String apartments = apartmentsField.getText();

            if (!name.isEmpty() && !surname.isEmpty() && !patronymics.isEmpty() && !phoneNumber.isEmpty() && !city.isEmpty() && !street.isEmpty() && !apartments.isEmpty()) {
                try {
                    dbFoodAPP.insertClient(clientId, document, name, surname, patronymics, phoneNumber, city, street, apartments);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                client.setAll(dbFoodAPP.getAllClients());
                insertClientStage.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setHeaderText(null);
                alert.setContentText("Please fill in all fields.");
                alert.showAndWait();
            }
        });
        gridPane.add(addButton, 1, 9);

        Scene scene = new Scene(gridPane, 300, 350);
        insertClientStage.setScene(scene);
        insertClientStage.initOwner(primaryStage);
        insertClientStage.initModality(Modality.WINDOW_MODAL);
        insertClientStage.show();
    }

    public static void main(String[] args)
    {
    launch(args);
    }
}