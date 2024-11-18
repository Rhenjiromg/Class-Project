package client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Operator {
    private int counter = 0;
    private String folderPath = "";

    public void Login(String Password, String Credentials) {
        String toPass = getUserPassword(Credentials);
        /**
         * if account exists, and password matches, and less than 3 attempts
         */
        if (!toPass.equals("") && toPass.equals(Password) && counter <= 3) {
            counter = 0;
            /**
             * Verified code here
             */
        } else {
            if (!toPass.equals("")) {
                counter++;
            }
        }
    }

    private boolean isSuperUser(String UserNumber) {
        /**
         * Super user is determined by: UserNumber starting with 1 instead of 0
         */
        return UserNumber.charAt(0) == '0';
    }

    private String getUserPassword(String credentials) {
        String fileName = credentials + ".txt";
        Path filePath = Paths.get(folderPath, fileName);

        // Check if the file exists and does comparison
        if (Files.exists(filePath) && Files.isRegularFile(filePath)) {
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.trim().startsWith("password : ")) {
                        int index = line.indexOf("\"");
                        if (index != -1) {
                            return line.substring(index + 1, line.lastIndexOf("\""));
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading the file: " + e.getMessage());
            }
        } else {
            System.out.println("File not found: " + filePath);
        }
        return "";
    }
}
