/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

/**
 *
 * @author Admin
 */
public class Items {
    private int Id;
    private String Name;
    private double UnitPrice;
    private double Discount;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public double getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(double UnitPrice) {
        this.UnitPrice = UnitPrice;
    }

    public double getDiscount() {
        return Discount;
    }

    public void setDiscount(double Discount) {
        this.Discount = Discount;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

    public boolean isAvailable() {
        return Available;
    }

    public void setAvailable(boolean Available) {
        this.Available = Available;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int CategoryId) {
        this.CategoryId = CategoryId;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    public Items(int Id, String Name, double UnitPrice, double Discount, String Image, boolean Available, int CategoryId, int Quantity) {
        this.Id = Id;
        this.Name = Name;
        this.UnitPrice = UnitPrice;
        this.Discount = Discount;
        this.Image = Image;
        this.Available = Available;
        this.CategoryId = CategoryId;
        this.Quantity = Quantity;
    }

    public Items() {
    }
    private String Image;
    private boolean Available;
    private int CategoryId;
    private int Quantity;
}
