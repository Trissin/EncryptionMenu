import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.*;
import java.io.File;
import java.util.Base64;
// import javax.xml.bind.DatatypeConverter;   // only for use with Java 8, does not work with Java 9
// import org.apache.commons.codec.binary.Base64;

public class Encryptor {
    Scanner scanner = new Scanner(System.in);
    private String encodedKey;
    private byte[] encryptedMessage1;
    private byte[] encryptedMessage2;
    private byte[] encryptedMessage3;
    private String base64file = "";
    private String content = "";
    private byte[] bArray;
    Path fileLocation;
    private byte[] data;
    private byte[] fileData;
    String encodedData;
    private KeyGenerator keygenerator;
    private SecretKey key;
    // String filePath = "";
    String outputPath = "";
    String chosenName = "";
    String outputName = "";
    String outputName2 = "";
    String outputName3 = "";
    String writeContents1 = "";
    String writeContents2 = "";
    String writeContents3;
    Cipher ciphertext = Cipher.getInstance("AES/ECB/PKCS5PADDING");

    // JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

    public Encryptor() throws Exception{
        System.out.println("Encryptor created");
    }

    /*
    public String getFileLocation(){
        filePath = "";
        System.out.println("Please select the file you would like to encrypt.");
        // filePath is a string to hold the selected input path

        int returnValue = fileChooser.showOpenDialog(null);
        // int returnValue = jfc.showSaveDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            filePath = selectedFile.getAbsolutePath();
        }
        System.out.println("You selected the file path: " + filePath);
        return filePath;
    }



    public String getOutputLocation(){
        outputPath = "";
        System.out.println("Please select a directory to save your encrypted file.");
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
        }
        return outputPath;
    }
    */

    public void encrypt (String inputPath){
        if (inputPath != null && !inputPath.isEmpty()){
            base64file = "";

            System.out.println("Encrypting file...");
            fileLocation = Paths.get(inputPath);
            bArray = "initializer".getBytes();

            try {
                File selectedFile = new File(inputPath);
                try (FileInputStream fileContent = new FileInputStream(selectedFile)){
                    fileData = new byte[(int) selectedFile.length()];
                    fileContent.read(fileData);
                    base64file = Base64.getEncoder().encodeToString(fileData);
                }
                catch (IOException e){
                    System.out.println("Encryptor exception while reading: " + e);
                    e.getMessage();
                    e.getStackTrace();
                }
                catch (Exception e){
                    System.out.println("Encryptor file input exception: " + e);
                    e.getMessage();
                    e.printStackTrace();
                }


                data = Files.readAllBytes(fileLocation);

                keygenerator = KeyGenerator.getInstance("AES");
                keygenerator.init(256);

                key = keygenerator.generateKey();
                encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());

                ciphertext.init(Cipher.ENCRYPT_MODE, key);

                encryptedMessage1 = ciphertext.doFinal(data);
                encryptedMessage2 = ciphertext.doFinal(fileData);


                // Base64 base64 = new Base64;
                // String testContents = DatatypeConverter.printBase64Binary(encryptedMessage);  // Only works with Java 8, not 9

                writeContents1 = Base64.getEncoder().encodeToString(encryptedMessage1); // incorrect ending byte at 30124
                writeContents2 = Base64.getEncoder().encodeToString(ciphertext.doFinal(base64file.getBytes("UTF-8"))); // Illegal base64 character 5c
                writeContents3 = Base64.getEncoder().encodeToString(ciphertext.doFinal(Files.readAllBytes(fileLocation))); // Illegal base64 character 5c



                System.out.println("Read complete.");
            }
            catch (IOException e){
                System.out.println("IOException: " + e);
                e.getMessage();
                e.getStackTrace();
            }
            catch (Exception e){
                System.out.println("Exception: " + e);
                e.getMessage();
                e.getStackTrace();
            }
        }
    }

    public void print(String outputLocation){
        if (outputLocation != null && !outputLocation.isEmpty()){
            System.out.println("Please enter what you would like to name your file below.");
            chosenName = scanner.nextLine();
            outputName = outputLocation + "\\" + chosenName + ".txt";
            outputName2 = outputLocation + "\\" + chosenName + "2.txt";
            outputName3 = outputLocation + "\\" + chosenName + "3.txt";

            String utputName = outputLocation + "\\" + chosenName + "TEST.txt";


            System.out.println("Beginning print...");

            try
            {
                FileWriter writer = new FileWriter(outputName);

                writer.write(writeContents1 + "\\|");
                writer.write(encodedKey);
                writer.close();


                FileWriter writer2 = new FileWriter(outputName2);

                writer2.write(writeContents2 + "\\|");
                writer2.write(encodedKey);
                writer2.close();

                FileWriter writer3 = new FileWriter(outputName3);

                writer3.write(writeContents3 + "\\|");
                writer3.write(encodedKey);
                writer3.close();

                writeContents3 = Base64.getEncoder().encodeToString(ciphertext.doFinal(Files.readAllBytes(fileLocation))); // Illegal base64 character 5c

                System.out.println(base64file);
                System.out.println(Base64.getEncoder().withoutPadding().encodeToString(ciphertext.doFinal(base64file.getBytes(StandardCharsets.UTF_8))));

                byte[] decodedContents = Base64.getDecoder().decode(writeContents3);
                ciphertext.init(Cipher.DECRYPT_MODE, key);
                byte[] decryptedMessage = ciphertext.doFinal(decodedContents);

                FileOutputStream fos = new FileOutputStream(utputName);
                fos.write(decryptedMessage);
                fos.close();


                System.out.println("Encryption print complete.");
            }
            catch (IOException e)
            {
                System.out.println("Encryptor print IOException error: " + e);
                e.getMessage();
                e.getStackTrace();
            }
            catch (Exception e){
                System.out.println("Encryptor print error: " + e);
                e.getMessage();
                e.printStackTrace();
            }
        }
    }
}
