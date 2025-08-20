/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.company.dao;

import Entity.BillItem;
import Util.XJdbc;
import Util.XQuery;
import java.util.List;

/**
 * BillItemDAOImpl - triển khai CRUD cho bảng BillItem
 *
 * @author Admin
 */
public class BillItemDAOImpl implements BillItemDAO {

    // SQL (khai báo rõ cột khi INSERT)
    String createSql = "INSERT INTO [BillItem] ([BillID],[ProductID],[UnitPrice],[Discount],[Quantity],[Note],[Unit]) OUTPUT INSERTED.ID VALUES (?,?,?,?,?,?,?)";
    String findAllSql = "SELECT * FROM [BillItem]";
    String findByIdSql = "SELECT * FROM [BillItem] WHERE ID = ?";
    String findByBillIdSql = "SELECT * FROM [BillItem] WHERE BillID = ?";
    String updateSql = "UPDATE [BillItem] SET [BillID]=?, [ProductID]=?, [UnitPrice]=?, [Discount]=?, [Quantity]=?, [Note]=?, [Unit]=? WHERE [ID] = ?";
    String deleteSql = "DELETE FROM [BillItem] WHERE [ID] = ?";
    String deleteByBillIdSql = "DELETE FROM [BillItem] WHERE BillID = ?";

    @Override
    public BillItem create(BillItem entity) {
        Object[] values = {
            entity.getBillID(),
            entity.getProductID(),
            entity.getUnitprice(),
            entity.getDiscount(),
            entity.getQuantity(),
            entity.getNote(),
            entity.getUnit()
        };
        Object idObj = XJdbc.getValue(createSql, values);
        if (idObj != null) {
            if (idObj instanceof Number) {
                entity.setID(((Number) idObj).intValue());
            } else {
                try {
                    entity.setID(Integer.parseInt(idObj.toString()));
                } catch (Exception ex) {
                    // ignore parse error, ID sẽ không được set
                }
            }
        }
        return entity;
    }

    @Override
    public void update(BillItem entity) {
        Object[] values = {
            entity.getBillID(),
            entity.getProductID(),
            entity.getUnitprice(),
            entity.getDiscount(),
            entity.getQuantity(),
            entity.getNote(),
            entity.getUnit(),
            entity.getID()
        };
        XJdbc.executeUpdate(updateSql, values);
    }

    @Override
    public List<BillItem> findAll() {
        return XQuery.getBeanList(BillItem.class, findAllSql);
    }

    @Override
    public BillItem findById(Integer id) {
        Object[] values = { id };
        return XQuery.getSingleBean(BillItem.class, findByIdSql, values);
    }

    @Override
    public List<BillItem> findByBillId(Integer billId) {
        Object[] values = { billId };
        return XQuery.getBeanList(BillItem.class, findByBillIdSql, values);
    }

    @Override
    public void deleteById(Integer id) {
        XJdbc.executeUpdate(deleteSql, id);
    }

    @Override
    public void deleteByBillId(Integer billId) {
        XJdbc.executeUpdate(deleteByBillIdSql, billId);
    }
}