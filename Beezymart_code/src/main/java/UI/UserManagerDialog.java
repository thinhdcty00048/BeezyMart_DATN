/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package UI;

import Entity.User;
import Util.XDate;
import Util.XDialog;
import Util.XStr;
import com.company.dao.UserDAO;
import com.company.dao.UserDAOImpl;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Hii
 */
public class UserManagerDialog extends javax.swing.JDialog {

    /**
     * Creates new form UserManagerDialog
     */
    public UserManagerDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        fillToTable();
    }

    List<User> items;
    UserDAO dao = new UserDAOImpl();

    public void fillToTable() {
        DefaultTableModel model = (DefaultTableModel) tblUser.getModel();
        model.setRowCount(0);

        items = dao.findAll();
        items.forEach(item -> {
            Object[] rowData = {
                item.getID(),
                item.getFullname(),
                item.isIsManager() ? "Quản lí" : "Nhân viên",
                item.getEmail(),
                item.getPhoneNumber(),
                XDate.format(item.getCreateAt(), "dd/MM/yyyy"),
                XDate.format(item.getDateOfBirth(), "dd/MM/yyyy"),
                item.isEnable() ? "Khả dụng" : "Bị chặn"
            };
            model.addRow(rowData);
        });
    }

    private User getForm() {
        String fullname = tfFullName.getText().trim();
        String password = tfPassword.getText().trim();
        String email = txtEmail.getText().trim();
        String phone = txtPhone.getText().trim();
        String dob = txtDateOfBirth.getText().trim();
        String passConfirm = tfConfirmPasword.getText();
        boolean enable = cboTrangThai.getSelectedIndex() == 0 ? true : false;
        boolean isManager = cbchucvu.getSelectedIndex() == 0 ? false : true;

        if (fullname.isEmpty()) {
            XDialog.alert("Vui lòng điền Họ và Tên!");
            return null;
        }

        if (password.isEmpty()) {
            XDialog.alert("Vui lòng điền Mật khẩu!");
            return null;
        }

        if (!password.equals(passConfirm)) {
            XDialog.alert("Xác nhận mật khẩu sai");
            return null;
        }

        if (email.isEmpty()) {
            XDialog.alert("Vui lòng điền Email!");
            return null;
        }

        if (phone.isEmpty()) {
            XDialog.alert("Vui lòng điền Số điện thoại!");
            return null;
        }

        if (dob.isEmpty()) {
            XDialog.alert("Vui lòng điền Ngày sinh!");
            return null;
        }

        String mahoa = XStr.encodeB64(password);
        User user = new User();
        user.setFullname(fullname);
        user.setIsManager(isManager);
        user.setCreateAt(new Date());
        user.setEmail(email);
        user.setPhoneNumber(phone);
        user.setEnable(enable);
        user.setDateOfBirth(XDate.parse(dob, "dd/MM/yyyy"));
        user.setProfilePicture("");
        user.setPassword(mahoa);

        return user;
    }

    public void create() {
        User user = getForm();
        if (user != null) {
            dao.create(user);
            XDialog.alert("Đã tạo tài khoảng thành công");
            jTabbedPane1.setSelectedIndex(0);
            fillToTable();
        }

    }

    private void setForm(User user) {
        tfFullName.setText(user.getFullname());
        txtDateOfBirth.setText(XDate.format(user.getDateOfBirth(), "dd/MM/yyyy"));
        txtEmail.setText(user.getEmail());
        txtPhone.setText(user.getPhoneNumber());
        cbchucvu.setSelectedIndex(user.isIsManager() ? 0 : 1);
        cboTrangThai.setSelectedIndex(user.isEnable() ? 0 : 1);
        txtID.setText(user.getID() + "");
        String giaima = XStr.decodeB64(user.getPassword());
        tfPassword.setText(giaima);
        tfConfirmPasword.setText(giaima);
    }

    private void disableButton() {
        btnCreate.setEnabled(false);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);

    }

    private void update() {

        User user = getForm();

        if (user != null) {
            user.setID(Integer.parseInt(txtID.getText()));
            dao.update(user);
            XDialog.alert("Cập nhật nhân viên thành công");
            jTabbedPane1.setSelectedIndex(0);
            fillToTable();
        }

    }

    private void reset() {
        txtDateOfBirth.setText("");
        txtEmail.setText("");
        txtID.setText("");
        txtPhone.setText("");
        tfConfirmPasword.setText("");
        tfPassword.setText("");
        tfFullName.setText("");
        btnCreate.setEnabled(true);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
    }

    private void delete() {
        User user = getForm();
        if (user != null) {
            user.setID(Integer.parseInt(txtID.getText()));
            dao.deleteById(user.getID());
            XDialog.alert("Xóa thành công nhân viên " + user.getFullname());
            jTabbedPane1.setSelectedIndex(0);
            fillToTable();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUser = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        tfFullName = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        tfPassword = new javax.swing.JPasswordField();
        txtDateOfBirth = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtPhone = new javax.swing.JTextField();
        tfConfirmPasword = new javax.swing.JPasswordField();
        jLabel6 = new javax.swing.JLabel();
        cbchucvu = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        cboTrangThai = new javax.swing.JComboBox<>();
        btnCreate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblUser1 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        tfFullName1 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtEmail1 = new javax.swing.JTextField();
        tfPassword1 = new javax.swing.JPasswordField();
        txtDateOfBirth1 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtPhone1 = new javax.swing.JTextField();
        tfConfirmPasword1 = new javax.swing.JPasswordField();
        jLabel15 = new javax.swing.JLabel();
        cbchucvu1 = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        cboTrangThai1 = new javax.swing.JComboBox<>();
        btnCreate1 = new javax.swing.JButton();
        btnDelete1 = new javax.swing.JButton();
        btnUpdate1 = new javax.swing.JButton();
        btnRefresh1 = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        txtID1 = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();

        jTabbedPane1.setBackground(new java.awt.Color(0, 204, 204));

        tblUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Họ và tên", "Chức vụ", "Email", "Số điện thoại", "Ngày tạo", "Ngày sinh", "Trạng thái"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUserMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblUser);

        jTabbedPane1.addTab("Danh sách nhân viên", jScrollPane1);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tfFullName.setActionCommand("<Not Set>");
        tfFullName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfFullNameActionPerformed(evt);
            }
        });
        jPanel1.add(tfFullName, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 116, 334, -1));

        jLabel9.setText("Ngày sinh");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 212, -1, -1));

        jLabel4.setText("Nhập Mật Khẩu");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(368, 94, -1, -1));
        jPanel1.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 184, 334, -1));

        tfPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfPasswordActionPerformed(evt);
            }
        });
        jPanel1.add(tfPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(368, 116, 334, -1));
        jPanel1.add(txtDateOfBirth, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 246, 334, -1));

        jLabel5.setText("Nhập Lại Mật Khẩu");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(368, 144, -1, -1));
        jPanel1.add(txtPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 305, 334, -1));
        jPanel1.add(tfConfirmPasword, new org.netbeans.lib.awtextra.AbsoluteConstraints(368, 166, 334, -1));

        jLabel6.setText("Chức Vụ");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(368, 224, -1, -1));

        cbchucvu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nhân Viên", "Quản Lý" }));
        jPanel1.add(cbchucvu, new org.netbeans.lib.awtextra.AbsoluteConstraints(368, 246, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setText("Tạo Tài Khoản");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(272, 15, -1, -1));

        jLabel7.setText("Email");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 144, -1, -1));

        jLabel3.setText("Họ Và Tên");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 94, -1, -1));

        jLabel8.setText("Số Điện Thoại");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 280, -1, -1));

        jLabel10.setText("Trạng thái");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(368, 280, -1, -1));

        cboTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Khả dụng", "Bị chặn" }));
        jPanel1.add(cboTrangThai, new org.netbeans.lib.awtextra.AbsoluteConstraints(368, 305, 88, -1));

        btnCreate.setBackground(new java.awt.Color(255, 153, 51));
        btnCreate.setText("Tạo mới");
        btnCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateActionPerformed(evt);
            }
        });
        jPanel1.add(btnCreate, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 358, 133, 33));

        btnDelete.setText("Xóa");
        btnDelete.setEnabled(false);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jPanel1.add(btnDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(368, 358, 133, 33));

        btnUpdate.setText("Cập nhật");
        btnUpdate.setEnabled(false);
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        jPanel1.add(btnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(193, 358, 133, 33));

        btnRefresh.setText("Làm mới");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });
        jPanel1.add(btnRefresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(533, 358, 133, 33));

        jLabel11.setText("ID");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 50, -1, -1));

        txtID.setActionCommand("<Not Set>");
        txtID.setEnabled(false);
        txtID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIDActionPerformed(evt);
            }
        });
        jPanel1.add(txtID, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 72, 334, -1));

        jPanel2.setBackground(new java.awt.Color(0, 204, 204));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 740, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 540, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 740, 540));

        jTabbedPane1.addTab("Chi tiết nhân viên", jPanel1);

        jPanel3.setBackground(new java.awt.Color(0, 153, 153));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Quản lí nhân viên");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(268, 268, 268)
                .addComponent(jLabel1)
                .addContainerGap(298, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(22, 22, 22))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTabbedPane2.setBackground(new java.awt.Color(0, 204, 204));

        tblUser1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Họ và tên", "Chức vụ", "Email", "Số điện thoại", "Ngày tạo", "Ngày sinh", "Trạng thái"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblUser1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUser1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblUser1);

        jTabbedPane2.addTab("Danh sách nhân viên", jScrollPane2);

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tfFullName1.setActionCommand("<Not Set>");
        tfFullName1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfFullName1ActionPerformed(evt);
            }
        });
        jPanel4.add(tfFullName1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 116, 334, -1));

        jLabel12.setText("Ngày sinh");
        jPanel4.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 212, -1, -1));

        jLabel13.setText("Nhập Mật Khẩu");
        jPanel4.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(368, 94, -1, -1));
        jPanel4.add(txtEmail1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 184, 334, -1));

        tfPassword1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfPassword1ActionPerformed(evt);
            }
        });
        jPanel4.add(tfPassword1, new org.netbeans.lib.awtextra.AbsoluteConstraints(368, 116, 334, -1));
        jPanel4.add(txtDateOfBirth1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 246, 334, -1));

        jLabel14.setText("Nhập Lại Mật Khẩu");
        jPanel4.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(368, 144, -1, -1));
        jPanel4.add(txtPhone1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 305, 334, -1));
        jPanel4.add(tfConfirmPasword1, new org.netbeans.lib.awtextra.AbsoluteConstraints(368, 166, 334, -1));

        jLabel15.setText("Chức Vụ");
        jPanel4.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(368, 224, -1, -1));

        cbchucvu1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nhân Viên", "Quản Lý" }));
        jPanel4.add(cbchucvu1, new org.netbeans.lib.awtextra.AbsoluteConstraints(368, 246, -1, -1));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel16.setText("Tạo Tài Khoản");
        jPanel4.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(272, 15, -1, -1));

        jLabel17.setText("Email");
        jPanel4.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 144, -1, -1));

        jLabel18.setText("Họ Và Tên");
        jPanel4.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 94, -1, -1));

        jLabel19.setText("Số Điện Thoại");
        jPanel4.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 280, -1, -1));

        jLabel20.setText("Trạng thái");
        jPanel4.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(368, 280, -1, -1));

        cboTrangThai1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Khả dụng", "Bị chặn" }));
        jPanel4.add(cboTrangThai1, new org.netbeans.lib.awtextra.AbsoluteConstraints(368, 305, 88, -1));

        btnCreate1.setBackground(new java.awt.Color(255, 153, 51));
        btnCreate1.setText("Tạo mới");
        btnCreate1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreate1ActionPerformed(evt);
            }
        });
        jPanel4.add(btnCreate1, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 358, 133, 33));

        btnDelete1.setText("Xóa");
        btnDelete1.setEnabled(false);
        btnDelete1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelete1ActionPerformed(evt);
            }
        });
        jPanel4.add(btnDelete1, new org.netbeans.lib.awtextra.AbsoluteConstraints(368, 358, 133, 33));

        btnUpdate1.setText("Cập nhật");
        btnUpdate1.setEnabled(false);
        btnUpdate1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdate1ActionPerformed(evt);
            }
        });
        jPanel4.add(btnUpdate1, new org.netbeans.lib.awtextra.AbsoluteConstraints(193, 358, 133, 33));

        btnRefresh1.setText("Làm mới");
        btnRefresh1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefresh1ActionPerformed(evt);
            }
        });
        jPanel4.add(btnRefresh1, new org.netbeans.lib.awtextra.AbsoluteConstraints(533, 358, 133, 33));

        jLabel21.setText("ID");
        jPanel4.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 50, -1, -1));

        txtID1.setActionCommand("<Not Set>");
        txtID1.setEnabled(false);
        txtID1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtID1ActionPerformed(evt);
            }
        });
        jPanel4.add(txtID1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 72, 334, -1));

        jPanel5.setBackground(new java.awt.Color(0, 204, 204));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 740, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 540, Short.MAX_VALUE)
        );

        jPanel4.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 740, 540));

        jTabbedPane2.addTab("Chi tiết nhân viên", jPanel4);

        jPanel6.setBackground(new java.awt.Color(0, 153, 153));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Quản lí nhân viên");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(268, 268, 268)
                .addComponent(jLabel22)
                .addContainerGap(298, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(jLabel22)
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 754, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(4, 4, 4)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 750, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 617, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(1, 1, 1)
                    .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUserMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            int index = tblUser.getSelectedRow();
            User user = items.get(index);
            setForm(user);
            disableButton();
            btnDelete.setEnabled(true);
            btnUpdate.setEnabled(true);
            jTabbedPane1.setSelectedIndex(1);
        }
    }//GEN-LAST:event_tblUserMouseClicked

    private void tfFullNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfFullNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfFullNameActionPerformed

    private void tfPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfPasswordActionPerformed

    private void btnCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateActionPerformed
        // TODO add your handling code here:
        create();
    }//GEN-LAST:event_btnCreateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        delete();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
        reset();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void txtIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIDActionPerformed

    private void tblUser1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUser1MouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            int index = tblUser.getSelectedRow();
            User user = items.get(index);
            setForm(user);
            disableButton();
            btnDelete.setEnabled(true);
            btnUpdate.setEnabled(true);
            jTabbedPane1.setSelectedIndex(1);
        }
    }//GEN-LAST:event_tblUser1MouseClicked

    private void tfFullName1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfFullName1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfFullName1ActionPerformed

    private void tfPassword1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfPassword1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfPassword1ActionPerformed

    private void btnCreate1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreate1ActionPerformed
        // TODO add your handling code here:
        create();
    }//GEN-LAST:event_btnCreate1ActionPerformed

    private void btnDelete1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelete1ActionPerformed
        // TODO add your handling code here:
        delete();
    }//GEN-LAST:event_btnDelete1ActionPerformed

    private void btnUpdate1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdate1ActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_btnUpdate1ActionPerformed

    private void btnRefresh1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefresh1ActionPerformed
        // TODO add your handling code here:
        reset();
    }//GEN-LAST:event_btnRefresh1ActionPerformed

    private void txtID1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtID1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtID1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UserManagerDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserManagerDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserManagerDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserManagerDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                UserManagerDialog dialog = new UserManagerDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCreate;
    private javax.swing.JButton btnCreate1;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDelete1;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnRefresh1;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnUpdate1;
    private javax.swing.JComboBox<String> cbchucvu;
    private javax.swing.JComboBox<String> cbchucvu1;
    private javax.swing.JComboBox<String> cboTrangThai;
    private javax.swing.JComboBox<String> cboTrangThai1;
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
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable tblUser;
    private javax.swing.JTable tblUser1;
    private javax.swing.JPasswordField tfConfirmPasword;
    private javax.swing.JPasswordField tfConfirmPasword1;
    private javax.swing.JTextField tfFullName;
    private javax.swing.JTextField tfFullName1;
    private javax.swing.JPasswordField tfPassword;
    private javax.swing.JPasswordField tfPassword1;
    private javax.swing.JTextField txtDateOfBirth;
    private javax.swing.JTextField txtDateOfBirth1;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtEmail1;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtID1;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtPhone1;
    // End of variables declaration//GEN-END:variables
}
