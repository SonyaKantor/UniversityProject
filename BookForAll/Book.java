package com.ajax;

import java.util.ArrayList;
import java.util.List;
public class Book {
    private String title;
    private String author;
    private String about;
    private String genres;
    private int publish_year; 
    private String publisher;
    private String format;
    private String ISBN;
    private String img;   
    private double price;
    private int amount;
    public Book(){
        this.title= "Unknown title";
        this.author = "Unknown author";
        this.about = "-";
        this.genres = "";
        this.publish_year = 0;
        this.publisher = "Unknown publisher";
        this.format = "Unknown format";
        this.ISBN = "0";
        this.img = "noPhoto.png";
        this.price = 0.0;
        this.amount = 0;
    }
    public Book(String title,String author,String about,String genres,int publish_year,String publisher,
    String format,String ISBN,String img,double price,int amount){
        this.title=title;
        this.author = author;
        this.about = about;
        this.genres = genres;
        this.publish_year = publish_year;
        this.publisher = publisher;
        this.format = format;
        this.ISBN = ISBN;
        this.img = img;
        this.price = price;
        this.amount = amount;
    }
    public String get_title(){ return this.title;}
    public String get_author(){ return this.author;}
    public String get_about(){ return this.about;}
    public String get_genres(){return this.genres;}
    public int get_publish_year(){return this.publish_year;}
    public String get_publisher(){ return this.publisher;}
    public String get_format(){ return this.format;}
    public String get_ISBN(){ return this.ISBN;}
    public String get_image(){ return this.img;}
    public double get_price(){ return this.price;}
    public  int get_amount(){ return this.amount;}
    public   void set_title(String title){this.title = title;}
    public  void set_author(String author){this.author = author;}
    public  void set_about(String about){this.about = about;}
    public  void set_genres(String genres){this.genres=genres;}
    public  void set_publish_year(int publish_year){this.publish_year=publish_year;}
    public  void set_publisher(String publisher){this.publisher = publisher;}
    public  void set_format(String format){this.format = format;}
    public  void set_ISBN(String ISBN){this.ISBN=ISBN;}
    public  void set_image(String img){this.img=img;}
    public  void set_price(double price)
    {
        if(price >= 0)
            this.price = price;
    }
    public   void add_amount(int amount)
    {
        if(amount>0)
            this.amount+=amount;
    }
}
