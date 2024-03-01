/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Book;

import Member.MemberType;
import Member.Members;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import manage.DbConnect;
import manage.GlobalVar;
import manage.Lang;

/**
 *
 * @author wkhan
 */
public class BooksReturn extends javax.swing.JInternalFrame {
    private int intTotal;
    private String strMemberID;   
    private  String strBookReg;
    private String strBookName;
    private String strFullName;
    private int intMoney;
    private String dateCurrent;
    /**
     * Creates new form BooksReturn
     */
    public BooksReturn() {
        initComponents();
         setTitle("ข้อมูลการคืนหนังสือ");
        //set size
        int width = 1050;
        int height = 750;
        setBounds(GlobalVar.screenCenterX-(width/2),20,width,height);
        
          //
        panel1.setBorder(BorderFactory.createTitledBorder("ข้อมูลหนังสือที่สมาชิกคืน"));        
        panel3.setBorder(BorderFactory.createTitledBorder("รายการค้างส่งหนังสือ"));
        panel4.setBorder(BorderFactory.createTitledBorder("จำนวนเงิน(ค่าปรับ)"));
        //
        txtBookReg.requestFocusInWindow();
       //clear
       clearAll();
    }
    
    //Class to ArrayList object
    public ArrayList<Books> getBooksList(String book_reg){
        ArrayList<Books> booksList = new ArrayList<Books>();
        Connection con = DbConnect.getConnection();
         // Sql Command              
        String sql = "SELECT * FROM tblBookData  WHERE "+
                "BookReg ='" + book_reg + "'";
        //Variable Statement and Resultset
        Statement st;
        ResultSet rs;
        //
        try{
            st = con.createStatement();     //Create Statement
            rs = st.executeQuery(sql);
            Books books;
            while(rs.next()){
                //Read Colume
                books = new Books(rs.getInt("ID"),rs.getString("BookReg"),rs.getString("ISBN"),rs.getString("BookCode1"),rs.getString("BookCode2"),
                        rs.getString("BookName1"),rs.getString("BookName2"),rs.getString("BookType"),rs.getString("BookDateIncome"),rs.getString("BookAuthor1"),
                        rs.getString("BookAuthor2"),rs.getString("BookAuthor3"),rs.getString("BookAuthor4"),rs.getString("BookPublisher"),rs.getString("BookTranslateName"),
                        rs.getString("BookPubAddress"),rs.getString("BookTitle"),rs.getString("BookTitle2"),rs.getString("BookYear"),rs.getString("BookNum"),
                        rs.getString("BookSource"),rs.getString("BookMoney"),rs.getString("BookTotal"),rs.getString("BookRent"),rs.getString("BookOver"),
                        rs.getString("BookNormal"),rs.getInt("BookRentType"),rs.getInt("BookComment"),rs.getString("BookImage"),rs.getInt("BookStatus"));
                 // Add data to ArrayList      
                booksList.add(books);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return booksList;
    }
    
    //execute Query 
    public boolean executeSQLQuery(String query, String message){
        Connection con = DbConnect.getConnection();
        Statement st;
        boolean status = false;
        try{
            st = con.createStatement();
            if((st.executeUpdate(query)) == 1){
               status = true;
            }
        }catch(Exception e){
            e.printStackTrace();
            status = false;
        }
        return status;
    }
    
    private void findBookReturn() throws ParseException{
           // Search Bookdata
        String search = txtBookReg.getText();
        ArrayList<Books> list = getBooksList(search);
        if(list.size()>0){
           txtISBN.setText(list.get(0).getISBN());
           txtBookName.setText(list.get(0).getBookName1());
           txtBookTranslate.setText(list.get(0).getBookTranslateName());         
           txtBookOver.setText(list.get(0).getBookOver());
           txtBookRent.setText(list.get(0).getBookRent());
           //Add Bookdata to Borrow Table
            showReturnInTable();
            clearBookTextField();
        } else{
             JOptionPane.showMessageDialog(this, "ไม่พบข้อมูลทะเบียนหนังสือ : "+txtMemberID.getText());
        }
    }
    
    
     //Class to ArrayList object
    public ArrayList<Borrow> getBorrowList(){
        ArrayList<Borrow> borrowList = new ArrayList<Borrow>();
        Connection con = DbConnect.getConnection();  
        String strBookReg = txtBookReg.getText();
        String strMemberID = txtMemberID.getText();
         // Sql Command              
        String sql = "SELECT * FROM tblBookRent WHERE BookReg = '" + strBookReg 
                    + "' AND BookReturn = '1' " ;
        //Variable Statement and Resultset
        Statement st;
        ResultSet rs;
        //
        try{
            st = con.createStatement();     //Create Statement
            rs = st.executeQuery(sql);
            Borrow borrow;
           //
            while(rs.next()){
                borrow = new Borrow(rs.getInt("ID"),rs.getString("MemberID"),rs.getString("BookReg"),rs.getString("DateRent"),rs.getString("DateReturn")
                        ,rs.getInt("BookReturn"));
                //set memberID and check Same MemberID
               if(strMemberID.equals(rs.getString("MemberID"))){  // not same
                    txtMemberID.setText(rs.getString("MemberID"));
                    borrowList.add(borrow);
                }else{
                    if(intTotal > 0 ){
                        //Show message dialog
                          JOptionPane.showMessageDialog(this, "กรุณาปริ้นต์ค่าปรับสมาชิกก่อน ทำรายการให้สมาชิกรายอื่น");
                    }else{
                       resetTableReturn();
                        txtMemberID.setText(rs.getString("MemberID"));
                        borrowList.add(borrow);
                    }
                }
            }
        }catch(SQLException e){            
            e.printStackTrace();
        }
        return borrowList;
}   
    
    public void showReturnInTable() throws ParseException{
        ArrayList<Borrow> list = getBorrowList();
        DefaultTableModel model = (DefaultTableModel) tblReturn.getModel();
        Object[] row = new Object[5];
        //Add data to Table
        row[0] = list.get(0).getBookReg();  //เลขทะเบียนหนังสือ
        row[1] = txtBookName.getText(); //ชื่อหนังสือ
        row[2] = list.get(0).getDateRent(); //กำหนดวันยืม        
        row[3] = list.get(0).getDateReturn(); //กำหนดวันส่ง
        //Calculator Day Over
        GregorianCalendar gc = new GregorianCalendar();        
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");         
        Date Date1 = df.parse(df.format(gc.getTime()));     //Today
        Date Date2 = df.parse(row[3].toString());               //กำหนดวันส่ง
        Calendar day1 = Calendar.getInstance();
        Calendar day2 = Calendar.getInstance(); 
        day1.setTime(Date1);
        day2.setTime(Date2);
        //คำนวนวันที่เกิน วันส่ง - วันยืม
         int daysBetween = day1.get(Calendar.DAY_OF_YEAR) - day2.get(Calendar.DAY_OF_YEAR); 
          int rent =   Integer.parseInt(txtBookOver.getText());
        row[4] = daysBetween * rent;       //คำนวนค่่าปรับ     
        
        //เพิ่มในตาราง
        model.addRow(row);
        //row in add
         int row2 = tblReturn.getRowCount();
        lblTotalBookRent.setText( "จำนวนหนังสือที่คืนแล้ว: " + row2 + " เล่ม");
        //หาค่าปรับรวม
        intTotal=0;
        while(row2 > 0){
                int total = Integer.parseInt(tblReturn.getValueAt(row2-1, 4).toString());
                intTotal += total;
               row2 --;
        }
        txtTotal.setText( String.valueOf( intTotal)); 
        txtNet.setText(String.valueOf( intTotal - (intTotal*Integer.parseInt(txtDown.getText())))); 
        //find member data
         findMemberReturn();
        //set button
        btnPrintBill.setVisible(false);
         btnReturn.setVisible(true);
          btnCancel.setVisible(true);
        btnClose.setVisible(false);     
        //Refreah tblBorrows
        resetTableBorrows();       
    }
    
     private void clearBookTextField(){
         txtBookReg.setText("");
         txtISBN.setText("");
         txtBookName.setText("");        
         txtBookOver.setText("");
         txtBookRent.setText("");
         txtBookTranslate.setText("");
    }
     //Class to ArrayList object
    public ArrayList<Members> getMembersList(){
        ArrayList<Members> membersList = new ArrayList<Members>();
        Connection con = DbConnect.getConnection();
        
        //Text Search
        String memberid = txtMemberID.getText();
         // Sql Command              
        String sql = "SELECT * FROM tblMember  WHERE "+
                "MemberID = '" + memberid + "'";
        //Variable Statement and Resultset
        Statement st;
        ResultSet rs;
        //
        try{
            st = con.createStatement();     //Create Statement
            rs = st.executeQuery(sql);
            Members members;
            while(rs.next()){
                members = new Members(rs.getInt("ID"),rs.getString("MemberID"),rs.getString("Name"),rs.getString("Email"),
                                rs.getString("Username"),rs.getString("Password"),rs.getString("MemberType"),rs.getInt("MemberTypeCode"),
                                rs.getBoolean("Admin"),rs.getString("NameDepart"),rs.getString("DateIn"),rs.getString("DateOut"),rs.getString("Address"),
                                rs.getString("Phone"),rs.getString("Sex"),rs.getInt("Status"),rs.getString("BookTotal"),rs.getString("MemberPic"),rs.getString("MemberBarcode"));
                membersList.add(members);
                txtName.setText(rs.getString("Name"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return membersList;
    }
    
     private void findMemberReturn(){
         ArrayList<Members> list = getMembersList();
        if(list.size()>0){
           txtMemberID.setText(list.get(0).getMemberID());
           strMemberID = list.get(0).getMemberID();
           strFullName = list.get(0).getName();
           txtName.setText(list.get(0).getName());           
           //Add Bookdata to Borrow Table
            showBorrowInTable();
            clearBookTextField();
        } else{
             JOptionPane.showMessageDialog(this, "ไม่พบข้อมูลทะเบียนหนังสือ : "+txtMemberID.getText());
        }
    }
     
     //Class to ArrayList object
    public ArrayList<Borrow> getBorrowMemberList(){
        ArrayList<Borrow> borrowList = new ArrayList<Borrow>();
        Connection con = DbConnect.getConnection();  
        String strMemberID = txtMemberID.getText();        
         // Sql Command              
        String sql = "SELECT * FROM tblBookRent WHERE MemberID = '" + strMemberID 
                    + "' AND BookReturn = '1' " ;
        //Variable Statement and Resultset
        Statement st;
        ResultSet rs;
        //
        try{
            st = con.createStatement();     //Create Statement
            rs = st.executeQuery(sql);
            Borrow borrow;
            while(rs.next()){
                borrow = new Borrow(rs.getInt("ID"),rs.getString("MemberID"),rs.getString("BookReg"),rs.getString("DateRent"),rs.getString("DateReturn")
                        ,rs.getInt("BookReturn"));
                borrowList.add(borrow);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return borrowList;
}
    
     public void showBorrowInTable(){
        ArrayList<Borrow> list = getBorrowMemberList();
        DefaultTableModel model = (DefaultTableModel) tblBorrows.getModel();
        Object[] row = new Object[3];
        for(int i=0;i<list.size();i++){
            //Add data to Table
            row[0] = list.get(i).getBookReg();  //เลขทะเบียนหนังสือ
           //Search book name
           String strBookReg = list.get(i).getBookReg();
            ArrayList<Books> book_list = getBooksList(strBookReg);
            if(book_list.size()>0){           
               row[1] = book_list.get(0).getBookName1();           
            } 
            row[2] = list.get(i).getDateReturn(); //กำหนดวันส่ง
             model.addRow(row);
       }
         //row in add
         int row2 = tblBorrows.getRowCount();
        lblTotalBookRent2.setText( "จำนวนหนังสือที่ค้างส่ง: " + row2 + " เล่ม");
    }
     
     private void checkBill(){
         int row = tblReturn.getRowCount();
        while(row > 0){
             strBookReg = tblReturn.getValueAt(row-1, 0).toString();
            //Update
             String sql = "UPDATE tblBookRent SET " +
             " BookReturn = '0' "+
            " WHERE MemberID = '" + strMemberID + 
             "' AND BookReg = '" + strBookReg +
             "' AND BookReturn = '1' ";
            //System.out.print(sql);
            //Execute Query database
            if (executeSQLQuery(sql, "Inserted") == true){
                          row --;
              }
           }
        if (row == 0){
                   //enable PrintBill
                   btnReturn.setVisible(false);
                   btnCancel.setVisible(false);
                   btnPrintBill.setVisible(true);
                   btnClose.setVisible(false);
                 //Show message dialog
                  JOptionPane.showMessageDialog(this, "บันทึกข้อมูลการยืมหนังสือเรียบร้อยแล้ว");
           }else{
                 JOptionPane.showMessageDialog(this, "เกิดข้อผิดพลาดไม่สามารถบันทึกข้อมูลได้ !!");
           }
     }
      private void billPrint() {
          int row = tblReturn.getRowCount();
           //Date Current
            GregorianCalendar gc = new GregorianCalendar();        
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateCurrent = df.format(gc.getTime());
            intTotal = 0;       //reset money Total
        while(row > 0){
             strBookReg = tblReturn.getValueAt(row-1, 0).toString();
             strBookName = tblReturn.getValueAt(row-1, 1).toString();
             intMoney = Integer.parseInt(tblReturn.getValueAt(row-1, 4).toString());            
             //Total pay money           
             intTotal += intMoney;
             if(intMoney>0){
                    //Insert Record
                   String sql = "INSERT INTO tblHistoryMoney (ID,BookReg, BookName, MemberID, FullName, Money,DateCurrent) VALUES (null,'"+
                   strBookReg + "','" +
                   strBookName + "','" +
                   strMemberID + "','" +
                   strFullName + "','" +
                   intMoney + "','" +
                  dateCurrent + "')";
                   //System.out.print(sql);
                   //Execute Query database
                   executeSQLQuery(sql, "Inserted");                   
             }
            row --;
           }
        if (row == 0){
                   //set button
                   btnReturn.setVisible(false);
                    btnCancel.setVisible(false);
                   btnPrintBill.setVisible(false);
                   btnClose.setVisible(true);  
                    //Show message dialog
                String message = "จำนวนเงินค่าปรับที่ต้องชำระ \n รวมทั้งสิ้น :"+intTotal+" บาท" ;
                        message += "\nสมาชิกสามารถปริ้นต์ใบเสร็จได้ทางเว็บไซต์  http:///info.pccm.ac.th";
                JOptionPane.showMessageDialog(this, message);
                //Clear
                clearAll();
           }else{
                 JOptionPane.showMessageDialog(this, "เกิดข้อผิดพลาดไม่สามารถบันทึกข้อมูลได้ !!");
           }
         
    }
      
       public void resetTableBorrows(){
      //refresh Table Borrow
    DefaultTableModel model = (DefaultTableModel)tblBorrows.getModel();
     model.setRowCount(0); 
 }
 
 public void resetTableReturn(){
      //refresh Table Borrow
    DefaultTableModel model = (DefaultTableModel)tblReturn.getModel();
     model.setRowCount(0); 
 }
 
 public void clearAll(){
     //reset variable
     strBookReg="";
     strBookName="";
     strMemberID="";
     strFullName="";
     intMoney=0;
     intTotal=0;
     //reset table
      resetTableBorrows();
      resetTableReturn();
      //reset view
      txtMemberID.setText("");
      txtName.setText("");
      txtBookReg.setText("");
      clearBookTextField();
      //reset button
       //set button
        btnPrintBill.setVisible(false);
        btnCancel.setVisible(false);
         btnReturn.setVisible(false);
        btnClose.setVisible(true);
 }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtMemberID = new javax.swing.JTextField();
        btnFind = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        txtBookReg = new javax.swing.JTextField();
        btnBookSearch = new javax.swing.JButton();
        txtISBN = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtBookTranslate = new javax.swing.JTextField();
        txtBookName = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtBookOver = new javax.swing.JTextField();
        txtBookRent = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblReturn = new javax.swing.JTable();
        lblTotalBookRent = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        panel4 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        btnClose = new javax.swing.JButton();
        txtDown = new javax.swing.JTextField();
        txtNet = new javax.swing.JTextField();
        txtTotal = new javax.swing.JTextField();
        btnReturn = new javax.swing.JButton();
        btnPrintBill = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        panel3 = new javax.swing.JScrollPane();
        tblBorrows = new javax.swing.JTable();
        lblTotalBookRent2 = new javax.swing.JLabel();

        getContentPane().setLayout(null);

        panel1.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        panel1.setLayout(null);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/boy128.png"))); // NOI18N
        panel1.add(jLabel1);
        jLabel1.setBounds(20, 10, 128, 153);

        jLabel3.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        jLabel3.setText("ชื่อ-นามสกุล สมาชิก");
        panel1.add(jLabel3);
        jLabel3.setBounds(190, 90, 101, 25);

        txtName.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        panel1.add(txtName);
        txtName.setBounds(190, 120, 290, 31);

        jLabel4.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        jLabel4.setText("รหัสประจำตัว");
        panel1.add(jLabel4);
        jLabel4.setBounds(190, 20, 68, 25);

        txtMemberID.setBackground(new java.awt.Color(204, 255, 204));
        txtMemberID.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        txtMemberID.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtMemberIDMouseClicked(evt);
            }
        });
        txtMemberID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMemberIDKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMemberIDKeyReleased(evt);
            }
        });
        panel1.add(txtMemberID);
        txtMemberID.setBounds(190, 50, 190, 31);

        btnFind.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        btnFind.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons/find32.png"))); // NOI18N
        btnFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindActionPerformed(evt);
            }
        });
        panel1.add(btnFind);
        btnFind.setBounds(400, 40, 70, 40);

        jLabel9.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        jLabel9.setText("เลขทะเบียน");
        panel1.add(jLabel9);
        jLabel9.setBounds(20, 170, 60, 25);

        txtBookReg.setBackground(new java.awt.Color(204, 255, 255));
        txtBookReg.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        txtBookReg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtBookRegMouseClicked(evt);
            }
        });
        txtBookReg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBookRegKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBookRegKeyReleased(evt);
            }
        });
        panel1.add(txtBookReg);
        txtBookReg.setBounds(20, 200, 180, 31);

        btnBookSearch.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        btnBookSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons/find24.png"))); // NOI18N
        btnBookSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBookSearchActionPerformed(evt);
            }
        });
        panel1.add(btnBookSearch);
        btnBookSearch.setBounds(210, 190, 40, 40);

        txtISBN.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        txtISBN.setEnabled(false);
        panel1.add(txtISBN);
        txtISBN.setBounds(260, 200, 220, 31);

        jLabel6.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        jLabel6.setText("หมายเลข ISBN");
        panel1.add(jLabel6);
        jLabel6.setBounds(260, 170, 77, 25);

        jLabel13.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        jLabel13.setText("ผู้แต่งหนังสือ");
        panel1.add(jLabel13);
        jLabel13.setBounds(260, 240, 65, 25);

        txtBookTranslate.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        txtBookTranslate.setEnabled(false);
        panel1.add(txtBookTranslate);
        txtBookTranslate.setBounds(260, 270, 222, 31);

        txtBookName.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        txtBookName.setEnabled(false);
        panel1.add(txtBookName);
        txtBookName.setBounds(20, 270, 230, 31);

        jLabel12.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        jLabel12.setText("ชื่อหนังสือ");
        panel1.add(jLabel12);
        jLabel12.setBounds(20, 240, 51, 25);

        txtBookOver.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        txtBookOver.setEnabled(false);
        panel1.add(txtBookOver);
        txtBookOver.setBounds(20, 340, 146, 31);

        txtBookRent.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        txtBookRent.setEnabled(false);
        panel1.add(txtBookRent);
        txtBookRent.setBounds(260, 340, 220, 31);

        jLabel15.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        jLabel15.setText("กำหนดคืนหนังสือ");
        panel1.add(jLabel15);
        jLabel15.setBounds(260, 310, 150, 25);

        tblReturn.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        tblReturn.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "เลขทะเบียน", "ชื่อหนังสือ", "วันที่ยืม", "กำหนดส่ง", "ค่าปรับ"
            }
        ));
        tblReturn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblReturnMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblReturn);
        if (tblReturn.getColumnModel().getColumnCount() > 0) {
            tblReturn.getColumnModel().getColumn(0).setMinWidth(20);
            tblReturn.getColumnModel().getColumn(1).setMinWidth(100);
            tblReturn.getColumnModel().getColumn(2).setMinWidth(30);
            tblReturn.getColumnModel().getColumn(3).setMinWidth(30);
            tblReturn.getColumnModel().getColumn(4).setMinWidth(30);
        }

        panel1.add(jScrollPane2);
        jScrollPane2.setBounds(20, 390, 460, 250);

        lblTotalBookRent.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        lblTotalBookRent.setText("จำนวนหนังสือที่คืน: ");
        panel1.add(lblTotalBookRent);
        lblTotalBookRent.setBounds(20, 650, 460, 25);

        jLabel19.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        jLabel19.setText("ค่าปรับ (บาท) /วัน");
        panel1.add(jLabel19);
        jLabel19.setBounds(20, 310, 120, 25);

        getContentPane().add(panel1);
        panel1.setBounds(10, 20, 500, 690);

        panel4.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        panel4.setLayout(null);

        jLabel16.setFont(new java.awt.Font("TH Sarabun New", 1, 24)); // NOI18N
        jLabel16.setForeground(java.awt.Color.blue);
        jLabel16.setText("ส่วนลด :");
        panel4.add(jLabel16);
        jLabel16.setBounds(22, 34, 95, 33);

        jLabel17.setFont(new java.awt.Font("TH Sarabun New", 1, 24)); // NOI18N
        jLabel17.setForeground(java.awt.Color.blue);
        jLabel17.setText("เป็นเงิน : ");
        panel4.add(jLabel17);
        jLabel17.setBounds(22, 85, 70, 33);

        jLabel18.setFont(new java.awt.Font("TH Sarabun New", 1, 24)); // NOI18N
        jLabel18.setForeground(java.awt.Color.blue);
        jLabel18.setText("ยอดสุทธิ :");
        panel4.add(jLabel18);
        jLabel18.setBounds(22, 135, 95, 33);

        btnClose.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        btnClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons/x-button32.png"))); // NOI18N
        btnClose.setText("ปิดโปรแกรม");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });
        panel4.add(btnClose);
        btnClose.setBounds(320, 190, 151, 64);

        txtDown.setFont(new java.awt.Font("TH Sarabun New", 1, 24)); // NOI18N
        txtDown.setText("0");
        txtDown.setEnabled(false);
        panel4.add(txtDown);
        txtDown.setBounds(129, 34, 143, 39);

        txtNet.setFont(new java.awt.Font("TH Sarabun New", 1, 24)); // NOI18N
        txtNet.setText("0.0");
        txtNet.setEnabled(false);
        panel4.add(txtNet);
        txtNet.setBounds(129, 132, 143, 39);

        txtTotal.setFont(new java.awt.Font("TH Sarabun New", 1, 24)); // NOI18N
        txtTotal.setText("0.0");
        txtTotal.setEnabled(false);
        panel4.add(txtTotal);
        txtTotal.setBounds(129, 80, 143, 39);

        btnReturn.setBackground(new java.awt.Color(255, 204, 204));
        btnReturn.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        btnReturn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons/exchange32.png"))); // NOI18N
        btnReturn.setText("คืนหนังสือ");
        btnReturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReturnActionPerformed(evt);
            }
        });
        panel4.add(btnReturn);
        btnReturn.setBounds(320, 40, 151, 64);

        btnPrintBill.setBackground(new java.awt.Color(255, 204, 204));
        btnPrintBill.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        btnPrintBill.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons/money24.png"))); // NOI18N
        btnPrintBill.setText("พิมพ์ไบเสร็จ");
        btnPrintBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintBillActionPerformed(evt);
            }
        });
        panel4.add(btnPrintBill);
        btnPrintBill.setBounds(120, 190, 151, 64);

        btnCancel.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons/remove32.png"))); // NOI18N
        btnCancel.setText("ยกเลิกรายการ");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        panel4.add(btnCancel);
        btnCancel.setBounds(320, 110, 151, 64);

        getContentPane().add(panel4);
        panel4.setBounds(530, 410, 500, 300);

        tblBorrows.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        tblBorrows.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "เลขทะเบียน", "ชื่อหนังสือ", "กำหนดส่ง"
            }
        ));
        tblBorrows.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBorrowsMouseClicked(evt);
            }
        });
        panel3.setViewportView(tblBorrows);
        if (tblBorrows.getColumnModel().getColumnCount() > 0) {
            tblBorrows.getColumnModel().getColumn(0).setMinWidth(50);
            tblBorrows.getColumnModel().getColumn(1).setMinWidth(250);
            tblBorrows.getColumnModel().getColumn(2).setMinWidth(50);
        }

        getContentPane().add(panel3);
        panel3.setBounds(530, 20, 500, 350);

        lblTotalBookRent2.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        lblTotalBookRent2.setText("จำนวนหนังสือที่คืน: ");
        getContentPane().add(lblTotalBookRent2);
        lblTotalBookRent2.setBounds(530, 380, 500, 25);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtMemberIDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMemberIDMouseClicked
        txtMemberID.setText("");
    }//GEN-LAST:event_txtMemberIDMouseClicked

    private void txtMemberIDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMemberIDKeyPressed
        int key = evt.getKeyCode();
        //if press Enter key
        if (key == KeyEvent.VK_ENTER) {
            resetTableBorrows();
            findMemberReturn();
        }
    }//GEN-LAST:event_txtMemberIDKeyPressed

    private void txtMemberIDKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMemberIDKeyReleased

    }//GEN-LAST:event_txtMemberIDKeyReleased

    private void btnFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindActionPerformed
        resetTableBorrows();
        findMemberReturn();
    }//GEN-LAST:event_btnFindActionPerformed

    private void tblReturnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblReturnMouseClicked
        int row = tblReturn.getSelectedRow();		//get select row
        txtBookReg.setText(tblReturn.getValueAt(row, 0).toString());
        ArrayList<Books> list = getBooksList(txtBookReg.getText());
        if(list.size()>0){
            txtISBN.setText(list.get(0).getISBN());
            txtBookName.setText(list.get(0).getBookName1());
            txtBookTranslate.setText(list.get(0).getBookTranslateName());
            txtBookOver.setText(list.get(0).getBookOver());
            txtBookRent.setText(list.get(0).getBookRent());
        }
    }//GEN-LAST:event_tblReturnMouseClicked

    private void txtBookRegMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtBookRegMouseClicked
        clearBookTextField();
    }//GEN-LAST:event_txtBookRegMouseClicked

    private void txtBookRegKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBookRegKeyPressed
        int key = evt.getKeyCode();
        //if press Enter key
        if (key == KeyEvent.VK_ENTER) {
           try{
                    findBookReturn();                   
                }catch(Exception e ){
                    e.printStackTrace();
                }
        }
    }//GEN-LAST:event_txtBookRegKeyPressed

    private void btnBookSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBookSearchActionPerformed
        try{
            findBookReturn();
        }catch(Exception e ){
            e.printStackTrace();
        }
        
    }//GEN-LAST:event_btnBookSearchActionPerformed

    private void tblBorrowsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBorrowsMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblBorrowsMouseClicked

    private void btnReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReturnActionPerformed
         checkBill();
    }//GEN-LAST:event_btnReturnActionPerformed

    private void txtBookRegKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBookRegKeyReleased
        
    }//GEN-LAST:event_txtBookRegKeyReleased

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnPrintBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintBillActionPerformed
       
        billPrint();       
        
    }//GEN-LAST:event_btnPrintBillActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        clearAll();
    }//GEN-LAST:event_btnCancelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBookSearch;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnFind;
    private javax.swing.JButton btnPrintBill;
    private javax.swing.JButton btnReturn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblTotalBookRent;
    private javax.swing.JLabel lblTotalBookRent2;
    private javax.swing.JPanel panel1;
    private javax.swing.JScrollPane panel3;
    private javax.swing.JPanel panel4;
    private javax.swing.JTable tblBorrows;
    private javax.swing.JTable tblReturn;
    private javax.swing.JTextField txtBookName;
    private javax.swing.JTextField txtBookOver;
    private javax.swing.JTextField txtBookReg;
    private javax.swing.JTextField txtBookRent;
    private javax.swing.JTextField txtBookTranslate;
    private javax.swing.JTextField txtDown;
    private javax.swing.JTextField txtISBN;
    private javax.swing.JTextField txtMemberID;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtNet;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables

}
