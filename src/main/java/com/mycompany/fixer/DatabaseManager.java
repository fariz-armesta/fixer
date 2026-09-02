/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.fixer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author FARIZ-T14
 */
public class DatabaseManager {
    
    private static final String DB_URL = "jdbc:sqlite:fixer.db";

    public DatabaseManager() {
        createTableIfNotExists();
    }
    
    private void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS contacts ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name TEXT,"
                + "company TEXT,"
                + "email TEXT,"
                + "phone TEXT,"
                + "tag TEXT,"
                + "social TEXT,"
                + "desc TEXT"
                + ");";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertContact(String name, String company, String email,
                               String phone, String tag, String social, String desc) {
        String sql = "INSERT INTO contacts (name, company, email, phone, tag, social, desc) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, company);
            pstmt.setString(3, email);
            pstmt.setString(4, phone);
            pstmt.setString(5, tag);
            pstmt.setString(6, social);
            pstmt.setString(7, desc);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void deleteAllContacts() {
        String sql = "DELETE FROM contacts";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<>();
        String sql = "SELECT * FROM contacts";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                contacts.add(new Contact(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("company"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("tag"),
                    rs.getString("social"),
                    rs.getString("desc")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contacts;
    }
    
    public void deleteContactById(int id) {
        String sql = "DELETE FROM contacts WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
