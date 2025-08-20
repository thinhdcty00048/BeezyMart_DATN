/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.company.dao;

import Entity.Category;
import Util.XJdbc;
import Util.XQuery;
import java.util.List;

/**
 * CategoryDAOImpl - triển khai CRUD cho bảng Category
 *
 * @author Admin
 */
public class CategoryDAOImpl implements CategoryDAO {

    // Chú ý: viết rõ các cột khi INSERT để tránh lỗi nếu bảng có cột ID là identity
    String createSql = "INSERT INTO [Category] ([Name]) VALUES (?)";
    String findAllSql = "SELECT * FROM [Category]";
    String findByIdSql = "SELECT * FROM [Category] WHERE ID = ?";
    String updateSql = "UPDATE [Category] SET [Name] = ? WHERE [ID] = ?";
    String deleteSql = "DELETE FROM [Category] WHERE [ID] = ?";

    @Override
    public Category create(Category entity) {
        Object[] values = {
            entity.getName()
        };
        XJdbc.executeUpdate(createSql, values);
        return entity;
    }

    @Override
    public void update(Category entity) {
        Object[] values = {
            entity.getName(),
            entity.getID()
        };
        XJdbc.executeUpdate(updateSql, values);
    }

    @Override
    public List<Category> findAll() {
        return XQuery.getBeanList(Category.class, findAllSql);
    }

    @Override
    public Category findById(Integer id) {
        Object[] values = { id };
        return XQuery.getSingleBean(Category.class, findByIdSql, values);
    }

    @Override
    public void deleteById(Integer id) {
        XJdbc.executeUpdate(deleteSql, id);
    }
}