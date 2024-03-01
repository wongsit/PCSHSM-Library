/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Book;

import Member.MemberManager;
import Member.Members;
import java.awt.event.KeyEvent;
import static java.lang.Integer.parseInt;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import static java.util.Date.parse;
import java.util.GregorianCalendar;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import manage.DbConnect;
import manage.GlobalVar;
import manage.Lang;

/**
 *
 * @author khanchaiwongsit
 */
public class BooksBorrow extends javax.swing.JInternalFrame {

    /**
     * Creates new form Borrows
     */
    public BooksBorrow() {
        initComponents();
        setTitle("ข้อมูลการยืมหนังสือ");
        //set size
        int width = 1110;
        int height = 750;
        setBounds(GlobalVar.screenCenterX-(width/2),20,width,height);
        //
        panel1.setBorder(BorderFactory.createTitledBorder("ข้อมูลสมาชิก"));
        panel2.setBorder(BorderFactory.createTitledBorder("รายการยืมหนังสือ ของสมาชิก"));
        panel3.setBorder(BorderFactory.createTitledBorder("ข้อมูลหนังสือ"));
        panel4.setBorder(BorderFactory.createTitledBorder("จำนวนเงิน(ค่ายืม)"));
        //
        txtMemberID.requestFocusInWindow();
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
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return membersList;
    }
    
    //Class to ArrayList object
    public ArrayList<Books> getBooksList(){
        ArrayList<Books> booksList = new ArrayList<Books>();
        Connection con = DbConnect.getConnection();
        
        //Text Search
        String search = txtBookReg.getText();
         // Sql Command              
        String sql = "SELECT * FROM tblBookData  WHERE "+
                "BookReg ='" + search + "'";
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
    
    
    private void clearTextField(){
        txtMemberID.setText("");
        txtMemberType.setText("");
         txtName.setText("");
         txtAddress.setText("");
         txtNameDepart.setText("");
         txtPhone.setText("");
         txtSex.setText("");
         txtBookReg.setText("");
         txtISBN.setText("");
         txtBookName.setText("");
         txtBookNormal.setText("");
         txtBookOver.setText("");
         txtBookRent.setText("");
         txtBookTranslate.setText("");
    }
    
    private void clearBookTextField(){
         txtBookReg.setText("");
         txtISBN.setText("");
         txtBookName.setText("");
         txtBookNormal.setText("");
         txtBookOver.setText("");
         txtBookRent.setText("");
         txtBookTranslate.setText("");
    }
    
    private void findMember(){
       ArrayList<Members> list = getMembersList();
        if(list.size()>0){
           txtMemberType.setText(list.get(0).getMemberType());
           txtName.setText(list.get(0).getName());
           txtAddress.setText(list.get(0).getAddress());
           txtNameDepart.setText(list.get(0).getNameDepart());
           txtSex.setText(list.get(0).getSex());
           txtPhone.setText(list.get(0).getPhone());
        }else{
            JOptionPane.showMessageDialog(this, "ไม่พบข้อมูลสมาชิกหมายเลข : "+txtMemberID.getText());
        }
    }
    
    private void findBook(){
         ArrayList<Books> list = getBooksList();
        if(list.size()>0){
           txtISBN.setText(list.get(0).getISBN());
           txtBookName.setText(list.get(0).getBookName1());
           txtBookTranslate.setText(list.get(0).getBookTranslateName());
           txtBookNormal.setText(list.get(0).getBookNormal());
           txtBookOver.setText(list.get(0).getBookOver());
           txtBookRent.setText(list.get(0).getBookRent());
           //Add Bookdata to Borrow Table
            showBorrowInTable();
            clearBookTextField();
        } else{
             JOptionPane.showMessageDialog(this, "ไม่พบข้อมูลทะเบียนหนังสือ : "+txtMemberID.getText());
        }
    }
    
    //Class to ArrayList object
    public ArrayList<Borrow> getBorrowList(){
        ArrayList<Borrow> borrowList = new ArrayList<Borrow>();
            String MemberID = txtMemberID.getText();
            String BookReg = txtBookReg.getText();
            //Date_Rent
            GregorianCalendar gc = new GregorianCalendar();        
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String DateRent = df.format(gc.getTime());
            //Date Return           
            int book_rent_day =  Integer.parseInt(txtBookRent.getText());                 
            long dayCal = (long) gc.getTimeInMillis();     //Get Millisecond 
           dayCal += (86400000* book_rent_day );  //Caculate day return = 1000*60*60*24 = 86400000 millisec
           gc.setTimeInMillis(dayCal);     //Set  New Time
            String  DateReturn = df.format(gc.getTime());
            //
             //check holiday
            int nHoliday = checkHoliday(DateRent, DateReturn);
            String sDate;
            while(nHoliday>0){
                sDate = DateReturn;
                 dayCal += (86400000 * nHoliday );  //Caculate day return = 1000*60*60*24 = 86400000 millisec
                gc.setTimeInMillis(dayCal);     //Set  New Time
                DateReturn = df.format(gc.getTime());
                nHoliday = 0;
                nHoliday = checkHoliday(sDate, DateReturn);
            }       
            Borrow borrow;          
            borrow = new Borrow(0,MemberID,BookReg,DateRent,DateReturn,1);
            // Add data to ArrayList      
            borrowList.add(borrow);
        return borrowList;
}
    
public int checkHoliday(String sDate,String eDate){
         Connection con = DbConnect.getConnection();
        int nHoliday = 0;
         // Sql Command              
        String sql = "SELECT * FROM tblHoliday  WHERE "+
                "day Between '" + sDate + "' AND '"+ eDate+ "'";
        //Variable Statement and Resultset
        Statement st;
        ResultSet rs;
        try{
            st = con.createStatement();     //Create Statement
            rs = st.executeQuery(sql);
            while(rs.next()){
                nHoliday ++ ;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return nHoliday;
}

    public void showBorrowInTable(){
        ArrayList<Borrow> list = getBorrowList();
        DefaultTableModel model = (DefaultTableModel) tblBorrows.getModel();
        Object[] row = new Object[3];
        //Add data to Table
        row[0] = list.get(0).getBookReg();  //เลขทะเบียนหนังสือ
        row[1] = txtBookName.getText(); //ชื่อหนังสือ
        row[2] = list.get(0).getDateReturn(); //กำหนดวันส่ง

        model.addRow(row);
        //row in add
         int row2 = tblBorrows.getRowCount();
        lblTotalBookRent.setText( "จำนวนหนังสือที่ต้องการยืม: " + row2 + " เล่ม");
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
        txtMemberType = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtMemberID = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAddress = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        txtSex = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtNameDepart = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtPhone = new javax.swing.JTextField();
        btnFind = new javax.swing.JButton();
        panel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblBorrows = new javax.swing.JTable();
        lblTotalBookRent = new javax.swing.JLabel();
        panel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtISBN = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtBookReg = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtBookNormal = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtBookName = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtBookTranslate = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtBookOver = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtBookRent = new javax.swing.JTextField();
        btnBookSearch = new javax.swing.JButton();
        panel4 = new javax.swing.JPanel();
        btnBorrow = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        btnClear = new javax.swing.JButton();
        jTextField14 = new javax.swing.JTextField();
        jTextField15 = new javax.swing.JTextField();
        jTextField16 = new javax.swing.JTextField();
        btnClose = new javax.swing.JButton();

        getContentPane().setLayout(null);

        panel1.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        panel1.setLayout(null);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/boy128.png"))); // NOI18N
        panel1.add(jLabel1);
        jLabel1.setBounds(21, 26, 128, 153);

        txtMemberType.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        txtMemberType.setEnabled(false);
        panel1.add(txtMemberType);
        txtMemberType.setBounds(167, 58, 175, 31);

        jLabel2.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        jLabel2.setText("ประเภทสมาชิก");
        panel1.add(jLabel2);
        jLabel2.setBounds(167, 26, 75, 25);

        jLabel3.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        jLabel3.setText("ชื่อ-นามสกุล สมาชิก");
        panel1.add(jLabel3);
        jLabel3.setBounds(354, 26, 101, 25);

        txtName.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        txtName.setEnabled(false);
        panel1.add(txtName);
        txtName.setBounds(354, 58, 139, 31);

        jLabel4.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        jLabel4.setText("รหัสประจำตัว");
        panel1.add(jLabel4);
        jLabel4.setBounds(40, 210, 68, 25);

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
        txtMemberID.setBounds(10, 250, 140, 31);

        jLabel5.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        jLabel5.setText("ที่อยู่");
        panel1.add(jLabel5);
        jLabel5.setBounds(167, 102, 22, 25);

        txtAddress.setColumns(20);
        txtAddress.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        txtAddress.setRows(5);
        txtAddress.setEnabled(false);
        jScrollPane1.setViewportView(txtAddress);

        panel1.add(jScrollPane1);
        jScrollPane1.setBounds(167, 129, 326, 131);

        jLabel7.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        jLabel7.setText("เพศ");
        panel1.add(jLabel7);
        jLabel7.setBounds(330, 270, 21, 25);

        txtSex.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        txtSex.setEnabled(false);
        panel1.add(txtSex);
        txtSex.setBounds(330, 299, 59, 31);

        jLabel8.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        jLabel8.setText("แผนกวิชา");
        panel1.add(jLabel8);
        jLabel8.setBounds(167, 267, 51, 25);

        txtNameDepart.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        txtNameDepart.setEnabled(false);
        panel1.add(txtNameDepart);
        txtNameDepart.setBounds(169, 299, 154, 31);

        jLabel10.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        jLabel10.setText("โทรศัพท์");
        panel1.add(jLabel10);
        jLabel10.setBounds(396, 267, 44, 25);

        txtPhone.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        txtPhone.setEnabled(false);
        panel1.add(txtPhone);
        txtPhone.setBounds(396, 299, 97, 31);

        btnFind.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        btnFind.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons/find32.png"))); // NOI18N
        btnFind.setText("ค้นหา");
        btnFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindActionPerformed(evt);
            }
        });
        panel1.add(btnFind);
        btnFind.setBounds(10, 290, 140, 40);

        getContentPane().add(panel1);
        panel1.setBounds(12, 17, 510, 350);

        panel2.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        panel2.setLayout(null);

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
        jScrollPane2.setViewportView(tblBorrows);
        if (tblBorrows.getColumnModel().getColumnCount() > 0) {
            tblBorrows.getColumnModel().getColumn(0).setMinWidth(15);
            tblBorrows.getColumnModel().getColumn(1).setMinWidth(30);
        }

        panel2.add(jScrollPane2);
        jScrollPane2.setBounds(10, 10, 470, 290);

        lblTotalBookRent.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        lblTotalBookRent.setText("จำนวนหนังสือที่ยืม: ");
        panel2.add(lblTotalBookRent);
        lblTotalBookRent.setBounds(10, 310, 460, 25);

        getContentPane().add(panel2);
        panel2.setBounds(534, 17, 500, 343);

        panel3.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        panel3.setLayout(null);

        jLabel6.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        jLabel6.setText("หมายเลข ISBN");
        panel3.add(jLabel6);
        jLabel6.setBounds(260, 20, 77, 25);

        txtISBN.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        txtISBN.setEnabled(false);
        panel3.add(txtISBN);
        txtISBN.setBounds(260, 50, 220, 31);

        jLabel9.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        jLabel9.setText("เลขทะเบียน");
        panel3.add(jLabel9);
        jLabel9.setBounds(17, 13, 60, 25);

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
        });
        panel3.add(txtBookReg);
        txtBookReg.setBounds(17, 45, 190, 31);

        jLabel11.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        jLabel11.setText("ค่ายืม/เล่ม");
        panel3.add(jLabel11);
        jLabel11.setBounds(17, 154, 53, 25);

        txtBookNormal.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        txtBookNormal.setEnabled(false);
        panel3.add(txtBookNormal);
        txtBookNormal.setBounds(17, 186, 146, 31);

        jLabel12.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        jLabel12.setText("ชื่อหนังสือ");
        panel3.add(jLabel12);
        jLabel12.setBounds(17, 83, 51, 25);

        txtBookName.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        txtBookName.setEnabled(false);
        panel3.add(txtBookName);
        txtBookName.setBounds(17, 110, 230, 31);

        jLabel13.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        jLabel13.setText("ผู้แต่งหนังสือ");
        panel3.add(jLabel13);
        jLabel13.setBounds(254, 83, 65, 25);

        txtBookTranslate.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        txtBookTranslate.setEnabled(false);
        panel3.add(txtBookTranslate);
        txtBookTranslate.setBounds(259, 110, 222, 31);

        jLabel14.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        jLabel14.setText("ค่าปรับ/วัน");
        panel3.add(jLabel14);
        jLabel14.setBounds(181, 154, 90, 25);

        txtBookOver.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        txtBookOver.setEnabled(false);
        panel3.add(txtBookOver);
        txtBookOver.setBounds(170, 186, 146, 31);

        jLabel15.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        jLabel15.setText("จำนวนวันให้ยืม");
        panel3.add(jLabel15);
        jLabel15.setBounds(334, 154, 82, 25);

        txtBookRent.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        txtBookRent.setEnabled(false);
        panel3.add(txtBookRent);
        txtBookRent.setBounds(334, 186, 146, 31);

        btnBookSearch.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        btnBookSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons/find24.png"))); // NOI18N
        btnBookSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBookSearchActionPerformed(evt);
            }
        });
        panel3.add(btnBookSearch);
        btnBookSearch.setBounds(210, 40, 40, 40);

        getContentPane().add(panel3);
        panel3.setBounds(10, 390, 510, 240);

        panel4.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        panel4.setLayout(null);

        btnBorrow.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        btnBorrow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons/plus24.png"))); // NOI18N
        btnBorrow.setText("ยืมหนังสือ");
        btnBorrow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrowActionPerformed(evt);
            }
        });
        panel4.add(btnBorrow);
        btnBorrow.setBounds(330, 20, 151, 64);

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

        btnClear.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        btnClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons/remove32.png"))); // NOI18N
        btnClear.setText("ยกเลิกทั้งหมด");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });
        panel4.add(btnClear);
        btnClear.setBounds(330, 90, 151, 64);

        jTextField14.setFont(new java.awt.Font("TH Sarabun New", 1, 24)); // NOI18N
        jTextField14.setEnabled(false);
        panel4.add(jTextField14);
        jTextField14.setBounds(129, 34, 143, 39);

        jTextField15.setFont(new java.awt.Font("TH Sarabun New", 1, 24)); // NOI18N
        jTextField15.setEnabled(false);
        panel4.add(jTextField15);
        jTextField15.setBounds(129, 132, 143, 39);

        jTextField16.setFont(new java.awt.Font("TH Sarabun New", 1, 24)); // NOI18N
        jTextField16.setEnabled(false);
        panel4.add(jTextField16);
        jTextField16.setBounds(129, 80, 143, 39);

        btnClose.setBackground(new java.awt.Color(255, 204, 204));
        btnClose.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        btnClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons/x-button32.png"))); // NOI18N
        btnClose.setText("ปิดโปรแกรม");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });
        panel4.add(btnClose);
        btnClose.setBounds(330, 160, 151, 64);

        getContentPane().add(panel4);
        panel4.setBounds(530, 390, 500, 240);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void txtMemberIDKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMemberIDKeyReleased
        
    }//GEN-LAST:event_txtMemberIDKeyReleased

    private void btnBookSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBookSearchActionPerformed
        findBook();
    }//GEN-LAST:event_btnBookSearchActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        //Close InternalForm
        dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindActionPerformed
        findMember();
    }//GEN-LAST:event_btnFindActionPerformed

    private void txtMemberIDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMemberIDKeyPressed
           int key = evt.getKeyCode();
           //if press Enter key
           if (key == KeyEvent.VK_ENTER) { 
               findMember();
               
              }
    }//GEN-LAST:event_txtMemberIDKeyPressed

    private void txtBookRegKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBookRegKeyPressed
         int key = evt.getKeyCode();
           //if press Enter key
           if (key == KeyEvent.VK_ENTER) { 
               findBook();
                txtBookReg.requestFocusInWindow();
              }
    }//GEN-LAST:event_txtBookRegKeyPressed

    private void btnBorrowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrowActionPerformed
       String member_id = txtMemberID.getText();
       String member_name = txtName.getText();
       if(member_id.equals("")){
           JOptionPane.showMessageDialog(this, Lang.warn_textfild_null + "รหัสสมาชิก", Lang.warn_title, JOptionPane.WARNING_MESSAGE);
            txtMemberID.requestFocusInWindow();
       }else if(member_name.equals("")){
           JOptionPane.showMessageDialog(this, Lang.warn_textfild_null + "ชื่อสมาชิก", Lang.warn_title, JOptionPane.WARNING_MESSAGE);
            txtName.requestFocusInWindow();
       }else{
            int add = 0;
            add = JOptionPane.showConfirmDialog(this, "คุณต้องการเพิ่มรายการยืมหนังสือในระบบใช่หรือไม่ ?");
            if(add == 0) {
                    String book_reg;
                    String date_return;
                    member_id = txtMemberID.getText();
                    //Date_Rent
                    GregorianCalendar gc = new GregorianCalendar();        
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String date_rent = df.format(gc.getTime());
                    int book_return = 1;
                       //
                    int row = tblBorrows.getRowCount();
                   while(row > 0){
                       book_reg = tblBorrows.getValueAt(row-1, 0).toString();
                       date_return = tblBorrows.getValueAt(row-1, 2).toString();
                       //
                       //Insert Record
                        String sql = "INSERT INTO tblBookRent (ID,MemberID,BookReg, DateRent, DateReturn,BookReturn) VALUES (null,'"+
                        member_id + "','" +
                        book_reg + "','" +
                        date_rent + "','" +
                        date_return + "','" +
                        book_return + "')";
                        //
                        //System.out.println(sql);
                        //Execute Query database
                        if (executeSQLQuery(sql, "Inserted") == true){
                              row --;
                        }
                   }
                    if (row == 0){
                              //refreash Table
                               DefaultTableModel model = (DefaultTableModel)tblBorrows.getModel();
                                model.setRowCount(0);
                                //Clear
                               clearTextField();
                                //Show message dialog
                                JOptionPane.showMessageDialog(this, "บันทึกข้อมูลการยืมหนังสือเรียบร้อยแล้ว");
                       }else{
                             JOptionPane.showMessageDialog(this, "เกิดข้อผิดพลาดไม่สามารถบันทึกข้อมูลได้ !!");
                       }
            }
       }
    }//GEN-LAST:event_btnBorrowActionPerformed

    private void txtMemberIDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMemberIDMouseClicked
         txtMemberID.setText("");
    }//GEN-LAST:event_txtMemberIDMouseClicked

    private void tblBorrowsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBorrowsMouseClicked
        int row = tblBorrows.getSelectedRow();		//get select row
        txtBookReg.setText(tblBorrows.getValueAt(row, 0).toString()); 
         ArrayList<Books> list = getBooksList();
        if(list.size()>0){
           txtISBN.setText(list.get(0).getISBN());
           txtBookName.setText(list.get(0).getBookName1());
           txtBookTranslate.setText(list.get(0).getBookTranslateName());
           txtBookNormal.setText(list.get(0).getBookNormal());
           txtBookOver.setText(list.get(0).getBookOver());
           txtBookRent.setText(list.get(0).getBookRent());
          
        }
    }//GEN-LAST:event_tblBorrowsMouseClicked

    private void txtBookRegMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtBookRegMouseClicked
        clearBookTextField();
    }//GEN-LAST:event_txtBookRegMouseClicked

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
         //refreash Table
        DefaultTableModel model = (DefaultTableModel)tblBorrows.getModel();
         model.setRowCount(0);
         //Clear
        clearTextField();
         //Show message dialog
         JOptionPane.showMessageDialog(this, "ยกเลิกข้อมูลการยืมหนังสือเรียบร้อยแล้ว");
    }//GEN-LAST:event_btnClearActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBookSearch;
    private javax.swing.JButton btnBorrow;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnFind;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JLabel lblTotalBookRent;
    private javax.swing.JPanel panel1;
    private javax.swing.JPanel panel2;
    private javax.swing.JPanel panel3;
    private javax.swing.JPanel panel4;
    private javax.swing.JTable tblBorrows;
    private javax.swing.JTextArea txtAddress;
    private javax.swing.JTextField txtBookName;
    private javax.swing.JTextField txtBookNormal;
    private javax.swing.JTextField txtBookOver;
    private javax.swing.JTextField txtBookReg;
    private javax.swing.JTextField txtBookRent;
    private javax.swing.JTextField txtBookTranslate;
    private javax.swing.JTextField txtISBN;
    private javax.swing.JTextField txtMemberID;
    private javax.swing.JTextField txtMemberType;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtNameDepart;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtSex;
    // End of variables declaration//GEN-END:variables
}
