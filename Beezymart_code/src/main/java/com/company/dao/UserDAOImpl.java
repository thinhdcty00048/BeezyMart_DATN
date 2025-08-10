/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.company.dao;

import Entity.User;
import Util.XJdbc;
import Util.XQuery;
import java.util.List;

/**
 *
 * @author Admin
 */
public class UserDAOImpl implements UserDAO {

    String createSql = "INSERT INTO [User] VALUES(?,?,?,?,?,?,?,?,?)";
    String LoginSql = "SELECT * FROM [User] WHERE Email = ? AND Password = ?";

    @Override
    public User create(User entity) {
        Object[] values = {
            entity.getPassword(),
            entity.isEnable(),
            entity.getFullname(),
            entity.isIsManager(),
            entity.getEmail(),
            entity.getPhoneNumber(),
            entity.getCreatedAt(),
            entity.getProfilePictule(),
            entity.getDateOfBirth()
        };
        XJdbc.executeUpdate(createSql, values);
        return entity;
    }

    @Override
    public void update(User entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<User> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public User findById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public User Login(String email, String password) {
         Object[] values = {
            email,
             password
        };
        return XQuery.getSingleBean(User.class, LoginSql, values);
    }

}
