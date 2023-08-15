package com.pharmacy.Model;

import com.pharmacy.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Patient {
    private int id;
    private int user_id;
    private int medicine_id;
    private String name;
    private String allergies;

    private Medicine medicine;
    private User worker;

    public Patient(int id, int user_id, int medicine_id, String name, String allergies) {
        this.id = id;
        this.user_id = user_id;
        this.medicine_id = medicine_id;
        this.name = name;
        this.allergies = allergies;
        this.medicine = Medicine.getFetch(medicine_id);
        this.worker = User.getFetch(user_id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getMedicine_id() {
        return medicine_id;
    }

    public void setMedicine_id(int medicine_id) {
        this.medicine_id = medicine_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public Medicine getPatient() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public User getWorker() {
        return worker;
    }

    public void setWorker(User worker) {
        this.worker = worker;
    }

    public static ArrayList<Patient> getList() {
        ArrayList<Patient> patientList = new ArrayList<>();
        Patient obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * from Patient");
            while (rs.next()) {
                int id = rs.getInt("id");
                int user_id = rs.getInt("user_id");
                int medicine_id = rs.getInt("medicine_id");
                String name = rs.getString("name");
                String allergies = rs.getString("allergies");
                obj = new Patient(id, user_id, medicine_id, name, allergies);
                patientList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return patientList;

    }
    public static ArrayList<Patient> getListByUser(int user_id) {
        ArrayList<Patient> patientList = new ArrayList<>();
        Patient obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * from Patient where user_id = " + user_id);
            while (rs.next()) {
                int id = rs.getInt("id");
                int userID = rs.getInt("user_id");
                int medicine_id = rs.getInt("medicine_id");
                String name = rs.getString("name");
                String allergies = rs.getString("allergies");
                obj = new Patient(id, userID, medicine_id, name, allergies);
                patientList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return patientList;
    }


    public static boolean add(int user_id, int medicine_id, String name, String allergies) {
        String query = "INSERT INTO Patient (user_id, medicine_id, name, allergies) values (?,?,?,?)";

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, user_id);
            pr.setInt(2, medicine_id);
            pr.setString(3, name);
            pr.setString(4, allergies);
            return pr.executeUpdate() != -1;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //return true;
    }

    public static boolean delete(int id) {
        String query = "DELETE FROM Patient WHERE id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, id);

            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

}
