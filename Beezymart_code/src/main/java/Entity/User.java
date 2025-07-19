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
    private int Id;
    private String Password;
    private boolean Enable;
    private String FullName; 
    private boolean Maneger;
    private String Email;
    private String PhoneNumber;
    private Date CreatedAt;
    private Date LastLogin;
    private String Role;
    private String ProfilePictule;
    private Date DateOfBirth;
    private String Department;

    public User() {
    }

    public User(int Id, String Password, boolean Enable, String FullName, boolean Maneger, String Email, String PhoneNumber, Date CreatedAt, Date LastLogin, String Role, String ProfilePictule, Date DateOfBirth, String Department) {
        this.Id = Id;
        this.Password = Password;
        this.Enable = Enable;
        this.FullName = FullName;
        this.Maneger = Maneger;
        this.Email = Email;
        this.PhoneNumber = PhoneNumber;
        this.CreatedAt = CreatedAt;
        this.LastLogin = LastLogin;
        this.Role = Role;
        this.ProfilePictule = ProfilePictule;
        this.DateOfBirth = DateOfBirth;
        this.Department = Department;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
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

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String FullName) {
        this.FullName = FullName;
    }

    public boolean isManeger() {
        return Maneger;
    }

    public void setManeger(boolean Maneger) {
        this.Maneger = Maneger;
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

    public Date getLastLogin() {
        return LastLogin;
    }

    public void setLastLogin(Date LastLogin) {
        this.LastLogin = LastLogin;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String Role) {
        this.Role = Role;
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

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String Department) {
        this.Department = Department;
    }

 
    
    
}
