/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

/**
 *
 * @author ADMIN
 */
public class Product {
    private int ID;
    private String Name;
    private int UnitPrice;
    private String UnitName;
    private int Discount;
    private boolean Available;
    private int Quantity;
    private int CategoryID;

    public Product(int ID, String Name, int UnitPrice, String UnitName, int Discount, boolean Available, int Quantity, int CategoryID) {
        this.ID = ID;
        this.Name = Name;
        this.UnitPrice = UnitPrice;
        this.UnitName = UnitName;
        this.Discount = Discount;
        this.Available = Available;
        this.Quantity = Quantity;
        this.CategoryID = CategoryID;
    }

    public Product() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public int getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(int UnitPrice) {
        this.UnitPrice = UnitPrice;
    }

    public String getUnitName() {
        return UnitName;
    }

    public void setUnitName(String UnitName) {
        this.UnitName = UnitName;
    }

    public int getDiscount() {
        return Discount;
    }

    public void setDiscount(int Discount) {
        this.Discount = Discount;
    }

    public boolean isAvailable() {
        return Available;
    }

    public void setAvailable(boolean Available) {
        this.Available = Available;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(int CategoryID) {
        this.CategoryID = CategoryID;
    }
    
}
