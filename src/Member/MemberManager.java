/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Member;

import Book.BookTypeManage;
import MainForm.Main;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import manage.DbConnect;
import manage.GlobalVar;
import manage.Lang;

/**
 *
 * @author khanchaiwongsit
 */
public class MemberManager extends javax.swing.JInternalFrame {
    
    /**
     * Creates new form MemberManager
     */
    private int strID ;
    private String strMemberID;
    private String strName ;
    private String strEmail ;
    private String strUserName ;
    private String strPassword ;
    private String strConfirmPassword ;
    private String strMemberType;
    private int intMemberTypeCode;
    private Boolean booAdmin;
    private String strNameDepart;
    private String strDateIn;
    private String strDateOut;
    private String strAddress;
    private String strPhone;
    private String strSex;
    private int intStatus;
    private String strBookTotal;
    private String strMemberPic;
    private String strMemberBarcode;
    private  int width ;
    private int height ;
    public MemberManager() {
        initComponents();
        addItemComboMemberType();
        
        dateIn.setDateFormatString("MM-dd-yyyy");
        dateOut.setDateFormatString("MM-dd-yyyy");
         setTitle(Lang.menu_member_manage);
        //set size
        int width = 1050;
        int height = 760;
        setBounds(GlobalVar.screenCenterX-(width/2),20,width,height);
        
        showMembersInTable();
        //Set Text form Language select
       // lblMemberID.setText(Lang.label_user_id);
        //lblName.setText(Lang.label_name);
    }
    
    //Class to ArrayList object
    public ArrayList<Members> getMembersList(){
        ArrayList<Members> membersList = new ArrayList<Members>();
        Connection con = DbConnect.getConnection();
        
        //Text Search
        String search = txtSearch.getText();
         // Sql Command              
        String sql = "SELECT * FROM tblMember  WHERE "+
                "MemberID LIKE '%"+ search +"%'"+
                "OR Name LIKE '%" + search +"%'" +
                "OR Email LIKE '%" + search +"%'" +
                "OR Username LIKE '%" + search +"%'" +
                "OR MemberType LIKE '%" + search +"%'" +
                "OR NameDepart LIKE '%" + search +"%'" +
                "OR Address LIKE '%" + search +"%'" +
                "OR Phone LIKE '%" + search +"%'" +
                "OR Status LIKE '%" + search +"%'" +
                "OR MemberBarcode LIKE '%" + search +"%'";
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
    
    //Show data to Table
    public void showMembersInTable(){
        ArrayList<Members> list = getMembersList();
        DefaultTableModel model = (DefaultTableModel)tblComp.getModel();
        Object[] row = new Object[19];
        for(int i=0;i<list.size();i++){
            row[0] = list.get(i).getID();
            row[1] = list.get(i).getMemberID();
            row[2] = list.get(i).getName();
            row[3] = list.get(i).getEmail();
            row[4] = list.get(i).getUsername();
            row[5] = list.get(i).getPassword();
            row[6] = list.get(i).getMemberType();
            row[7] = list.get(i).getMemberTypeCode();
            row[8] = list.get(i).getAdmin();
            row[9] = list.get(i).getNameDepart();
            row[10] = list.get(i).getDateIn();
            row[11] = list.get(i).getDateOut();
            row[12] = list.get(i).getAddress();
            row[13] = list.get(i).getPhone();
            row[14] = list.get(i).getSex();
            row[15] = list.get(i).getStatus();
            row[16] = list.get(i).getBookTotal();
            row[17] = list.get(i).getMemberPic();
            row[18] = list.get(i).getMemberBarcode();
            
            model.addRow(row);
        }
        txtPassword.disable();
        txtConfirmPassword.disable();
    }
    
    //execute Query 
    public void executeSQLQuery(String query, String message){
        Connection con = DbConnect.getConnection();
        Statement st;
        try{
            st = con.createStatement();
            if((st.executeUpdate(query)) == 1){
                 //refreash Table
                DefaultTableModel model = (DefaultTableModel)tblComp.getModel();
                 model.setRowCount(0);
                 showMembersInTable();
                 //clearTextField();
                 //Show message dialog
                JOptionPane.showMessageDialog(this, "Data " + message + " Sucessfuly");
                 //Clear
                clearTextField();
            }else{
                JOptionPane.showMessageDialog(this, "Data not " + message);
            }
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Data not " + message);
        }
    }
   
    //Class to ArrayList object for Combo BookType
    public ArrayList<MemberType> getMemberTypeList(){
        ArrayList<MemberType> membertypeList = new ArrayList<MemberType>();
        Connection con = DbConnect.getConnection();
        
         // Sql Command              
        String sql = "SELECT * FROM tblMemberType ";
        //Variable Statement and Resultset
        Statement st;
        ResultSet rs;
        //
        try{
            st = con.createStatement();     //Create Statement
            rs = st.executeQuery(sql);
            MemberType membertype;
            while(rs.next()){
                membertype = new MemberType(rs.getInt("ID"),rs.getInt("MemberCode"),rs.getString("MemberType"),rs.getInt("ValueRent"),rs.getInt("ValueOverReturn")
                        ,rs.getInt("ValueExtra"),rs.getInt("NumBook"));
                membertypeList.add(membertype);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return membertypeList;
    }
    
    //add item to Combo 
    public void addItemComboMemberType(){
        ArrayList<MemberType> list = getMemberTypeList();
        Object[] row = new Object[7];
        for(int i=0;i<list.size();i++){
           //add Item
           cmbMemberType.addItem(list.get(i).getMemberType());
        }
       
    }
    
    //Clear TextField
    public void clearTextField(){
        //Reset TextField
        txtMemberID.setText("");
        txtName.setText("");
        txtEmail.setText("");
        txtUserName.setText("");
        txtPassword.setText("");
        txtConfirmPassword.setText("");
        cmbMemberType.getModel().setSelectedItem("None");
        chkAdmin.setSelected(false);
        txtNameDepart.setText("");
        txtAddress.setText("");
        txtPhone.setText("");
        cmbSex.getModel().setSelectedItem("None");
        cmbStatus.getModel().setSelectedItem("None");
        txtBorrow.setText("");
        txtBarcode.setText("");
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnClose = new javax.swing.JButton();
        lblUsername = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        lblMemberType = new javax.swing.JLabel();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        lblMemberID = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        txtMemberID = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtUserName = new javax.swing.JTextField();
        cmbMemberType = new javax.swing.JComboBox<>();
        txtPassword = new javax.swing.JPasswordField();
        txtConfirmPassword = new javax.swing.JPasswordField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblComp = new javax.swing.JTable();
        lblPic = new javax.swing.JLabel();
        lblMemberID1 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        lblMemberID2 = new javax.swing.JLabel();
        txtPhone = new javax.swing.JTextField();
        chkAdmin = new javax.swing.JCheckBox();
        dateIn = new com.toedter.calendar.JDateChooser();
        lblMemberID3 = new javax.swing.JLabel();
        lblMemberID4 = new javax.swing.JLabel();
        dateOut = new com.toedter.calendar.JDateChooser();
        lblMemberID5 = new javax.swing.JLabel();
        lblMemberID6 = new javax.swing.JLabel();
        txtNameDepart = new javax.swing.JTextField();
        lblMemberID7 = new javax.swing.JLabel();
        cmbSex = new javax.swing.JComboBox<>();
        lblMemberID8 = new javax.swing.JLabel();
        cmbStatus = new javax.swing.JComboBox<>();
        lblMemberID9 = new javax.swing.JLabel();
        txtBorrow = new javax.swing.JTextField();
        btnPic = new javax.swing.JButton();
        txtBarcode = new javax.swing.JTextField();
        lblMemberID10 = new javax.swing.JLabel();
        txtAddress = new javax.swing.JTextField();

        getContentPane().setLayout(null);

        btnClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons/x-button32.png"))); // NOI18N
        btnClose.setText("Close");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });
        getContentPane().add(btnClose);
        btnClose.setBounds(750, 470, 139, 56);

        lblUsername.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        lblUsername.setText("* ชื่อผู้ใช้งาน");
        getContentPane().add(lblUsername);
        lblUsername.setBounds(201, 169, 64, 25);

        lblName.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        lblName.setText("* ชื่อ-นามสกุล");
        getContentPane().add(lblName);
        lblName.setBounds(208, 54, 72, 25);

        lblEmail.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        lblEmail.setText("* E-mail");
        getContentPane().add(lblEmail);
        lblEmail.setBounds(235, 92, 45, 25);

        lblMemberType.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        lblMemberType.setText("* ประเภทสมาชิก");
        getContentPane().add(lblMemberType);
        lblMemberType.setBounds(187, 131, 85, 25);

        btnUpdate.setBackground(new java.awt.Color(204, 255, 255));
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons/exchange32.png"))); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.setFocusable(false);
        btnUpdate.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnUpdate.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        getContentPane().add(btnUpdate);
        btnUpdate.setBounds(754, 127, 139, 83);

        btnDelete.setBackground(new java.awt.Color(255, 204, 204));
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons/user.png"))); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.setFocusable(false);
        btnDelete.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDelete.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        getContentPane().add(btnDelete);
        btnDelete.setBounds(754, 217, 139, 91);

        btnNew.setBackground(new java.awt.Color(204, 255, 204));
        btnNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons/add-user.png"))); // NOI18N
        btnNew.setText("Add Member");
        btnNew.setFocusable(false);
        btnNew.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNew.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });
        getContentPane().add(btnNew);
        btnNew.setBounds(753, 27, 139, 91);

        lblMemberID.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        lblMemberID.setText("* รหัสสมาชิก");
        getContentPane().add(lblMemberID);
        lblMemberID.setBounds(383, 16, 66, 25);

        jLabel6.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        jLabel6.setText("รหัสผ่าน");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(222, 207, 43, 25);

        jLabel7.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        jLabel7.setText("ยืนยันรหัสผ่าน");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(190, 251, 75, 25);

        jLabel8.setFont(new java.awt.Font("TH Sarabun New", 0, 18)); // NOI18N
        jLabel8.setText("Search");
        getContentPane().add(jLabel8);
        jLabel8.setBounds(264, 520, 36, 25);

        txtSearch.setFont(new java.awt.Font("TH Sarabun New", 0, 18)); // NOI18N
        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });
        getContentPane().add(txtSearch);
        txtSearch.setBounds(25, 517, 227, 31);

        txtMemberID.setBackground(new java.awt.Color(204, 255, 255));
        txtMemberID.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        getContentPane().add(txtMemberID);
        txtMemberID.setBounds(454, 13, 160, 31);

        txtName.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        getContentPane().add(txtName);
        txtName.setBounds(285, 51, 173, 31);

        txtEmail.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        getContentPane().add(txtEmail);
        txtEmail.setBounds(285, 89, 331, 31);

        txtUserName.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        getContentPane().add(txtUserName);
        txtUserName.setBounds(277, 166, 327, 31);

        cmbMemberType.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        cmbMemberType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbMemberTypeItemStateChanged(evt);
            }
        });
        cmbMemberType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbMemberTypeActionPerformed(evt);
            }
        });
        getContentPane().add(cmbMemberType);
        cmbMemberType.setBounds(283, 128, 167, 31);

        txtPassword.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        txtPassword.setText("***************");
        txtPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtPasswordMouseClicked(evt);
            }
        });
        txtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordActionPerformed(evt);
            }
        });
        getContentPane().add(txtPassword);
        txtPassword.setBounds(277, 204, 327, 31);

        txtConfirmPassword.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        txtConfirmPassword.setText("jPasswordField2");
        txtConfirmPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtConfirmPasswordMouseClicked(evt);
            }
        });
        getContentPane().add(txtConfirmPassword);
        txtConfirmPassword.setBounds(277, 248, 327, 31);

        tblComp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "MemberID", "Name", "Email", "UserName", "Password", "MemberType", "TypeCode", "Admin", "Depart", "In", "Out", "Address", "Phone", "Sex", "Status", "Total", "Pic", "Barcode"
            }
        ));
        tblComp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCompMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblComp);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(25, 560, 887, 180);

        lblPic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/boy128.png"))); // NOI18N
        getContentPane().add(lblPic);
        lblPic.setBounds(38, 24, 128, 128);

        lblMemberID1.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        lblMemberID1.setText("ID");
        getContentPane().add(lblMemberID1);
        lblMemberID1.setBounds(269, 16, 11, 25);

        txtID.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        txtID.setEnabled(false);
        getContentPane().add(txtID);
        txtID.setBounds(285, 13, 80, 31);

        lblMemberID2.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        lblMemberID2.setText("แผนก");
        getContentPane().add(lblMemberID2);
        lblMemberID2.setBounds(234, 295, 31, 25);

        txtPhone.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        getContentPane().add(txtPhone);
        txtPhone.setBounds(277, 422, 160, 31);

        chkAdmin.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        chkAdmin.setText("เจ้าหน้าที่");
        chkAdmin.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkAdminStateChanged(evt);
            }
        });
        getContentPane().add(chkAdmin);
        chkAdmin.setBounds(503, 127, 73, 33);
        getContentPane().add(dateIn);
        dateIn.setBounds(277, 330, 146, 20);

        lblMemberID3.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        lblMemberID3.setText("วันหมดอายุ");
        getContentPane().add(lblMemberID3);
        lblMemberID3.setBounds(428, 330, 59, 25);

        lblMemberID4.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        lblMemberID4.setText("วันที่สมัคร");
        getContentPane().add(lblMemberID4);
        lblMemberID4.setBounds(212, 330, 53, 25);
        getContentPane().add(dateOut);
        dateOut.setBounds(492, 330, 138, 20);

        lblMemberID5.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        lblMemberID5.setText("โทรศัพท์");
        getContentPane().add(lblMemberID5);
        lblMemberID5.setBounds(215, 425, 44, 25);

        lblMemberID6.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        lblMemberID6.setText("ที่อยู่");
        getContentPane().add(lblMemberID6);
        lblMemberID6.setBounds(234, 372, 38, 25);

        txtNameDepart.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        getContentPane().add(txtNameDepart);
        txtNameDepart.setBounds(277, 292, 160, 31);

        lblMemberID7.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        lblMemberID7.setText("เพศ");
        getContentPane().add(lblMemberID7);
        lblMemberID7.setBounds(470, 54, 21, 25);

        cmbSex.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        cmbSex.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "ชาย", "หญิง" }));
        cmbSex.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbSexItemStateChanged(evt);
            }
        });
        cmbSex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSexActionPerformed(evt);
            }
        });
        getContentPane().add(cmbSex);
        cmbSex.setBounds(503, 51, 113, 31);

        lblMemberID8.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        lblMemberID8.setText("สถานะ");
        getContentPane().add(lblMemberID8);
        lblMemberID8.setBounds(449, 425, 35, 25);

        cmbStatus.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "หมดอายุ/ยกเลิก", "ปกติ" }));
        cmbStatus.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbStatusItemStateChanged(evt);
            }
        });
        cmbStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbStatusActionPerformed(evt);
            }
        });
        getContentPane().add(cmbStatus);
        cmbStatus.setBounds(496, 422, 116, 31);

        lblMemberID9.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        lblMemberID9.setText("จำนวนหนังสือที่ยืมไปแล้ว");
        getContentPane().add(lblMemberID9);
        lblMemberID9.setBounds(132, 456, 133, 25);

        txtBorrow.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        getContentPane().add(txtBorrow);
        txtBorrow.setBounds(277, 453, 85, 31);

        btnPic.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        btnPic.setText("รูปภาพ");
        getContentPane().add(btnPic);
        btnPic.setBounds(56, 189, 69, 33);

        txtBarcode.setBackground(new java.awt.Color(255, 204, 153));
        txtBarcode.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        getContentPane().add(txtBarcode);
        txtBarcode.setBounds(15, 234, 170, 31);

        lblMemberID10.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        lblMemberID10.setText("รหัสบาร์โค้ดสมาชิก");
        getContentPane().add(lblMemberID10);
        lblMemberID10.setBounds(47, 272, 96, 25);

        txtAddress.setFont(new java.awt.Font("TH Sarabun New", 1, 18)); // NOI18N
        getContentPane().add(txtAddress);
        txtAddress.setBounds(277, 369, 353, 31);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
         //Close InternalForm
        dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    //Insert Data Record
    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        //input value to variable
            strMemberID = txtMemberID.getText();
            strName = txtName.getText();
            strEmail = txtEmail.getText();
            strUserName = txtUserName.getText();
            strPassword = new String( txtPassword.getPassword());
            strConfirmPassword = new String( txtConfirmPassword.getPassword());
            strMemberType = cmbMemberType.getModel().getSelectedItem().toString();
             intMemberTypeCode = cmbMemberType.getSelectedIndex();
            booAdmin = chkAdmin.isSelected();
            strNameDepart = txtNameDepart.getText();
           //Date In
            Calendar cal = dateIn.getCalendar();
            int datevar = cal.get(Calendar.DATE);
            int monthvar = cal.get(Calendar.MONTH);
            int yearvar = cal.get(Calendar.YEAR);
            strDateIn = (monthvar+1) + "/"+ datevar + "/ " + yearvar;
            //Date Out
           cal = dateOut.getCalendar();
            datevar = cal.get(Calendar.DATE);
            monthvar = cal.get(Calendar.MONTH);
            yearvar = cal.get(Calendar.YEAR);
            strDateOut = (monthvar+1) + "/"+ datevar + "/ " + yearvar;
            //
            strAddress = txtAddress.getText();
            strPhone = txtPhone.getText();
            strSex = cmbSex.getModel().getSelectedItem().toString();
            intStatus = cmbStatus.getSelectedIndex();
            strBookTotal = txtBorrow.getText();
            strMemberPic = lblPic.getText();
            strMemberBarcode = txtBarcode.getText();
    
        //variable for Encryption
            EncryptMD5 md5 = new EncryptMD5();
            String passwordToHash = md5.EncryptMD5(strPassword);
         //Check input value
            if(strMemberID.equals("")) {			//Check User ID
                    JOptionPane.showMessageDialog(this, Lang.warn_textfild_null + "รหัสสมาชิก", Lang.warn_title, JOptionPane.WARNING_MESSAGE);
                    txtMemberID.requestFocusInWindow();
                    //return false;
            }else if(strName.equals("")) {			//Check Name
                    JOptionPane.showMessageDialog(this, Lang.warn_textfild_null + "ชื่อ-นามสกุล", Lang.warn_title, JOptionPane.WARNING_MESSAGE);
                    txtName.requestFocusInWindow();
                    //return false;
            }else if(strEmail.equals("")) {			//Check Email
                    JOptionPane.showMessageDialog(this, Lang.warn_textfild_null + "Email", Lang.warn_title, JOptionPane.WARNING_MESSAGE);
                    txtEmail.requestFocusInWindow();
                    //return false;
            }else if(strUserName.equals("")) {			//Check UserName
                    JOptionPane.showMessageDialog(this, Lang.warn_textfild_null + "ชื่อผู้ใช้", Lang.warn_title, JOptionPane.WARNING_MESSAGE);
                    txtUserName.requestFocusInWindow();
                    //return false;
            }else if(strMemberType.equals("None")) {			//Check UserName
                    JOptionPane.showMessageDialog(this, Lang.warn_textfild_null, Lang.warn_title, JOptionPane.WARNING_MESSAGE);
                    cmbMemberType.requestFocusInWindow();
                    //return false;
            }else if(booAdmin.equals(true)){
                    if(strConfirmPassword.equals("")) {			//Check UserName
                          JOptionPane.showMessageDialog(this, Lang.warn_textfild_null + "ยืนยันรหัสผ่าน", Lang.warn_title, JOptionPane.WARNING_MESSAGE);
                          txtConfirmPassword.requestFocusInWindow();
                      }else if(!strPassword.equals(strConfirmPassword)) {			//Check UserName
                              JOptionPane.showMessageDialog(this, Lang.warn_wrong_password, Lang.warn_title, JOptionPane.WARNING_MESSAGE);
                              txtPassword.requestFocusInWindow();
                  }
            }else if(strDateIn.equals("")) {			//Check UserName
                    JOptionPane.showMessageDialog(this, Lang.warn_textfild_null + "วันที่สมัคร", Lang.warn_title, JOptionPane.WARNING_MESSAGE);
                    dateIn.requestFocusInWindow();
                    //return false;
             }else if(strDateOut.equals("")) {			//Check UserName
                    JOptionPane.showMessageDialog(this, Lang.warn_textfild_null + "วันหมดอายุ", Lang.warn_title, JOptionPane.WARNING_MESSAGE);
                    dateOut.requestFocusInWindow();
                    //return false;
            }else if(strPhone.equals("")) {			//Check UserName
                    JOptionPane.showMessageDialog(this, Lang.warn_textfild_null + "เบอร์โทรศัพท์", Lang.warn_title, JOptionPane.WARNING_MESSAGE);
                    txtPhone.requestFocusInWindow();
                    //return false;
            }else {
                //Insert Record
                String sql = "INSERT INTO tblMember (ID,MemberID,Name, Email, Username,Password,MemberType,MemberTypeCode,Admin"+
                        ",NameDepart,DateIn,DateOut,Address,Phone,Sex,Status,BookTotal,MemberPic,MemberBarcode) VALUES (null,'"+
                strMemberID + "','" +
                strName+ "','" +
                strEmail + "','" +
                strUserName + "','" +
                passwordToHash + "','" +
                strMemberType + "','" +
                intMemberTypeCode + "','" +  
                booAdmin + "','" +
                strNameDepart + "','" +
                strDateIn + "','" +       
                strDateOut + "','" +
                strAddress + "','" +
                strPhone + "','" +
                strSex + "','" +       
                intStatus + "','" +  
                strBookTotal + "','" +  
                strMemberPic + "','" +  
                strMemberBarcode + "')";
                
                //System.out.print(sql);
                //Execute Query database
                executeSQLQuery(sql, "Inserted");
            }
    }//GEN-LAST:event_btnNewActionPerformed

    //Update Data Record
    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
            //input value to variable
            strMemberID = txtMemberID.getText();
            strName = txtName.getText();
            strEmail = txtEmail.getText();
            strUserName = txtUserName.getText();
            strPassword = new String( txtPassword.getPassword());
            strConfirmPassword = new String( txtConfirmPassword.getPassword());
            strMemberType = cmbMemberType.getModel().getSelectedItem().toString();
             intMemberTypeCode = cmbMemberType.getSelectedIndex();
            booAdmin = chkAdmin.isSelected();
            strNameDepart = txtNameDepart.getText();
           //Date In
            Calendar cal = dateIn.getCalendar();
            int datevar = cal.get(Calendar.DATE);
            int monthvar = cal.get(Calendar.MONTH);
            int yearvar = cal.get(Calendar.YEAR);
            strDateIn =  yearvar + "- "+ (monthvar+1) + "-"+ datevar ;
            
            //Date Out
           cal = dateOut.getCalendar();
            datevar = cal.get(Calendar.DATE);
            monthvar = cal.get(Calendar.MONTH);
            yearvar = cal.get(Calendar.YEAR);
             strDateOut =  yearvar + "- "+ (monthvar+1) + "-"+ datevar ;
            //
            strAddress = txtAddress.getText();
            strPhone = txtPhone.getText();
            strSex = cmbSex.getModel().getSelectedItem().toString();
            intStatus = cmbStatus.getSelectedIndex();
            strBookTotal = txtBorrow.getText();
            strMemberPic = lblPic.getText();
            strMemberBarcode = txtBarcode.getText();
    
        //variable for Encryption
            EncryptMD5 md5 = new EncryptMD5();
            String passwordToHash = md5.EncryptMD5(strPassword);
         //Check input value
            if(strMemberID.equals("")) {			//Check User ID
                    JOptionPane.showMessageDialog(this, Lang.warn_textfild_null + "รหัสสมาชิก", Lang.warn_title, JOptionPane.WARNING_MESSAGE);
                    txtMemberID.requestFocusInWindow();
                    //return false;
            }else if(strName.equals("")) {			//Check Name
                    JOptionPane.showMessageDialog(this, Lang.warn_textfild_null + "ชื่อ-นามสกุล", Lang.warn_title, JOptionPane.WARNING_MESSAGE);
                    txtName.requestFocusInWindow();
                    //return false;
            }else if(strEmail.equals("")) {			//Check Email
                    JOptionPane.showMessageDialog(this, Lang.warn_textfild_null + "Email", Lang.warn_title, JOptionPane.WARNING_MESSAGE);
                    txtEmail.requestFocusInWindow();
                    //return false;
            }else if(strUserName.equals("")) {			//Check UserName
                    JOptionPane.showMessageDialog(this, Lang.warn_textfild_null + "ชื่อผู้ใช้", Lang.warn_title, JOptionPane.WARNING_MESSAGE);
                    txtUserName.requestFocusInWindow();
                    //return false;
            }else if(strMemberType.equals("None")) {			//Check UserName
                    JOptionPane.showMessageDialog(this, Lang.warn_textfild_null, Lang.warn_title, JOptionPane.WARNING_MESSAGE);
                    cmbMemberType.requestFocusInWindow();
                    //return false;
            }else if(booAdmin){
                    if(strConfirmPassword.equals("")) {			//Check UserName
                          JOptionPane.showMessageDialog(this, Lang.warn_textfild_null + "ยืนยันรหัสผ่าน", Lang.warn_title, JOptionPane.WARNING_MESSAGE);
                          txtConfirmPassword.requestFocusInWindow();
                      }else if(!strPassword.equals(strConfirmPassword)) {			//Check UserName
                              JOptionPane.showMessageDialog(this, Lang.warn_wrong_password, Lang.warn_title, JOptionPane.WARNING_MESSAGE);
                              txtPassword.requestFocusInWindow();
                  }
            }else if(strDateIn.equals("")) {			//Check UserName
                    JOptionPane.showMessageDialog(this, Lang.warn_textfild_null + "วันที่สมัคร", Lang.warn_title, JOptionPane.WARNING_MESSAGE);
                    dateIn.requestFocusInWindow();
                    //return false;
             }else if(strDateOut.equals("")) {			//Check UserName
                    JOptionPane.showMessageDialog(this, Lang.warn_textfild_null + "วันหมดอายุ", Lang.warn_title, JOptionPane.WARNING_MESSAGE);
                    dateOut.requestFocusInWindow();
                    //return false;
            }else if(strPhone.equals("")) {			//Check UserName
                    JOptionPane.showMessageDialog(this, Lang.warn_textfild_null + "เบอร์โทรศัพท์", Lang.warn_title, JOptionPane.WARNING_MESSAGE);
                    txtPhone.requestFocusInWindow();
                    //return false;
            }else {
                 //Update
                String sql = "UPDATE `tblMember` SET " +
                                "`MemberID`='" + strMemberID +"',"+
                                "`Name`='" + strName +"',"+
                               "`Email`='" + strEmail +"',"+
                               "`Username`='" + strUserName +"',"+
                                "`Password`='" + passwordToHash +"',"+
                                "`MemberType`='" + strMemberType +"',"+
                                "`MemberTypeCode`='" + intMemberTypeCode +"',"+
                                "`Admin`='" + booAdmin +"',"+
                                "`NameDepart`='" + strNameDepart +"',"+
                                "`DateIn`='" + strDateIn +"',"+
                                "`DateOut`='" + strDateOut +"',"+
                                "`Address`='" + strAddress +"',"+
                                "`Phone`='" + strPhone+ "',"+
                                "`Sex`='" + strSex +"',"+
                                "`Status`='" + intStatus +"',"+
                                "`BookTotal`='" + strBookTotal +"',"+
                                "`MemberPic`='" + strMemberPic+"',"+
                                "`MemberBarcode`='" + strMemberBarcode+"'"+
                               " WHERE `ID`= '" + strID + "'";
                 //System.out.print(sql);
                //Execute Query database
                executeSQLQuery(sql, "Updated");
            }
    }//GEN-LAST:event_btnUpdateActionPerformed

    //Select Record form Table
    private void tblCompMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCompMouseClicked
        int row = tblComp.getSelectedRow();		//get select row
        strID = (int) tblComp.getValueAt(row, 0);
        //get value to TextField
        String MemberID = tblComp.getValueAt(row, 1).toString();
        txtMemberID.setText(MemberID);
        //
        String Name = tblComp.getValueAt(row, 2).toString();
        txtName.setText(Name);
        //
        String Email = tblComp.getValueAt(row, 3).toString();
        txtEmail.setText(Email);
        //
        String Username = tblComp.getValueAt(row, 4).toString();
        txtUserName.setText(Username);
        //
        String Password = tblComp.getValueAt(row, 5).toString();
        txtPassword.setText(Password);
        //
        String ConfirmPassword = tblComp.getValueAt(row, 5).toString();
        txtConfirmPassword.setText(ConfirmPassword);
        //Combo
        String MemberTypeIndex = tblComp.getValueAt(row, 6).toString();
        cmbMemberType.getModel().setSelectedItem(MemberTypeIndex);
        //Combo
        int MemberTypeCode = (int)  tblComp.getValueAt(row, 7);
       cmbMemberType.setSelectedIndex(MemberTypeCode);
        //Checkbox
        boolean CheckAdmin = (boolean) tblComp.getValueAt(row, 8);
        chkAdmin.setSelected(CheckAdmin);
        if(CheckAdmin){
            txtPassword.enable();
            txtConfirmPassword.enable();
        }else{
             txtPassword.disable();
            txtConfirmPassword.disable();
        }
       
        //Name Depart
        String Depart= tblComp.getValueAt(row, 9).toString();
        txtNameDepart.setText(Depart);
        
         //Date In
        String DateIn = tblComp.getValueAt(row, 10).toString();
            try{
            Date df1 = new SimpleDateFormat("yyyy-MM-dd").parse(DateIn);
            dateIn.setDate(df1);
            }catch(Exception e){
                e.printStackTrace();
            }
        
        //Date Out
        String DateOut =  tblComp.getValueAt(row, 11).toString();
         try{
            Date df2 = new SimpleDateFormat("yyyy-MM-dd").parse(DateOut);
            dateOut.setDate(df2);
            }catch(Exception e){
                e.printStackTrace();
            }
        
        //Address
        String Address = tblComp.getValueAt(row, 12).toString();
        txtAddress.setText(Address);
        
        //Phone
        String Phone = tblComp.getValueAt(row, 13).toString();
        txtPhone.setText(Phone);
        
        //Sex
        String Sex = tblComp.getValueAt(row, 14).toString();
        cmbSex.getModel().setSelectedItem(Sex);
        
        //Status
        int Status = (int) tblComp.getValueAt(row, 15);
        cmbStatus.setSelectedIndex(Status);
        
        //BookTotal
        String BookTotal = tblComp.getValueAt(row, 16).toString();
        txtBorrow.setText(BookTotal);
        
        //MemberPic
        String MwmberPic= tblComp.getValueAt(row, 17).toString();
        
        //Member Barcode
        String Barcode= tblComp.getValueAt(row, 18).toString();
        txtBarcode.setText(Barcode);
     
    }//GEN-LAST:event_tblCompMouseClicked

    //Change Item JCombobox
    private void cmbMemberTypeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbMemberTypeItemStateChanged
        
    }//GEN-LAST:event_cmbMemberTypeItemStateChanged

    //Delete Record
    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int c = 0;
        c = JOptionPane.showConfirmDialog(null, "You sure delete this row in Table ?.");
        if(c==0) {
                // Delete Record
                String sql = "DELETE FROM tblMember WHERE MemberID='" + txtMemberID.getText() + "'";
                //
                executeSQLQuery(sql, "Deleted");
        }else {

        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void cmbMemberTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbMemberTypeActionPerformed
        
    }//GEN-LAST:event_cmbMemberTypeActionPerformed

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
         //Clear Password
        txtPassword.setText("");
        
    }//GEN-LAST:event_txtPasswordActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // Search
       
    }//GEN-LAST:event_txtSearchActionPerformed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        // Search data 
        DefaultTableModel model = (DefaultTableModel)tblComp.getModel();
        model.setRowCount(0);
         showMembersInTable();
    }//GEN-LAST:event_txtSearchKeyReleased

    private void txtPasswordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPasswordMouseClicked
       //Clear Password
        txtPassword.setText("");
    }//GEN-LAST:event_txtPasswordMouseClicked

    private void txtConfirmPasswordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtConfirmPasswordMouseClicked
        ///Clear Password
        txtConfirmPassword.setText("");
    }//GEN-LAST:event_txtConfirmPasswordMouseClicked

    private void cmbSexItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbSexItemStateChanged
        
    }//GEN-LAST:event_cmbSexItemStateChanged

    private void cmbSexActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSexActionPerformed
        
        
    }//GEN-LAST:event_cmbSexActionPerformed

    private void cmbStatusItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbStatusItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbStatusItemStateChanged

    private void cmbStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbStatusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbStatusActionPerformed

    private void chkAdminStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkAdminStateChanged
        booAdmin = chkAdmin.isSelected();
        if(booAdmin){
            txtPassword.enable();
            txtConfirmPassword.enable();
            txtPassword.setText("");
            txtConfirmPassword.setText("");
        }else{
             txtPassword.disable();
            txtConfirmPassword.disable();
            txtPassword.setText("");
            txtConfirmPassword.setText("");
        }
    }//GEN-LAST:event_chkAdminStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnPic;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JCheckBox chkAdmin;
    private javax.swing.JComboBox<String> cmbMemberType;
    private javax.swing.JComboBox<String> cmbSex;
    private javax.swing.JComboBox<String> cmbStatus;
    private com.toedter.calendar.JDateChooser dateIn;
    private com.toedter.calendar.JDateChooser dateOut;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblMemberID;
    private javax.swing.JLabel lblMemberID1;
    private javax.swing.JLabel lblMemberID10;
    private javax.swing.JLabel lblMemberID2;
    private javax.swing.JLabel lblMemberID3;
    private javax.swing.JLabel lblMemberID4;
    private javax.swing.JLabel lblMemberID5;
    private javax.swing.JLabel lblMemberID6;
    private javax.swing.JLabel lblMemberID7;
    private javax.swing.JLabel lblMemberID8;
    private javax.swing.JLabel lblMemberID9;
    private javax.swing.JLabel lblMemberType;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblPic;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JTable tblComp;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtBarcode;
    private javax.swing.JTextField txtBorrow;
    private javax.swing.JPasswordField txtConfirmPassword;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtMemberID;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtNameDepart;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtUserName;
    // End of variables declaration//GEN-END:variables
}
