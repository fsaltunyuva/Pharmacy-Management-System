package com.pharmacy.Model;

import com.pharmacy.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Medicine {

    private int id;
    private String name;
    private int stockCount;
    private String activeIngredient;
    private int dose;
    private int price;
    private String expireDate;

    public Medicine(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Medicine(int id, String name, int stockCount, String activeIngredient, int dose, int price, String expireDate) {
        this.id = id;
        this.name = name;
        this.stockCount = stockCount;
        this.activeIngredient = activeIngredient;
        this.dose = dose;
        this.price = price;
        this.expireDate = expireDate;
    }

    public int getStockCount() {
        return stockCount;
    }

    public void setStockCount(int stockCount) {
        this.stockCount = stockCount;
    }

    public String getActiveIngredient() {
        return activeIngredient;
    }

    public void setActiveIngredient(String activeIngredient) {
        this.activeIngredient = activeIngredient;
    }

    public int getDose() {
        return dose;
    }

    public void setDose(int dose) {
        this.dose = dose;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static ArrayList<Medicine> getList() {
        ArrayList<Medicine> medicineList = new ArrayList<>();
        Medicine obj;

        Statement st = null;
        try {
            st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery("select * from Medicine");

            while (rs.next()) {
                //obj = new Medicine(rs.getInt("id"), rs.getString("name"));
                obj = new Medicine(rs.getInt("id"), rs.getString("name"), rs.getInt("stockCount"), rs.getString("activeIngredient"), rs.getInt("dose"), rs.getInt("price"), rs.getString("expireDate"));
                medicineList.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicineList;

    }

    public static boolean add(String name, int stockCount, String activeIngredient, int dose, int price, String expireDate) {
        String query = "INSERT INTO Medicine (name, stockCount, activeIngredient, dose, price, expireDate) values (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1, name);
            pr.setInt(2, stockCount);
            pr.setString(3, activeIngredient);
            pr.setInt(4, dose);
            pr.setInt(5, price);
            pr.setString(6, expireDate);
            return pr.executeUpdate() != -1;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;

    }

    public static boolean update(int id, String name, int stockCount, String activeIngredient, int dose, int price, String expireDate) {
        String query = "UPDATE Medicine SET name = ?, stockCount = ?, activeIngredient = ?, dose = ?, price = ?, expireDate = ? WHERE id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1, name);
            pr.setInt(2, stockCount);
            pr.setString(3, activeIngredient);
            pr.setInt(4, dose);
            pr.setInt(5, price);
            pr.setString(6, expireDate);
            pr.setInt(7, id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static Medicine getFetch(int id) {
        Medicine obj = null;
        String query = "SELECT * FROM Medicine WHERE id = ?";

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                obj = new Medicine(rs.getInt("id"), rs.getString("name"), rs.getInt("stockCount"), rs.getString("activeIngredient"), rs.getInt("dose"), rs.getInt("price"), rs.getString("expireDate"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return obj;
    }

    public static boolean delete(int id) {
        String query = "DELETE FROM Medicine WHERE id = ?";
        ArrayList<Patient> patientList = Patient.getList();
        for(Patient obj : patientList){
            if(obj.getPatient().getId() == id){
                Patient.delete(obj.getId());
            }
        }
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
