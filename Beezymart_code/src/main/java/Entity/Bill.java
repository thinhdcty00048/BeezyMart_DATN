/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.util.Date;

/**
 *
 * @author ADMIN
 */
public class Bill {
    private int ID;
    private int UserID;
    private Date Checkout;
    private String Status;
    private float TotalAmount;
    private String PaymentMethod;
    private int GuestID;

    public Bill(int ID, int UserID, Date Checkout, String Status, float TotalAmount, String PaymentMethod, int GuestID) {
        this.ID = ID;
        this.UserID = UserID;
        this.Checkout = Checkout;
        this.Status = Status;
        this.TotalAmount = TotalAmount;
        this.PaymentMethod = PaymentMethod;
        this.GuestID = GuestID;
    }

    public Bill() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public Date getCheckout() {
        return Checkout;
    }

    public void setCheckout(Date Checkout) {
        this.Checkout = Checkout;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public float getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(float TotalAmount) {
        this.TotalAmount = TotalAmount;
    }

    public String getPaymentMethod() {
        return PaymentMethod;
    }

    public void setPaymentMethod(String PaymentMethod) {
        this.PaymentMethod = PaymentMethod;
    }

    public int getGuestID() {
        return GuestID;
    }

    public void setGuestID(int GuestID) {
        this.GuestID = GuestID;
    }
    
}
