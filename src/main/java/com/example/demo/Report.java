package com.example.demo;

import com.example.demo.db.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class Report {

    @FXML
    private ComboBox<String> combReportType;

    public void initialize() throws ClassNotFoundException {
        comboFetch();
    }

    private void comboFetch() throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Statement stmt = null;
        try {
            String query = "Select * from tbl_fitns_type";
            System.out.println(query);
            stmt = DatabaseConnection.getStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString("name");
                System.out.println(id);
                combReportType.getItems().add(name);
            }
        } catch (SQLException e) {
            System.out.println("Exception: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void backEntryPage(ActionEvent actionEvent) {
        loadScene("home.fxml", actionEvent);
    }


    private void loadScene(String fxmlFile, ActionEvent event) {
        try {
            Parent newRoot = FXMLLoader.load(getClass().getResource(fxmlFile));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(newRoot);
            stage.setTitle("Reports");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private ListView<Object> myListView;


    @FXML
    void generate_report_btn(ActionEvent event) throws ClassNotFoundException {

        String reportData = combReportType.getSelectionModel().getSelectedItem();

        String dateString = LocalDate.now().toString();  // Assign current date to dateString
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateString, formatter);

        String dayFormat = date.format(formatter);
        String weekFormat = String.format("%d-%d", date.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()), date.getYear());
        String monthFormat = String.format("%02d-%d", date.getMonthValue(), date.getYear());
        String yearFormat = String.valueOf(date.getYear());



        String query = "";


        if(reportData.equals("Daily")){
            query = "SELECT * FROM reports WHERE daily = '"+dayFormat+"'";
        }else if(reportData.equals("Weakly")){
            query = "SELECT * FROM reports WHERE weakly = '"+weekFormat+"'";
        }else if(reportData.equals("Monthly")){
            query = "SELECT * FROM reports WHERE monthly = '"+monthFormat+"'";
        }else if(reportData.equals("Yearly")){
            query = "SELECT * FROM reports WHERE yearly = '"+yearFormat+"'";
        }


        Class.forName("com.mysql.cj.jdbc.Driver");
        Statement stmt = null;

        try {

            stmt = DatabaseConnection.getStatement();
            ResultSet rs = stmt.executeQuery(query);

            ObservableList<Object> data = FXCollections.observableArrayList();
            int count = 1;
            while (rs.next()) {
                int id = count++;
                String title = rs.getString("title");
                String duration = rs.getString("duration");
                String item = String.format("%d ------- %s ------- %s hours", id, title, duration);
                data.add(item);
                myListView.setItems(data);
            }
        } catch (SQLException e) {
            System.out.println("Exception: " + e.getMessage());
            throw new RuntimeException(e);
        }





    }



}
