/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Book;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author khanchaiwongsit
 */
public class Books {
    private int id;
    private String book_reg;
    private String isbn;
    private String book_code1;
    private String book_code2;
    private String book_name1;
    private String book_name2;
    private String book_type;
    private String book_date_income;
    private String book_author1;
    private String book_author2;
    private String book_author3;
    private String book_author4;
    private String book_publisher;
    private String book_translate_name;
    private String book_pub_address;
    private String book_title;
    private String book_title2;
    private String book_year;
    private String book_num;
    private String book_source;
    private String book_money;
    private String book_total;
    private String book_rent;
    private String book_over;
    private String book_normal;
    private int book_rent_type;
    private int book_comment;
    private String book_image;
    private int book_status;
    
   
    //Constructor
    public Books( int ID,String BookReg,String ISBN,String BookCode1,String BookCode2,String BookName1,String BookName2,String BookType,
                   String BookDateIncome,String BookAuthor1,String BookAuthor2,String BookAuthor3,String BookAuthor4,String BookPublisher,
	 String BookTranslateName, String BookPubAddress,String BookTitle,String BookTitle2,String BookYear,String BookNum,
                    String BookSource,String BookMoney,String BookTotal,String BookRent,String BookOver,String BookNormal,int BookRentType,
	 int BookComment,String BookImage,int BookStatus){
        
      this.id = ID;
      this.book_reg = BookReg;
      this.isbn = ISBN;
      this.book_code1 = BookCode1;
      this.book_code2 = BookCode2;
      this.book_name1 = BookName1;
      this.book_name2 = BookName2;
      this.book_type = BookType;
      this.book_date_income = BookDateIncome;
      this.book_author1 = BookAuthor1;
      this.book_author2 = BookAuthor2;
      this.book_author3 = BookAuthor3;
      this.book_author4 = BookAuthor4;
      this.book_publisher = BookPublisher;
      this.book_translate_name = BookTranslateName;
      this.book_pub_address = BookPubAddress;
      this.book_title = BookTitle;
      this.book_title2 = BookTitle2;
      this.book_year = BookYear;
      this.book_num = BookNum;
      this.book_source = BookSource;
      this.book_money = BookMoney;
      this.book_total = BookTotal;
      this.book_rent = BookRent;
      this.book_over = BookOver;
      this.book_normal = BookNormal;
      this.book_rent_type = BookRentType;
      this.book_comment = BookComment;
      this.book_image = BookImage;
      this.book_status = BookStatus;
    }
    
    //Get Method
    public int getID(){
        return id;
        
    }
    
    public String getBookReg(){
        return book_reg;
    }
    public String getISBN(){
        return isbn;
    }
    public String getBookCode1(){
        return book_code1;
    }
   public String getBookCode2(){
       return book_code2;
   }
   public String getBookName1(){
       return book_name1;
   }
    public  String getBookName2(){
        return book_name2;
    }
     public String getBookType(){
        return book_type;
    }
     public String getBookDateIncome(){
        return book_date_income;
    }
      public String getBookAuthor1(){
        return book_author1;
    }
      public String getBookAuthor2(){
        return book_author2;
    }
      public String getBookAuthor3(){
        return book_author3;
    }
      public  String getBookAuthor4(){
        return book_author4;
    }
     public String getBookPublisher(){
        return book_publisher;
    }
    public String getBookTranslateName(){
        return book_translate_name;
    }
     public String getBookPubAddress(){
        return book_pub_address;
    }
    public  String getBookTitle(){
        return book_title;
    }
     public String getBookTitle2(){
        return book_title2;
    }
     public  String getBookYear(){
        return book_year;
    }
     public   String getBookNum(){
        return book_num;
    }
    public   String getBookSource(){
        return book_source;
    }
    public  String getBookMoney(){
        return book_money;
    }
    public  String getBookTotal(){
        return book_total;
    }
    public  String getBookRent(){
        return book_rent;
    }
    public String getBookOver(){
        return book_over;
    }
    public String getBookNormal(){
        return book_normal;
    }
    public int getBookRentType(){
        return book_rent_type;
    }
    public  int getBookComment(){
        return book_comment;
    }
    public  String getBookImage(){
        return book_image;
    }
    public  int getBookStatus(){
        return book_status;
    }
}
