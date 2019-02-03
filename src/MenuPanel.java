import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.filechooser.FileSystemView;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.*;
import java.io.File;
import javax.swing.filechooser.FileSystemView;

public class MenuPanel extends JPanel{
    Encryptor encryptor = new Encryptor();
    Decryptor decryptor = new Decryptor();

    JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

    String filePath = "";
    String outputPath = "";

    public MenuPanel() throws Exception{
        System.out.println("MenuPanel constructed");
        this.setPreferredSize(new Dimension(600,400));
        //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
        JButton eButton = new JButton ("Encrypt");
        //eButton.setPreferredSize(new Dimension(200, 100));
        eButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent pressEncrypt){
                System.out.println("Encrypt button pressed.");
                try {
                    encryptFile();
                }
                catch (Exception e){
                    //
                }
            }
        });
        this.add(eButton, FlowLayout.LEFT);

        JButton dButton = new JButton ("Decrypt");
        //dButton.setPreferredSize(new Dimension(200, 100));
        dButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent pressDecrypt){
                System.out.println("Decrypt button pressed.");
                try {
                    decryptFile();
                }
                catch (Exception e){
                    //
                }
            }
        });
        this.add(dButton, FlowLayout.CENTER);

        JButton lButton = new JButton ("Library");
        //lButton.setPreferredSize(new Dimension(200, 100));
        lButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent pressLibrary){
                System.out.println("Library button pressed.");
                accessLibrary();
                // when this button is pressed, the menu frame should remove menupanel and add a different panel called encryptionpanel
            }
        });
        this.add(lButton, FlowLayout.RIGHT);

        JButton qButton = new JButton ("Quit");
        //lButton.setPreferredSize(new Dimension(200, 100));
        qButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent pressLibrary){
                System.exit(0);
            }
        });
        this.add(qButton, BorderLayout.SOUTH);
        //=================================================================================================================================================================
    }

    public String getFile(){
        filePath = "";
        System.out.println("Please select your file.");

        fileChooser.setDialogTitle("Choose a file: ");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int returnValue = fileChooser.showOpenDialog(null);
        // int returnValue = jfc.showSaveDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            filePath = selectedFile.getAbsolutePath();
            System.out.println("You selected the file: " + filePath);
        }
        else if (returnValue == JFileChooser.CANCEL_OPTION){
            System.out.println("File selection was cancelled by the user.");
        }
        return filePath;
    }

    public String getDirectory(){
        outputPath = "";
        System.out.println("Please select a directory to save your file.");

        fileChooser.setDialogTitle("Choose a directory: ");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int returnValue = fileChooser.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            if (fileChooser.getSelectedFile().isDirectory()) {
                File selectedDirectory = fileChooser.getSelectedFile();
                outputPath = selectedDirectory.getAbsolutePath();
                System.out.println("You selected the directory: " + outputPath);
            }
        }
        else if (returnValue == JFileChooser.CANCEL_OPTION){
            System.out.println("Directory selection was cancelled by the user.");
        }
        return outputPath;
    }

    public void encryptFile(){
        System.out.println("Panel encrypt method called");
        filePath = "";
        filePath = getFile();
        //showSelected();     // this method displays to the user the selected file, using paintcomponent to draw the name of the file and an icon of the file type
        JButton encryptButton = new JButton ("Encrypt File");     // a new jbutton is created, it is a second jbutton which the user must press to actually encrypt a file
        //dButton.setPreferredSize(new Dimension(200, 100));
        encryptButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent encryptPress) {
            System.out.println("Encrypt button pressed.");
            encryptor.encrypt(filePath);
            encryptor.print(getDirectory());
            }
        });

        this.add(encryptButton, BorderLayout.SOUTH);
    }

    public void decryptFile(){
        System.out.println("Panel decrypt method called");
        filePath = "";
        decryptor.decrypt(getFile());
        decryptor.print(getDirectory());
    }

    public void accessLibrary(){
        System.out.println("Library access method called");
    }

    // if encrypt button pressed, encryptor.getFileLocation(), encryptor.getOutputLocation(); encryptor.read(); and encryptor.print();

    public void draw (Graphics g){
        System.out.println("Panel draw method called");
        super.paintComponent(g);
        g.setColor(Color.black);
        g.fillRect(0,0,100,10);
        g.fillRect(100,100,800,800);
    }

    public void showSelected (Graphics g, String filePath){
        System.out.println("Drawing selected file");
        super.paintComponent(g);
        g.setColor(Color.black);
    }
}
