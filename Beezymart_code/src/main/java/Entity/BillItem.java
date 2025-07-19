/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

/**
 *
 * @author Admin
 */
public class BillItem {
    private int Id;
    private int Bill;
    private int Itemd;
    private double UnitPrice;
    private double Discount;
    private int Quantity;
    private String Note;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getBill() {
        return Bill;
    }

    public void setBill(int Bill) {
        this.Bill = Bill;
    }

    public int getItemd() {
        return Itemd;
    }

    public void setItemd(int Itemd) {
        this.Itemd = Itemd;
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

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String Note) {
        this.Note = Note;
    }

    public BillItem(int Id, int Bill, int Itemd, double UnitPrice, double Discount, int Quantity, String Note) {
        this.Id = Id;
        this.Bill = Bill;
        this.Itemd = Itemd;
        this.UnitPrice = UnitPrice;
        this.Discount = Discount;
        this.Quantity = Quantity;
        this.Note = Note;
    }

    public BillItem() {
    }
}
