/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.util.Date;

/**
 *
 * @author Admin
 */
public class Bill {
    private int Id;
    private int UserId;
    private Date Checkin;
    private Date Checkout;
    private String Status;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int UserId) {
        this.UserId = UserId;
    }

    public Date getCheckin() {
        return Checkin;
    }

    public void setCheckin(Date Checkin) {
        this.Checkin = Checkin;
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

    public double getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(double TotalAmount) {
        this.TotalAmount = TotalAmount;
    }

    public String getPaymentMethod() {
        return PaymentMethod;
    }

    public void setPaymentMethod(String PaymentMethod) {
        this.PaymentMethod = PaymentMethod;
    }

    public Bill(int Id, int UserId, Date Checkin, Date Checkout, String Status, double TotalAmount, String PaymentMethod) {
        this.Id = Id;
        this.UserId = UserId;
        this.Checkin = Checkin;
        this.Checkout = Checkout;
        this.Status = Status;
        this.TotalAmount = TotalAmount;
        this.PaymentMethod = PaymentMethod;
    }

    public Bill() {
    }
    private double TotalAmount;
    private String PaymentMethod;
}
