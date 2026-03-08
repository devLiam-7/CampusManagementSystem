package ui;

import utils.ThemeManager;
import ui.ViewFeesFrame;
import database.DBConnection;
import java.sql.*;
import javax.swing.JOptionPane;

public class FeeManagementFrame extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FeeManagementFrame.class.getName());

    public FeeManagementFrame() {
        initComponents();
        for (java.awt.Component c : getContentPane().getComponents()) {
        if (c instanceof javax.swing.JButton b) {
        b.setFocusPainted(false);
        b.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
    }
}
        setLocationRelativeTo(null);
        ThemeManager.applyTheme(this);
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        txtDue = new javax.swing.JTextField();
        txtPaid = new javax.swing.JTextField();
        txtTotal = new javax.swing.JTextField();
        txtRoll = new javax.swing.JTextField();
        btnSave = new javax.swing.JButton();
        btnView = new javax.swing.JButton();
        btnDarkMode = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Fee Management");

        jLabel1.setText("Roll No  : ");

        jLabel2.setText("Total Fees  :");

        jLabel3.setText("Paid Fees :");

        jLabel4.setText("Due Fees : ");

        jLabel5.setText("Status :");

        txtDue.addActionListener(this::txtDueActionPerformed);

        txtTotal.addActionListener(this::txtTotalActionPerformed);

        btnSave.setText("Save Fee");
        btnSave.addActionListener(this::btnSaveActionPerformed);

        btnView.setText("View Fee Details");
        btnView.addActionListener(this::btnViewActionPerformed);

        btnDarkMode.setText("Dark Mode");
        btnDarkMode.addActionListener(this::btnDarkModeActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(btnSave))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnView)
                            .addComponent(lblStatus)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtRoll, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPaid, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDue, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(141, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnDarkMode))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnDarkMode)
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtRoll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtPaid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtDue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lblStatus))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnView))
                .addContainerGap(73, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewActionPerformed

    ViewFeesFrame vf = new ViewFeesFrame();
    vf.setLocationRelativeTo(null); // center window
    vf.setVisible(true);
    this.dispose();

    }//GEN-LAST:event_btnViewActionPerformed

    private void txtTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalActionPerformed

    private void txtDueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDueActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        
        if(txtRoll.getText().isEmpty() || txtTotal.getText().isEmpty() || txtPaid.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"All Fields Required");
            return;
        }
        
        int roll;
        double total,paid,due;
        String status;
        
        try{
            
            roll = Integer.parseInt(txtRoll.getText());
            total = Double.parseDouble(txtTotal.getText());
            paid = Double.parseDouble(txtPaid.getText());
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(this,"Enter Valid Numbers");
            return;
        }
        if(paid>total){
            JOptionPane.showMessageDialog(this,"Paid Fees Cant Be Greater Than Total Fees");
            return;
        }
        due=total-paid;
        
        if(due == 0){
            status = "PAID";
        }else if(paid == 0){
            status = "Due";
        }else{
            status ="PARTIAL";
        }
        
        txtDue.setText(String.valueOf(due));
        lblStatus.setText(status);
        
        try{
            
            Connection con = DBConnection.getConnection();
            
            String checkSql = "SELECT roll_no FROM students WHERE roll_no=?";
            PreparedStatement checkPst = con.prepareStatement(checkSql);
            checkPst.setInt(1, roll);
            ResultSet rs = checkPst.executeQuery();

                if (!rs.next()) {
                    JOptionPane.showMessageDialog(this, "Student does not exist. Add student first.");
                         con.close();
                         return;
                }
            
            String sql = "REPLACE INTO fees (roll_no , total_fee , paid_fee , due_fee , status) values ( ?,?,?,?,?)";
            
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setInt(1,roll);
            pst.setDouble(2,total);
            pst.setDouble(3,paid);
            pst.setDouble(4,due);
            pst.setString(5,status);
            
            pst.executeUpdate();
            
            JOptionPane.showMessageDialog(this,"Fee Details Saved Succesfully");
            con.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(this,e.getMessage());
        }
    
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnDarkModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDarkModeActionPerformed
    ThemeManager.setDarkMode(!ThemeManager.isDarkMode());
    ThemeManager.applyTheme(this);
    }//GEN-LAST:event_btnDarkModeActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDarkMode;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnView;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JTextField txtDue;
    private javax.swing.JTextField txtPaid;
    private javax.swing.JTextField txtRoll;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
