/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Book;

import com.sun.org.apache.bcel.internal.generic.RETURN;

/**
 *
 * @author wkhan
 */
public class HistoryMoney {
    private int id;
    private String book_reg;
    private String book_name;
    private String member_id;
    private String full_name;
    private int money;
    private String date_current;
    
 public HistoryMoney(int ID, String BookReg,String BookName,String MemberID, String FullName,int Money,String DateCurrent){
         this.id = ID;
         this.book_reg = BookReg;
         this.book_name = BookName;
         this.member_id = MemberID;
         this.full_name = FullName;
         this.money = Money;
         this.full_name = FullName;
         this.date_current = DateCurrent;         
     }
 
    public int getID(){
        return id;
    }
    
    public String getBookReg(){
        return book_reg;
    }
    
    public String getBookName(){
        return book_name;
    }    
    
    public String getMemberID(){
        return  member_id;
    }
    
    public String getFullName(){
        return full_name;
    }
    
    public int getMoney(){
        return money;
    }
    
    public String getDateCurrent(){
        return date_current;
}
}
