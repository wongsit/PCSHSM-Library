/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Book;

/**
 *
 * @author khanchaiwongsit
 */
public class BookType {
    private int id;
    private String code;
    private String bookname;
    
    public BookType(int ID, String Code, String BookName){
        this.id = ID;
        this.code = Code;
        this.bookname = BookName;
    }
    
    public int getID(){
        return id;
    }
    
    public String getCode(){
        return code;
    }
    
    public String getBookName(){
        return bookname;
    }
}
