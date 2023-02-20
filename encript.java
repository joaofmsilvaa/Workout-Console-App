import java.util.Random;
// Java program to encrypt a password using asterisks (*) 
public class encript {

    // Method to encrypt the password
    public static String[] password_Encript(String originalPassword){
        String[] passwords = new String[2];

        // String variable to store the encrypted password
        String encriptedPassword = "";

        // Length of the original password
        int passwordSize = originalPassword.length();

        // Create a random object to generate asterisks
        Random rand = new Random();

        // Loop through each character of the password
        for(int index = 0; index < passwordSize; index++){
            // Replace each character with an asterisk (*)
            String asterisk = "*";
            encriptedPassword = encriptedPassword + asterisk;
        }

        // Store the original and encrypted password in the array
        passwords[0] = originalPassword;
        passwords[1] = encriptedPassword;

        // Return the array with both passwords
        return passwords;
    }
}

