/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Book;

/**
 *
 * @author admin
 */
public class Borrow {
    private int id;
    private String member_id;
    private String book_reg;
  
    private String date_rent;
    private String date_return;
    private int book_return;
    
    public Borrow(int ID, String MemberID, String BookReg,String DateRent,String DateReturn,int Book_Return){
        this.id = ID;
        this.member_id = MemberID;
        this.book_reg = BookReg;
        this.date_rent = DateRent;
        this.date_return = DateReturn;
        this.book_return = book_return;
    }
    
    public int getID(){
        return id;
    }
    
    public String getMemberID(){
        return member_id;
    }
    
    public String getBookReg(){
        return book_reg;
    }
    
    public String getDateRent(){
        return  date_rent;
    }
    
    public String getDateReturn(){
        return date_return;
    }
    
    public int getBookReturn(){
        return book_return;
    }
}
