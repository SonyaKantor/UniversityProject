package com.ajax;

import java.util.Date;
public class Request {
    private int id;
    private int user_id;
    private int book_id;
    private Date req_date;
    private boolean status; 
    Request(){}
    Request(int id,int user_id,int book_id,Date req_date,boolean status ){
        this.id = id;
        this.user_id = user_id;
        this.book_id = book_id;
        this.req_date = req_date;
        this.status = status;
    }
    int get_id(){return this.id;}
    int get_book_id(){return this.book_id;}
    int get_user_id(){return this.user_id;}    
    Date get_req_date(){return this.req_date;}
    boolean get_status(){return this.status;}
    
}

