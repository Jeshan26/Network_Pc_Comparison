package network_tool_and_pc_comparison;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Lab-3
 */
public class ComparePcFrame extends javax.swing.JFrame {

    ArrayList<PcInfo> pcInfoList;
    ArrayList<String> selectedIpList = new ArrayList<String>();
    Tablemodel2 table2 = new Tablemodel2();

    public ComparePcFrame(ArrayList<PcInfo> pcInfoList) {
        initComponents();
        getContentPane().setBackground(Color.BLACK);
        setSize(500,500);
        this.pcInfoList = pcInfoList;

        jTable1.setModel(table2);
        table2.fireTableDataChanged();

    }

    class Tablemodel2 extends AbstractTableModel {

        String names[] = {"Ip Address", "PC Name", "Select"};

        @Override
        public int getRowCount() {
            return pcInfoList.size();
        }

        @Override
        public int getColumnCount() {
            return 3;

        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {

            PcInfo pcInfo = pcInfoList.get(rowIndex);
            if (columnIndex == 0) {
                return pcInfo.ip;
            } else if (columnIndex == 1) {

                return pcInfo.pcName;
            } else {
                return pcInfo.cb;

            }

        }

        public String getColumnName(int ColumnIndex) {
            return names[ColumnIndex];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {

            return getValueAt(0, columnIndex).getClass();

        }

        @Override
        public void setValueAt(Object value, int rowIndex, int columnIndex) {

            pcInfoList.get(rowIndex).cb = (Boolean) (value);
            fireTableCellUpdated(rowIndex, columnIndex);

        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {

            if (columnIndex == 2) {

                return true;
            } else {
                return false;
            }

        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(70, 60, 280, 120);

        jButton1.setBackground(new java.awt.Color(51, 255, 255));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 51, 255));
        jButton1.setText("SELECT ALL");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(110, 210, 140, 25);

        jButton2.setBackground(new java.awt.Color(51, 255, 255));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 51, 255));
        jButton2.setText("DESELECT ALL");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2);
        jButton2.setBounds(110, 240, 140, 25);

        jButton3.setBackground(new java.awt.Color(51, 255, 255));
        jButton3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(0, 0, 255));
        jButton3.setText("COMPARE");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3);
        jButton3.setBounds(110, 270, 140, 25);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        for (int i = 0; i < pcInfoList.size(); i++) {
            pcInfoList.get(i).cb = true;

        }
        table2.fireTableDataChanged();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        for (int i = 0; i < pcInfoList.size(); i++) {
            pcInfoList.get(i).cb = false;

        }
        table2.fireTableDataChanged();

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        selectedIpList.clear();
        for (int i = 0; i < pcInfoList.size(); i++) {

            if (pcInfoList.get(i).cb == true) {
                selectedIpList.add(pcInfoList.get(i).ip);

            }
        }
        System.out.println("size of list:----------- " + selectedIpList.size());
        if (selectedIpList.size() < 2 || selectedIpList.size() > 5) {
            JOptionPane.showMessageDialog(ComparePcFrame.this, "Please Select in the given range");
        } else {
            int r = JOptionPane.showConfirmDialog(ComparePcFrame.this, "You have selected " + selectedIpList.size() + "." + "Do you want to continue?");
            if (r == JOptionPane.OK_OPTION) {

                CompareForm compareForm = new CompareForm(selectedIpList);
                compareForm.setVisible(true);

            }
        }

    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(ComparePcFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ComparePcFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ComparePcFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ComparePcFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // new ComparePcFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
