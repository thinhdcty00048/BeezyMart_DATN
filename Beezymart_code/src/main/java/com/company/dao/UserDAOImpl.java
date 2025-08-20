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
    String findAllSql = "SELECT * FROM [User]";
    String updateSql = "UPDATE [User]\n"
            + "SET [Password] = ?,\n"
            + "    [Enable] = ?,\n"
            + "    [Fullname] = ?,\n"
            + "    [IsManager] = ?,\n"
            + "    [Email] = ?,\n"
            + "    [PhoneNumber] = ?,\n"
            + "    [CreateAt] = ?,\n"
            + "    [ProfilePicture] = ?,\n"
            + "    [DateOfBirth] = ?\n"
            + "WHERE [ID] = ?";
    String deleteSql = "DELETE FROM [User] WHERE ID = ?";

    @Override
    public User create(User entity) {
        Object[] values = {
            entity.getPassword(),
            entity.isEnable(),
            entity.getFullname(),
            entity.isIsManager(),
            entity.getEmail(),
            entity.getPhoneNumber(),
            entity.getCreateAt(),
            entity.getProfilePicture(),
            entity.getDateOfBirth()
        };
        XJdbc.executeUpdate(createSql, values);
        return entity;
    }

    @Override
    public void update(User entity) {
        Object[] values = {
            entity.getPassword(),
            entity.isEnable(),
            entity.getFullname(),
            entity.isIsManager(),
            entity.getEmail(),
            entity.getPhoneNumber(),
            entity.getCreateAt(),
            entity.getProfilePicture(),
            entity.getDateOfBirth(),
            entity.getID()
        };
        XJdbc.executeUpdate(updateSql, values);
    }


    @Override
    public List<User> findAll() {
        return XQuery.getBeanList(User.class, findAllSql);
    }

 

    @Override
    public User Login(String email, String password) {
        Object[] values = {
            email,
            password
        };
        return XQuery.getSingleBean(User.class, LoginSql, values);
    }

    @Override
    public void deleteById(Integer id) {
         XJdbc.executeUpdate(deleteSql, id);
    }

    @Override
    public User findById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
