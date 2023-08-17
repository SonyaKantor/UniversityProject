package com.ajax;

public class Publisher {
        private int id;
        private String name;
        private int form_year;
        private String country;
        public Publisher(){
            this.id = 0;
            this.name = "No name";
            this.form_year = 0;
            this.country = "None";
        }
        public Publisher( int id,String name,String last_name,int form_year,String country){
            this.id = id;
            this.name = name;
            this.form_year = form_year;
            this.country = country;
        }
        public  int get_ID(){return this.id;}
        public   String get_name(){return this.name;}
        public   int get_birth_year(){return this.form_year;}
        public   String get_country(){return this.country;}  
        public   void set_ID(int id){this.id = id;}
        public   void set_name(String name){this.name = name;}
        public   void set_birth_year(int form_year){
            if(form_year>0)
                this.form_year = form_year;
        }
        public   void set_country(String country){this.country=country;}
    }
