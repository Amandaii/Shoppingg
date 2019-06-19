package cn.edu.swufe.shopping;

import java.io.Serializable;

public class User implements Serializable {

    private String phone;
    private String realname;
    private String address;
    private String username;
    private String password;
    public User() {
        super();
        // TODO Auto-generated constructor stub
    }
    public User(String username, String password, String address, String phone,  String realname) {
        super();
        this.username = username;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.realname = realname;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getRealname(){
        return realname;
    }
    public void setRealname(String realname){
        this.realname = realname;
    }
    @Override
    public String toString() {
        return "User [username=" + username + ", password="
                + password + ", address=" + address + ", phone=" + phone +", realname=" + realname + "]";
    }

}
