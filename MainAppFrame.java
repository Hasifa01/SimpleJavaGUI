import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;


@SuppressWarnings("serial")
public class MainAppFrame extends JFrame {

    private JPanel contentPane;
    File targetFile;
    BufferedImage targetImg;
    public JPanel panel,panel_1;
    private Font font = new Font("monospaced",Font.BOLD,25);
    private static final int baseSize = 600;
    private static final String basePath ="/home/ankitz/Desktop";

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainAppFrame frame = new MainAppFrame();
                    frame.setVisible(true);
                    frame.setResizable(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public MainAppFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 1000);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(6, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        panel = new JPanel();
        panel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true));
        contentPane.add(panel, BorderLayout.CENTER);

        JButton btnBrowse = new JButton("Browse");
        btnBrowse.setFont(new Font("Arial", Font.PLAIN, 40));
        btnBrowse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                browseButtonActionPerformed(e);
            }
        });

        JLabel lblSelectTargetPicture = new JLabel("Select target picture..");
		lblSelectTargetPicture.setFont(new Font("Arial", Font.PLAIN, 40));
        JButton btnDetect = new JButton("Detect");
        btnDetect.setFont(new Font("Arial", Font.PLAIN, 40));
        btnDetect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });

        /*
        JButton btnAddDigit = new JButton("Add Digit");
        btnAddDigit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        */

        JButton button = new JButton("Exit");
        button.setFont(new Font("Arial", Font.PLAIN, 40));
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            //terminate(e);
            System.exit(0);
            }
        });

        panel_1 = new JPanel(); //The Smaller Panel
        panel_1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
            gl_panel.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel.createSequentialGroup()
                    .addGap(100)
                    .addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel.createSequentialGroup()
                            .addComponent(lblSelectTargetPicture)
                            .addGap(100)
                            .addComponent(btnBrowse))
                        .addGroup(gl_panel.createSequentialGroup()
                            .addGap(300) //for the detect button
                            .addComponent(btnDetect)
                            //.addGap(18)
                            //.addComponent(btnAddDigit)
                            )))
                .addGroup(gl_panel.createSequentialGroup()
                    .addGap(430) //for Exit button
                    .addComponent(button))
                .addGroup(gl_panel.createSequentialGroup()
                    .addContainerGap()
                    .addContainerGap()
                    .addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 600, GroupLayout.DEFAULT_SIZE))
        ); // Width ke liye
        gl_panel.setVerticalGroup(
            gl_panel.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel.createSequentialGroup()
                    .addGroup(gl_panel.createParallelGroup(Alignment.CENTER)
                        .addGroup(gl_panel.createSequentialGroup()
                            .addGap(7)
                            .addComponent(lblSelectTargetPicture))
                        .addGroup(gl_panel.createSequentialGroup()
                            .addGap(3)
                            .addComponent(btnBrowse)))
                    .addGap(18) //Smaller panel ke upar ka gap
                    .addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 600, GroupLayout.DEFAULT_SIZE) //Height ke liye
                    .addGap(22) //Smaller Panel ke neeche ka gap
                    .addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
                        .addComponent(btnDetect)
                        //.addComponent(btnAddDigit)
                        )
                    .addGap(18)
                    .addComponent(button)
                    .addContainerGap())
        );

        panel.setLayout(gl_panel);
    }
    public BufferedImage rescale(BufferedImage originalImage)
    {
        BufferedImage resizedImage = new BufferedImage(baseSize, baseSize, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, baseSize, baseSize, null);
        g.dispose();
        return resizedImage;
    }
    public void setTarget(File reference)
    {
        try {
            targetFile = reference;
            targetImg = rescale(ImageIO.read(reference));
        } catch (IOException ex) {
            Logger.getLogger(MainAppFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        panel_1.setLayout(new BorderLayout(0, 0));
        panel_1.add(new JLabel(new ImageIcon(targetImg))); 
        setVisible(true);
    }
    private void browseButtonActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser fc = new JFileChooser(basePath);
        fc.setPreferredSize(new Dimension(800, 600));
        
        setFileChooserFont(fc.getComponents());  
        
        fc.setFileFilter(new JPEGImageFileFilter());
        int res = fc.showOpenDialog(null);
        // We have an image!
        try {
            if (res == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                setTarget(file);
            } // Oops!
            else {
                JOptionPane.showMessageDialog(null,
                        "You must select one image to be the reference.", "Aborting...",
                        JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception iOException) {
        }

    }
    private void setFileChooserFont(Component[] comp)
    {  
      for(int x = 0; x < comp.length; x++)  
      {  
        if(comp[x] instanceof Container) setFileChooserFont(((Container)comp[x]).getComponents());  
        try{comp[x].setFont(font);}  
        catch(Exception e){}//do nothing  
      }  
    }  
    /*
    private void terminate(java.awt.event.ActionEvent evt){
    System.exit(0);
    }
    */
}



