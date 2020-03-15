
package network_tool_and_pc_comparison;

public class SinglePanel extends javax.swing.JPanel {

   
    public SinglePanel() {
        initComponents();
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        hostname = new javax.swing.JLabel();
        hostip = new javax.swing.JLabel();

        setBackground(new java.awt.Color(0, 204, 153));
        setLayout(null);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/comp_1.jpg"))); // NOI18N
        add(jLabel1);
        jLabel1.setBounds(40, 10, 70, 70);

        hostname.setText("PcName");
        add(hostname);
        hostname.setBounds(10, 120, 140, 20);

        hostip.setText("IpAddress");
        add(hostip);
        hostip.setBounds(10, 140, 140, 20);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel hostip;
    public javax.swing.JLabel hostname;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
