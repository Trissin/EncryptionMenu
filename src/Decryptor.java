import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.util.Base64;
import java.io.*;
import java.io.File;
// import javax.xml.bind.DatatypeConverter;  // only for use with Java 8, does not work with Java 9


public class Decryptor {
    Scanner scanner = new Scanner(System.in);
    byte[] decodedKey;
    SecretKey originalKey;
    byte[] decryptedMessage;
    FileOutputStream fos;
    String filePath = "";
    String outputPath = "";
    String chosenName = "";
    String outputName = "";
    String readContents = "";  // string to hold user input
    String uKey = "";
    JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

    public Decryptor() throws Exception{
        System.out.println("Decryptor created.");
    }

    /*
    public void getFileLocation(){
        System.out.println("Please select the file you would like to decrypt.");
        // filePath is a string to hold the selected input path

        int returnValue = fileChooser.showOpenDialog(null);
        // int returnValue = jfc.showSaveDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            filePath = selectedFile.getAbsolutePath();
            System.out.println("You selected the file: " + filePath);
        }
        else {
            System.out.println("Error selecting file. Please try again.");
        }
    }


    public void getOutputLocation(){
        System.out.println("Please select a directory to save your decrypted file.");
        // outputPath is a string to hold the selected output path

        fileChooser.setDialogTitle("Choose a directory to save your file: ");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int returnValue = fileChooser.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            if (fileChooser.getSelectedFile().isDirectory()) {
                File selectedDirectory = fileChooser.getSelectedFile();
                outputPath = selectedDirectory.getAbsolutePath();
                System.out.println("You selected the directory: " + outputPath);
            }
            else {
                System.out.println("Error selecting directory. Please try again.");
            }
        }
    }
    */

    public void decrypt(String inputPath){
        if (inputPath != null && !inputPath.isEmpty()){
            System.out.println("Reading encrypted file...");
            File file = new File(inputPath);
            try {
                Scanner fileScanner = new Scanner(file).useDelimiter("\\s*\\|\\s*");
                readContents = fileScanner.next();
                uKey = fileScanner.next();
                fileScanner.close();

                System.out.println("Encrypted data found: " + readContents);
                System.out.println("Key found: " + uKey);

                System.out.println("Read complete.");
            }
            catch (IOException e){
                System.out.println("Decryptor exception while reading: " + e);
                e.getMessage();
                e.getStackTrace();
            }
            catch (Exception e){
                System.out.println("Decryptor file input exception: " + e);
                e.getMessage();
                e.printStackTrace();
            }
        }
    }

    public void print(String outputLocation){
        if (outputLocation != null && !outputLocation.isEmpty()){
            System.out.println("Please enter what you would like to name your file below.");
            chosenName = scanner.nextLine() + ".txt";
            outputName = outputLocation + "\\" + chosenName;

            System.out.println("Beginning print...");

            try {
                decodedKey = Base64.getDecoder().decode(uKey);
                originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
                Cipher ciphertext = Cipher.getInstance("AES/ECB/PKCS5Padding");
                ciphertext.init(Cipher.DECRYPT_MODE, originalKey);
                // System.out.println("Fetching decoder");
                byte[] decodedContents = Base64.getDecoder().decode(readContents.trim());

                // String base64file = Base64.getEncoder().encodeToString(fileData);
                // byte[] parsedContents = DatatypeConverter.parseBase64Binary(readContents);  // Only works with Java 8, not Java 9
                // System.out.println("Decoder retrieved! Continuing");
                decryptedMessage = ciphertext.doFinal(decodedContents);

                FileOutputStream fos = new FileOutputStream(outputName);
                fos.write(decryptedMessage);
                fos.close();

                System.out.println("Decryption print complete.");
            }
            catch (IOException e){
                System.out.println("Decryptor print IOException error: " + e);
                e.getMessage();
                e.getStackTrace();
            }
            catch (Exception e){
                System.out.println("Decryptor print error: " + e);
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
