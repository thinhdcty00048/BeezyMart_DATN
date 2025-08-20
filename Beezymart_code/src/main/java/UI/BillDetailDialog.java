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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
/**
 *
 * @author Hii
 */
public class BillDetailDialog extends javax.swing.JDialog {

     private final int billId;
    private JTable tblDetail;
    private DefaultTableModel modelDetail;
    private JLabel lblDetailTotal;

    private static final DecimalFormat MONEY_FMT = new DecimalFormat("#,##0.00");

    public BillDetailDialog(Window owner, boolean modal, int billId) {
        // sử dụng ModalityType cho Window owner
        super(owner, modal ? ModalityType.APPLICATION_MODAL : ModalityType.MODELESS);
        this.billId = billId;
        initComp();
        loadDetail();
        setTitle("Chi tiết Bill - ID=" + billId);
        setSize(700, 420);
        setLocationRelativeTo(owner);
    }

    private void initComp() {
        modelDetail = new DefaultTableModel(new Object[]{"ID","ProductID","Tên sản phẩm","Đơn giá","Giảm (%)","Số lượng","Đơn vị","Thành tiền","Ghi chú"}, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
            @Override public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0 || columnIndex == 1) return Integer.class;
                if (columnIndex == 3 || columnIndex == 7) return Double.class;
                if (columnIndex == 5) return Integer.class;
                return String.class;
            }
        };
        tblDetail = new JTable(modelDetail);

        // renderer tiền ở cột 3 (UnitPrice) và 7 (Amount)
        DefaultTableCellRenderer moneyRend = new DefaultTableCellRenderer() {
            @Override public void setValue(Object value) {
                if (value instanceof Number) setText(MONEY_FMT.format(((Number) value).doubleValue()));
                else setText(value == null ? "" : value.toString());
            }
        };

        // bảo đảm có cột trước khi thiết renderer (tránh NPE nếu model thay đổi)
        if (tblDetail.getColumnCount() > 3) tblDetail.getColumnModel().getColumn(3).setCellRenderer(moneyRend);
        if (tblDetail.getColumnCount() > 7) tblDetail.getColumnModel().getColumn(7).setCellRenderer(moneyRend);

        lblDetailTotal = new JLabel("Tổng bill: 0.00");

        getContentPane().setLayout(new BorderLayout(6,6));
        getContentPane().add(new JScrollPane(tblDetail), BorderLayout.CENTER);
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p.add(lblDetailTotal);
        getContentPane().add(p, BorderLayout.SOUTH);
    }

    private void loadDetail() {
        modelDetail.setRowCount(0);
        double sum = 0.0;

        // query BillItem join Product to get product name and compute amount
        String sql = "SELECT bi.ID, bi.ProductID, p.Name AS ProductName, bi.UnitPrice, bi.Discount, bi.Quantity, bi.Unit, bi.Note, " +
                "(bi.UnitPrice * bi.Quantity * (1.0 - bi.Discount/100.0)) AS Amount " +
                "FROM [BillItem] bi LEFT JOIN [Product] p ON bi.ProductID = p.ID " +
                "WHERE bi.BillID = ?";

        try (Connection conn = XJdbc.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, billId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("ID");
                    int productId = rs.getInt("ProductID");
                    String prodName = rs.getString("ProductName");
                    double unitPrice = rs.getDouble("UnitPrice");
                    int discount = rs.getInt("Discount");
                    int qty = rs.getInt("Quantity");
                    String unit = rs.getString("Unit");
                    String note = rs.getString("Note");
                    double amount = rs.getDouble("Amount");

                    sum += amount;
                    modelDetail.addRow(new Object[]{id, productId, prodName, unitPrice, discount, qty, unit, amount, note});
                }
            }
            lblDetailTotal.setText("Tổng bill: " + MONEY_FMT.format(sum));
        } catch (Exception ex) {
            ex.printStackTrace();
            XDialog.alert("Lỗi khi tải chi tiết bill: " + ex.getMessage());
        }
    }
    
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
            java.util.logging.Logger.getLogger(BillDetailDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BillDetailDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BillDetailDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BillDetailDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
