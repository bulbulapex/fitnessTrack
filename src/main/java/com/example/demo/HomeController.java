package com.example.demo;
import com.example.demo.db.DatabaseConnection;
import com.example.demo.db.Reports;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Locale;



import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import javafx.scene.control.ComboBox;

public class HomeController {
    @FXML
    private Label lblDate;

    @FXML
    private Label lblDuration;

    @FXML
    private Label lbltitle;

    @FXML
    private DatePicker txt_date;

    @FXML
    private TextField txt_duration;

    @FXML
    private TextField txt_title;



    public void initialize() {
        txt_date.setValue(LocalDate.now());
    }


    @FXML
    void entryPage(ActionEvent event) throws ClassNotFoundException {
        fetchFitness();
        String title = txt_title.getText();
        String duration = txt_duration.getText();
        String daily = txt_date.getValue().toString();
        Reports rp = new Reports(title, duration, daily);
        Statement stmt = null;

        //System.out.println(daily);
        String dateString = daily;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Parse the date
        LocalDate date = LocalDate.parse(dateString, formatter);

        // Format the outputs
        String dayFormat = date.format(formatter);
        String weekFormat = String.format("%d-%d", date.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()), date.getYear());
        String monthFormat = String.format("%02d-%d", date.getMonthValue(), date.getYear());
        String yearFormat = String.valueOf(date.getYear());


        try {
            String query = "insert into reports(title, duration, daily, weakly, monthly, yearly) values('"+rp.getTitle()+"', '"+rp.getDuration()+"', '"+dayFormat+"', '"+weekFormat+"', '"+monthFormat+"', '"+yearFormat+"')";
            System.out.println(query);
            stmt = DatabaseConnection.getStatement();
            stmt.execute(query);
            System.out.println("Track inserted into log");
            handleResetAction();

        } catch (SQLException e) {
            System.out.println("Exception save: " + e.getMessage());
            throw new RuntimeException(e);
        }

    }

    private void handleResetAction() {
        txt_title.setText("");
        txt_duration.setText("");
        //txt_date.setValue(LocalDate.now());
        //txt_date.setValue(null);
    }

    private void fetchFitness() throws ClassNotFoundException {
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
            }
        } catch (SQLException e) {
            System.out.println("Exception: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @FXML
    void progressReport(ActionEvent event) {
        System.out.println("progressreport");
        loadScene("report.fxml", event);
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

}