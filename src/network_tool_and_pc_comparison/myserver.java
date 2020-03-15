package network_tool_and_pc_comparison;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.imageio.ImageIO;

public class myserver extends javax.swing.JFrame {

    public myserver() {
        initComponents();
        getContentPane().setBackground(Color.BLACK);
        setSize(500, 500);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 102, 102));
        jButton1.setText("START SERVER");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(190, 180, 180, 90);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new Thread(new Server()).start();
        new Thread(new PhotoServer()).start();
        jButton1.setEnabled(false);
    }//GEN-LAST:event_jButton1ActionPerformed
    public class Server implements Runnable {

        ServerSocket sersock;
        Socket sock;

        @Override
        public void run() {

            try {

                sersock = new ServerSocket(9000);
                System.out.println("Server Started!!");

                while (true) {

                    sock = sersock.accept();
                    System.out.println("Connection Accepted!!");

                    ClientHandler clientHandler = new ClientHandler(sock);
                    Thread t = new Thread(clientHandler);
                    t.start();

                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

    }

    public class ClientHandler implements Runnable {

        Socket sock;
        DataInputStream dis;
        DataOutputStream dos;

        ClientHandler(Socket sock) {
            this.sock = sock;
        }

        @Override
        public void run() {

            try {

                dis = new DataInputStream(sock.getInputStream());
                dos = new DataOutputStream(sock.getOutputStream());
                Robot r = new Robot();
                while (true) {
                    String message = dis.readLine();
                    System.out.println(message);
                    if (message.equals("Hello Server")) {
                        dos.writeBytes("Hello Client\r\n");
                        break;
                    } else if (message.equals("SendConfiguration")) {
                        InetAddress ip;
                        try {

                            ip = InetAddress.getLocalHost();

                            dos.writeBytes(ip.getHostAddress() + "\r\n");
                            dos.writeBytes(ip.getHostName() + "\r\n");
                            String nameOS = System.getProperty("os.name");
                            dos.writeBytes(nameOS + "\r\n");
                            String osType = System.getProperty("os.arch");
                            dos.writeBytes(osType + "\r\n");

                            dos.writeBytes(Runtime.getRuntime().availableProcessors() + "\r\n");

                            double memorySize = ((com.sun.management.OperatingSystemMXBean) ManagementFactory
                                    .getOperatingSystemMXBean()).getTotalPhysicalMemorySize();
                            dos.writeBytes((memorySize / (1024 * 1024 * 1024)) + "\r\n");

                            break;

                        } catch (UnknownHostException e) {

                            e.printStackTrace();
                        }
                    } else if (message.equals("ShutDownPerformed")) {
                        Runtime runtime = Runtime.getRuntime();
                        Process proc = runtime.exec("shutdown -s -t 0");
                        System.exit(0);
                    } else if (message.equals("RestartPerformed")) {

                        Runtime rt = Runtime.getRuntime();
                        Process proc = rt.exec("cmd /c shutdown -r");
                    } else if (message.equals("LogOffPerformed")) {

                        Runtime runtime = Runtime.getRuntime();
                        Process proc = runtime.exec("cmd /c shutdown -l");
                    } else if (message.equals("ViewScreenPerformed")) {
                        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                        double width = screenSize.getWidth();
                        double height = screenSize.getHeight();

                        dos.writeInt((int) width);
                        dos.writeInt((int) height);

                    } else if (message.equals("mousemove")) {
                        int x = dis.readInt();
                        int y = dis.readInt();
                        r.mouseMove(x, y);
                    } else if (message.equals("mousedragged")) {
                        int x = dis.readInt();
                        int y = dis.readInt();
                        r.mousePress(MouseEvent.BUTTON1_MASK);
                        r.mouseMove(x, y);
                    } else if (message.equals("mousereleased")) {
                        
                        int x = dis.readInt();
                        int y = dis.readInt();
                        r.mouseMove(x, y);
                        r.mouseRelease(MouseEvent.BUTTON1_MASK);
                        
                    }
                    else if (message.equals("mouseclicked")) {
                        int x = dis.readInt();
                        int y = dis.readInt();
                        int b=dis.readInt();
                        if(b==1){
                            r.mousePress(MouseEvent.BUTTON1_MASK);
                            r.mouseRelease(MouseEvent.BUTTON1_MASK);
                            
                        }
                        else if(b==2){
                        r.mousePress(MouseEvent.BUTTON2_MASK);
                        r.mouseRelease(MouseEvent.BUTTON2_MASK);
                        }
                        else{
                        r.mousePress(MouseEvent.BUTTON3_MASK);
                        r.mouseRelease(MouseEvent.BUTTON3_MASK);
                            
                    }
                    }
                    else if(message.equals("keypressed")){
                        
                        int keyCode = dis.readInt();
                        r.keyPress(keyCode);
                        r.keyRelease(keyCode);
                        
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();

            }

        }

    }

    class PhotoServer implements Runnable {

        @Override
        public void run() {

            ServerSocket sersock1;
            Socket sock;

            try {

                sersock1 = new ServerSocket(8000);
                System.out.println("Photo Server Started!!");

                while (true) {

                    sock = sersock1.accept();
                    System.out.println("Connection Accepted!!");

                    PhotoClientHandler PclientHandler = new PhotoClientHandler(sock);
                    Thread t = new Thread(PclientHandler);
                    t.start();

                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

    }

    class PhotoClientHandler implements Runnable {

        Socket sock1;
        DataInputStream Pdis;
        DataOutputStream Pdos;

        PhotoClientHandler(Socket sock1) {
            this.sock1 = sock1;
        }

        @Override
        public void run() {

            try {

                Pdis = new DataInputStream(sock1.getInputStream());
                Pdos = new DataOutputStream(sock1.getOutputStream());

                while (true) {
                    String message = Pdis.readLine();
                    System.out.println(message + "---------");

                    Robot r = new Robot();

                    // It saves screenshot to desired path                         
                    String path = "Screenshot.jpg";

                    // Used to get ScreenSize and capture image 
                    Rectangle capture
                            = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
                    BufferedImage Image = r.createScreenCapture(capture);
                    ImageIO.write(Image, "jpg", new File(path));
                    System.out.println("Screenshot saved");

                    File f = new File("Screenshot.jpg");
                    long size = f.length();
                    System.out.println(size + " size of file");
                    Pdos.writeLong(size);
                    FileInputStream Pfis = new FileInputStream(f);
                    byte b[] = new byte[1000];
                    int count = 0;
                    while (true) {
                        int r1 = Pfis.read(b, 0, 1000);
                        count = count + r1;
                        Pdos.write(b, 0, r1);
                        if (count == size) {
                            break;
                        }

                    }

                    System.out.println(Pdis.readLine());
                    Thread.sleep(120);
                }

            } catch (Exception e) {

            }
        }

    }

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
            java.util.logging.Logger.getLogger(myserver.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(myserver.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(myserver.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(myserver.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new myserver().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    // End of variables declaration//GEN-END:variables
}
