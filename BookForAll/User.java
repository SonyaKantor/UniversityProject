package com.ajax;

import java.util.Date;
public class User {
    private String name;
    private String last_name;
    private Date birth_date;
    private String email;
    private String password;
    private Date reg_date;
    private boolean ads;
    private String adres; 
    public User(){}
    public User(String name, String last_name,Date birth_date,String email, String password,
    Date reg_date, boolean ads, String adres){
        this.name = name;
        this.last_name = last_name;
        this.birth_date = birth_date;
        this.email = email;
        this.password = password;
        this.reg_date = reg_date;
        this.ads = ads;
        this.adres=adres;
    }
    public String get_name(){return this.name;}
    public String get_last_name(){return this.last_name;}
    public String get_full_name(){return this.name+this.last_name;}
    public Date get_birth_date(){return this.birth_date;}
    public String get_email(){return this.email;}
    public String get_password(){return this.password;}
    public Date get_reg_date(){return this.reg_date;}
    public boolean get_ads(){return this.ads;}
    public void set_name(String name){this.name = name;}
    public void set_last_name(String last_name){this.last_name = last_name;}
    public void set_birth_date(Date birth_date){this.birth_date = birth_date;}
    public void set_email(String email){this.email = email;}
    public void set_password(String password){this.password = password;}
    public void set_ads( boolean ads){this.ads = ads;}
}

