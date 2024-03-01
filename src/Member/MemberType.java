/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Member;

/**
 *
 * @author khanchaiwongsit
 */
public class MemberType {
    private int id;
    private int member_code;
    private String member_type;
    private int value_rent;
    private int value_over_return;
    private int value_extra;
    private int num_book;
    
    public MemberType(int ID,int MemberCode,String MemberType,int ValueRent,int ValueOverReturn,int ValueExtra,int NumBook){
        this.id = ID;
        this.member_code = MemberCode;
        this.member_type = MemberType;
        this.value_rent = ValueRent;
        this.value_over_return = ValueOverReturn;
        this.value_extra = ValueExtra;
        this.num_book = NumBook;
    }
    
    public int getID(){
        return id;
    }
    
    public int getMemberCode(){
        return member_code;
    }
    
    public String getMemberType(){
        return member_type;
    }
    
    public int getValueRent(){
        return value_rent;
        
    }
    
    public int getValueOverReturn(){
        return value_over_return;
    }
    
    public int getValueExtra(){
        return value_extra;
    }
    
    public int getNumBook(){
        return num_book;
    }
}
