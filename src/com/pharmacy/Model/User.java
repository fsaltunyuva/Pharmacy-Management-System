package com.pharmacy.Model;

import com.pharmacy.Helper.DBConnector;
import com.pharmacy.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class User {
    private int id;
    private String name;
    private String uname;
    private String pass;
    private String type;

    public User() {
    }

    public User(int id, String name, String uname, String pass, String type) {
        this.id = id;
        this.name = name;
        this.uname = uname;
        this.pass = pass;
        this.type = type;
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

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static ArrayList<User> getList() {
        ArrayList<User> userList = new ArrayList<>();
        String query = "use patika2 SELECT * FROM [User]";

        User obj;

        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                obj = new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUname(rs.getString("uname"));
                obj.setPass(rs.getString("pass"));
                obj.setType(rs.getString("type"));

                userList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return userList;
    }

    public static ArrayList<User> getListOnlyWorker() {
        ArrayList<User> userList = new ArrayList<>();
        String query = "use patika2 SELECT * FROM [User] where type = 'worker' ";
        User obj;

        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                obj = new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUname(rs.getString("uname"));
                obj.setPass(rs.getString("pass"));
                obj.setType(rs.getString("type"));

                userList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return userList;
    }

    public static boolean add(int id, String name, String uname, String pass, String type) {
        String query = "INSERT INTO [User] (id, name, uname, pass, type) VALUES (?,?,?,?,?)";
        User findUser = User.getFetch(uname);

        User findID = User.getFetch(id);
        if (findID != null) {
            Helper.showMessage("Bu ID daha önceden eklenmiş. Lütfen farklı bir ID giriniz.");
            return false;
        }
        if (findUser != null) {
            Helper.showMessage("Bu kullanıcı adı daha önceden eklenmiş. Lütfen farklı bir kullanıcı adı giriniz.");
            return false;
        }
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, id);
            pr.setString(2, name);
            pr.setString(3, uname);
            pr.setString(4, pass);
            pr.setString(5, type);

            int response = pr.executeUpdate();

            if (response == -1) {
                Helper.showMessage("error");
            }
            return -1 != response;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return true;
    }

    public static User getFetch(String uname) {
        User obj = null;
        String query = "SELECT * FROM [User] WHERE uname = ?";

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1, uname);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                obj = new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUname(rs.getString("uname"));
                obj.setPass(rs.getString("pass"));
                obj.setType(rs.getString("type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return obj;
    }


    public static User getFetch(int id) {
        User obj = null;
        String query = "SELECT * FROM [User] WHERE id = ?";

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) { //While -> If?
                obj = new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUname(rs.getString("uname"));
                obj.setPass(rs.getString("pass"));
                obj.setType(rs.getString("type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return obj;
    }

    public static boolean delete(int id) {
        String query = "DELETE FROM [User] WHERE id = ?";
        ArrayList<Patient> patientList = Patient.getListByUser(id);
        for(Patient c : patientList){
            Patient.delete(c.getId());
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

    public static boolean update(int id, String name, String uname, String pass, String type) {
        String query = "UPDATE [User] SET name=?, uname=?, pass=?, type=? WHERE id=?";

        //TO-DO Eğer kullanıcı type'ı verilen 3 değerden daha farklı bir şey yapmaya çalışırsa uyarı penceresi aç

        User findUser = User.getFetch(uname);

        User findID = User.getFetch(id);
        if (findID != null) {
            Helper.showMessage("Bu ID daha önceden eklenmiş. Lütfen farklı bir ID giriniz.");
            return false;
        }
        if (findUser != null && findUser.getId() != id) {
            Helper.showMessage("Bu kullanıcı adı daha önceden eklenmiş. Lütfen farklı bir kullanıcı adı giriniz.");
            return false;
        }

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,name);
            pr.setString(2,uname);
            pr.setString(3,pass);
            pr.setString(4,type);
            pr.setInt(5,id);
            return pr.executeUpdate() != -1;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static ArrayList<User> searchUserList(String query){
        ArrayList<User> userList = new ArrayList<>();
        User obj;

        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                obj = new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUname(rs.getString("uname"));
                obj.setPass(rs.getString("pass"));
                obj.setType(rs.getString("type"));

                userList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return userList;
    }

    public static String searchQuery(String name, String uname, String type){
        String query = "SELECT * FROM [User] WHERE uname LIKE '%{{uname}}%' AND name LIKE '%{{name}}%'";

        query = query.replace("{{uname}}", uname);
        query = query.replace("{{name}}", name);

        if(!type.isEmpty()){
            query += "AND type LIKE '{{type}}'";
            query = query.replace("{{type}}", type);
        }

        return query;

    }

    public static User getFetch(String uname, String pass) {
        Operator obj = null;
        String query = "SELECT * FROM [User] WHERE uname = ? AND pass = ?";

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1, uname);
            pr.setString(2, pass);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                switch (rs.getString("type")){
                    case "operator":
                        obj = new Operator();
                        break;
                    default:
                        obj = new Operator();
                        break;
                }
                obj = new Operator();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUname(rs.getString("uname"));
                obj.setPass(rs.getString("pass"));
                obj.setType(rs.getString("type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return obj;
    }

}
