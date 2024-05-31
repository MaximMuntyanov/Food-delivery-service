package org.example;
import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.Date;
import org.example.couriers.Status;
import org.example.couriers.TypeOfCourier;
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

        client = FXCollections.observableArrayList(dbFoodAPP.getAllClients());
        couriers = FXCollections.observableArrayList(dbFoodAPP.getAllCouriers());
        menu = FXCollections.observableArrayList(dbFoodAPP.getAllMenu());
        ordered_food = FXCollections.observableArrayList(dbFoodAPP.getAllOrderedFood());
        orders = FXCollections.observableArrayList(dbFoodAPP.getAllOrders());
        position_types = FXCollections.observableArrayList(dbFoodAPP.getAllPositionTypes());
        reports = FXCollections.observableArrayList(dbFoodAPP.getAllReports());
        restaraunts = FXCollections.observableArrayList(dbFoodAPP.getAllRestaraunts());

        //тут все необходимые кнопки
        RadioButton clientRadioButton = new RadioButton("Client");
        RadioButton couriersRadioButton = new RadioButton("Couriers");
        RadioButton menuRadioButton = new RadioButton("Menu");
        RadioButton ordered_foodRadioButton = new RadioButton("Ordered food");
        RadioButton ordersRadioButton = new RadioButton("Orders");
        RadioButton position_typesRadioButton = new RadioButton("Position types");
        RadioButton reportsRadioButton = new RadioButton("Reports");
        RadioButton restarauntsRadioButton = new RadioButton("Restaraunts");


        Button deleteButton = new Button("Delete");
        Button createButton = new Button("Create");
        Button updateButton = new Button("Update");

        CheckBox IsDelivered = new CheckBox("Is delivered?");

        VBox root = new VBox();

        Map<RadioButton, Runnable> createsMap = new HashMap<>();
        createsMap.put(clientRadioButton, () -> showInsertClientWindow(primaryStage));
        createsMap.put(couriersRadioButton, () -> showInsertCourierWindow(primaryStage));
        createsMap.put(menuRadioButton, () -> showInsertMenuWindow(primaryStage));
        createsMap.put(ordered_foodRadioButton, () -> showInsertOrderedFoodWindow(primaryStage));
        createsMap.put(ordersRadioButton, () -> showInsertOrdersWindow(primaryStage));
        createsMap.put(position_typesRadioButton, () -> showInsertPositionTypesWindow(primaryStage));
        createsMap.put(reportsRadioButton, () -> showInsertReportWindow(primaryStage));
        createsMap.put(restarauntsRadioButton, () -> showInsertRestaurantWindow(primaryStage));

        Map<RadioButton,Runnable> deleteMap = new HashMap<>();
        deleteMap.put(clientRadioButton, this::deleteSelectedClient);
        deleteMap.put(couriersRadioButton, this::deleteSelectedCourier);
        deleteMap.put(menuRadioButton, this::deleteSelectedMenuPosition);
        deleteMap.put(ordered_foodRadioButton, this::deleteSelectedOrderedFood);
        deleteMap.put(ordersRadioButton, this::deleteSelectedOrder);
        deleteMap.put(position_typesRadioButton, this::deleteSelectedPositionType);
        deleteMap.put(reportsRadioButton, this::deleteSelectedReport);
        deleteMap.put(restarauntsRadioButton, this::deleteSelectedRestaraunt);

        Map<RadioButton,Runnable> updateMap = new HashMap<>();
        updateMap.put(clientRadioButton,() -> showUpdateClientWindow(primaryStage));
        updateMap.put(couriersRadioButton,() -> showUpdateCourierWindow(primaryStage));
        updateMap.put(menuRadioButton, () -> showUpdateMenuWindow(primaryStage));
        updateMap.put(ordered_foodRadioButton, () -> showUpdateOrderedFoodWindow(primaryStage));
        updateMap.put(ordersRadioButton,() -> showUpdateOrderWindow(primaryStage));
        updateMap.put(position_typesRadioButton, () -> showUpdatePositionTypeWindow(primaryStage));
        updateMap.put(reportsRadioButton, () -> showUpdateReportWindow(primaryStage));
        updateMap.put(restarauntsRadioButton, () -> showUpdateRestaurantWindow(primaryStage));

        clientRadioButton.setOnAction(event -> {
            if (clientRadioButton.isSelected())
            {
                if (clientTable != null)
                {
                    clientTable = null;

                }
                initializeClientTable(client);
                root.getChildren().addAll(clientTable);
            }
            else
            {
                root.getChildren().remove(clientTable);
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
                if(restarauntsTable != null)
                {
                    restarauntsTable = null;
                }
                initializeRestarauntsTable(restaraunts);
                root.getChildren().addAll(restarauntsTable);
            }
            else
            {
                root.getChildren().remove(restarauntsTable);
            }
        });


        createButton.setOnAction(event -> {
            for (Map.Entry<RadioButton, Runnable> entry : createsMap.entrySet())
            {
                if (entry.getKey().isSelected())
                {
                    entry.getValue().run();
                    return;
                }
            }
            warningChooseSomething();
        });

        deleteButton.setOnAction(event -> {
            for (Map.Entry<RadioButton, Runnable> entry : deleteMap.entrySet())
            {
                if (entry.getKey().isSelected())
                {
                    entry.getValue().run();
                    return;
                }
            }
            warningChooseSomethingToDelete();
        });

        updateButton.setOnAction(event -> {
            for (Map.Entry<RadioButton, Runnable> entry : updateMap.entrySet())
            {
                if (entry.getKey().isSelected())
                {
                    entry.getValue().run();
                    return;
                }
            }
            warningChooseSomethingToUpdate();
        });
        primaryStage.setOnCloseRequest(windowEvent ->
        {
            try {
                dbFoodAPP.disconnect();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        IsDelivered.setOnAction(event -> {
            if (IsDelivered.isSelected())
            {
                DeliveredOrders(primaryStage);
            } else
            {
                System.out.println("Click again :)");
            }
        });
        //При создание новой кнопки вписывай сюда её название
        root.getChildren().addAll(clientRadioButton,couriersRadioButton,menuRadioButton,ordered_foodRadioButton,ordersRadioButton,position_typesRadioButton,reportsRadioButton,restarauntsRadioButton,createButton,updateButton,deleteButton,IsDelivered);


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
    private void showInsertCourierWindow(Stage primaryStage) {
    Stage insertCourierStage = new Stage();
    insertCourierStage.setTitle("Insert Courier");

    GridPane gridPane = new GridPane();
    gridPane.setPadding(new Insets(10));
    gridPane.setVgap(5);
    gridPane.setHgap(5);

    TextField courierIdField = new TextField();
    courierIdField.setPromptText("Enter courier ID");
    gridPane.add(new Label("Courier ID:"), 0, 0);
    gridPane.add(courierIdField, 1, 0);

    TextField courierDocumentField = new TextField();
    courierDocumentField.setPromptText("Enter courier document");
    gridPane.add(new Label("Courier Document:"), 0, 1);
    gridPane.add(courierDocumentField, 1, 1);

    TextField phoneField = new TextField();
    phoneField.setPromptText("Enter phone number");
    gridPane.add(new Label("Phone Number:"), 0, 2);
    gridPane.add(phoneField, 1, 2);

    ComboBox<String> statusComboBox = new ComboBox<>();
    statusComboBox.getItems().addAll("busy", "free", "delivering");
    gridPane.add(new Label("Status:"), 0, 3);
    gridPane.add(statusComboBox, 1, 3);

    TextField nameField = new TextField();
    nameField.setPromptText("Enter name");
    gridPane.add(new Label("Name:"), 0, 4);
    gridPane.add(nameField, 1, 4);

    TextField surnameField = new TextField();
    surnameField.setPromptText("Enter surname");
    gridPane.add(new Label("Surname:"), 0, 5);
    gridPane.add(surnameField, 1, 5);

    TextField patronymicsField = new TextField();
    patronymicsField.setPromptText("Enter patronymics");
    gridPane.add(new Label("Patronymics:"), 0, 6);
    gridPane.add(patronymicsField, 1, 6);

    TextField experienceField = new TextField();
    experienceField.setPromptText("Enter experience");
    gridPane.add(new Label("Experience:"), 0, 7);
    gridPane.add(experienceField, 1, 7);

    ComboBox<String> typeOfCourierComboBox = new ComboBox<>();
    typeOfCourierComboBox.getItems().addAll("walking", "bicycling", "by car");
    gridPane.add(new Label("Type of Courier:"), 0, 8);
    gridPane.add(typeOfCourierComboBox, 1, 8);

    TextField deliveryPriceField = new TextField();
    deliveryPriceField.setPromptText("Enter delivery price");
    gridPane.add(new Label("Delivery Price:"), 0, 9);
    gridPane.add(deliveryPriceField, 1, 9);

    Button addButton = new Button("Add");
    addButton.setOnAction(e -> {
        int courierId = Integer.parseInt(courierIdField.getText());
        int courierDocument = Integer.parseInt(courierDocumentField.getText());
        long phone = Long.parseLong(phoneField.getText());
        Status status = Status.valueOf(statusComboBox.getValue());
        String name = nameField.getText();
        String surname = surnameField.getText();
        String patronymics = patronymicsField.getText();
        String experience = experienceField.getText();
        TypeOfCourier typeOfCourier = TypeOfCourier.valueOf(typeOfCourierComboBox.getValue());
        double deliveryPrice = Double.parseDouble(deliveryPriceField.getText());

        if (!name.isEmpty() && !surname.isEmpty() && !patronymics.isEmpty() && !experience.isEmpty() && status != null && typeOfCourier != null) {
            try {
                dbFoodAPP.insertCourier(courierId, courierDocument, phone, status, name, surname, patronymics, experience, typeOfCourier, deliveryPrice);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            couriers.setAll(dbFoodAPP.getAllCouriers());
            insertCourierStage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields.");
            alert.showAndWait();
        }
    });
    gridPane.add(addButton, 1, 10);

    Scene scene = new Scene(gridPane, 400, 450);
    insertCourierStage.setScene(scene);
    insertCourierStage.initOwner(primaryStage);
    insertCourierStage.initModality(Modality.WINDOW_MODAL);
    insertCourierStage.show();
}
    private void showInsertMenuWindow(Stage primaryStage) {
        Stage insertMenuStage = new Stage();
        insertMenuStage.setTitle("Insert Menu Position");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        TextField positionIdField = new TextField();
        positionIdField.setPromptText("Enter position ID");
        gridPane.add(new Label("Position ID:"), 0, 0);
        gridPane.add(positionIdField, 1, 0);

        TextField restarauntsIdField = new TextField();
        restarauntsIdField.setPromptText("Enter restaurant ID");
        gridPane.add(new Label("Restaurant ID:"), 0, 1);
        gridPane.add(restarauntsIdField, 1, 1);

        TextField positionTypesIdField = new TextField();
        positionTypesIdField.setPromptText("Enter position type ID");
        gridPane.add(new Label("Position Type ID:"), 0, 2);
        gridPane.add(positionTypesIdField, 1, 2);

        TextField priceField = new TextField();
        priceField.setPromptText("Enter price");
        gridPane.add(new Label("Price:"), 0, 3);
        gridPane.add(priceField, 1, 3);

        TextField foodNameField = new TextField();
        foodNameField.setPromptText("Enter food name");
        gridPane.add(new Label("Food Name:"), 0, 4);
        gridPane.add(foodNameField, 1, 4);

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            int positionId = Integer.parseInt(positionIdField.getText());
            int restarauntsId = Integer.parseInt(restarauntsIdField.getText());
            int positionTypesId = Integer.parseInt(positionTypesIdField.getText());
            double price = Double.parseDouble(priceField.getText());
            String foodName = foodNameField.getText();

            if (foodName != null && !foodName.isEmpty()) {
                try {
                    dbFoodAPP.insertMenuItem(positionId, restarauntsId, positionTypesId, price, foodName);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                menu.setAll(dbFoodAPP.getAllMenu());
                insertMenuStage.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setHeaderText(null);
                alert.setContentText("Please fill in all fields.");
                alert.showAndWait();
            }
        });
        gridPane.add(addButton, 1, 5);

        Scene scene = new Scene(gridPane, 300, 250);
        insertMenuStage.setScene(scene);
        insertMenuStage.initOwner(primaryStage);
        insertMenuStage.initModality(Modality.WINDOW_MODAL);
        insertMenuStage.show();
    }
    private void showInsertOrderedFoodWindow(Stage primaryStage) {
        Stage insertOrderedFoodStage = new Stage();
        insertOrderedFoodStage.setTitle("Insert Ordered Food");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        TextField orderIdField = new TextField();
        orderIdField.setPromptText("Enter order ID");
        gridPane.add(new Label("Order ID:"), 0, 0);
        gridPane.add(orderIdField, 1, 0);

        TextField menuPositionIdField = new TextField();
        menuPositionIdField.setPromptText("Enter menu position ID");
        gridPane.add(new Label("Menu Position ID:"), 0, 1);
        gridPane.add(menuPositionIdField, 1, 1);

        TextField countField = new TextField();
        countField.setPromptText("Enter count");
        gridPane.add(new Label("Count:"), 0, 2);
        gridPane.add(countField, 1, 2);

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            int orderId = Integer.parseInt(orderIdField.getText());
            int menuPositionId = Integer.parseInt(menuPositionIdField.getText());
            int count = Integer.parseInt(countField.getText());

            if (count > 0) {
                try {
                    dbFoodAPP.insertOrderedFood(orderId, menuPositionId, count);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                ordered_food.setAll(dbFoodAPP.getAllOrderedFood());
                insertOrderedFoodStage.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setHeaderText(null);
                alert.setContentText("Please fill in all fields.");
                alert.showAndWait();
            }
        });
        gridPane.add(addButton, 1, 3);

        Scene scene = new Scene(gridPane, 300, 200);
        insertOrderedFoodStage.setScene(scene);
        insertOrderedFoodStage.initOwner(primaryStage);
        insertOrderedFoodStage.initModality(Modality.WINDOW_MODAL);
        insertOrderedFoodStage.show();
    }
    private void showInsertOrdersWindow(Stage primaryStage) {
        Stage insertOrdersStage = new Stage();
        insertOrdersStage.setTitle("Insert Order");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        TextField orderIdField = new TextField();
        orderIdField.setPromptText("Enter order ID");
        gridPane.add(new Label("Order ID:"), 0, 0);
        gridPane.add(orderIdField, 1, 0);

        TextField clientIdField = new TextField();
        clientIdField.setPromptText("Enter client ID");
        gridPane.add(new Label("Client ID:"), 0, 1);
        gridPane.add(clientIdField, 1, 1);

        TextField couriersIdField = new TextField();
        couriersIdField.setPromptText("Enter courier ID");
        gridPane.add(new Label("Courier ID:"), 0, 2);
        gridPane.add(couriersIdField, 1, 2);

        TextField datetimeField = new TextField();
        datetimeField.setPromptText("Enter datetime (YYYY-MM-DD)");
        gridPane.add(new Label("Datetime:"), 0, 3);
        gridPane.add(datetimeField, 1, 3);

        ChoiceBox<String> statusChoiceBox = new ChoiceBox<>();
        statusChoiceBox.getItems().addAll("created", "delivery", "in progress", "delivered");
        statusChoiceBox.setValue("created");
        gridPane.add(new Label("Status:"), 0, 4);
        gridPane.add(statusChoiceBox, 1, 4);

        TextField priceField = new TextField();
        priceField.setPromptText("Enter price");
        gridPane.add(new Label("Price:"), 0, 5);
        gridPane.add(priceField, 1, 5);

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            try {
                int orderId = Integer.parseInt(orderIdField.getText());
                int clientId = Integer.parseInt(clientIdField.getText());
                int couriersId = Integer.parseInt(couriersIdField.getText());
                java.sql.Date datetime = java.sql.Date.valueOf(datetimeField.getText());
                String status = statusChoiceBox.getValue();
                double price = Double.parseDouble(priceField.getText());

                if (!datetime.toString().isEmpty() && !status.isEmpty() && price >= 0) {
                    try {
                        dbFoodAPP.insertOrder(orderId, clientId, couriersId, datetime, status, price);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    orders.setAll(dbFoodAPP.getAllOrders());
                    insertOrdersStage.close();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Input Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Please fill in all fields correctly.");
                    alert.showAndWait();
                }
            } catch (IllegalArgumentException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setHeaderText(null);
                alert.setContentText("Invalid date format. Please use YYYY-MM-DD.");
                alert.showAndWait();
            }
        });
        gridPane.add(addButton, 1, 6);

        Scene scene = new Scene(gridPane, 300, 300);
        insertOrdersStage.setScene(scene);
        insertOrdersStage.initOwner(primaryStage);
        insertOrdersStage.initModality(Modality.WINDOW_MODAL);
        insertOrdersStage.show();
    }
    private void showInsertPositionTypesWindow(Stage primaryStage) {
        Stage insertPositionTypesStage = new Stage();
        insertPositionTypesStage.setTitle("Insert Position Type");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        TextField positionTypeIdField = new TextField();
        positionTypeIdField.setPromptText("Enter position type ID");
        gridPane.add(new Label("Position Type ID:"), 0, 0);
        gridPane.add(positionTypeIdField, 1, 0);

        TextField foodTypeField = new TextField();
        foodTypeField.setPromptText("Enter food type");
        gridPane.add(new Label("Food Type:"), 0, 1);
        gridPane.add(foodTypeField, 1, 1);

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            try {
                int positionTypeId = Integer.parseInt(positionTypeIdField.getText());
                String foodType = foodTypeField.getText();

                if (!foodType.isEmpty()) {
                    try {
                        dbFoodAPP.insertPositionType(positionTypeId, foodType);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    position_types.setAll(dbFoodAPP.getAllPositionTypes());
                    insertPositionTypesStage.close();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Input Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Please fill in all fields.");
                    alert.showAndWait();
                }
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setHeaderText(null);
                alert.setContentText("Invalid number format.");
                alert.showAndWait();
            }
        });
        gridPane.add(addButton, 1, 2);

        Scene scene = new Scene(gridPane, 300, 200);
        insertPositionTypesStage.setScene(scene);
        insertPositionTypesStage.initOwner(primaryStage);
        insertPositionTypesStage.initModality(Modality.WINDOW_MODAL);
        insertPositionTypesStage.show();
    }
    private void showInsertReportWindow(Stage primaryStage) {
        Stage insertReportStage = new Stage();
        insertReportStage.setTitle("Insert Report");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        TextField orderIdField = new TextField();
        orderIdField.setPromptText("Enter order ID");
        gridPane.add(new Label("Order ID:"), 0, 0);
        gridPane.add(orderIdField, 1, 0);

        DatePicker complDatetimePicker = new DatePicker();
        gridPane.add(new Label("Complaint Date:"), 0, 1);
        gridPane.add(complDatetimePicker, 1, 1);

        TextField gradeField = new TextField();
        gradeField.setPromptText("Enter grade");
        gridPane.add(new Label("Grade:"), 0, 2);
        gridPane.add(gradeField, 1, 2);

        TextField commentField = new TextField();
        commentField.setPromptText("Enter comment");
        gridPane.add(new Label("Comment:"), 0, 3);
        gridPane.add(commentField, 1, 3);

        TextField resultField = new TextField();
        resultField.setPromptText("Enter result");
        gridPane.add(new Label("Result:"), 0, 4);
        gridPane.add(resultField, 1, 4);

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            try {
                int orderId = Integer.parseInt(orderIdField.getText());
                java.sql.Date complDatetime = java.sql.Date.valueOf(complDatetimePicker.getValue());
                short grade = Short.parseShort(gradeField.getText());
                String comment = commentField.getText();
                String result = resultField.getText();

                if (complDatetime != null && !comment.isEmpty() && !result.isEmpty()) {
                    try {
                        dbFoodAPP.insertReport(orderId, complDatetime, grade, comment, result);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    reports.setAll(dbFoodAPP.getAllReports());
                    insertReportStage.close();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Input Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Please fill in all fields.");
                    alert.showAndWait();
                }
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setHeaderText(null);
                alert.setContentText("Invalid number format.");
                alert.showAndWait();
            }
        });
        gridPane.add(addButton, 1, 5);

        Scene scene = new Scene(gridPane, 300, 250);
        insertReportStage.setScene(scene);
        insertReportStage.initOwner(primaryStage);
        insertReportStage.initModality(Modality.WINDOW_MODAL);
        insertReportStage.show();
    }
    private void showInsertRestaurantWindow(Stage primaryStage) {
        Stage insertRestaurantStage = new Stage();
        insertRestaurantStage.setTitle("Insert Restaurant");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        TextField restaurantIdField = new TextField();
        restaurantIdField.setPromptText("Enter restaurant ID");
        gridPane.add(new Label("Restaurant ID:"), 0, 0);
        gridPane.add(restaurantIdField, 1, 0);

        TextField addressField = new TextField();
        addressField.setPromptText("Enter address");
        gridPane.add(new Label("Address:"), 0, 1);
        gridPane.add(addressField, 1, 1);

        TextField nameField = new TextField();
        nameField.setPromptText("Enter name");
        gridPane.add(new Label("Name:"), 0, 2);
        gridPane.add(nameField, 1, 2);

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            try {
                int restaurantId = Integer.parseInt(restaurantIdField.getText());
                String address = addressField.getText();
                String name = nameField.getText();

                if (!address.isEmpty() && !name.isEmpty()) {
                    try {
                        dbFoodAPP.insertRestaurant(restaurantId, address, name);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    restaraunts.setAll(dbFoodAPP.getAllRestaraunts());
                    insertRestaurantStage.close();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Input Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Please fill in all fields.");
                    alert.showAndWait();
                }
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setHeaderText(null);
                alert.setContentText("Invalid number format.");
                alert.showAndWait();
            }
        });
        gridPane.add(addButton, 1, 3);

        Scene scene = new Scene(gridPane, 300, 200);
        insertRestaurantStage.setScene(scene);
        insertRestaurantStage.initOwner(primaryStage);
        insertRestaurantStage.initModality(Modality.WINDOW_MODAL);
        insertRestaurantStage.show();
    }
    private void showUpdateClientWindow(Stage primaryStage) {
        Stage updateClientStage = new Stage();
        updateClientStage.setTitle("Update Client");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        TextField clientIdField = new TextField();
        clientIdField.setPromptText("Enter client ID to update");
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

        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> {
            int clientId = Integer.parseInt(clientIdField.getText());
            String document = documentField.getText();
            String name = nameField.getText();
            String surname = surnameField.getText();
            String patronymics = patronymicsField.getText();
            String phoneNumber = phoneNumberField.getText();
            String city = cityField.getText();
            String street = streetField.getText();
            String apartments = apartmentsField.getText();

            if (!clientIdField.getText().isEmpty()) {
                try {
                    dbFoodAPP.updateClient(clientId, document, name, surname, patronymics, phoneNumber, city, street, apartments);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                client.setAll(dbFoodAPP.getAllClients());
                updateClientStage.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setHeaderText(null);
                alert.setContentText("Please enter the client ID.");
                alert.showAndWait();
            }
        });
        gridPane.add(updateButton, 1, 9);

        Scene scene = new Scene(gridPane, 300, 350);
        updateClientStage.setScene(scene);
        updateClientStage.initOwner(primaryStage);
        updateClientStage.initModality(Modality.WINDOW_MODAL);
        updateClientStage.show();
    }
    private void showUpdateCourierWindow(Stage primaryStage) {
        Stage updateCourierStage = new Stage();
        updateCourierStage.setTitle("Update Courier");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        TextField courierIdField = new TextField();
        courierIdField.setPromptText("Enter courier ID to update");
        gridPane.add(new Label("Courier ID:"), 0, 0);
        gridPane.add(courierIdField, 1, 0);

        TextField courierDocumentField = new TextField();
        courierDocumentField.setPromptText("Enter courier document");
        gridPane.add(new Label("Courier Document:"), 0, 1);
        gridPane.add(courierDocumentField, 1, 1);

        TextField phoneField = new TextField();
        phoneField.setPromptText("Enter phone");
        gridPane.add(new Label("Phone:"), 0, 2);
        gridPane.add(phoneField, 1, 2);

        TextField statusField = new TextField();
        statusField.setPromptText("Enter status");
        gridPane.add(new Label("Status:"), 0, 3);
        gridPane.add(statusField, 1, 3);

        TextField nameField = new TextField();
        nameField.setPromptText("Enter name");
        gridPane.add(new Label("Name:"), 0, 4);
        gridPane.add(nameField, 1, 4);

        TextField surnameField = new TextField();
        surnameField.setPromptText("Enter surname");
        gridPane.add(new Label("Surname:"), 0, 5);
        gridPane.add(surnameField, 1, 5);

        TextField patronymicsField = new TextField();
        patronymicsField.setPromptText("Enter patronymics");
        gridPane.add(new Label("Patronymics:"), 0, 6);
        gridPane.add(patronymicsField, 1, 6);

        TextField experienceField = new TextField();
        experienceField.setPromptText("Enter experience");
        gridPane.add(new Label("Experience:"), 0, 7);
        gridPane.add(experienceField, 1, 7);

        TextField typeOfCourierField = new TextField();
        typeOfCourierField.setPromptText("Enter type of courier");
        gridPane.add(new Label("Type of Courier:"), 0, 8);
        gridPane.add(typeOfCourierField, 1, 8);

        TextField deliveryPriceField = new TextField();
        deliveryPriceField.setPromptText("Enter delivery price");
        gridPane.add(new Label("Delivery Price:"), 0, 9);
        gridPane.add(deliveryPriceField, 1, 9);

        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> {
            int courierId = Integer.parseInt(courierIdField.getText());
            Integer courierDocument = courierDocumentField.getText().isEmpty() ? null : Integer.parseInt(courierDocumentField.getText());
            Long phone = phoneField.getText().isEmpty() ? null : Long.parseLong(phoneField.getText());
            String status = statusField.getText();
            String name = nameField.getText();
            String surname = surnameField.getText();
            String patronymics = patronymicsField.getText();
            String experience = experienceField.getText();
            String typeOfCourier = typeOfCourierField.getText();
            Double deliveryPrice = deliveryPriceField.getText().isEmpty() ? null : Double.parseDouble(deliveryPriceField.getText());

            if (!courierIdField.getText().isEmpty()) {
                try {
                    dbFoodAPP.updateCourier(courierId, courierDocument, phone, status, name, surname, patronymics, experience, typeOfCourier, deliveryPrice);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                couriers.setAll(dbFoodAPP.getAllCouriers());
                updateCourierStage.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setHeaderText(null);
                alert.setContentText("Please enter the courier ID.");
                alert.showAndWait();
            }
        });
        gridPane.add(updateButton, 1, 10);

        Scene scene = new Scene(gridPane, 400, 500);
        updateCourierStage.setScene(scene);
        updateCourierStage.initOwner(primaryStage);
        updateCourierStage.initModality(Modality.WINDOW_MODAL);
        updateCourierStage.show();
    }
    private void showUpdateMenuWindow(Stage primaryStage) {
        Stage updateMenuStage = new Stage();
        updateMenuStage.setTitle("Update Menu Item");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        TextField positionIdField = new TextField();
        positionIdField.setPromptText("Enter position ID to update");
        gridPane.add(new Label("Position ID:"), 0, 0);
        gridPane.add(positionIdField, 1, 0);

        TextField restaurantsIdField = new TextField();
        restaurantsIdField.setPromptText("Enter restaurants ID");
        gridPane.add(new Label("Restaurants ID:"), 0, 1);
        gridPane.add(restaurantsIdField, 1, 1);

        TextField positionTypesIdField = new TextField();
        positionTypesIdField.setPromptText("Enter position types ID");
        gridPane.add(new Label("Position Types ID:"), 0, 2);
        gridPane.add(positionTypesIdField, 1, 2);

        TextField priceField = new TextField();
        priceField.setPromptText("Enter price");
        gridPane.add(new Label("Price:"), 0, 3);
        gridPane.add(priceField, 1, 3);

        TextField foodNameField = new TextField();
        foodNameField.setPromptText("Enter food name");
        gridPane.add(new Label("Food Name:"), 0, 4);
        gridPane.add(foodNameField, 1, 4);

        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> {
            int positionId = Integer.parseInt(positionIdField.getText());
            Integer restaurantsId = restaurantsIdField.getText().isEmpty() ? null : Integer.parseInt(restaurantsIdField.getText());
            Integer positionTypesId = positionTypesIdField.getText().isEmpty() ? null : Integer.parseInt(positionTypesIdField.getText());
            Double price = priceField.getText().isEmpty() ? null : Double.parseDouble(priceField.getText());
            String foodName = foodNameField.getText();

            if (!positionIdField.getText().isEmpty()) {
                try {
                    dbFoodAPP.updateMenu(positionId, restaurantsId, positionTypesId, price, foodName);

                    updateMenuStage.close();
                    menu.setAll(dbFoodAPP.getAllMenu());
                } catch (NumberFormatException ex) {
                    System.out.println(ex.getMessage());
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setHeaderText(null);
                alert.setContentText("Please enter the position ID.");
                alert.showAndWait();
            }
        });
        gridPane.add(updateButton, 1, 5);

        Scene scene = new Scene(gridPane, 400, 250);
        updateMenuStage.setScene(scene);
        updateMenuStage.initOwner(primaryStage);
        updateMenuStage.initModality(Modality.WINDOW_MODAL);
        updateMenuStage.show();
    }
    private void showUpdateOrderedFoodWindow(Stage primaryStage) {
        Stage updateOrderedFoodStage = new Stage();
        updateOrderedFoodStage.setTitle("Update Ordered Food");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        TextField orderIdField = new TextField();
        orderIdField.setPromptText("Enter Order ID to update");
        gridPane.add(new Label("Order ID:"), 0, 0);
        gridPane.add(orderIdField, 1, 0);

        TextField menuPositionIdField = new TextField();
        menuPositionIdField.setPromptText("Enter Menu Position ID");
        gridPane.add(new Label("Menu Position ID:"), 0, 1);
        gridPane.add(menuPositionIdField, 1, 1);

        TextField countField = new TextField();
        countField.setPromptText("Enter Count");
        gridPane.add(new Label("Count:"), 0, 2);
        gridPane.add(countField, 1, 2);

        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> {
            int orderId = Integer.parseInt(orderIdField.getText());
            Integer menuPositionId = menuPositionIdField.getText().isEmpty() ? null : Integer.parseInt(menuPositionIdField.getText());
            Integer count = countField.getText().isEmpty() ? null : Integer.parseInt(countField.getText());

            if (!orderIdField.getText().isEmpty()) {
                try {
                    dbFoodAPP.updateOrderedFood(orderId, menuPositionId, count);
                    ordered_food.setAll(dbFoodAPP.getAllOrderedFood());
                    updateOrderedFoodStage.close();
                } catch (NumberFormatException ex) {
                    System.out.println(ex.getMessage());
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setHeaderText(null);
                alert.setContentText("Please enter the Order ID.");
                alert.showAndWait();
            }
        });
        gridPane.add(updateButton, 1, 3);

        Scene scene = new Scene(gridPane, 400, 200);
        updateOrderedFoodStage.setScene(scene);
        updateOrderedFoodStage.initOwner(primaryStage);
        updateOrderedFoodStage.initModality(Modality.WINDOW_MODAL);
        updateOrderedFoodStage.show();
    }
    private void showUpdateOrderWindow(Stage primaryStage) {
        Stage updateOrderStage = new Stage();
        updateOrderStage.setTitle("Update Order");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        TextField orderIdField = new TextField();
        orderIdField.setPromptText("Enter Order ID to update");
        gridPane.add(new Label("Order ID:"), 0, 0);
        gridPane.add(orderIdField, 1, 0);

        TextField clientIdField = new TextField();
        clientIdField.setPromptText("Enter Client ID");
        gridPane.add(new Label("Client ID:"), 0, 1);
        gridPane.add(clientIdField, 1, 1);

        TextField couriersIdField = new TextField();
        couriersIdField.setPromptText("Enter Couriers ID");
        gridPane.add(new Label("Couriers ID:"), 0, 2);
        gridPane.add(couriersIdField, 1, 2);

        DatePicker datetimePicker = new DatePicker();
        datetimePicker.setPromptText("Select Date");
        gridPane.add(new Label("Date:"), 0, 3);
        gridPane.add(datetimePicker, 1, 3);

        TextField statusField = new TextField();
        statusField.setPromptText("Enter Status");
        gridPane.add(new Label("Status:"), 0, 4);
        gridPane.add(statusField, 1, 4);

        TextField priceField = new TextField();
        priceField.setPromptText("Enter Price");
        gridPane.add(new Label("Price:"), 0, 5);
        gridPane.add(priceField, 1, 5);

        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> {
            int orderId = Integer.parseInt(orderIdField.getText());
            Integer clientId = clientIdField.getText().isEmpty() ? null : Integer.parseInt(clientIdField.getText());
            Integer couriersId = couriersIdField.getText().isEmpty() ? null : Integer.parseInt(couriersIdField.getText());
            java.sql.Date datetime = datetimePicker.getValue() != null ? java.sql.Date.valueOf(datetimePicker.getValue()) : null;
            String status = statusField.getText();
            Double price = priceField.getText().isEmpty() ? null : Double.parseDouble(priceField.getText());

            if (!orderIdField.getText().isEmpty()) {
                try {
                    dbFoodAPP.updateOrder(orderId, clientId, couriersId, datetime, status, price);

                    updateOrderStage.close();
                    orders.setAll(dbFoodAPP.getAllOrders());
                } catch (NumberFormatException ex) {
                    System.out.println(ex.getMessage());
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setHeaderText(null);
                alert.setContentText("Please enter the Order ID.");
                alert.showAndWait();
            }
        });
        gridPane.add(updateButton, 1, 6);

        Scene scene = new Scene(gridPane, 400, 250);
        updateOrderStage.setScene(scene);
        updateOrderStage.initOwner(primaryStage);
        updateOrderStage.initModality(Modality.WINDOW_MODAL);
        updateOrderStage.show();
    }
    private void showUpdatePositionTypeWindow(Stage primaryStage) {
        Stage updatePositionTypeStage = new Stage();
        updatePositionTypeStage.setTitle("Update Position Type");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        TextField positionTypeIdField = new TextField();
        positionTypeIdField.setPromptText("Enter Position Type ID to update");
        gridPane.add(new Label("Position Type ID:"), 0, 0);
        gridPane.add(positionTypeIdField, 1, 0);

        TextField foodTypeField = new TextField();
        foodTypeField.setPromptText("Enter Food Type");
        gridPane.add(new Label("Food Type:"), 0, 1);
        gridPane.add(foodTypeField, 1, 1);

        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> {
            int positionTypeId = Integer.parseInt(positionTypeIdField.getText());
            String foodType = foodTypeField.getText();

            if (!positionTypeIdField.getText().isEmpty()) {
                try {
                    dbFoodAPP.updatePositionType(positionTypeId, foodType);
                    position_types.setAll(dbFoodAPP.getAllPositionTypes());
                    updatePositionTypeStage.close();

                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setHeaderText(null);
                alert.setContentText("Please enter the Position Type ID.");
                alert.showAndWait();
            }
        });
        gridPane.add(updateButton, 1, 2);

        Scene scene = new Scene(gridPane, 400, 150);
        updatePositionTypeStage.setScene(scene);
        updatePositionTypeStage.initOwner(primaryStage);
        updatePositionTypeStage.initModality(Modality.WINDOW_MODAL);
        updatePositionTypeStage.show();
    }
    private void showUpdateReportWindow(Stage primaryStage) {
        Stage updateReportStage = new Stage();
        updateReportStage.setTitle("Update Report");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        TextField orderIdField = new TextField();
        orderIdField.setPromptText("Enter Order ID to update report");
        gridPane.add(new Label("Order ID:"), 0, 0);
        gridPane.add(orderIdField, 1, 0);

        DatePicker complDatetimePicker = new DatePicker();
        complDatetimePicker.setPromptText("Select Completion Date");
        gridPane.add(new Label("Completion Date:"), 0, 1);
        gridPane.add(complDatetimePicker, 1, 1);

        TextField gradeField = new TextField();
        gradeField.setPromptText("Enter Grade");
        gridPane.add(new Label("Grade:"), 0, 2);
        gridPane.add(gradeField, 1, 2);

        TextField commentClField = new TextField();
        commentClField.setPromptText("Enter Client Comment");
        gridPane.add(new Label("Client Comment:"), 0, 3);
        gridPane.add(commentClField, 1, 3);

        TextField resultField = new TextField();
        resultField.setPromptText("Enter Result");
        gridPane.add(new Label("Result:"), 0, 4);
        gridPane.add(resultField, 1, 4);

        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> {
            int orderId = Integer.parseInt(orderIdField.getText());
            LocalDate complDatetime = complDatetimePicker.getValue();
            java.sql.Date complSqlDate = complDatetime != null ? java.sql.Date.valueOf(complDatetime) : null;
            Short grade = gradeField.getText().isEmpty() ? null : Short.parseShort(gradeField.getText());
            String commentCl = commentClField.getText();
            String result = resultField.getText();

            if (!orderIdField.getText().isEmpty()) {
                try {
                    dbFoodAPP.updateReport(orderId, complSqlDate, grade, commentCl, result);
                    reports.setAll(dbFoodAPP.getAllReports());
                    updateReportStage.close();

                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setHeaderText(null);
                alert.setContentText("Please enter the Order ID.");
                alert.showAndWait();
            }
        });
        gridPane.add(updateButton, 1, 5);

        Scene scene = new Scene(gridPane, 400, 250);
        updateReportStage.setScene(scene);
        updateReportStage.initOwner(primaryStage);
        updateReportStage.initModality(Modality.WINDOW_MODAL);
        updateReportStage.show();
    }
    private void showUpdateRestaurantWindow(Stage primaryStage) {
        Stage updateRestaurantStage = new Stage();
        updateRestaurantStage.setTitle("Update Restaurant");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        TextField restaurantIdField = new TextField();
        restaurantIdField.setPromptText("Enter Restaurant ID to update");
        gridPane.add(new Label("Restaurant ID:"), 0, 0);
        gridPane.add(restaurantIdField, 1, 0);

        TextField addressField = new TextField();
        addressField.setPromptText("Enter Address");
        gridPane.add(new Label("Address:"), 0, 1);
        gridPane.add(addressField, 1, 1);

        TextField nameField = new TextField();
        nameField.setPromptText("Enter Name");
        gridPane.add(new Label("Name:"), 0, 2);
        gridPane.add(nameField, 1, 2);

        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> {
            int restaurantId = Integer.parseInt(restaurantIdField.getText());
            String address = addressField.getText();
            String name = nameField.getText();

            if (!restaurantIdField.getText().isEmpty()) {
                try {
                    dbFoodAPP.updateRestaraunt(restaurantId, address, name);
                    restaraunts.setAll(dbFoodAPP.getAllRestaraunts());
                    updateRestaurantStage.close();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setHeaderText(null);
                alert.setContentText("Please enter the Restaurant ID.");
                alert.showAndWait();
            }
        });
        gridPane.add(updateButton, 1, 3);

        Scene scene = new Scene(gridPane, 400, 200);
        updateRestaurantStage.setScene(scene);
        updateRestaurantStage.initOwner(primaryStage);
        updateRestaurantStage.initModality(Modality.WINDOW_MODAL);
        updateRestaurantStage.show();
    }
    private void DeliveredOrders(Stage primaryStage) {
        Stage updateOrderStage = new Stage();
        updateOrderStage.setTitle("Is delivered?)");
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        TextField orderIdField = new TextField();
        orderIdField.setPromptText("Enter order number(id)");
        gridPane.add(new Label("Order ID:"), 0, 0);
        gridPane.add(orderIdField, 1, 0);

        TextField courierIdField = new TextField();
        courierIdField.setPromptText("Enter the ID of the courier who served you");
        gridPane.add(new Label("Courier ID:"), 0, 1);
        gridPane.add(courierIdField, 1, 1);

        Button updateButton = new Button("Delivered");
        updateButton.setOnAction(e -> {
            int orderId = Integer.parseInt(orderIdField.getText());
            int courierId = Integer.parseInt(courierIdField.getText());

            try {
                dbFoodAPP.isDelivered(orderId, courierId);
                updateOrderStage.close();
                couriers.setAll(dbFoodAPP.getAllCouriers());
                orders.setAll(dbFoodAPP.getAllOrders());
            } catch (NumberFormatException ex) {
                System.out.println(ex.getMessage());
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        });
        gridPane.add(updateButton, 1, 2);

        Scene scene = new Scene(gridPane, 300, 150);
        updateOrderStage.setScene(scene);
        updateOrderStage.initOwner(primaryStage);
        updateOrderStage.initModality(Modality.WINDOW_MODAL);
        updateOrderStage.show();
    }
    //тут будут обновление тут это выше :)
    private void deleteSelectedClient() {
        client selectedClient = clientTable.getSelectionModel().getSelectedItem();
        if (selectedClient != null) {
            try {
                dbFoodAPP.deleteClient(selectedClient.getClientId());
                client.remove(selectedClient);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a client to delete.");
            alert.showAndWait();
        }
    }

    private void deleteSelectedCourier() {
        couriers selectedCourier = couriersTable.getSelectionModel().getSelectedItem();
        if (selectedCourier != null) {
            try {
                dbFoodAPP.deleteCourier(selectedCourier.getCourierId());
                couriers.remove(selectedCourier);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a courier to delete.");
            alert.showAndWait();
        }
    }
    private void deleteSelectedMenuPosition() {
        menu selectedMenuPosition = menuTable.getSelectionModel().getSelectedItem();
        if (selectedMenuPosition != null) {
            try {
                dbFoodAPP.deleteMenuItem(selectedMenuPosition.getPositionId());
                menu.remove(selectedMenuPosition);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a menu position to delete.");
            alert.showAndWait();
        }
    }
    private void deleteSelectedOrderedFood() {
        ordered_food selectedOrderedFood = ordered_foodTable.getSelectionModel().getSelectedItem();
        if (selectedOrderedFood != null) {
            try {
                dbFoodAPP.deleteOrderedFood(selectedOrderedFood.getOrderId());
                ordered_food.remove(selectedOrderedFood);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select an ordered food item to delete.");
            alert.showAndWait();
        }
    }
    private void deleteSelectedOrder() {
        orders selectedOrder = ordersTable.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            try {
                dbFoodAPP.deleteOrder(selectedOrder.getOrder_id());
                orders.remove(selectedOrder);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select an order to delete.");
            alert.showAndWait();
        }
    }
    private void deleteSelectedPositionType() {
        position_types selectedPositionType = position_typesTable.getSelectionModel().getSelectedItem();
        if (selectedPositionType != null) {
            try {
                dbFoodAPP.deletePositionType(selectedPositionType.getPosition_type_id());
                position_types.remove(selectedPositionType);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a position type to delete.");
            alert.showAndWait();
        }
    }
    private void deleteSelectedReport() {
        reports selectedReport = reportsTable.getSelectionModel().getSelectedItem();
        if (selectedReport != null) {
            try {
                dbFoodAPP.deleteReport(selectedReport.getOrder_id());
                reports.remove(selectedReport);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a report to delete.");
            alert.showAndWait();
        }
    }
    private void deleteSelectedRestaraunt() {
        restaraunts restaraunt = restarauntsTable.getSelectionModel().getSelectedItem();

        if (restaraunt != null) {
            try {
                dbFoodAPP.deleteRestaraunt(restaraunt.getRestaraunt_id());
                restaraunts.remove(restaraunt);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a restaraunt to delete.");
            alert.showAndWait();

        }
    }

    private void warningChooseSomething()
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("No Selection");
        alert.setHeaderText(null);
        alert.setContentText("First select the table in which you want to make a new insert.");
        alert.showAndWait();
    }
    private void warningChooseSomethingToDelete()
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("No Selection");
        alert.setHeaderText(null);
        alert.setContentText("First select the table, then select the field you want to delete and click “delete”.");
        alert.showAndWait();
    }
    private void warningChooseSomethingToUpdate()
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("No Selection");
        alert.setHeaderText(null);
        alert.setContentText("First select the table, then click the “Update” button and enter the necessary data to update.");
        alert.showAndWait();
    }

    public static void main(String[] args)
    {
    launch(args);
    }
}