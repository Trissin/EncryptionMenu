import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;


// imports
public class MenuDriver extends JFrame implements ActionListener {

    MenuPanel menupanel = new MenuPanel();

    JMenuItem fileItem = new JMenuItem("Open");
    JMenuItem encryptItem = new JMenuItem ("Encrypt");
    JMenuItem decryptItem = new JMenuItem ("Decrypt");
    JMenuItem  helpItem= new JMenuItem ("Help");
    JMenuItem settingsItem = new JMenuItem ("Settings");

    public MenuDriver (String title) throws Exception {
        super(title);
        //===========================================================================================================================================
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        JMenu settingsMenu = new JMenu("Settings");
        JMenu helpMenu = new JMenu("Help");
        //===========================================================================================================================================
        fileItem.addActionListener(this);
        encryptItem.addActionListener(this);
        decryptItem.addActionListener(this);
        helpItem.addActionListener(this);
        settingsItem.addActionListener(this);
        //===========================================================================================================================================
        fileMenu.add(fileItem);
        fileMenu.add(encryptItem);
        fileMenu.add(decryptItem);
        editMenu.add(settingsMenu);
        helpMenu.add(helpItem);
        settingsMenu.add(settingsItem);
        //===========================================================================================================================================
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(settingsMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        this.getContentPane().add(menupanel);
        this.setBackground(new Color(53, 56, 64));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        // Organize frame properly
        this.setVisible(true);
    }

        //-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == fileItem) {
            openClicked();
        }
        else if (event.getSource() == encryptItem) {
            encryptClicked();
        }
        else if (event.getSource() == decryptItem) {
            decryptClicked();
        }
        else if (event.getSource() == helpItem) {
            helpClicked();
        }
        else if (event.getSource() == settingsItem) {
            settingsClicked();
        }
    }
    //===========================================================================================================================================

    public void openClicked(){
        System.out.println("menu open clicked");
    }

    public void encryptClicked(){
        System.out.println("menu encrypt clicked, calling method");
        try{
            menupanel.encryptFile();
        }
        catch (Exception e){
            System.out.println("Driver encryptClick error: " + e);
            e.getMessage();
            e.getStackTrace();
        }
    }

    public void decryptClicked(){
        System.out.println("menu decrypt clicked, calling method");
        try{
            menupanel.decryptFile();
        }
        catch (Exception e){
            System.out.println("Driver decryptClick error: " + e);
            e.getMessage();
            e.getStackTrace();
        }
    }

    public void helpClicked(){
        System.out.println("menu help clicked");
    }

    public void settingsClicked(){
        System.out.println("settings clicked");
    }

    public static void main(String[] args) throws Exception{ // main method
        MenuDriver frame = new MenuDriver("Encryption Program Test Graphics");
    }
}