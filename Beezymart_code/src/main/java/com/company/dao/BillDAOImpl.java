/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.company.dao;

import Entity.Bill;
import Util.XJdbc;
import Util.XQuery;
import java.util.List;

/**
 * BillDAOImpl - triển khai CRUD cho bảng Bill
 *
 * @author Admin
 */
public class BillDAOImpl implements BillDAO {

    // Khai báo SQL (ghi rõ cột khi INSERT)
    String createSql = "INSERT INTO [Bill] ([UserID],[Checkout],[Status],[TotalAmount],[PaymentMethod],[GuestID]) VALUES (?,?,?,?,?,?)";
    String findAllSql = "SELECT * FROM [Bill]";
    String findByIdSql = "SELECT * FROM [Bill] WHERE ID = ?";
    String updateSql = "UPDATE [Bill] SET [UserID] = ?, [Checkout] = ?, [Status] = ?, [TotalAmount] = ?, [PaymentMethod] = ?, [GuestID] = ? WHERE [ID] = ?";
    String deleteSql = "DELETE FROM [Bill] WHERE [ID] = ?";

    @Override
    public Bill create(Bill entity) {
        String sql = createSql + "; SELECT SCOPE_IDENTITY()"; // Lấy ID mới tạo
        Object[] values = {
            entity.getUserID(),
            entity.getCheckout(),
            entity.getStatus(),
            entity.getTotalAmount(),
            entity.getPaymentMethod(),
            entity.getGuestID()
        };

        try (
                var conn = XJdbc.openConnection(); var ps = conn.prepareStatement(sql)) {
            for (int i = 0; i < values.length; i++) {
                ps.setObject(i + 1, values[i]);
            }
            var rs = ps.executeQuery();
            if (rs.next()) {
                int newId = rs.getInt(1);
                entity.setID(newId); // Gán lại ID cho Bill
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return entity;
    }

    @Override
    public void update(Bill entity) {
        Object[] values = {
            entity.getUserID(),
            entity.getCheckout(),
            entity.getStatus(),
            entity.getTotalAmount(),
            entity.getPaymentMethod(),
            entity.getGuestID(),
            entity.getID()
        };
        XJdbc.executeUpdate(updateSql, values);
    }

    @Override
    public List<Bill> findAll() {
        return XQuery.getBeanList(Bill.class, findAllSql);
    }

    @Override
    public Bill findById(Integer id) {
        Object[] values = {id};
        return XQuery.getSingleBean(Bill.class, findByIdSql, values);
    }

    @Override
    public void deleteById(Integer id) {
        XJdbc.executeUpdate(deleteSql, id);
    }
}
