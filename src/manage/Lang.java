/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manage;

/**
 *
 * @author khanchaiwongsit
 */
public class Lang {
    public static String myLang;
	
	public static String program_name;
		
	//เมนู Files
	public static String menu_file;
	public static String menu_file_backup;
	public static String menu_file_logout;
	public static String menu_file_exit;
	//เมนู Book
	public static String menu_book;
	public static String menu_borrow;
	public static String menu_return;
	public static String menu_book_data;
	public static String menu_book_set;
	//เมนู Member
		public static String menu_member;
		public static String menu_member_register;
		public static String menu_member_manage;
		public static String menu_member_import;
		
	//เมนูSetting
	public static String menu_setting;
	public static String menu_db;
	
	//เมนู Help
	public static String menu_help ;
	public static String menu_help_manual ;
	
	//ผลลัพท์
	public static String result_login_success;					//Login
	public static String result_login_fail;
	
	public static String result_db_add_success;					//Database
	public static String result_db_add_fail;	
	public static String result_db_edit_success;
	public static String result_db_edit_fail;
	public static String result_db_delete_success;
	public static String result_db_delete_fail;
	public static String result_db_connect_success;
	public static String result_db_connect_fail;
	
	//แจ้งเตือน
	public static String warn_title;
	public static String warn_textfild_null;
	public static String warn_wrong_password;
	
	//Button
	public static String button_list;
	public static String button_ok;
	public static String button_save;
	public static String button_add;
	public static String button_update;
	public static String button_delete;
	public static String button_login;
	public static String button_cancel;
	public static String button_close;
	
	//Login Form
	public static String titleLogin;
	public static String label_username;
	public static String label_password;
	public static String option_dialog_title;
	public static String option_dialog_massage_fail;
	public static String option_dialog_massage_close;
	
	//panel Main
	
	//Member Panel
	public static String label_user_id;
	public static String label_name;
	public static String label_confirm_password;
	public static String label_email;
	public static String label_address;
	public static String label_phone;
	public static String label_search;
	public static String label_member_type;

	//Book From
	public static String label_Book_ID;
	public static String label_BookReg;
	public static String label_ISBN;
	public static String label_BookCode1;
	public static String label_BookCode2;
	public static String label_BookName1;
	public static String label_BookName2;
	public static String label_BookType;
	public static String label_BookDateIncome;
	public static String label_BookAuthor1;
	public static String label_BookAuthor2;
	public static String label_BookAuthor3;
	public static String label_BookAuthor4;
	public static String label_BookPublisher;
	public static String label_BookTranslateName;
	public static String label_BookPubAddress;
	public static String label_BookTitle;
	public static String label_BookTitle2;
	public static String label_BookYear;
	public static String label_BookNum;
	public static String label_BookSource;
	public static String label_BookMoney;
	public static String label_BookTotal;
	public static String label_BookRent;
	public static String label_BookOver;
	public static String label_BookNormal;
	public static String label_BookRentType;
	public static String label_BookComment;
	public static String label_BookImage;
	public static String label_BookStatus;
	
	//Coustructor
	public Lang() {
		
	}
        public static void getLang() {
            if(myLang == "Thai") {
                    program_name = "Smart Library:โปรแกรมบริการหอสมุดและแหล่งเรียนรู้-ผู้พัฒนา: HS3WMN";
                    menu_file= "แฟ้ม";
                    menu_file_backup= "สำรองข้อมูล";
                    menu_file_logout="ออกจากระบบ";
                    menu_file_exit = "ออกจากโปรแกรม";

                    menu_book = "หนังสือ";
                    menu_borrow = "ยืมหนังสือ";
                    menu_return= "คืนหนังสือ";
                    menu_book_data = "ข้อมูลหนังสือ";
                    menu_book_set = "กำหนดค่า";

                    menu_member="สมาชิก";
                    menu_member_register="ลงทะเบียน";
                    menu_member_manage="จัดการข้อมูลสมาชิก";
                    menu_member_import="นำเข้าข้อมูลสมาชิก (.csv,txt)";

                    menu_setting = "การตั้งค่า";
                    menu_db = "เชื่อมต่อฐานข้อมูล";

                    menu_help = "ช่วยเหลือ";
                    menu_help_manual="คู่มือการใช้งาน";

                    result_login_success="ลงชื่อเข้าใช้สำเร็จ";					//Login
                    result_login_fail="ชื่อผู้ใช้หรือรหัสผ่าน ไม่ถูกต้อง";

                    result_db_add_success="เพื่มข้อมูลสำเร็จ";					//Database
                    result_db_add_fail="การเพิ่มข้อมูลผิดพลาด";	
                    result_db_edit_success="แก้ไขข้อมูลสำเร็จ";
                    result_db_edit_fail="การแก้ไขข้อมูลผิดพลาด";
                    result_db_delete_success="ลบข้อมูลสำเร็จ";
                    result_db_delete_fail="การลบข้อมูลผิดพลาด";
                    result_db_connect_success="การเชื่อมต่อสำเร็จ";
                    result_db_connect_fail="การเชื่อมต่อไม่สำเร็จ โปรดติดต่อผู้ดูแลระบบ";

                    //Warning
                    warn_title = "แจ้งเตือน";
                    warn_textfild_null = "คุณยังไม่ได้กรอกข้อมูลลงใน ->  "; 
                    warn_wrong_password="รหัสผ่าน ไม่ถูกต้อง";


                    button_list = "ดูทั้งหมด";						//Button
                    button_ok = "ตกลง";	
                    button_save = "บันทึก";
                    button_add = "เพิ่ม";
                    button_update = "ปรับปรุง";
                    button_delete = "ลบ";
                    button_login = "เข้าสู่ระบบ";
                    button_cancel = "ยกเลิก";
                    button_close = "ปิดโปรแกรม";

                    //Login Form
                    titleLogin="ลงชื่อเข้าสู่ระบบ";
                    label_username="ชื่อผู้ใช้";
                    label_password="รหัสผ่าน";
                    button_login="ตกลง";
                    button_cancel="ปิดโปรแกรม";
                    option_dialog_title="เกิดข้อผิดพลาดในการเข้าสู่ระบบ !!";
                    option_dialog_massage_fail="ชื่อผู้ใช้ หรือรหัสผ่านไม่ถูกต้อง (ลืมรหัสผ่านติดต่อผู้ดูแลระบบ)";
                    option_dialog_massage_close="คุณแน่ใจว่าต้องการปิดโปรแกรม ? (กด Yes หากต้องการปิดโปรแกรม ,กด No กลับไปที่โปรแกรม )";

                    label_user_id = "รหัสประจำตัว";		//Register
                    label_name = "ชื่อ - นามสกุล";
                    label_confirm_password = "ยืนยันรหัสผ่าน";
                    label_email = "อีเมล์";
                    label_address = "ที่อยู่";
                    label_phone = "หมายเลขโทรศัพท์";
                    label_search="ค้นหา";
                    label_member_type="ระดับสมาชิก";

                    //Book From
                    label_Book_ID="ลำดับ";
                    label_BookReg="เลขทะเบียน";
                    label_ISBN="รหัส ISBN";
                    label_BookCode1="เลขหมู่";
                    label_BookCode2="เลขผู้แต่ง";
                    label_BookName1="ชื่อเรื่อง";
                    label_BookName2="ชื่อเรื่อง 2";
                    label_BookType="ประเถทหนังสือ";
                    label_BookDateIncome="วันที่รับหนังสือ";
                    label_BookAuthor1="ผู้แต่ง 1";
                    label_BookAuthor2="ผู้แต่ง 2";
                    label_BookAuthor3="ผู้แต่ง 3";
                    label_BookAuthor4="ผู้แต่ง 4";
                    label_BookPublisher="สำนักพิมพ์";
                    label_BookTranslateName="ผู้แปล";
                    label_BookPubAddress="สถานที่พิมพ์";
                    label_BookTitle="หัวเรื่อง";
                    label_BookTitle2="หัวเรื่อง 2";
                    label_BookYear="ปีที่พิมพ์";
                    label_BookNum="ครั้งที่พิมพ์";
                    label_BookSource="แหล่งที่มา";
                    label_BookMoney="ราคา";
                    label_BookTotal="จำนวนหนังสือ";
                    label_BookRent="จำนวนวันที่ให้ยืม";
                    label_BookOver="ค่าปรับต่อวัน";
                    label_BookNormal="ค่ายืมต่อวัน";
                    label_BookRentType="ประเภทการยืม";
                    label_BookComment="หมายเหตุ";
                    label_BookImage="Path ที่เก็บรูป";
                    label_BookStatus="สถานะการยืม";
            }else {
                    program_name = "Smart Library: Library Services System For School-Developer: HS3WMN";
                    menu_file= "File";
                    menu_file_backup= "Backup";
                    menu_file_logout = "Logout";
                    menu_file_exit = "Exit";

                    menu_book = "Book";
                    menu_borrow = "Borrow";
                    menu_return= "Return";
                    menu_book_data = "Book Data";
                    menu_book_set = "Book Setting";

                    menu_member="Member";
                    menu_member_register="Register";
                    menu_member_manage="Member Manage";
                    menu_member_import="Import File  Member (.csv,txt)";

                    menu_setting = "Setting";
                    menu_db = "Connect Data Base";

                    menu_help = "Help";
                    menu_help_manual="Manual";
                    //Login
                    result_login_success="Login Successful ^_^";					
                    result_login_fail="Login Fail !!";
                    //Database
                    result_db_add_success="Add Data Successful";					
                    result_db_add_fail="Add Data Fail !!";	
                    result_db_edit_success="Edit Data Successful";
                    result_db_edit_fail="Update Data Fail !!";
                    result_db_delete_success="Delete Data Successful";
                    result_db_delete_fail="Delete Data Fail !!";
                    result_db_connect_success="Connected...";
                    result_db_connect_fail="Connecting...Fail";
                    //Warning
                    warn_title = "Warring Message";
                    warn_textfild_null = "Please Check Text Box -> "; 			
                    warn_wrong_password="Wrong Password";
                    //Button
                    button_list = "List";	
                    button_ok = "OK";						
                    button_save = "Save";
                    button_add = "Add";
                    button_update = "Update";
                    button_delete = "Delete";
                    button_login = "Login";
                    button_cancel = "Cancel";
                    button_close = "Close";

                    //Login Form
                    titleLogin="Login Form.";
                    label_username="Username: ";
                    label_password="Password: ";
                    button_login="OK";
                    option_dialog_title="Login Fail Please Check Username and Password";
                    option_dialog_massage_fail="Your Username or Password Incorrect (Forget password please call admin.)";
                    option_dialog_massage_close= "You sure !! to close program ? (Select Yes to close program ,or No Back to program. )";

                    label_user_id = "member ID";		//Register
                    label_name = "Name";
                    label_confirm_password = "Confirm Password";
                    label_email = "E-mail";
                    label_address = "Address";
                    label_phone = "Phone number";
                    label_search="Search";
                    label_member_type="Membet Type";
            }
        }
}
