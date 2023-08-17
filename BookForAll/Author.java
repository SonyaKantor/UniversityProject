package com.ajax;

import java.util.Date;
public class Author {
    private int id;
    private String name;
    private String last_name;
    private Date birth_date;
    private String country;
    private String lang;
    public Author(){
        this.id = 0;
        this.name = "No name";
        this.last_name = "No last name";
        this.birth_date = new Date();
        this.country = "None";
        this.lang = "None";
    }
    public Author( int id,String name,String last_name,Date birth_date,String country,String lang){
        this.id = id;
        this.name = name;
        this.last_name = last_name;
        this.birth_date = birth_date;
        this.country = country;
        this.lang = lang;
    }
    public int get_ID(){return this.id;}
    public String get_name(){return this.name;}
    public String get_last_name(){return this.last_name;}
    public String get_full_name(){return (this.name + this.last_name);}
    public Date get_birth_year(){return this.birth_date;}
    public String get_country(){return this.country;} 
    public String get_language(){return this.lang;}   
    public void set_ID(int id){this.id = id;}
    public void set_name(String name){this.name = name;}
    public void set_last_name(String last_name){this.last_name = last_name;}
    public void set_birth_year(Date birth_date){this.birth_date = birth_date;}
    public  void set_country(String country){this.country=country;}
    public void set_lang(String language){this.lang = language;}
    
}
