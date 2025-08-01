/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Dao;

/**
 *
 * @author ADMIN
 */
import Entity.BillDetail;
import Util.XJdbc;
import Util.XQuery;
import java.util.List;


/**
 *
 * @author Admin
 */
public class BillDetailDAOImpl implements BillDetailDAO {
String createSql = "INSERT INTO BillDetail(id, billId, drinkId, unitPrice, discount, quantity) VALUES(?, ?,?,?,?,?)";
String updateSql = "UPDATE BillDetail SET id = ? and billId = ? and drinkId = ? and unitPrice = ? and discount = ? and quantity = ? WHERE Username=?";
String deleteSql = "DELETE FROM BillDetail WHERE id = ?";
String findAllSql = "SELECT * FROM BillDetail";
String findByIdSql = "SELECT * FROM BillDetail WHERE id = ?";

    @Override
    public List<BillDetail> findByBillId(Long billId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<BillDetail> findByDrinkId(String drinkId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public BillDetail create(BillDetail entity) {
        Object[] values = {
            entity.getId(),
            entity.getBillId(),
            entity.getDrinkId(),
            entity.getUnitPrice(),
            entity.getDiscount(),
            entity.getQuantity()
        };
        XJdbc.executeUpdate(createSql, values);
        return entity;
    }

    @Override
    public void update(BillDetail entity) {
                 Object[] values = {
            entity.getId(),
            entity.getBillId(),
            entity.getDrinkId(),
            entity.getUnitPrice(),
            entity.getDiscount(),
            entity.getQuantity()
        };
        XJdbc.executeUpdate(updateSql, values);
    }

    @Override
    public void deleteById(Long id) {
            XJdbc.executeUpdate(deleteSql, id); 
    }

    @Override
    public List<BillDetail> findAll() {
       return XQuery.getBeanList(BillDetail.class, findAllSql);
    }

    @Override
    public BillDetail findById(Long id) {
        return XQuery.getSingleBean(BillDetail.class, findByIdSql, id);
    }

}
