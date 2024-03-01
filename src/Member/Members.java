/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Member;

/**
 *
 * @author wkhan
 */
public class Members {
    private int id;
    private String member_id;
    private String name;
    private String email;
    private String member_type;
    private String username;
    private String password;
    private int member_type_code;
    private Boolean admin;
    private String name_depart;
    private String datein;
    private String dateout;
    private String address;
    private String phone;
    private String sex;
    private int status;
    private String  book_total;
    private String member_pic;
    private String member_barcode;
     
    public Members(int ID, String MemberID, String Name, String Email, String Username, String Password, String MemberType,
                        int MemberTypeCode, Boolean Admin, String NameDepart, String DateIn, String DateOut, String Address,
                        String Phone, String Sex, int Status, String BookTotal, String MemberPic, String MemberBarcode){
        this.id = ID;
        this.member_id = MemberID;
        this.name = Name;
        this.email = Email;
        this.member_type = MemberType;
        this.username = Username;
        this.password = Password;
        this.member_type_code = MemberTypeCode;
        this.admin = Admin;
        this.name_depart = NameDepart;
        this.datein = DateIn;
        this.dateout = DateOut;
        this.address = Address;
        this.phone = Phone;
        this.sex = Sex;
        this.status = Status;
        this.book_total = BookTotal;
        this.member_pic = MemberPic;
        this.member_barcode = MemberBarcode;
    }
    
    public int getID(){
        return id;
    }
    
    public String getMemberID(){
        return member_id;
    }
    
    public String getName(){
        return name;
    }
    
    public String getEmail(){
        return email;
    }
    
    public String getMemberType(){
        return member_type;
    }
    
    public String getUsername(){
        return username;
    }
    
    public String getPassword(){
        return password;
    }
    
    public int getMemberTypeCode(){
        return member_type_code;
    }
    
   public Boolean getAdmin(){
       return admin;
   }
   
   public String getNameDepart(){
       return name_depart;
   }
   
   public String getDateIn(){
       return datein;
   }
   
   public String getDateOut(){
       return  dateout;
   }
   
   public String getAddress(){
       return address;
   }
   
   public String getPhone(){
       return phone;
   }
   
   public String getSex(){
       return sex;
   }
   
   public int getStatus(){
       return status;
   }
   
   public String getBookTotal(){
       return book_total;
   }
   
   public String getMemberPic(){
       return member_pic;
   }
   
   public String getMemberBarcode(){
       return member_barcode;
   }
}
