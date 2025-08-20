/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.company.dao;

import Entity.BillItem;
import java.util.List;

/**
 *
 * @author Hii
 */
public interface BillItemDAO extends CrudDAO<BillItem, Integer>{
    List<BillItem> findByBillId(Integer billId);
    void deleteByBillId(Integer billId);
}
