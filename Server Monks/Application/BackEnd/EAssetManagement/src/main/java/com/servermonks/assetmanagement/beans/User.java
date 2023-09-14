package com.servermonks.assetmanagement.beans;

public class User {
    private int userId;
    private String name;
    private boolean role;
    private String telephone;
    private String email;
    private String username;
    private String password;

    public User() {
        // Default constructor
    }

    public User(int userId, String name, boolean role, String telephone, String email, String username, String password) {
        this.userId = userId;
        this.name = name;
        this.role = role;
        this.telephone = telephone;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    // Getter and Setter methods for all attributes

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getRole() {
        return role;
    }

    public void setRole(boolean role) {
        this.role = role;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
