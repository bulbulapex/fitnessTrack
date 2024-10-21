package com.example.demo;
import com.example.demo.db.DatabaseConnection;
import java.sql.*;

import com.example.demo.db.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HelloController {

    @FXML
    private Label lbl_address;

    @FXML
    private Label lbl_birth;

    @FXML
    private Label lbl_email;

    @FXML
    private Label lbl_name;

    @FXML
    private Label lbl_student_id;

    @FXML
    private TextField txt_adress;

    @FXML
    private DatePicker txt_birth;

    @FXML
    private TextField txt_email;

    @FXML
    private TextField txt_name;

    @FXML
    private TextField txt_student_id;

    @FXML
    private Label welcomeText;

    public void connectionTest() throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Statement stmt = null;
        try {
            String query = "Select * from student";

            System.out.println(query);


            stmt = DatabaseConnection.getStatement();

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String adress = rs.getString("adress");

                System.out.println("Student data: " + name);
            }
        } catch (SQLException e) {
            System.out.println("Exception: " + e.getMessage());
            throw new RuntimeException(e);
        }

    }

    @FXML
    protected void onHelloButtonClick() throws ClassNotFoundException {
        connectionTest();
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    void saveData(ActionEvent event) {
        String name = txt_name.getText();
        String adress = txt_adress.getText();
        String email = txt_email.getText();
        String birth = txt_birth.getValue().toString();
        String student_id = txt_student_id.getText();
        Student std = new Student(name, birth, adress, student_id, email);
        Statement stmt = null;

        try {
            String query = "insert into student(name, birth, adress, STUDENT_ID, email) values('"+std.getName()+"', '"+std.getBirth()+"', '"+std.getAddress()+"', '"+std.getStudent_id()+"', '"+std.getEmail()+"')";
            System.out.println(query);
            stmt = DatabaseConnection.getStatement();
            stmt.execute(query);
            System.out.println("Student data inserted");

        } catch (SQLException e) {
            System.out.println("Exception save: " + e.getMessage());
            throw new RuntimeException(e);
        }

    }



}