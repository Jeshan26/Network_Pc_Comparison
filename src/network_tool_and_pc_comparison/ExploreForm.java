
package network_tool_and_pc_comparison;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


public class ExploreForm extends javax.swing.JFrame {

    String request = "";
    ArrayList<PcInfo> pcInfoList;
     int width;
     int height;
    

    public ExploreForm(ArrayList<PcInfo> pcInfoList) {
        initComponents();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int)screenSize.getWidth();
         height = (int)screenSize.getHeight();

        setSize((int) width, (int) height);
        setTitle("ExploreForm");

        double w1 = 0.8 * width;
        double w2 = 0.2 * width;

        jPanel1.setBounds(0, 0, (int) w1, (int) height);
        jPanel2.setBounds((int) w1, 0, (int) w2, (int) height);

        this.pcInfoList = pcInfoList;

        SinglePanel sp[] = new SinglePanel[pcInfoList.size()];
        int x = 50;
        int y = 50;
        for (int i = 0; i < pcInfoList.size(); i++) {
            sp[i] = new SinglePanel();
            sp[i].hostip.setText(pcInfoList.get(i).ip);
            sp[i].hostname.setText(pcInfoList.get(i).pcName);
            sp[i].setBounds(x, y, 160, 155);
            jPanel1.add(sp[i]);

            x = x + 165;
            if (x > 550) {
                x = 50;
                y = 210;
            }
           final String ipAddress = pcInfoList.get(i).ip;
            sp[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {

                    if (e.getClickCount() == 2) {
                        request = "send configuration";
                        new Thread(new Client(ipAddress)).start();
                    } else if (e.getButton() == MouseEvent.BUTTON3) {
                        PopupMenu popup = new PopupMenu();

                        MenuItem item1 = new MenuItem("Shut Down");
                        MenuItem item2 = new MenuItem("Restart");
                        MenuItem item3 = new MenuItem("Log Off");
                        MenuItem item4 = new MenuItem("View Screen");
                        popup.add(item1);
                        popup.add(item2);
                        popup.add(item3);
                        popup.add(item4);
                        add(popup);
                        popup.show(ExploreForm.this, e.getXOnScreen(), e.getYOnScreen());

                        item1.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                request = "Shut Down";
                                Client c = new Client(ipAddress);
                                Thread t = new Thread(c);
                                t.start();

                            }

                        });

                        item2.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                request = "Restart";
                                Client c = new Client(ipAddress);
                                Thread t = new Thread(c);
                                t.start();

                            }

                        });

                        item3.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                request = "Log Off";
                                Client c = new Client(ipAddress);
                                Thread t = new Thread(c);
                                t.start();

                            }
                        });

                        item4.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                request = "View Screen";
                                Client c = new Client(ipAddress);
                                Thread t = new Thread(c);
                                t.start();

                            }
                        });
                    }
                }
            });
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
                System.out.println(ip);

                String pcName = sock.getInetAddress().getHostName();

                DataInputStream dis = new DataInputStream(sock.getInputStream());
                DataOutputStream dos = new DataOutputStream(sock.getOutputStream());

                if (request.equals("send configuration")) {
                    dos.writeBytes("SendConfiguration\r\n");
                    String ip = dis.readLine();
                    String Pcname = dis.readLine();
                    String ostype = dis.readLine();
                    String osname = dis.readLine();
                    String cores = dis.readLine();
                    String memory = dis.readLine();
                    jLabel8.setText(Pcname);
                    jLabel9.setText(ip);
                    jLabel10.setText(memory);
                    jLabel11.setText(cores);
                    jLabel12.setText(osname);
                    jLabel13.setText(ostype);

                } else if (request.equals("Shut Down")) {
                    dos.writeBytes("ShutDownPerformed\r\n");

                } else if (request.equals("Restart")) {
                    dos.writeBytes("RestartPerformed\r\n");

                } else if (request.equals("Log Off")) {
                    dos.writeBytes("LogOffPerformed\r\n");

                } else if (request.equals("View Screen")) {
                   dos.writeBytes("ViewScreenPerformed\r\n");
                   int w=  dis.readInt();
                   int h= dis.readInt();
                   ViewScreen vs =new ViewScreen(ip);
                   vs.setSize((int)w,(int)h);
                   vs.setVisible(true);
                   vs.jScrollPane1.setSize(width,height);
                   vs.jLabel1.setSize(w,h);
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
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(153, 153, 255));
        jPanel1.setLayout(null);
        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 530, 500);

        jPanel2.setBackground(new java.awt.Color(255, 204, 204));
        jPanel2.setLayout(null);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/comp_1.jpg"))); // NOI18N
        jLabel1.setText("jLabel1");
        jPanel2.add(jLabel1);
        jLabel1.setBounds(90, 0, 70, 80);

        jLabel2.setText("NAME");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(10, 130, 60, 20);

        jLabel3.setText("IP");
        jPanel2.add(jLabel3);
        jLabel3.setBounds(10, 180, 40, 14);

        jLabel4.setText("RAM");
        jPanel2.add(jLabel4);
        jLabel4.setBounds(10, 220, 70, 20);

        jLabel5.setText("CORES");
        jPanel2.add(jLabel5);
        jLabel5.setBounds(10, 260, 60, 20);

        jLabel6.setText("Os Type");
        jPanel2.add(jLabel6);
        jLabel6.setBounds(10, 290, 70, 20);

        jLabel7.setText("Os Name");
        jPanel2.add(jLabel7);
        jLabel7.setBounds(10, 330, 80, 20);

        jLabel8.setText("jLabel8");
        jPanel2.add(jLabel8);
        jLabel8.setBounds(100, 130, 120, 14);

        jLabel9.setText("jLabel9");
        jPanel2.add(jLabel9);
        jLabel9.setBounds(100, 180, 100, 14);

        jLabel10.setText("jLabel10");
        jPanel2.add(jLabel10);
        jLabel10.setBounds(110, 220, 90, 14);

        jLabel11.setText("jLabel11");
        jPanel2.add(jLabel11);
        jLabel11.setBounds(110, 260, 100, 14);

        jLabel12.setText("jLabel12");
        jPanel2.add(jLabel12);
        jLabel12.setBounds(110, 290, 90, 20);

        jLabel13.setText("jLabel13");
        jPanel2.add(jLabel13);
        jLabel13.setBounds(110, 330, 90, 14);

        jButton1.setText("Export To Excel");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1);
        jButton1.setBounds(30, 390, 160, 40);

        getContentPane().add(jPanel2);
        jPanel2.setBounds(530, 0, 280, 500);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jLabel8.getText();
        jLabel9.getText();
        jLabel10.getText();
        jLabel11.getText();
        jLabel12.getText();
        jLabel13.getText();

        try {

            File f1 = new File(System.getProperty("user.home") + "\\Controller Files");
            if (!f1.exists()) {
                f1.mkdir();
            }
            File f = new File(System.getProperty("user.home") + "\\Controller Files\\" + jLabel8.getText() + ".xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("FirstSheet");

            HSSFRow row0 = sheet.createRow((short) 0);
            row0.createCell(0).setCellValue("Ip address : ");
            row0.createCell(1).setCellValue(jLabel8.getText());

            HSSFRow row1 = sheet.createRow((short) 1);
            row1.createCell(0).setCellValue("Pcname : ");
            row1.createCell(1).setCellValue(jLabel9.getText());

            HSSFRow row2 = sheet.createRow((short) 2);
            row2.createCell(0).setCellValue("NameOS : ");
            row2.createCell(1).setCellValue(jLabel10.getText());

            HSSFRow row3 = sheet.createRow((short) 3);
            row3.createCell(0).setCellValue("No of Processor");
            row3.createCell(1).setCellValue(jLabel11.getText());
            HSSFRow row4 = sheet.createRow((short) 4);
            row4.createCell(0).setCellValue("Processor");
            row4.createCell(1).setCellValue(jLabel12.getText());

            HSSFRow row5 = sheet.createRow((short) 5);
            row5.createCell(0).setCellValue("FreeMemory");
            row5.createCell(1).setCellValue(jLabel13.getText());        

            FileOutputStream fileOut = new FileOutputStream(f);
            workbook.write(fileOut);
            fileOut.close();
            //System.out.println("Your excel file has been generated!");
            int r = JOptionPane.showConfirmDialog(rootPane, "Excel File has been generated!!!!!!!\n Do you want to open? ");
            if (r == JOptionPane.YES_OPTION) {
                Desktop.getDesktop().open(f);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }//GEN-LAST:event_jButton1ActionPerformed


    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ExploreForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ExploreForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ExploreForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ExploreForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
