package com.example.iss_45.domain;

public interface User extends Entity<Integer> {
    public String getUsername();
    public String getPassword();
    public void setUsername(String username);
    public void setPassword(String password);
}