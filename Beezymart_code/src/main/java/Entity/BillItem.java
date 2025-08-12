/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

/**
 *
 * @author ADMIN
 */
public class BillItem {
    private int ID;
    private int BillID;
    private int ProductID;
    private float Unitprice ;
    private int Discount;
    private int Quantity;
    private String Note;
    private String Unit;

    public BillItem() {
    }

    public BillItem(int ID, int BillID, int ProductID, float Unitprice, int Discount, int Quantity, String Note, String Unit) {
        this.ID = ID;
        this.BillID = BillID;
        this.ProductID = ProductID;
        this.Unitprice = Unitprice;
        this.Discount = Discount;
        this.Quantity = Quantity;
        this.Note = Note;
        this.Unit = Unit;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getBillID() {
        return BillID;
    }

    public void setBillID(int BillID) {
        this.BillID = BillID;
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int ProductID) {
        this.ProductID = ProductID;
    }

    public float getUnitprice() {
        return Unitprice;
    }

    public void setUnitprice(float Unitprice) {
        this.Unitprice = Unitprice;
    }

    public int getDiscount() {
        return Discount;
    }

    public void setDiscount(int Discount) {
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

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String Unit) {
        this.Unit = Unit;
    }
    
}
