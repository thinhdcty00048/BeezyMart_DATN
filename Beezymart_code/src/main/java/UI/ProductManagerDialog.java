/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package UI;

import Entity.Category;
import Entity.Product;
import Util.XDialog;
import com.company.dao.CategoryDAO;
import com.company.dao.CategoryDAOImpl;
import com.company.dao.ProductDAO;
import com.company.dao.ProductDAOImpl;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Hii
 */
public class ProductManagerDialog extends javax.swing.JDialog {

    List<Product> items;
    List<Category> categories;
    ProductDAO dao = new ProductDAOImpl();
    CategoryDAO cdao = new CategoryDAOImpl();

    /**
     * Creates new form ProductManagerDialog
     */
    public ProductManagerDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initExtra();
        loadCategories();
        fillToTable();

    }

    private void initExtra() {
        // ban đầu
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);

        // bảng double click
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });

        // hành động nút
        btnCreate.addActionListener(e -> create());
        btnUpdate.addActionListener(e -> update());
        btnDelete.addActionListener(e -> delete());
        btnRefresh.addActionListener(e -> reset());
    }

    /**
     * Load categories vào cboCategory Khi chọn index 0 => available = true,
     * index 1 => false (áp dụng cho cboAvalable)
     */
    private void loadCategories() {
        DefaultComboBoxModel<String> cboModel = (DefaultComboBoxModel<String>) cboCategory.getModel();
        cboModel.removeAllElements();
        categories = cdao.findAll();

        for (Category category : categories) {
            cboModel.addElement(category.getName()); 
        }

    }

    /**
     * Fill table products
     */
    public void fillToTable() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        items = dao.findAll();
        if (items != null) {
            items.forEach(p -> {
                Object[] rowData = {
                    p.getID(),
                    p.getName(),
                    p.getUnitPrice(),
                    p.getUnitName(),
                    p.getDiscount(),
                    p.isAvailable() ? "Đang bán" : "Ngừng bán",
                    p.getQuantity(),
                    // tìm tên category theo CategoryID
                    findCategoryNameById(p.getCategoryID())
                };
                model.addRow(rowData);
            });
        }

        // reset nút
        btnCreate.setEnabled(true);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
    }

    private String findCategoryNameById(int categoryId) {
        if (categories == null) {
            return "";
        }
        return categories.stream()
                .filter(c -> c.getID() == categoryId)
                .map(Category::getName)
                .findFirst()
                .orElse("");
    }

    /**
     * Lấy dữ liệu từ form, validate và trả về Product (không set ID)
     */
    private Product getForm() {
        String name = txtName.getText().trim();
        String unitName = txtUnitName.getText().trim();
        String unitPriceStr = txtUnitPrice.getText().trim();
        String discountStr = txtDiscount.getText().trim();
        String quantityStr = txtQuantity.getText().trim();

        if (name.isEmpty()) {
            XDialog.alert("Vui lòng nhập tên sản phẩm!");
            return null;
        }

        if (unitPriceStr.isEmpty()) {
            XDialog.alert("Vui lòng nhập đơn giá!");
            return null;
        }

        if (quantityStr.isEmpty()) {
            XDialog.alert("Vui lòng nhập số lượng!");
            return null;
        }

        // parse số
        int unitPrice;
        int discount = 0;
        int quantity;
        try {
            unitPrice = Integer.parseInt(unitPriceStr);
        } catch (NumberFormatException ex) {
            XDialog.alert("Đơn giá không hợp lệ!");
            return null;
        }
        if (!discountStr.isEmpty()) {
            try {
                discount = Integer.parseInt(discountStr);
            } catch (NumberFormatException ex) {
                XDialog.alert("Giảm giá phải là số nguyên!");
                return null;
            }
        }
        try {
            quantity = Integer.parseInt(quantityStr);
        } catch (NumberFormatException ex) {
            XDialog.alert("Số lượng phải là số nguyên!");
            return null;
        }

        // available: index 0 => true, index 1 => false
        boolean available = cboAvalable.getSelectedIndex() == 0;

        // category
        int catIndex = cboCategory.getSelectedIndex();
        int categoryId = -1;
        if (categories != null && catIndex >= 0 && catIndex < categories.size()) {
            categoryId = categories.get(catIndex).getID();
        } else {
            XDialog.alert("Vui lòng chọn loại sản phẩm!");
            return null;
        }

        Product p = new Product();
        p.setName(name);
        p.setUnitName(unitName);
        p.setUnitPrice(unitPrice);
        p.setDiscount(discount);
        p.setAvailable(available);
        p.setQuantity(quantity);
        p.setCategoryID(categoryId);

        return p;
    }

    /**
     * Tạo mới
     */
    private void create() {
        Product p = getForm();
        if (p != null) {
            dao.create(p);
            XDialog.alert("Đã tạo sản phẩm thành công");
            jTabbedPane1.setSelectedIndex(0);
            loadCategories(); // nếu category có thể thay đổi ngoài thì reload
            fillToTable();
            reset();
        }
    }

    /**
     * Đổ dữ liệu từ Product vào form
     */
    private void setForm(Product p) {
        if (p == null) {
            return;
        }
        txtID.setText(String.valueOf(p.getID()));
        txtName.setText(p.getName());
        txtUnitName.setText(p.getUnitName());
        txtUnitPrice.setText(String.valueOf(p.getUnitPrice()));
        txtDiscount.setText(String.valueOf(p.getDiscount()));
        txtQuantity.setText(String.valueOf(p.getQuantity()));
        cboAvalable.setSelectedIndex(p.isAvailable() ? 0 : 1);

        // chọn category trong cboCategory theo CategoryID
        if (categories != null) {
            for (int i = 0; i < categories.size(); i++) {
                if (categories.get(i).getID() == p.getCategoryID()) {
                    cboCategory.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    private void disableButton() {
        btnCreate.setEnabled(false);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
    }

    /**
     * Cập nhật
     */
    private void update() {
        Product p = getForm();
        if (p != null) {
            try {
                int id = Integer.parseInt(txtID.getText());
                p.setID(id);
                dao.update(p);
                XDialog.alert("Cập nhật sản phẩm thành công");
                jTabbedPane1.setSelectedIndex(0);
                fillToTable();
                reset();
            } catch (NumberFormatException ex) {
                XDialog.alert("ID sản phẩm không hợp lệ");
            }
        }
    }

    private void reset() {
        txtID.setText("");
        txtName.setText("");
        txtUnitName.setText("");
        txtUnitPrice.setText("");
        txtDiscount.setText("");
        txtQuantity.setText("");
        cboAvalable.setSelectedIndex(0); // mặc định Đang bán => true
        if (cboCategory.getItemCount() > 0) {
            cboCategory.setSelectedIndex(0);
        }
        btnCreate.setEnabled(true);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
    }

    private void delete() {
        try {
            int id = Integer.parseInt(txtID.getText());
            dao.deleteById(id);
            XDialog.alert("Xóa sản phẩm thành công");
            jTabbedPane1.setSelectedIndex(0);
            fillToTable();
            reset();
        } catch (NumberFormatException ex) {
            XDialog.alert("Vui lòng chọn sản phẩm cần xóa");
        }
    }

    // Bắt event double click trên bảng
    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {
        if (evt.getClickCount() == 2) {
            int index = jTable1.getSelectedRow();
            if (index >= 0 && index < items.size()) {
                Product p = items.get(index);
                setForm(p);
                disableButton();
                btnDelete.setEnabled(true);
                btnUpdate.setEnabled(true);
                jTabbedPane1.setSelectedIndex(1);
            }
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

        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtUnitName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtUnitPrice = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtDiscount = new javax.swing.JTextField();
        txtQuantity = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cboAvalable = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        cboCategory = new javax.swing.JComboBox<>();
        btnUpdate = new javax.swing.JButton();
        btnCreate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("QUản lí sản phẩm");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Tên", "Đơn giá", "Đơn vị tính", "Giảm giá", "Trạng thái", "Số lượng còn", "Loại sản phẩm"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Float.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jTabbedPane1.addTab("Danh sách sản phẩm", jScrollPane1);

        jLabel2.setText("ID");

        txtID.setEnabled(false);

        jLabel3.setText("Đơn vị tính");

        jLabel4.setText("Tên sản phẩm");

        txtName.setText("asdasd");

        jLabel5.setText("Đơn giá");

        jLabel6.setText("Giảm giá (%)");

        jLabel7.setText("Số lượng còn");

        jLabel8.setText("Trạng thái");

        cboAvalable.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Đang bán", "Ngừng bán" }));

        jLabel9.setText("Loại sản phầm");

        btnUpdate.setText("Cập nhật");

        btnCreate.setText("Tạo mới");

        btnDelete.setText("Xóa");

        btnRefresh.setText("Làm mới");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtUnitName, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(114, 114, 114))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtUnitPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(cboAvalable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cboCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtUnitName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtUnitPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboAvalable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(70, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Chi tiết sản phẩm", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(328, 328, 328)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(ProductManagerDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProductManagerDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProductManagerDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProductManagerDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ProductManagerDialog dialog = new ProductManagerDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cboAvalable;
    private javax.swing.JComboBox<String> cboCategory;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtDiscount;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtQuantity;
    private javax.swing.JTextField txtUnitName;
    private javax.swing.JTextField txtUnitPrice;
    // End of variables declaration//GEN-END:variables
}
