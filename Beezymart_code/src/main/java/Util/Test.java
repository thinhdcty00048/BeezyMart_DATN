/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

/**
 *
 * @author ADMIN
 */
public class Test {
  public static void main(String[] args){
  String sql = "INSERT INTO Categories (Id, Name) VALUES(?, ?)";
        XJdbc.executeUpdate(sql, "C05", "Loại 5");
        XJdbc.executeUpdate(sql, "C06", "Loại 6");
  }
}

