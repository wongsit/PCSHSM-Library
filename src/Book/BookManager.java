/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Book;

import Member.EncryptMD5;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import manage.DbConnect;
import manage.GlobalVar;
import manage.Lang;

/**
 *
 * @author khanchaiwongsit
 */
public class BookManager extends javax.swing.JInternalFrame {
    private String strID;
    private String strBookReg;
    private String strISBN;
    private String strBookCode1;
    private String strBookCode2;
    private String strBookName1;
    private String strBookName2;
    private String strBookType;
    private String strBookDateIncome;
    private String strBookAuthor1;
    private String strBookAuthor2;
    private String strBookAuthor3;
    private String strBookAuthor4;
    private String strBookPublisher;
    private String strBookTranslateName;
    private String strBookPubAddress;
    private String strBookTitle;
    private String strBookTitle2;
    private String strBookYear;
    private String strBookNum;
    private String strBookSource;
    private String strBookMoney;
    private String strBookTotal;
    private String strBookRent;
    private String strBookOver;
    private String strBookNormal;
    private int strBookRentType;
    private int strBookComment;
    private String strBookImage;
    private int strBookStatus;
    /**
     * Creates new form BookManager
     */
    public BookManager() {
        initComponents();
        addItemComboBookType();     //add item top combo
        setTitle(Lang.menu_book_data);
        //set size
        int width = 1190;
        int height = 860;
        setBounds(GlobalVar.screenCenterX-(width/2),20,width,height);
        //initial
        clearTextField();
        showBooksInTable();
        //set Button
         btnInit.setVisible(true);
        btnAdd.setVisible(false);
    }   
        
    
//Class to ArrayList object
    public ArrayList<Books> getBooksList(){
        ArrayList<Books> booksList = new ArrayList<Books>();
        Connection con = DbConnect.getConnection();
        
        //Text Search
        String search = txtSearch.getText();
         // Sql Command              
        String sql = "SELECT * FROM tblBookData  WHERE "+
                "ID LIKE '%"+ search +"%'"+
                "OR BookReg LIKE '%" + search +"%'" +
                "OR ISBN LIKE '%" + search +"%'" +
                "OR BookCode1 LIKE '%" + search +"%'" +
                "OR BookCode2 LIKE '%" + search +"%'" +
                "OR BookName1 LIKE '%" + search +"%'" +
                "OR BookName2 LIKE '%" + search +"%'" +
                "OR BookType LIKE '%" + search +"%'" +
                "OR BookAuthor1 LIKE '%" + search +"%'" +
                "OR BookAuthor2 LIKE '%" + search +"%'" +
                "OR BookPublisher LIKE '%" + search +"%'" +
                "OR BookTranslateName LIKE '%" + search +"%'" +
                "OR BookTitle LIKE '%" + search +"%'" +
                "OR BookYear LIKE '%" + search +"%'" +
                "OR BookSource LIKE '%" + search +"%'" +
                "OR BookStatus LIKE '%" + search +"%'";
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
    
    //Show data to Table
    public void showBooksInTable(){
        ArrayList<Books> list = getBooksList();
        DefaultTableModel model = (DefaultTableModel)tblBooks.getModel();
        Object[] row = new Object[30];      //Object[number colume]
        for(int i=0;i<list.size();i++){
            row[0] = list.get(i).getID();
            row[1] = list.get(i).getBookReg();
            row[2] = list.get(i).getISBN();
            row[3] = list.get(i).getBookCode1();
            row[4] = list.get(i).getBookCode2();
            row[5] = list.get(i).getBookName1();
            row[6] = list.get(i).getBookName2();
            row[7] = list.get(i).getBookType();
            row[8] = list.get(i).getBookDateIncome();
            row[9] = list.get(i).getBookAuthor1();
            row[10] = list.get(i).getBookAuthor2();
            row[11] = list.get(i).getBookAuthor3();
            row[12] = list.get(i).getBookAuthor4();
            row[13] = list.get(i).getBookPublisher();
            row[14] = list.get(i).getBookTranslateName();
            row[15] = list.get(i).getBookPubAddress();
            row[16] = list.get(i).getBookTitle();
            row[17] = list.get(i).getBookTitle2();
            row[18] = list.get(i).getBookYear();
            row[19] = list.get(i).getBookNum();
            row[20] = list.get(i).getBookSource();
            row[21] = list.get(i).getBookMoney();
            row[22] = list.get(i).getBookTotal();
            row[23] = list.get(i).getBookRent();
            row[24] = list.get(i).getBookOver();
            row[25] = list.get(i).getBookNormal();
            row[26] = list.get(i).getBookRentType();
            row[27] = list.get(i).getBookComment();
            row[28] = list.get(i).getBookImage();
            row[29] = list.get(i).getBookStatus();
                            
            model.addRow(row);
        }
        //
        //row in add    
         int row2 = 0;
         row2 = tblBooks.getRowCount();
        lblTotalBooks.setText( "จำนวนหนังสือที่ค้นได้ในระบบ: " + row2 + " เล่ม");
        //new BookReg
        int mTotal = Integer.parseInt(tblBooks.getValueAt(row2-1, 1).toString());
        String newBookReg = String.valueOf(mTotal+1);
         txtID.setText(newBookReg);
        int mLength =  newBookReg.length();
        if(mLength<6){
            for(int i=6-mLength;i>0;i--){
                newBookReg = "0"+newBookReg;
            }
        }       
        txtBookReg.setText(newBookReg);
        //set date
        //Date_Rent
        GregorianCalendar gc = new GregorianCalendar();        
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateincome = df.format(gc.getTime());
        try{
            Date df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateincome);
            dateIncome.setDateFormatString("yyyy-MM-dd HH:mm:ss");
            dateIncome.setDate(df1);
            }catch(Exception e){
                e.printStackTrace();
            }
       //
    }
    
    //execute Query 
    public void executeSQLQuery(String query, String message){
        Connection con = DbConnect.getConnection();
        Statement st;
        try{
            st = con.createStatement();
            if((st.executeUpdate(query)) == 1){
                 //refreash Table
                clearTextField();               
                 //Show message dialog
                 if(message.equals("")){
                     //notshow dialog
                 }else{
                     JOptionPane.showMessageDialog(null, "Data " + message + " Sucessfuly");
                 }                
            }else{
                if(message.equals("")){
                     //notshow dialog
                 }else{
                      JOptionPane.showMessageDialog(null, "Data not " + message);
                 }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
   
    //Class to ArrayList object for Combo BookType
    public ArrayList<BookType> getBookTypeList(){
        ArrayList<BookType> booktypeList = new ArrayList<BookType>();
        Connection con = DbConnect.getConnection();
        
         // Sql Command              
        String sql = "SELECT * FROM tblBookType ";
        //Variable Statement and Resultset
        Statement st;
        ResultSet rs;
        //
        try{
            st = con.createStatement();     //Create Statement
            rs = st.executeQuery(sql);
            BookType booktype;
            while(rs.next()){
                booktype = new BookType(rs.getInt("ID"),rs.getString("Code"),rs.getString("BookName"));
                booktypeList.add(booktype);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return booktypeList;
    }
    
    //add item to Combo 
    public void addItemComboBookType(){
        ArrayList<BookType> list = getBookTypeList();
        Object[] row = new Object[3];
        for(int i=0;i<list.size();i++){
           //add Item
           cmbBookType.addItem(list.get(i).getBookName());
        }
       
    }
    
    //Clear TextField
    public void clearTextField(){
        //Reset TextField
        txtID.setText("");
        txtBookReg.setText("");
        txtISBN.setText("");
        txtBookCode1.setText("");
        txtBookCode2.setText("");
        txtBookName1.setText("");
        txtBookName2.setText("");
        cmbBookType.getModel().setSelectedItem("None");       
        txtBookAuthor1.setText("");
        txtBookAuthor2.setText("");
        txtBookAuthor3.setText("");
        txtBookAuthor4.setText("");
        txtBookPublisher.setText("");
        txtBookTranslateName.setText("");
        txtBookPubAddress.setText("");
        txtBookTitle.setText("");
        txtBookTitle2.setText("");
        txtBookYear.setText("");
        txtBookNum.setText("");
        txtBookSource.setText("");
        txtBookMoney.setText("");
        txtBookTotal.setText("");
        txtBookRent.setText("");
        txtBookOver.setText("");
        txtBookNormal.setText("");
        cmbBookRentType.getModel().setSelectedItem("สมาชิก");
        cmbBookComment.getModel().setSelectedItem("ยืมตามปกติ");
        btnBookImage.setText("");
        cmbBookStatus.getModel().setSelectedItem("ปกติ");
        //refreash Table
        DefaultTableModel model = (DefaultTableModel)tblBooks.getModel();
        model.setRowCount(0);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jRadioButtonMenuItem1 = new javax.swing.JRadioButtonMenuItem();
        jPanel1 = new javax.swing.JPanel();
        btnClose = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBooks = new javax.swing.JTable();
        btnBookImage = new javax.swing.JButton();
        lblID = new javax.swing.JLabel();
        lblID1 = new javax.swing.JLabel();
        lblID2 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        txtISBN = new javax.swing.JTextField();
        txtBookReg = new javax.swing.JTextField();
        lblID3 = new javax.swing.JLabel();
        txtBookCode1 = new javax.swing.JTextField();
        lblID4 = new javax.swing.JLabel();
        txtBookCode2 = new javax.swing.JTextField();
        lblID5 = new javax.swing.JLabel();
        txtBookName1 = new javax.swing.JTextField();
        lblID6 = new javax.swing.JLabel();
        txtBookName2 = new javax.swing.JTextField();
        lblID7 = new javax.swing.JLabel();
        lblID8 = new javax.swing.JLabel();
        cmbBookType = new javax.swing.JComboBox<>();
        lblID9 = new javax.swing.JLabel();
        txtBookAuthor1 = new javax.swing.JTextField();
        lblID10 = new javax.swing.JLabel();
        txtBookAuthor2 = new javax.swing.JTextField();
        lblID11 = new javax.swing.JLabel();
        txtBookAuthor3 = new javax.swing.JTextField();
        lblID12 = new javax.swing.JLabel();
        txtBookAuthor4 = new javax.swing.JTextField();
        lblID13 = new javax.swing.JLabel();
        txtBookPublisher = new javax.swing.JTextField();
        lblID14 = new javax.swing.JLabel();
        txtBookTranslateName = new javax.swing.JTextField();
        lblID15 = new javax.swing.JLabel();
        txtBookTitle = new javax.swing.JTextField();
        lblID16 = new javax.swing.JLabel();
        txtBookTitle2 = new javax.swing.JTextField();
        lblID17 = new javax.swing.JLabel();
        lblID18 = new javax.swing.JLabel();
        lblID19 = new javax.swing.JLabel();
        txtBookYear = new javax.swing.JTextField();
        txtBookNum = new javax.swing.JTextField();
        lblID20 = new javax.swing.JLabel();
        txtBookSource = new javax.swing.JTextField();
        lblID21 = new javax.swing.JLabel();
        txtBookMoney = new javax.swing.JTextField();
        lblID22 = new javax.swing.JLabel();
        lblID23 = new javax.swing.JLabel();
        txtBookRent = new javax.swing.JTextField();
        lblID24 = new javax.swing.JLabel();
        txtBookOver = new javax.swing.JTextField();
        lblID25 = new javax.swing.JLabel();
        txtBookNormal = new javax.swing.JTextField();
        lblID26 = new javax.swing.JLabel();
        lblID27 = new javax.swing.JLabel();
        lblID28 = new javax.swing.JLabel();
        cmbBookStatus = new javax.swing.JComboBox<>();
        cmbBookRentType = new javax.swing.JComboBox<>();
        cmbBookComment = new javax.swing.JComboBox<>();
        txtBookTotal = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtBookPubAddress = new javax.swing.JTextArea();
        lblID29 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        dateIncome = new com.toedter.calendar.JDateChooser();
        lblID31 = new javax.swing.JLabel();
        lblID32 = new javax.swing.JLabel();
        btnInit = new javax.swing.JButton();
        lblID30 = new javax.swing.JLabel();
        lblTotalBooks = new javax.swing.JLabel();

        jRadioButtonMenuItem1.setSelected(true);
        jRadioButtonMenuItem1.setText("jRadioButtonMenuItem1");

        getContentPane().setLayout(null);

        jPanel1.setLayout(null);

        btnClose.setFont(new java.awt.Font("TH Sarabun New", 1, 24)); // NOI18N
        btnClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons/x-button32.png"))); // NOI18N
        btnClose.setText("ปิด");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });
        jPanel1.add(btnClose);
        btnClose.setBounds(990, 280, 160, 56);

        btnUpdate.setFont(new java.awt.Font("TH Sarabun New", 1, 24)); // NOI18N
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons/refresh32.png"))); // NOI18N
        btnUpdate.setText("ปรับปรุง");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        jPanel1.add(btnUpdate);
        btnUpdate.setBounds(990, 102, 160, 61);

        btnDelete.setFont(new java.awt.Font("TH Sarabun New", 1, 24)); // NOI18N
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons/remove32.png"))); // NOI18N
        btnDelete.setText("ลบข้อมูล");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jPanel1.add(btnDelete);
        btnDelete.setBounds(990, 170, 160, 61);

        btnAdd.setBackground(new java.awt.Color(255, 255, 255));
        btnAdd.setFont(new java.awt.Font("TH Sarabun New", 1, 24)); // NOI18N
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons/floppy-disk32.png"))); // NOI18N
        btnAdd.setText("บันทึกข้อมูล");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        btnAdd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnAddKeyPressed(evt);
            }
        });
        jPanel1.add(btnAdd);
        btnAdd.setBounds(990, 30, 160, 65);

        tblBooks.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ลำดับ", "เลขทะเบียน", "ISBN", "Code1", "Code2", "ชื่อหนังสือ", "Name2", "Type", "DateIncome", "Author1", "Author2", "Author3", "Author4", "Publisher", "TranslateName", "Address", "Title", "Title2", "Year", "Num", "Source", "Money", "Total", "Rent", "Over", "Normal", "RentType", "Comment", "Image", "Status"
            }
        ));
        tblBooks.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBooksMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblBooks);
        if (tblBooks.getColumnModel().getColumnCount() > 0) {
            tblBooks.getColumnModel().getColumn(1).setMinWidth(100);
            tblBooks.getColumnModel().getColumn(2).setMinWidth(100);
            tblBooks.getColumnModel().getColumn(5).setMinWidth(200);
        }

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(12, 579, 1158, 210);

        btnBookImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons/book24.png"))); // NOI18N
        jPanel1.add(btnBookImage);
        btnBookImage.setBounds(15, 19, 146, 165);

        lblID.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        lblID.setText("ลำดับ");
        jPanel1.add(lblID);
        lblID.setBounds(233, 22, 30, 25);

        lblID1.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        lblID1.setText("เลขทะเบียน");
        jPanel1.add(lblID1);
        lblID1.setBounds(200, 60, 60, 25);

        lblID2.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        lblID2.setText("รหัส ISBN");
        jPanel1.add(lblID2);
        lblID2.setBounds(203, 105, 60, 25);

        txtID.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        jPanel1.add(txtID);
        txtID.setBounds(268, 19, 76, 31);

        txtISBN.setBackground(new java.awt.Color(204, 255, 255));
        txtISBN.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        jPanel1.add(txtISBN);
        txtISBN.setBounds(268, 102, 201, 31);

        txtBookReg.setBackground(new java.awt.Color(204, 255, 255));
        txtBookReg.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        jPanel1.add(txtBookReg);
        txtBookReg.setBounds(268, 56, 121, 31);

        lblID3.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        lblID3.setText("เลขหมู่");
        jPanel1.add(lblID3);
        lblID3.setBounds(223, 145, 40, 25);

        txtBookCode1.setBackground(new java.awt.Color(255, 255, 204));
        txtBookCode1.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        jPanel1.add(txtBookCode1);
        txtBookCode1.setBounds(268, 142, 201, 31);

        lblID4.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        lblID4.setText("เลขผู้แต่ง");
        jPanel1.add(lblID4);
        lblID4.setBounds(207, 189, 50, 25);

        txtBookCode2.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        jPanel1.add(txtBookCode2);
        txtBookCode2.setBounds(268, 186, 201, 31);

        lblID5.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        lblID5.setText("ชื่อเรื่อง");
        jPanel1.add(lblID5);
        lblID5.setBounds(217, 227, 40, 25);

        txtBookName1.setBackground(new java.awt.Color(255, 255, 204));
        txtBookName1.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        jPanel1.add(txtBookName1);
        txtBookName1.setBounds(268, 224, 242, 31);

        lblID6.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        lblID6.setText("ชื่อเรื่อง 2");
        jPanel1.add(lblID6);
        lblID6.setBounds(213, 265, 50, 25);

        txtBookName2.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        jPanel1.add(txtBookName2);
        txtBookName2.setBounds(268, 262, 242, 31);

        lblID7.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        lblID7.setText("วันที่รับหนังสือ");
        jPanel1.add(lblID7);
        lblID7.setBounds(183, 334, 80, 25);

        lblID8.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        lblID8.setText("ประเภทหนังสือ");
        jPanel1.add(lblID8);
        lblID8.setBounds(180, 300, 80, 25);

        cmbBookType.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        cmbBookType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None" }));
        jPanel1.add(cmbBookType);
        cmbBookType.setBounds(268, 293, 235, 31);

        lblID9.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        lblID9.setText("ผู้แต่ง 1");
        jPanel1.add(lblID9);
        lblID9.setBounds(223, 372, 40, 25);

        txtBookAuthor1.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        jPanel1.add(txtBookAuthor1);
        txtBookAuthor1.setBounds(268, 369, 242, 31);

        lblID10.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        lblID10.setText("ผู้แต่ง 2");
        jPanel1.add(lblID10);
        lblID10.setBounds(223, 410, 40, 25);

        txtBookAuthor2.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        jPanel1.add(txtBookAuthor2);
        txtBookAuthor2.setBounds(268, 407, 242, 31);

        lblID11.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        lblID11.setText("ผู้แต่ง 3");
        jPanel1.add(lblID11);
        lblID11.setBounds(223, 454, 40, 25);

        txtBookAuthor3.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        jPanel1.add(txtBookAuthor3);
        txtBookAuthor3.setBounds(268, 451, 242, 31);

        lblID12.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        lblID12.setText("ผู้แต่ง 4");
        jPanel1.add(lblID12);
        lblID12.setBounds(223, 492, 40, 25);

        txtBookAuthor4.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        jPanel1.add(txtBookAuthor4);
        txtBookAuthor4.setBounds(268, 489, 242, 31);

        lblID13.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        lblID13.setText("Book Publisher");
        jPanel1.add(lblID13);
        lblID13.setBounds(171, 538, 85, 25);

        txtBookPublisher.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        jPanel1.add(txtBookPublisher);
        txtBookPublisher.setBounds(268, 535, 242, 31);

        lblID14.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        lblID14.setText("ผู้แปล");
        jPanel1.add(lblID14);
        lblID14.setBounds(610, 490, 40, 25);

        txtBookTranslateName.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        jPanel1.add(txtBookTranslateName);
        txtBookTranslateName.setBounds(650, 490, 200, 31);

        lblID15.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        lblID15.setText("สถานที่พิมพ์");
        jPanel1.add(lblID15);
        lblID15.setBounds(568, 94, 70, 25);

        txtBookTitle.setBackground(new java.awt.Color(255, 255, 204));
        txtBookTitle.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        jPanel1.add(txtBookTitle);
        txtBookTitle.setBounds(644, 19, 280, 31);

        lblID16.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        lblID16.setText("หัวเรื่อง");
        jPanel1.add(lblID16);
        lblID16.setBounds(598, 25, 40, 25);

        txtBookTitle2.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        jPanel1.add(txtBookTitle2);
        txtBookTitle2.setBounds(644, 56, 280, 31);

        lblID17.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        lblID17.setText("ปีที่พิมพ์");
        jPanel1.add(lblID17);
        lblID17.setBounds(600, 180, 50, 25);

        lblID18.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        lblID18.setText("หัวเรื่อง 2");
        jPanel1.add(lblID18);
        lblID18.setBounds(588, 59, 50, 25);

        lblID19.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        lblID19.setText("ครั้งที่พิมพ์");
        jPanel1.add(lblID19);
        lblID19.setBounds(590, 220, 58, 25);

        txtBookYear.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        jPanel1.add(txtBookYear);
        txtBookYear.setBounds(650, 177, 109, 31);

        txtBookNum.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        jPanel1.add(txtBookNum);
        txtBookNum.setBounds(650, 215, 56, 31);

        lblID20.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        lblID20.setText("แหล่งที่มา");
        jPanel1.add(lblID20);
        lblID20.setBounds(590, 260, 60, 25);

        txtBookSource.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        jPanel1.add(txtBookSource);
        txtBookSource.setBounds(650, 257, 268, 31);

        lblID21.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        lblID21.setText("ราคา");
        jPanel1.add(lblID21);
        lblID21.setBounds(615, 296, 30, 25);

        txtBookMoney.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        jPanel1.add(txtBookMoney);
        txtBookMoney.setBounds(652, 293, 93, 31);

        lblID22.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        lblID22.setText("จำนวนหนังสือ");
        jPanel1.add(lblID22);
        lblID22.setBounds(565, 334, 80, 25);

        lblID23.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        lblID23.setText("จำนวนวันที่ให้ยืม");
        jPanel1.add(lblID23);
        lblID23.setBounds(555, 372, 90, 25);

        txtBookRent.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        jPanel1.add(txtBookRent);
        txtBookRent.setBounds(652, 369, 199, 31);

        lblID24.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        lblID24.setText("ค่าปรับ/วัน");
        jPanel1.add(lblID24);
        lblID24.setBounds(587, 416, 57, 25);

        txtBookOver.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        jPanel1.add(txtBookOver);
        txtBookOver.setBounds(652, 413, 199, 31);

        lblID25.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        lblID25.setText("ค่ายืม/วัน");
        jPanel1.add(lblID25);
        lblID25.setBounds(595, 454, 50, 25);

        txtBookNormal.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        jPanel1.add(txtBookNormal);
        txtBookNormal.setBounds(652, 451, 199, 31);

        lblID26.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        lblID26.setText("สถานะการยืม");
        jPanel1.add(lblID26);
        lblID26.setBounds(880, 490, 80, 25);

        lblID27.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        lblID27.setText("Book Comment");
        jPanel1.add(lblID27);
        lblID27.setBounds(657, 416, 89, 25);

        lblID28.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        lblID28.setText("Book Status");
        jPanel1.add(lblID28);
        lblID28.setBounds(679, 454, 67, 25);

        cmbBookStatus.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        cmbBookStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ปกติ", "ถูกยืม" }));
        jPanel1.add(cmbBookStatus);
        cmbBookStatus.setBounds(960, 490, 210, 31);

        cmbBookRentType.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        cmbBookRentType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "ทั่วไป", "สมาชิก", "VIP" }));
        jPanel1.add(cmbBookRentType);
        cmbBookRentType.setBounds(958, 369, 211, 36);

        cmbBookComment.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        cmbBookComment.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ยกเลิกให้ยืม", "ยืมตามปกติ" }));
        jPanel1.add(cmbBookComment);
        cmbBookComment.setBounds(960, 430, 210, 31);

        txtBookTotal.setBackground(new java.awt.Color(255, 255, 204));
        txtBookTotal.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        jPanel1.add(txtBookTotal);
        txtBookTotal.setBounds(652, 331, 199, 31);

        txtBookPubAddress.setColumns(20);
        txtBookPubAddress.setRows(5);
        jScrollPane2.setViewportView(txtBookPubAddress);

        jPanel1.add(jScrollPane2);
        jScrollPane2.setBounds(650, 94, 274, 63);

        lblID29.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        lblID29.setText("ค้นหาหนังสือ เชื่อ ชื่อหนังสือ, เลขทะเบียนม, ปีที่พิมพ์");
        jPanel1.add(lblID29);
        lblID29.setBounds(661, 540, 280, 25);

        txtSearch.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });
        jPanel1.add(txtSearch);
        txtSearch.setBounds(950, 540, 216, 31);

        jLabel2.setFont(new java.awt.Font("TH Sarabun New", 0, 24)); // NOI18N
        jLabel2.setForeground(java.awt.Color.red);
        jLabel2.setText("*");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(394, 54, 22, 33);

        jLabel3.setText("รูปปกหนังสือ");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(60, 190, 70, 14);
        jPanel1.add(dateIncome);
        dateIncome.setBounds(271, 330, 240, 30);

        lblID31.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        lblID31.setText("ประเภทการยืม");
        jPanel1.add(lblID31);
        lblID31.setBounds(880, 380, 80, 25);

        lblID32.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        lblID32.setText("หมายเหตุ");
        jPanel1.add(lblID32);
        lblID32.setBounds(900, 430, 60, 25);

        btnInit.setBackground(new java.awt.Color(255, 255, 255));
        btnInit.setFont(new java.awt.Font("TH Sarabun New", 1, 24)); // NOI18N
        btnInit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons/plus32.png"))); // NOI18N
        btnInit.setText("เพิ่มหนังสือ");
        btnInit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInitActionPerformed(evt);
            }
        });
        jPanel1.add(btnInit);
        btnInit.setBounds(990, 30, 160, 65);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(-1, 0, 1190, 800);

        lblID30.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        lblID30.setText("Book Reg");
        getContentPane().add(lblID30);
        lblID30.setBounds(211, 59, 52, 25);

        lblTotalBooks.setText("BookTotal");
        getContentPane().add(lblTotalBooks);
        lblTotalBooks.setBounds(10, 800, 260, 20);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        //Close InternalForm
        dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void tblBooksMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBooksMouseClicked
        //Select Row
         int row = tblBooks.getSelectedRow();		//get select row
        //get value to TextField
        txtID.setText(tblBooks.getValueAt(row, 0).toString());
        txtBookReg.setText(tblBooks.getValueAt(row, 1).toString());
        txtISBN.setText(tblBooks.getValueAt(row, 2).toString());
        txtBookCode1.setText(tblBooks.getValueAt(row, 3).toString());
        txtBookCode2.setText(tblBooks.getValueAt(row, 4).toString());
        txtBookName1.setText(tblBooks.getValueAt(row, 5).toString());
        txtBookName2.setText(tblBooks.getValueAt(row, 6).toString());
        cmbBookType.getModel().setSelectedItem(tblBooks.getValueAt(row, 7));        
         //Date In
        String DateIn = tblBooks.getValueAt(row, 8).toString();
            try{
            Date df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(DateIn);
            dateIncome.setDateFormatString("yyyy-MM-dd HH:mm:ss");
            dateIncome.setDate(df1);
            }catch(Exception e){
                e.printStackTrace();
            }
        txtBookAuthor1.setText(tblBooks.getValueAt(row, 9).toString());
        txtBookAuthor2.setText(tblBooks.getValueAt(row, 10).toString());
        txtBookAuthor3.setText(tblBooks.getValueAt(row, 11).toString());
        txtBookAuthor4.setText(tblBooks.getValueAt(row, 12).toString());
        txtBookPublisher.setText(tblBooks.getValueAt(row, 13).toString());
        txtBookTranslateName.setText(tblBooks.getValueAt(row, 14).toString());
        txtBookPubAddress.setText(tblBooks.getValueAt(row, 15).toString());
        txtBookTitle.setText(tblBooks.getValueAt(row, 16).toString());
        txtBookTitle2.setText(tblBooks.getValueAt(row, 17).toString());
        txtBookYear.setText(tblBooks.getValueAt(row, 18).toString());
        txtBookNum.setText(tblBooks.getValueAt(row, 19).toString());
        txtBookSource.setText(tblBooks.getValueAt(row, 20).toString());
        txtBookMoney.setText(tblBooks.getValueAt(row, 21).toString());
        txtBookTotal.setText(tblBooks.getValueAt(row, 22).toString());
        txtBookRent.setText(tblBooks.getValueAt(row, 23).toString());
        txtBookOver.setText(tblBooks.getValueAt(row, 24).toString());
        txtBookNormal.setText(tblBooks.getValueAt(row, 25).toString());
        cmbBookRentType.setSelectedIndex((int) tblBooks.getValueAt(row, 26));
        cmbBookComment.setSelectedIndex((int) tblBooks.getValueAt(row, 27));
        btnBookImage.setText(tblBooks.getValueAt(row, 28).toString());
        cmbBookStatus.setSelectedIndex((int) tblBooks.getValueAt(row, 29));
       //
    }//GEN-LAST:event_tblBooksMouseClicked

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
         // Search data 
        DefaultTableModel model = (DefaultTableModel)tblBooks.getModel();
        model.setRowCount(0);
         showBooksInTable();
    }//GEN-LAST:event_txtSearchKeyReleased

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        //input value to variable
        strID = txtID.getText();
        strBookReg = txtBookReg.getText();
        strISBN = txtISBN.getText();
        strBookCode1 = txtBookCode1.getText();
        strBookCode2 = txtBookCode2.getText();
        strBookName1 = txtBookName1.getText();
        strBookName2 = txtBookName2.getText();
        strBookType = cmbBookType.getModel().getSelectedItem().toString();      
        //Date_Income
        GregorianCalendar gc = new GregorianCalendar();        
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        gc.setTime(dateIncome.getDate());
        strBookDateIncome  = df.format(gc.getTime());
        //
        strBookAuthor1 = txtBookAuthor1.getText();
        strBookAuthor2 = txtBookAuthor2.getText();
        strBookAuthor3 = txtBookAuthor3.getText();
        strBookAuthor4 = txtBookAuthor4.getText();
        strBookPublisher = txtBookPublisher.getText();
        strBookTranslateName = txtBookTranslateName.getText();
        strBookPubAddress = txtBookPubAddress.getText();
        strBookTitle = txtBookTitle.getText();
        strBookTitle2 = txtBookTitle2.getText();
        strBookYear = txtBookYear.getText();
        strBookNum = txtBookNum.getText();
        strBookSource = txtBookSource.getText();
        strBookMoney = txtBookMoney.getText();
        strBookTotal = txtBookTotal.getText();
        strBookRent = txtBookRent.getText();
        strBookOver = txtBookOver.getText();
        strBookNormal = txtBookNormal.getText();
        strBookRentType = cmbBookRentType.getSelectedIndex();
        strBookComment = cmbBookComment.getSelectedIndex();
        strBookImage = btnBookImage.getText();
        strBookStatus = cmbBookStatus.getSelectedIndex();

        //Check input value
        if(strBookReg.equals("")) {			//Check BookReg
                JOptionPane.showMessageDialog(null, Lang.warn_textfild_null + Lang.label_BookReg, Lang.warn_title, JOptionPane.WARNING_MESSAGE);
                txtBookReg.requestFocusInWindow();
        }else if(strISBN.equals("")) {			//Check ISBN
                JOptionPane.showMessageDialog(null, Lang.warn_textfild_null + Lang.label_ISBN, Lang.warn_title, JOptionPane.WARNING_MESSAGE);
                txtISBN.requestFocusInWindow();
        }else if(strBookName1.equals("")) {                 //BookCode1
                JOptionPane.showMessageDialog(null, Lang.warn_textfild_null + Lang.label_BookName1, Lang.warn_title, JOptionPane.WARNING_MESSAGE);
                txtBookName1.requestFocusInWindow();
        }else if(strBookCode1.equals("")) {			//Check BookTitle
                JOptionPane.showMessageDialog(null, Lang.warn_textfild_null + Lang.label_BookCode1, Lang.warn_title, JOptionPane.WARNING_MESSAGE);
                txtBookCode1.requestFocusInWindow();
        }else if(strBookType.equals("")) {			//Check BookTotal
                JOptionPane.showMessageDialog(null, Lang.warn_textfild_null + Lang.label_BookType, Lang.warn_title, JOptionPane.WARNING_MESSAGE);
                cmbBookType.requestFocusInWindow();
         }else if(strBookDateIncome.equals("")) {			//Check BookTotal
                JOptionPane.showMessageDialog(null, Lang.warn_textfild_null + Lang.label_BookDateIncome, Lang.warn_title, JOptionPane.WARNING_MESSAGE);
                dateIncome.requestFocusInWindow();
         }else if(strBookAuthor1.equals("")) {			//Check BookTotal
                JOptionPane.showMessageDialog(null, Lang.warn_textfild_null + Lang.label_BookAuthor1, Lang.warn_title, JOptionPane.WARNING_MESSAGE);
                txtBookAuthor1.requestFocusInWindow();
         }else if(strBookPublisher.equals("")) {			//Check BookTotal
                JOptionPane.showMessageDialog(null, Lang.warn_textfild_null + Lang.label_BookPublisher, Lang.warn_title, JOptionPane.WARNING_MESSAGE);
                txtBookPublisher.requestFocusInWindow();
         }else if(strBookYear.equals("")) {			//Check BookTotal
                JOptionPane.showMessageDialog(null, Lang.warn_textfild_null + Lang.label_BookYear, Lang.warn_title, JOptionPane.WARNING_MESSAGE);
                txtBookYear.requestFocusInWindow();
         }else if(strBookNum.equals("")) {			//Check BookTotal
                JOptionPane.showMessageDialog(null, Lang.warn_textfild_null + Lang.label_BookNum, Lang.warn_title, JOptionPane.WARNING_MESSAGE);
                txtBookNum.requestFocusInWindow();
         }else if(strBookSource.equals("")) {			//Check BookTotal
                JOptionPane.showMessageDialog(null, Lang.warn_textfild_null + Lang.label_BookSource, Lang.warn_title, JOptionPane.WARNING_MESSAGE);
                txtBookSource.requestFocusInWindow();
         }else if(strBookMoney.equals("")) {			//Check BookTotal
                JOptionPane.showMessageDialog(null, Lang.warn_textfild_null + Lang.label_BookMoney, Lang.warn_title, JOptionPane.WARNING_MESSAGE);
                txtBookMoney.requestFocusInWindow();
        }else if(strBookTotal.equals("")) {			//Check BookTotal
                JOptionPane.showMessageDialog(null, Lang.warn_textfild_null + Lang.label_BookTotal, Lang.warn_title, JOptionPane.WARNING_MESSAGE);
                txtBookTotal.requestFocusInWindow();
         }else if(strBookRent.equals("")) {			//Check BookTotal
                JOptionPane.showMessageDialog(null, Lang.warn_textfild_null + Lang.label_BookRent, Lang.warn_title, JOptionPane.WARNING_MESSAGE);
                txtBookRent.requestFocusInWindow();
         }else if(strBookOver.equals("")) {			//Check BookTotal
                JOptionPane.showMessageDialog(null, Lang.warn_textfild_null + Lang.label_BookOver, Lang.warn_title, JOptionPane.WARNING_MESSAGE);
                txtBookOver.requestFocusInWindow();
         }else if(strBookRentType==0) {			//Check BookTotal
                JOptionPane.showMessageDialog(null, Lang.warn_textfild_null + Lang.label_BookRentType, Lang.warn_title, JOptionPane.WARNING_MESSAGE);
                cmbBookRentType.requestFocusInWindow();                
        }else{
                //Check BookNormal
               if(strBookNormal.equals("")) {			
                      strBookNormal="0";
               }    
                //ADD Book data
                 int bookTotal = Integer.parseInt(strBookTotal);
                int newBookReg;
                strBookTotal="1";
                for(int i=0;i<bookTotal;i++){
                //Insert Record
                 String sql = "INSERT INTO tblBookData (ID,BookReg,ISBN,BookCode1,BookCode2,BookName1,BookName2,BookType," +
                                     "BookDateIncome,BookAuthor1,BookAuthor2,BookAuthor3,BookAuthor4,BookPublisher," +
                                     "BookTranslateName, BookPubAddress,BookTitle,BookTitle2,BookYear,BookNum," +
                                     "BookSource,BookMoney,BookTotal,BookRent,BookOver,BookNormal,BookRentType," +
                                     "BookComment,BookImage,BookStatus) VALUES (null,'"+
                                     strBookReg + "','" +
                                     strISBN + "','" +
                                     strBookCode1 + "','" +
                                     strBookCode2 + "','" +
                                     strBookName1 + "','" +
                                     strBookName2 + "','" +
                                     strBookType + "','" +
                                     strBookDateIncome + "','" +
                                     strBookAuthor1 + "','" +
                                     strBookAuthor2 + "','" +
                                     strBookAuthor3 + "','" +
                                     strBookAuthor4 + "','" +
                                     strBookPublisher + "','" +
                                     strBookTranslateName + "','" +
                                     strBookPubAddress + "','" +
                                     strBookTitle + "','" +
                                     strBookTitle2 + "','" +
                                     strBookYear + "','" +
                                     strBookNum + "','" +
                                     strBookSource + "','" +
                                     strBookMoney + "','" +
                                     strBookTotal + "','" +
                                     strBookRent + "','" +
                                     strBookOver + "','" +
                                     strBookNormal + "','" +
                                     strBookRentType + "','" +
                                     strBookComment + "','" +
                                     strBookImage + "','" +
                                     strBookStatus + "')";
                // System.out.print(sql);
                 //Execute Query database
                 executeSQLQuery(sql, "");
                 //add bookReg
                  newBookReg = Integer.parseInt(strBookReg);      
                  newBookReg+=1;
                  strBookReg = String.valueOf(newBookReg);
                   int mLength =   strBookReg.length();
                   if(mLength<6){
                    for(int j=6-mLength;j>0;j--){
                         strBookReg = "0"+ strBookReg;
                    }
                }      
           }
            //Clear
            clearTextField();
             btnInit.setVisible(true);
            btnAdd.setVisible(false);
         }        
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        //input value to variable
        strID = txtID.getText();
        strBookReg = txtBookReg.getText();
        strISBN = txtISBN.getText();
        strBookCode1 = txtBookCode1.getText();
        strBookCode2 = txtBookCode2.getText();
        strBookName1 = txtBookName1.getText();
        strBookName2 = txtBookName2.getText();
        strBookType = cmbBookType.getModel().getSelectedItem().toString();
         //Date_Income
        GregorianCalendar gc = new GregorianCalendar();        
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        gc.setTime(dateIncome.getDate());
        strBookDateIncome  = df.format(gc.getTime());
        //
        strBookAuthor1 = txtBookAuthor1.getText();
        strBookAuthor2 = txtBookAuthor2.getText();
        strBookAuthor3 = txtBookAuthor3.getText();
        strBookAuthor4 = txtBookAuthor4.getText();
        strBookPublisher = txtBookPublisher.getText();
        strBookTranslateName = txtBookTranslateName.getText();
        strBookPubAddress = txtBookPubAddress.getText();
        strBookTitle = txtBookTitle.getText();
        strBookTitle2 = txtBookTitle2.getText();
        strBookYear = txtBookYear.getText();
        strBookNum = txtBookNum.getText();
        strBookSource = txtBookSource.getText();
        strBookMoney = txtBookMoney.getText();
        strBookTotal = txtBookTotal.getText();
        strBookRent = txtBookRent.getText();
        strBookOver = txtBookOver.getText();
        strBookNormal = txtBookNormal.getText();
        strBookRentType = cmbBookRentType.getSelectedIndex();
        strBookComment = cmbBookComment.getSelectedIndex();
        strBookImage = btnBookImage.getText();
        strBookStatus = cmbBookStatus.getSelectedIndex();

        //Check input value
        if(strBookReg.equals("")) {			//Check User ID
                JOptionPane.showMessageDialog(null, Lang.warn_textfild_null + Lang.label_BookReg, Lang.warn_title, JOptionPane.WARNING_MESSAGE);
                txtBookReg.requestFocusInWindow();
        }else if(strISBN.equals("")) {			//Check ISBN
                JOptionPane.showMessageDialog(null, Lang.warn_textfild_null + Lang.label_ISBN, Lang.warn_title, JOptionPane.WARNING_MESSAGE);
                txtISBN.requestFocusInWindow();
        }else if(strBookCode1.equals("")) {			//BookCode1
                JOptionPane.showMessageDialog(null, Lang.warn_textfild_null + Lang.label_BookCode1, Lang.warn_title, JOptionPane.WARNING_MESSAGE);
                txtBookCode1.requestFocusInWindow();
        }else if(strBookTitle.equals("")) {			//Check BookTitle
                JOptionPane.showMessageDialog(null, Lang.warn_textfild_null + Lang.label_BookTitle, Lang.warn_title, JOptionPane.WARNING_MESSAGE);
                txtBookTitle.requestFocusInWindow();
        }else if(strBookTotal.equals("")) {			//Check BookTotal
                JOptionPane.showMessageDialog(null, Lang.warn_textfild_null + Lang.label_BookTotal, Lang.warn_title, JOptionPane.WARNING_MESSAGE);
                txtBookTotal.requestFocusInWindow();
        }else {
               //Update
                String sql = "UPDATE tblBookData SET " +
                                    "BookReg = '" + strBookReg + "'," +
                                    "ISBN = '" + strISBN + "'," +
                                    "BookCode1 = '" + strBookCode1 + "'," +
                                    "BookCode2 = '" + strBookCode2 + "'," +
                                    "BookName1 = '" + strBookName1 + "'," +
                                    "BookName2 = '" + strBookName2 + "'," +
                                    "BookType = '" + strBookType + "'," +
                                    "BookDateIncome = '" + strBookDateIncome + "'," +
                                    "BookAuthor1 = '" + strBookAuthor1 + "'," +
                                    "BookAuthor2 = '" + strBookAuthor2 + "'," +
                                    "BookAuthor3 = '" + strBookAuthor3 + "'," +
                                    "BookAuthor4 = '" + strBookAuthor4 + "'," +
                                    "BookPublisher = '" + strBookPublisher + "'," +
                                    "BookTranslateName = '" + strBookTranslateName + "'," +
                                    "BookPubAddress = '" + strBookPubAddress + "'," +
                                    "BookTitle = '" + strBookTitle + "'," +
                                    "BookTitle2 = '" + strBookTitle2 + "'," +
                                    "BookYear = '" + strBookYear + "'," +
                                    "BookNum = '" + strBookNum + "'," +
                                    "BookSource = '" + strBookSource + "'," +
                                    "BookMoney = '" + strBookMoney + "'," +
                                    "BookTotal = '" + strBookTotal + "'," +
                                    "BookRent = '" + strBookRent + "'," +
                                    "BookOver = '" + strBookOver + "'," +
                                    "BookNormal = '" + strBookNormal + "'," +
                                    "BookRentType = '" + strBookRentType + "'," +
                                    "BookComment = '" + strBookComment + "'," +
                                    "BookImage = '" + strBookImage + "'," +
                                    "BookStatus = '" + strBookStatus + "'"+
                                    " WHERE ID = '" + strID + "'";
                //System.out.print(sql);
                //Execute Query database
                executeSQLQuery(sql, "Inserted");
                //Clear
                clearTextField();
        }
       //
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
         int c = 0;
        c = JOptionPane.showConfirmDialog(null, "You sure delete this row in Table ?.");
        if(c==0) {
                // Delete Record
                String sql = "DELETE FROM tblBookData WHERE ID='" + txtID.getText() + "'";
                //
                executeSQLQuery(sql, "Deleted");
                clearTextField();
        }else {

        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnInitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInitActionPerformed
        clearTextField();
        showBooksInTable();
        btnInit.setVisible(false);
        btnAdd.setVisible(true);
    }//GEN-LAST:event_btnInitActionPerformed

    private void btnAddKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAddKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAddKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnBookImage;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnInit;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cmbBookComment;
    private javax.swing.JComboBox<String> cmbBookRentType;
    private javax.swing.JComboBox<String> cmbBookStatus;
    private javax.swing.JComboBox<String> cmbBookType;
    private com.toedter.calendar.JDateChooser dateIncome;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblID1;
    private javax.swing.JLabel lblID10;
    private javax.swing.JLabel lblID11;
    private javax.swing.JLabel lblID12;
    private javax.swing.JLabel lblID13;
    private javax.swing.JLabel lblID14;
    private javax.swing.JLabel lblID15;
    private javax.swing.JLabel lblID16;
    private javax.swing.JLabel lblID17;
    private javax.swing.JLabel lblID18;
    private javax.swing.JLabel lblID19;
    private javax.swing.JLabel lblID2;
    private javax.swing.JLabel lblID20;
    private javax.swing.JLabel lblID21;
    private javax.swing.JLabel lblID22;
    private javax.swing.JLabel lblID23;
    private javax.swing.JLabel lblID24;
    private javax.swing.JLabel lblID25;
    private javax.swing.JLabel lblID26;
    private javax.swing.JLabel lblID27;
    private javax.swing.JLabel lblID28;
    private javax.swing.JLabel lblID29;
    private javax.swing.JLabel lblID3;
    private javax.swing.JLabel lblID30;
    private javax.swing.JLabel lblID31;
    private javax.swing.JLabel lblID32;
    private javax.swing.JLabel lblID4;
    private javax.swing.JLabel lblID5;
    private javax.swing.JLabel lblID6;
    private javax.swing.JLabel lblID7;
    private javax.swing.JLabel lblID8;
    private javax.swing.JLabel lblID9;
    private javax.swing.JLabel lblTotalBooks;
    private javax.swing.JTable tblBooks;
    private javax.swing.JTextField txtBookAuthor1;
    private javax.swing.JTextField txtBookAuthor2;
    private javax.swing.JTextField txtBookAuthor3;
    private javax.swing.JTextField txtBookAuthor4;
    private javax.swing.JTextField txtBookCode1;
    private javax.swing.JTextField txtBookCode2;
    private javax.swing.JTextField txtBookMoney;
    private javax.swing.JTextField txtBookName1;
    private javax.swing.JTextField txtBookName2;
    private javax.swing.JTextField txtBookNormal;
    private javax.swing.JTextField txtBookNum;
    private javax.swing.JTextField txtBookOver;
    private javax.swing.JTextArea txtBookPubAddress;
    private javax.swing.JTextField txtBookPublisher;
    private javax.swing.JTextField txtBookReg;
    private javax.swing.JTextField txtBookRent;
    private javax.swing.JTextField txtBookSource;
    private javax.swing.JTextField txtBookTitle;
    private javax.swing.JTextField txtBookTitle2;
    private javax.swing.JTextField txtBookTotal;
    private javax.swing.JTextField txtBookTranslateName;
    private javax.swing.JTextField txtBookYear;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtISBN;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
