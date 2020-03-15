package network_tool_and_pc_comparison;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

public class Controller extends javax.swing.JFrame {

    ArrayList<String> al;
    ArrayList<PcInfo> pcInfoList;
    Tablemodel table1 = new Tablemodel();
    Tablemodel2 table2 = new Tablemodel2();

    public Controller() {
        initComponents();
        getContentPane().setBackground(Color.CYAN);

        setSize(600, 600);
        setTitle("controller");

        al = new ArrayList();
        pcInfoList = new ArrayList();

        jTable1.setModel(table1);
        jTable2.setModel(table2);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 51, 51));
        getContentPane().setLayout(null);

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 51, 51));
        jButton2.setText("FILTER");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2);
        jButton2.setBounds(340, 120, 120, 30);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTable2);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(310, 190, 250, 140);

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(0, 51, 51));
        jButton3.setText("EXPLORE");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3);
        jButton3.setBounds(330, 380, 130, 30);

        jButton4.setBackground(new java.awt.Color(255, 255, 255));
        jButton4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton4.setForeground(new java.awt.Color(0, 51, 51));
        jButton4.setText("COMPARE");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton4);
        jButton4.setBounds(330, 430, 130, 40);

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
        jScrollPane1.setBounds(40, 190, 210, 140);

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 51, 51));
        jButton1.setText("DETECT");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(80, 120, 120, 31);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        task1 t1 = new task1();
        Thread thread = new Thread(t1);
        thread.start();
        jButton1.setEnabled(false);

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        for (int i = 0; i < al.size(); i++) {

            String ipAddress = al.get(i);
            Client c = new Client(ipAddress);
            Thread t = new Thread(c);
            t.start();
            jButton2.setEnabled(false);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        ExploreForm explore = new ExploreForm(pcInfoList);
        explore.setVisible(true);


    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        ComparePcFrame compare = new ComparePcFrame(pcInfoList);

        compare.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed
    public class Client implements Runnable {

        String ip;

        Client(String ip) {
            this.ip = ip;
        }

        @Override
        public void run() {

            try {

                Socket sock = new Socket(ip, 9000);

                System.out.println("Connection Accepted!!");
                System.out.println(ip);

                String pcName = sock.getInetAddress().getHostName();

                DataInputStream dis = new DataInputStream(sock.getInputStream());
                DataOutputStream dos = new DataOutputStream(sock.getOutputStream());

                dos.writeBytes("Hello Server\r\n");

                System.out.println(dis.readLine());
                System.out.println(pcName + "----------" + ip);
                pcInfoList.add(new PcInfo(ip, pcName));
                table2.fireTableDataChanged();

            } catch (IOException ex) {

                ex.printStackTrace();
            }

        }

    }

    public class task1 implements Runnable {

        @Override
        public void run() {
//            int count = 0;
//            for (int i = 0; i < 17; i++) {
//                Thread t[] = new Thread[15];
//                for (int j = 0; j < 15; j++) {
//                    Task task = new Task(count);
//                    t[j] = new Thread(task);
//                    t[j].start();
//                    count++;
//                }
//                for (int k = 0; k < 15; k++) {
//                    try {
//                        t[k].join();
//                    } catch (InterruptedException ex) {
//                        ex.printStackTrace();
//                    }
//                    System.out.println("slot" + i + "is completed");
//
//                }
            for (int i =125 ; i < 128; i++) {

                new Thread(new Task(i)).start();
            }

            
        }
    }

        public class Task implements Runnable {

            int i;

            Task(int i) {

                this.i = i;

            }

            public void run() {

                int count = 0;
                String ipAddress = "10.250.3." + i;
              //  String ipAddress = "192.168.0." + i;
                try {
                    Process p = Runtime.getRuntime().exec("ping " + ipAddress);
                    BufferedReader inputStream = new BufferedReader(
                            new InputStreamReader(p.getInputStream()));

                    String s = "";

                    while ((s = inputStream.readLine()) != null) {

                        if (s.contains("TTL")) {
                            count++;
                        }

                    }
                    if (count == 4) {

                        System.out.println("192.168.0." + i + " is connected!!");
                        al.add(ipAddress);

                        table1.fireTableDataChanged();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        class Tablemodel extends AbstractTableModel {

            String names[] = {"Ip Address"};

            @Override
            public int getRowCount() {
                return al.size();
            }

            @Override
            public int getColumnCount() {
                return 1;

            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                return al.get(rowIndex);
            }

            public String getColumnName(int ColumnIndex) {
                return names[0];
            }
        }

        class Tablemodel2 extends AbstractTableModel {

            String names[] = {"Ip Address", "PC Name"};

            @Override
            public int getRowCount() {
                return pcInfoList.size();
            }

            @Override
            public int getColumnCount() {
                return 2;

            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {

                PcInfo pcInfo = pcInfoList.get(rowIndex);
                if (columnIndex == 0) {
                    return pcInfo.ip;
                } else {
                    return pcInfo.pcName;
                }

            }

            public String getColumnName(int ColumnIndex) {
                return names[ColumnIndex];
            }
        }

        public static void main(String args[]) {

            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new Controller().setVisible(true);
                }
            });
        }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables
}
