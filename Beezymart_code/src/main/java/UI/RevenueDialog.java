/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package UI;


import Util.XJdbc;
import Util.XDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.Date;

/**
 *
 * @author Hii
 */
public class RevenueDialog extends javax.swing.JDialog {
 private JSpinner spFrom, spTo;
    private JComboBox<CategoryItem> cbCategory;
    private JButton btnLoad, btnRefresh;
    private JTable tblBills;
    private DefaultTableModel modelBills;
    private JLabel lblGrandTotal;

    // formatter cho số tiền
    private static final DecimalFormat MONEY_FMT = new DecimalFormat("#,##0.00");

    public RevenueDialog(Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initComponents2();
        initExtra();
        setTitle("Thống kê doanh thu - Danh sách bill");
        setSize(900, 560);
        setLocationRelativeTo(parent);
    }

    private void initComponents2() {
        // Controls top
        spFrom = new JSpinner(new SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH));
        spTo = new JSpinner(new SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH));
        spFrom.setEditor(new JSpinner.DateEditor(spFrom, "yyyy-MM-dd"));
        spTo.setEditor(new JSpinner.DateEditor(spTo, "yyyy-MM-dd"));

        cbCategory = new JComboBox<>();
        cbCategory.addItem(new CategoryItem(0, "Tất cả"));

        btnLoad = new JButton("Tải");
        btnRefresh = new JButton("Làm lại");

        // Bills table
        modelBills = new DefaultTableModel(new Object[]{"ID", "Thu ngân (UserID)", "Checkout", "Phương thức", "Tổng (VNĐ)"}, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
            @Override public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) return Integer.class;
                if (columnIndex == 4) return Double.class; // total amount numeric
                return String.class;
            }
        };
        tblBills = new JTable(modelBills);
        tblBills.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Renderer: format tiền cho cột 4
        DefaultTableCellRenderer moneyRend = new DefaultTableCellRenderer() {
            @Override public void setValue(Object value) {
                if (value instanceof Number) {
                    setText(MONEY_FMT.format(((Number) value).doubleValue()));
                } else setText(value == null ? "" : value.toString());
            }
        };
        tblBills.getColumnModel().getColumn(4).setCellRenderer(moneyRend);

        JScrollPane spBills = new JScrollPane(tblBills);

        lblGrandTotal = new JLabel("Tổng khoảng: 0.00");

        // Layout
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        top.add(new JLabel("Từ:"));
        top.add(spFrom);
        top.add(new JLabel("Đến:"));
        top.add(spTo);
        top.add(new JLabel("Loại sản phẩm:"));
        top.add(cbCategory);
        top.add(btnLoad);
        top.add(btnRefresh);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(lblGrandTotal, BorderLayout.WEST);

        getContentPane().setLayout(new BorderLayout(6,6));
        getContentPane().add(top, BorderLayout.NORTH);
        getContentPane().add(spBills, BorderLayout.CENTER);
        getContentPane().add(bottom, BorderLayout.SOUTH);
    }

    private void initExtra() {
        // load categories
        loadCategories();

        // actions
        btnLoad.addActionListener(e -> loadBills());
        btnRefresh.addActionListener(e -> {
            spFrom.setValue(new Date());
            spTo.setValue(new Date());
            cbCategory.setSelectedIndex(0);
            modelBills.setRowCount(0);
            lblGrandTotal.setText("Tổng khoảng: 0.00");
        });

        // double click to show bill detail
        tblBills.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int r = tblBills.getSelectedRow();
                    if (r >= 0) {
                        Integer billId = (Integer) modelBills.getValueAt(r, 0);
                        showBillDetailDialog(billId);
                    }
                }
            }
        });
    }

    // load categories from Category table (assume columns ID, Name)
    private void loadCategories() {
        String sql = "SELECT ID, [Name] FROM [Category] ORDER BY [Name]";
        try (Connection conn = XJdbc.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("Name");
                cbCategory.addItem(new CategoryItem(id, name));
            }
        } catch (Exception ex) {
            // nếu lỗi, vẫn không dừng chức năng chính
            ex.printStackTrace();
        }
    }

    // load bills in date range, optionally filter bills which contain items of selected category
    private void loadBills() {
        Date fromDate = (Date) spFrom.getValue();
        Date toDate = (Date) spTo.getValue();

        Timestamp fromTs = new Timestamp(startOfDay(fromDate).getTime());
        Timestamp toTs = new Timestamp(endOfDay(toDate).getTime());

        CategoryItem selCat = (CategoryItem) cbCategory.getSelectedItem();
        int catId = selCat == null ? 0 : selCat.id;

        modelBills.setRowCount(0);
        double grandTotal = 0.0;

        // SQL: lấy bills trong khoảng; nếu catId > 0 thì chỉ lấy bill có BillItem -> Product.CategoryID = ?
        String sqlBase = 
            "SELECT b.ID, b.UserID, b.Checkout, b.PaymentMethod, b.TotalAmount " +
            "FROM [Bill] b " +
            "WHERE b.[Checkout] BETWEEN ? AND ? ";

        String sqlExistCat = 
            "AND EXISTS (SELECT 1 FROM [BillItem] bi JOIN [Product] p ON bi.ProductID = p.ID WHERE bi.BillID = b.ID AND p.CategoryID = ?) ";

        String order = "ORDER BY b.Checkout DESC";

        String sql = sqlBase + (catId > 0 ? sqlExistCat : "") + order;

        try (Connection conn = XJdbc.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, fromTs);
            ps.setTimestamp(2, toTs);
            if (catId > 0) ps.setInt(3, catId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("ID");
                    String userId = String.valueOf(rs.getInt("UserID"));
                    Timestamp checkout = rs.getTimestamp("Checkout");
                    String pay = rs.getString("PaymentMethod");
                    double total = rs.getDouble("TotalAmount");

                    grandTotal += total;
                    modelBills.addRow(new Object[]{
                        id,
                        userId,
                        checkout == null ? "" : checkout.toString(),
                        pay == null ? "" : pay,
                        total
                    });
                }
            }

            lblGrandTotal.setText("Tổng khoảng: " + MONEY_FMT.format(grandTotal));
        } catch (Exception ex) {
            ex.printStackTrace();
            XDialog.alert("Lỗi khi tải bills: " + ex.getMessage());
        }
    }

    // show dialog with bill details (BillItem list)
    private void showBillDetailDialog(int billId) {
        BillDetailDialog dlg = new BillDetailDialog(this, true, billId);
        dlg.setVisible(true);
    }

    private Date startOfDay(Date d) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(d);
        cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
        cal.set(java.util.Calendar.MINUTE, 0);
        cal.set(java.util.Calendar.SECOND, 0);
        cal.set(java.util.Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    private Date endOfDay(Date d) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(d);
        cal.set(java.util.Calendar.HOUR_OF_DAY, 23);
        cal.set(java.util.Calendar.MINUTE, 59);
        cal.set(java.util.Calendar.SECOND, 59);
        cal.set(java.util.Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    // small holder cho category trong combobox
    private static class CategoryItem {
        final int id;
        final String name;
        CategoryItem(int id, String name) { this.id = id; this.name = name; }
        @Override public String toString() { return name; }
    }

    // Inner dialog hiển thị BillDetail (BillItem list) và tổng chi tiết
    


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
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
            java.util.logging.Logger.getLogger(RevenueDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RevenueDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RevenueDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RevenueDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                RevenueDialog dialog = new RevenueDialog(new javax.swing.JFrame(), true);
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
    // End of variables declaration//GEN-END:variables
}
