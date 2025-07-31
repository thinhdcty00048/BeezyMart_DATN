/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Dao;

import Entity.Bill;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public interface BillDAO extends CrudDAO<Bill, Long>{
List<Bill> findByUsername(String username);
List<Bill> findByCardId(Integer cardId);
}