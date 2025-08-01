/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Dao;

import Entity.Revenue;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public interface RevenueDAO {
 
    /**
    * Truy vấn doanh thu từng loại theo khoảng thời gian
    *
    * @param begin thời gian bắt đầu
    * @param end thời gian kết thúc
    * @return kết quả truy vấn
    */
    List<Revenue.ByCategory> getByCategory(Date begin, Date end);
    /**
    * Truy vấn doanh thu từng nhân viên theo khoảng thời gian
    *
    * @param begin thời gian bắt đầu
    * @param end thời gian kết thúc
    * @return kết quả truy vấn
    */
    List<Revenue.ByUser> getByUser(Date begin, Date end);

    
}
