package org.example.week02.pojo;

public class Admin {
    private String number;
    private String password;
    private Integer f_id;
    private String token;

    public Integer getF_id() {
        return f_id;
    }

    public void setF_id(Integer f_id) {
        this.f_id = f_id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "number='" + number + '\'' +
                ", password='" + password + '\'' +
                ", f_id=" + f_id +
                ", token='" + token + '\'' +
                '}';
    }
}
