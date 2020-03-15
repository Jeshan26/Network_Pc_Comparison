/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network_tool_and_pc_comparison;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lab-3
 */
public class CompareForm extends javax.swing.JFrame {

    int x = 5;
    int y = 5;
    int i;
    singlepanel2 sp2[];
    String request = "sendconfig";
    ArrayList<String> selectedIpList = new ArrayList<String>();

    public CompareForm(ArrayList<String> selectedIpList) {
        initComponents();

        setSize(1366, 768);
        setTitle("Compare form");

        this.selectedIpList = selectedIpList;

        CreatePanel cp = new CreatePanel();
        Thread t = new Thread(cp);
        t.start();

    }

    public class CreatePanel implements Runnable {

        @Override
        public void run() {
            sp2 = new singlepanel2[selectedIpList.size()];

            for (i = 0; i < selectedIpList.size(); i++) {
                try {
                    Client c = new Client(selectedIpList.get(i));
                    Thread t1 = new Thread(c);
                    t1.start();
                    t1.join();
                } catch (InterruptedException ex) {
                    Logger.getLogger(CompareForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

    }

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

                DataInputStream dis = new DataInputStream(sock.getInputStream());
                DataOutputStream dos = new DataOutputStream(sock.getOutputStream());

                if (request.equals("sendconfig")) {
                    dos.writeBytes("SendConfiguration\r\n");
                    String ip = dis.readLine();
                    String Pcname = dis.readLine();
                    String ostype = dis.readLine();
                    String osname = dis.readLine();
                    String cores = dis.readLine();
                    String memory = dis.readLine();

                    sp2[i] = new singlepanel2();

                    sp2[i].jLabel2.setText(Pcname);
                    sp2[i].jLabel3.setText(ip);
                    sp2[i].jLabel4.setText(memory);
                    sp2[i].jLabel5.setText(cores);
                    sp2[i].jLabel6.setText(osname);
                    sp2[i].jLabel7.setText(ostype);

                    jPanel3.setBounds(160, 10, 1200, 750);

                    sp2[i].setBounds(x, y, 140, 750);
                    jPanel3.add(sp2[i]);
                    repaint();
                    x = x + 150;

                }

            } catch (IOException ex) {

                ex.printStackTrace();
            }

        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(102, 0, 102));
        jPanel1.setLayout(null);

        jPanel3.setBackground(new java.awt.Color(255, 153, 153));
        jPanel3.setLayout(null);
        jPanel1.add(jPanel3);
        jPanel3.setBounds(160, 10, 1200, 750);

        jPanel2.setBackground(new java.awt.Color(153, 153, 255));
        jPanel2.setLayout(null);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/comp_1.jpg"))); // NOI18N
        jPanel2.add(jLabel1);
        jLabel1.setBounds(14, 20, 90, 80);

        jLabel2.setText("PC NAME");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(20, 180, 80, 20);

        jLabel3.setText("IP ADDRESS");
        jPanel2.add(jLabel3);
        jLabel3.setBounds(20, 280, 90, 20);

        jLabel4.setText("RAM");
        jPanel2.add(jLabel4);
        jLabel4.setBounds(20, 390, 80, 20);

        jLabel5.setText("CORES");
        jPanel2.add(jLabel5);
        jLabel5.setBounds(20, 480, 80, 20);

        jLabel6.setText("OS TYPE");
        jPanel2.add(jLabel6);
        jLabel6.setBounds(20, 570, 80, 20);

        jLabel7.setText("OS NAME");
        jPanel2.add(jLabel7);
        jLabel7.setBounds(20, 650, 90, 20);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(10, 10, 140, 750);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 1370, 770);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    // End of variables declaration//GEN-END:variables
}
