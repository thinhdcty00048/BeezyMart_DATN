/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.company.dao;

import Entity.Product;
import Util.XJdbc;
import Util.XQuery;
import java.util.List;

/**
 * ProductDAOImpl - CRUD cho bảng Product
 * Tương tự UserDAOImpl / CategoryDAOImpl
 *
 * Lưu ý:
 * - Nếu cột ID là IDENTITY (auto-increment) thì INSERT không nên chèn ID,
 *   nên chỉ liệt kê các cột còn lại trong câu INSERT.
 * - Nếu cần lấy ID vừa tạo, có thể dùng OUTPUT INSERTED.ID hoặc SELECT SCOPE_IDENTITY().
 *
 * @author ADMIN
 */
public class ProductDAOImpl implements ProductDAO {

    // Chỉ rõ cột khi INSERT để tránh lỗi nếu bảng có cột ID là identity
    String createSql = "INSERT INTO [Product] " +
            "([Name],[UnitPrice],[UnitName],[Discount],[Available],[Quantity],[CategoryID]) " +
            "VALUES (?,?,?,?,?,?,?)";

    String findAllSql = "SELECT * FROM [Product]";
    String findByIdSql = "SELECT * FROM [Product] WHERE [ID] = ?";
    String updateSql = "UPDATE [Product] SET " +
            "[Name] = ?, " +
            "[UnitPrice] = ?, " +
            "[UnitName] = ?, " +
            "[Discount] = ?, " +
            "[Available] = ?, " +
            "[Quantity] = ?, " +
            "[CategoryID] = ? " +
            "WHERE [ID] = ?";
    String deleteSql = "DELETE FROM [Product] WHERE [ID] = ?";

    @Override
    public Product create(Product entity) {
        Object[] values = {
            entity.getName(),
            entity.getUnitPrice(),
            entity.getUnitName(),
            entity.getDiscount(),
            entity.isAvailable(),
            entity.getQuantity(),
            entity.getCategoryID()
        };
        XJdbc.executeUpdate(createSql, values);
        return entity;
    }

    @Override
    public void update(Product entity) {
        Object[] values = {
            entity.getName(),
            entity.getUnitPrice(),
            entity.getUnitName(),
            entity.getDiscount(),
            entity.isAvailable(),
            entity.getQuantity(),
            entity.getCategoryID(),
            entity.getID()
        };
        XJdbc.executeUpdate(updateSql, values);
    }

    @Override
    public List<Product> findAll() {
        return XQuery.getBeanList(Product.class, findAllSql);
    }

    @Override
    public Product findById(Integer id) {
        Object[] values = { id };
        return XQuery.getSingleBean(Product.class, findByIdSql, values);
    }

    @Override
    public void deleteById(Integer id) {
        XJdbc.executeUpdate(deleteSql, id);
    }
}