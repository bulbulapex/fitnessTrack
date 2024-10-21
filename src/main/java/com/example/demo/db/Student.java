package com.example.demo.db;

public class Student {
    private String name;
    private String birth;
    private String address;
    private String student_id;
    private String email;

    public Student(){

    }
    public Student(String name, String birth, String address, String student_id, String email) {
        this.name = name;
        this.birth = birth;
        this.address = address;
        this.student_id = student_id;
        this.email = email;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getBirth() {
        return birth;
    }
    public void setBirth(String birth) {
        this.birth = birth;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getStudent_id() {
        return student_id;
    }
    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public void showData(){
        System.out.println("Birth date: " + birth);
    }

}
