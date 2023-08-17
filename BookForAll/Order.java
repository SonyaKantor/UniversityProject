package com.ajax;

import java.util.Date;
public class Order {
    private int id;
    private int user_id;
    private Date order_date;
    private int total_amount;
    private int[][] books; //book id+amount of copies of the book
    private double price;
    private boolean payment; // done or not
    private boolean status; // status of the order development
    public Order(){}
    public Order(int id, int user_id, Date order_date,int total_amount,
            int[][] books, double price,boolean payment,boolean status){
        this.id = id;
        this.user_id = user_id;
        this.order_date = order_date;
        this.total_amount = total_amount;
        this.books = books;
        this.price = price;
        this.payment = payment;
        this.status = status;
    }
    public int get_id(){return this.id;}
    public int get_user_id(){return this.user_id;}
    public Date get_order_date(){return this.order_date;}
    public int get_total_amount(){return this.total_amount;}
    public int[][] get_books(){return this.books;}
    public double get_price(){return this.price;}
    public boolean get_payment(){return this.payment;}
    public boolean status(){return this.status;}
    // are settets make any sense?
            
}
