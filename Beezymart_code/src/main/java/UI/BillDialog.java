/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package UI;

import Entity.Bill;
import Entity.Product;
import Entity.User;
import Util.XDialog;
import Util.XJdbc;
import com.company.dao.BillDAO;
import com.company.dao.BillDAOImpl;
import com.company.dao.BillItemDAO;
import com.company.dao.BillItemDAOImpl;
import com.company.dao.ProductDAO;
import com.company.dao.ProductDAOImpl;
import com.company.dao.UserDAO;
import com.company.dao.UserDAOImpl;

import java.awt.*;
import java.awt.event.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Hii
 */
public class BillDialog extends javax.swing.JDialog {

    /**
     * Creates new form BillDialog
     */
    // DAO
    private final ProductDAO productDao = new ProductDAOImpl();
    private final UserDAO userDao = new UserDAOImpl();
    private final BillDAO billDao = new BillDAOImpl();
    private final BillItemDAO billItemDao = new BillItemDAOImpl();
    // Data lists
    private List<Product> products;
    private List<User> users;

    // Cart items
    private final List<CartItem> cart = new ArrayList<>();

    public BillDialog(Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initExtra();
        loadProducts();
        loadUsers();
    }

    // Inner cart item model
    private static class CartItem {

        int productId;
        String productName;
        double unitPrice;
        int discount; // percent
        int qty;
        String unit;
        String note;

        double getTotal() {
            return unitPrice * qty * (1.0 - discount / 100.0);
        }
    }

    private void initExtra() {
        // init event handlers
        btnAddToCart.addActionListener(e -> addToCart());
        btnRemoveItem.addActionListener(e -> removeSelectedItem());
        btnClear.addActionListener(e -> clearCart());
        btnCheckout.addActionListener(e -> checkout());
        cboProduct.addActionListener(e -> productSelectionChanged());
        // configure table double-click to edit qty/note optionally
        tblCart.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    editSelectedItem();
                }
            }
        });
        // default values
        cboProduct.setSelectedIndex(-1);
        lblTotal.setText("0.00");
    }

    // load product list from DB into cboProduct
    private void loadProducts() {
        try {
            products = productDao.findAll();
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            if (products != null) {
                for (Product p : products) {
                    // store "Name (ID)" text; we will map selection index to products list
                    model.addElement(String.format("%s (ID:%d)", p.getName(), p.getID()));
                }
            }
            cboProduct.setModel(model);
            if (model.getSize() > 0) {
                cboProduct.setSelectedIndex(0);
            }
            productSelectionChanged();
        } catch (Exception ex) {
            XDialog.alert("Lỗi load sản phẩm: " + ex.getMessage());
        }
    }

    // load user list into cboUser (cashiers)
    private void loadUsers() {
        try {
            txtUser.setText("Nguyễn Phong");
        } catch (Exception ex) {
            XDialog.alert("Lỗi load nhân viên: " + ex.getMessage());
        }
    }

    // when product selection changed -> update unit price, discount, unit data
    private void productSelectionChanged() {
        int idx = cboProduct.getSelectedIndex();
        if (idx >= 0 && products != null && idx < products.size()) {
            Product p = products.get(idx);
            txtUnitPrice.setText(String.valueOf(p.getUnitPrice()));
            txtDiscount.setText(String.valueOf(p.getDiscount()));
            txtUnit.setText(p.getUnitName() == null ? "" : p.getUnitName());
        } else {
            txtUnitPrice.setText("");
            txtDiscount.setText("0");
            txtUnit.setText("");
        }
    }

    // add product to cart
    private void addToCart() {
        int pIndex = cboProduct.getSelectedIndex();
        if (pIndex < 0 || products == null || pIndex >= products.size()) {
            XDialog.alert("Chọn sản phẩm");
            return;
        }
        Product p = products.get(pIndex);
        int qty;
        int discount;
        try {
            qty = Integer.parseInt(txtQty.getText().trim());
            if (qty <= 0) {
                XDialog.alert("Số lượng phải lớn hơn 0");
                return;
            }
        } catch (NumberFormatException e) {
            XDialog.alert("Số lượng không hợp lệ");
            return;
        }
        try {
            discount = Integer.parseInt(txtDiscount.getText().trim());
            if (discount < 0) {
                discount = 0;
            }
        } catch (NumberFormatException e) {
            discount = p.getDiscount();
        }

        CartItem item = new CartItem();
        item.productId = p.getID();
        item.productName = p.getName();
        item.unitPrice = p.getUnitPrice();
        item.discount = discount;
        item.qty = qty;
        item.unit = txtUnit.getText().trim();
        item.note = txtNote.getText().trim();

        // if same product exists in cart, increase qty
        boolean merged = false;
        for (CartItem ci : cart) {
            if (ci.productId == item.productId && (ci.unit.equals(item.unit))) {
                ci.qty += item.qty;
                merged = true;
                break;
            }
        }
        if (!merged) {
            cart.add(item);
        }
        refreshCartTable();
        clearInputForNext();
    }

    private void clearInputForNext() {
        txtQty.setText("1");
        txtNote.setText("");
    }

    private void refreshCartTable() {
        DefaultTableModel model = (DefaultTableModel) tblCart.getModel();
        model.setRowCount(0);
        double total = 0.0;
        for (CartItem it : cart) {
            double t = it.getTotal();
            total += t;
            model.addRow(new Object[]{
                it.productId,
                it.productName,
                it.unitPrice,
                it.discount,
                it.qty,
                it.unit,
                String.format("%.2f", t),
                it.note
            });
        }
        lblTotal.setText(String.format("%.2f", total));
    }

    private void removeSelectedItem() {
        int r = tblCart.getSelectedRow();
        if (r < 0) {
            XDialog.alert("Chọn mục để xóa");
            return;
        }
        cart.remove(r);
        refreshCartTable();
    }

    private void clearCart() {
        cart.clear();
        refreshCartTable();
    }

    private void editSelectedItem() {
        int r = tblCart.getSelectedRow();
        if (r < 0) {
            return;
        }
        CartItem it = cart.get(r);
        // simple edit dialog to change qty and note
        JTextField fQty = new JTextField(String.valueOf(it.qty));
        JTextField fNote = new JTextField(it.note);
        Object[] message = {"Số lượng:", fQty, "Ghi chú:", fNote};
        int option = JOptionPane.showConfirmDialog(this, message, "Chỉnh sửa mục", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int newQty = Integer.parseInt(fQty.getText().trim());
                if (newQty <= 0) {
                    throw new NumberFormatException();
                }
                it.qty = newQty;
                it.note = fNote.getText().trim();
                refreshCartTable();
            } catch (NumberFormatException ex) {
                XDialog.alert("Số lượng không hợp lệ");
            }
        }
    }

    // Create Bill + BillItems in DB
    private void checkout() {
        if (cart.isEmpty()) {
            XDialog.alert("Giỏ hàng trống!");
            return;
        }

        int userId = 2; // lấy từ txtUser hoặc session nếu cần

        // compute total
        double totalAmount = cart.stream().mapToDouble(CartItem::getTotal).sum();

        String paymentMethod = (String) jComboBox2.getSelectedItem();
        if (paymentMethod == null) {
            paymentMethod = "Tiền mặt";
        }

        try {
            // Build Bill entity
            Bill bill = new Bill();
            bill.setUserID(userId);
            bill.setCheckout(new Date()); // Bill.setCheckout nhận Date
            bill.setStatus("1"); // hoặc "COMPLETED" tùy thiết kế
            bill.setTotalAmount((float) totalAmount); // Bill dùng float
            bill.setPaymentMethod(paymentMethod);
            bill.setGuestID(0); // 0 = no guest; nếu muốn null, đổi GuestID thành Integer trong Entity.Bill

            // create bill via DAO -> will set ID into bill (BillDAOImpl.create dùng OUTPUT INSERTED.ID)
            Bill createdBill = billDao.create(bill);
            int billId = createdBill.getID();
            if (billId <= 0) {
                XDialog.alert("Không tạo được bill (billId invalid)");
                return;
            }

            // insert BillItems via BillItemDAO
            for (CartItem it : cart) {
                Entity.BillItem bi = new Entity.BillItem();
                bi.setBillID(billId);
                bi.setProductID(it.productId);
                // BillItem.Unitprice là float trong Entity
                bi.setUnitprice((float) it.unitPrice);
                bi.setDiscount(it.discount);
                bi.setQuantity(it.qty);
                bi.setNote(it.note);
                bi.setUnit(it.unit);

                billItemDao.create(bi);
                // nếu bạn muốn kiểm tra ID vừa tạo: bi.getID() sau khi create sẽ có ID (nếu BillItemDAOImpl implement OUTPUT INSERTED.ID)
            }

            XDialog.alert("Tạo bill thành công (ID=" + billId + ") - Tổng: " + String.format("%.2f", totalAmount));
            clearCart();
        } catch (Exception ex) {
            XDialog.alert("Lỗi khi tạo bill: " + ex.getMessage());
            ex.printStackTrace();
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cboProduct = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        txtDiscount = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtUnitPrice = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCart = new javax.swing.JTable();
        lblTotal = new javax.swing.JLabel();
        btnClear = new javax.swing.JButton();
        btnRemoveItem = new javax.swing.JButton();
        btnCheckout = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        txtUnit = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtQty = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtNote = new javax.swing.JTextField();
        btnAddToCart = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(51, 102, 255));

        jPanel2.setBackground(new java.awt.Color(51, 102, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 540, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 540, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jPanel3.setBackground(new java.awt.Color(51, 102, 255));

        jPanel4.setBackground(new java.awt.Color(51, 102, 255));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 540, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 540, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(51, 204, 255));

        jLabel1.setText("Nhân viên (Thu ngân) :");

        txtUser.setEnabled(false);

        jLabel2.setText("Sản phẩm");

        cboProduct.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setText("Giảm giá (%)");

        txtDiscount.setEnabled(false);

        jLabel4.setText("Đơn giá");

        txtUnitPrice.setEnabled(false);
        txtUnitPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUnitPriceActionPerformed(evt);
            }
        });

        tblCart.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Đơn giá", "Giảm giá (%)", "Số lượng", "Đơn vị", "Thành tiền", "Ghi chú"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblCart);

        lblTotal.setText("0");

        btnClear.setText("Làm mới");

        btnRemoveItem.setText("Xóa mục");

        btnCheckout.setText("Thanh toán");
        btnCheckout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckoutActionPerformed(evt);
            }
        });

        jLabel7.setText("Thanh toán");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tiền mặt", "Chuyển khoản" }));

        jLabel8.setText("Đơn vị");

        txtUnit.setEnabled(false);

        jLabel9.setText("Số lượng");

        jLabel10.setText("Ghi chú");

        btnAddToCart.setText("Thêm vào giỏ");
        btnAddToCart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddToCartActionPerformed(evt);
            }
        });

        jLabel5.setText("Tổng (VNĐ):");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 755, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtUser)
                            .addComponent(cboProduct, 0, 369, Short.MAX_VALUE)
                            .addComponent(txtDiscount)
                            .addComponent(txtUnitPrice))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboBox2, 0, 163, Short.MAX_VALUE)
                            .addComponent(txtUnit)
                            .addComponent(txtQty)
                            .addComponent(txtNote))))
                .addContainerGap(25, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(137, 137, 137)
                .addComponent(btnAddToCart, javax.swing.GroupLayout.PREFERRED_SIZE, 527, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnRemoveItem)
                .addGap(18, 18, 18)
                .addComponent(btnClear)
                .addGap(18, 18, 18)
                .addComponent(btnCheckout)
                .addGap(31, 31, 31))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cboProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtUnitPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txtNote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addComponent(btnAddToCart)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotal)
                    .addComponent(btnClear)
                    .addComponent(btnRemoveItem)
                    .addComponent(btnCheckout)
                    .addComponent(jLabel5))
                .addGap(15, 15, 15))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtUnitPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUnitPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUnitPriceActionPerformed

    private void btnCheckoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckoutActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCheckoutActionPerformed

    private void btnAddToCartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddToCartActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAddToCartActionPerformed

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
            java.util.logging.Logger.getLogger(BillDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BillDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BillDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BillDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                BillDialog dialog = new BillDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnAddToCart;
    private javax.swing.JButton btnCheckout;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnRemoveItem;
    private javax.swing.JComboBox<String> cboProduct;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JTable tblCart;
    private javax.swing.JTextField txtDiscount;
    private javax.swing.JTextField txtNote;
    private javax.swing.JTextField txtQty;
    private javax.swing.JTextField txtUnit;
    private javax.swing.JTextField txtUnitPrice;
    private javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables
}
