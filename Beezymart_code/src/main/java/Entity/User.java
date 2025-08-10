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
public class User {
    private int ID;
    private String Password;
    private boolean Enable;
    private String Fullname; 
    private boolean IsManager;
    private String Email;
    private String PhoneNumber;
    private Date CreatedAt;
    private String ProfilePictule;
    private Date DateOfBirth;

    public User(int ID, String Password, boolean Enable, String Fullname, boolean IsManager, String Email, String PhoneNumber, Date CreatedAt, String ProfilePictule, Date DateOfBirth) {
        this.ID = ID;
        this.Password = Password;
        this.Enable = Enable;
        this.Fullname = Fullname;
        this.IsManager = IsManager;
        this.Email = Email;
        this.PhoneNumber = PhoneNumber;
        this.CreatedAt = CreatedAt;
        this.ProfilePictule = ProfilePictule;
        this.DateOfBirth = DateOfBirth;
    }

    public User() {
        }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public boolean isEnable() {
        return Enable;
    }

    public void setEnable(boolean Enable) {
        this.Enable = Enable;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String Fullname) {
        this.Fullname = Fullname;
    }

    public boolean isIsManager() {
        return IsManager;
    }

    public void setIsManager(boolean IsManager) {
        this.IsManager = IsManager;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }

    public Date getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(Date CreatedAt) {
        this.CreatedAt = CreatedAt;
    }

    public String getProfilePictule() {
        return ProfilePictule;
    }

    public void setProfilePictule(String ProfilePictule) {
        this.ProfilePictule = ProfilePictule;
    }

    public Date getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(Date DateOfBirth) {
        this.DateOfBirth = DateOfBirth;
    }

}
