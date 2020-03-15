package network_tool_and_pc_comparison;

import java.awt.Button;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.event.MouseInputListener;

public class ViewScreen extends javax.swing.JFrame implements MouseMotionListener, MouseInputListener, KeyListener {

    String ip;

    DataInputStream dis;
    DataOutputStream dos;

    public ViewScreen(String ip) {
        initComponents();

        this.ip = ip;

        new Thread(new PhotoClient(ip)).start();
        new Thread(new Client(ip)).start();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        System.out.println("Mouse Dragged!!");

        try {

            dos.writeBytes("mousedragged\r\n");
            int x = e.getX();
            int y = e.getY();
            dos.writeInt(x);
            dos.writeInt(y);
        } catch (Exception ex) {

            ex.printStackTrace();
        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        try {
            int x = e.getX();
            int y = e.getY();
            dos.writeBytes("mousemove\r\n");
            dos.writeInt(x);
            dos.writeInt(y);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Mouse Clicked!!");
        try {
            int x = e.getX();

            int y = e.getY();
            dos.writeBytes("mouseclicked\r\n");
            int b = e.getButton();
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeInt(b);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("Key Released!!");
        try {
            dos.writeBytes("keypressed\r\n");
            int keyCode = e.getKeyCode();
            dos.writeInt(keyCode);
        } catch(Exception ex) {
            ex.printStackTrace();
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

                jLabel1.addMouseListener(ViewScreen.this);
                jLabel1.addMouseMotionListener(ViewScreen.this);
                ViewScreen.this.addKeyListener(ViewScreen.this);
                
                dos = new DataOutputStream(sock.getOutputStream());
                dis = new DataInputStream(sock.getInputStream());

                

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("Mouse Released!!");
        try {

            dos.writeBytes("mousereleased\r\n");          

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    class PhotoClient implements Runnable {

        String ip;

        PhotoClient(String ip) {
            this.ip = ip;
        }

        @Override
        public void run() {

            try {

                Socket sock = new Socket(ip, 8000);

                System.out.println("Connection Accepted!!");
                System.out.println(ip);

                DataInputStream pdis = new DataInputStream(sock.getInputStream());
                DataOutputStream pdos = new DataOutputStream(sock.getOutputStream());
                while (true) {

                    pdos.writeBytes("sendscreenshot\r\n");
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();

                    long size = pdis.readLong();
                    System.out.println(size + " size from server!!");
                    byte b[] = new byte[1000];
                    int count = 0;
                    while (true) {
                        int r = pdis.read(b, 0, 1000);
                        count = count + r;
                        baos.write(b, 0, r);
                        if (count == size) {
                            break;
                        }

                    }

                    BufferedImage bi = ImageIO.read(new ByteArrayInputStream(baos.toByteArray()));

                    jLabel1.setIcon(new ImageIcon(bi));

                    pdos.writeBytes("screenshotrecieved\r\n");
                    Thread.sleep(120);

                }

            } catch (Exception e) {
                e.printStackTrace();

            }

        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jLabel1.setText("jLabel1");
        jScrollPane1.setViewportView(jLabel1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(0, 0, 760, 560);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel jLabel1;
    public javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
