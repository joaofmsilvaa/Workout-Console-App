import java.util.Scanner;
import java.sql.*;
import java.util.Random;

public class data
{
    // Method to display user data and options to edit or exit
    public static void DataMenu(Connection conn, int user_id, String name, String goal, String weight, String height, String[] password) {
        // Print user data
        System.out.println("User ID: " + user_id);
        System.out.println("Username: " + name);
        System.out.println("Goal: " + goal);
        System.out.println("Weight: " + weight + " kg");
        System.out.println("Height: " + height + " m");
        System.out.println("Password: " + password[1]);
        System.out.println();

        // Options to edit information or exit
        System.out.println("(1) Edit information");
        System.out.println("(E) Exit");
        System.out.println();

        // Controls the loop
        boolean goBackDados = false;

        // Loop to get user input
        while (!goBackDados) {
            System.out.println("Option:");
            Scanner optionSelector = new Scanner(System.in);
            String optionDados = optionSelector.nextLine();

            // If user chooses to exit
            if (optionDados.equals("E") || optionDados.equals("e")) {
                goBackDados = true;
                Menus.menu1(conn, user_id, name, goal, weight, height, password[0]);
            } 
            // If user chooses to edit information
            else if (optionDados.equals("1")) {
                goBackDados = true;   
                editData(conn, user_id, name, goal, weight, height, password);
            } 
            // If invalid option
            else {
                System.out.println("Option is invalid");
            }
        }
    }

    public static void editData(Connection conn,int user_id, String name, String goal, String weight, String height, String[] password){
        // Declare an array of field names
        String[] fields = new String[5];
        fields[0] = "name";
        fields[1] = "goal";
        fields[2] = "weight";
        fields[3] = "height";
        fields[4] = "password";

        // Clear the terminal
        System.out.print('\u000C');
        System.out.println("(1) Username: " +name);
        System.out.println("(2) Goal: "+ goal);
        System.out.println("(3) Weight: "+ weight + " kg");
        System.out.println("(4) height: "+ height + " m");
        System.out.println("(5) Password: " + password[1]);
        System.out.println("");
        System.out.println("(E) Exit");

        // Flag to determine whether to exit the loop or not
        boolean goBackEdit = false;

        // Loop until the user wants to exit
        while(!goBackEdit)
        {
            String newData = "";
            System.out.println("Insert the field  number that you want to edit:");
            Scanner optionSelector = new Scanner(System.in);
            String optionEdit = optionSelector.nextLine();
            int fieldNum = 0;

            try{
                // Parse the selected field number
                fieldNum = Integer.parseInt(optionEdit); 

                System.out.println("Insert your current password:");
                Scanner passScanner = new Scanner(System.in);
                String givenPassword = passScanner.nextLine();

                // Check the user's password
                String passwordCheck = searchUser.querry(name,givenPassword);

                // If the password is correct
                if(passwordCheck.equals("1")){

                    System.out.println("Insert a new "+ fields[fieldNum - 1] +":");
                    Scanner editedValue = new Scanner(System.in);

                    // If the selected field is the goal field
                    if(fieldNum - 1 == 1){
                        // Change the goal field value
                        if(goal.equals("ganhar massa")){
                            goal = "Perder peso";
                            newData = goal;
                        }
                        else{
                            goal = "ganhar massa";
                            newData = goal;
                        }

                        // Set the flag to exit the loop
                        goBackEdit = true;
                        // Call the function to edit the goal field in the database
                        editInDB(user_id,name,goal,weight,height,password,2,newData);
                    }
                    else{

                        //The switch statement is used to determine the value of fieldNum
                        switch(fieldNum){
                                //Case 1: If fieldNum is 1, the name field will be updated
                            case 1:
                                boolean case1 = true;
                                while(case1){
                                    name = editedValue.nextLine(); //Reads the new value for name from user input
                                    newData = name; //The new data is stored in the newData variable

                                    String userAmmount = registerUser.userCheck(name); //Call the userCheck method with the new name and connection as arguments

                                    boolean nameCicle = true; 

                                    //The while loop runs until nameCicle is false
                                    while(nameCicle){
                                        goBackEdit = true;

                                        if(userAmmount.equals("0")){
                                            //If userAmmount is equal to 0, the name is unique and can be updated
                                            nameCicle = false;
                                            case1 = false;
                                            editInDB(user_id,name,weight,height,goal,password,1, name); //Call the editInDB method to update the name field
                                            break;
                                        }
                                        else{
                                            //If the name already exists, the user is prompted to enter a different name
                                            nameCicle = false;
                                            System.out.println("The given name already exists");
                                            System.out.println("Insert a different one");
                                            System.out.println();
                                            
                                        }
                                    }
                                }
                                
                                break;

                                //Case 3: If fieldNum is 3, the weight field will be updated
                            case 3:
                                weight = editedValue.nextLine(); //Reads the new value for weight from user input
                                newData = weight; //The new data is stored in the newData variable

                                goBackEdit = true;
                                editInDB(user_id,name,weight,height,goal,password,3,newData); //Call the editInDB method to update the weight field
                                break;

                                //Case 4: If fieldNum is 4, the height field will be updated
                            case 4:
                                height = editedValue.nextLine(); //Reads the new value for height from user input
                                newData = height; //The new data is stored in the newData variable

                                goBackEdit = true;
                                editInDB(user_id,name,weight,height,goal,password,4,newData); //Call the editInDB method to update the height field
                                break;

                                //Case 5: If fieldNum is 5, the password field will be updated
                            case 5:
                                password[0] = editedValue.nextLine(); //Reads the new value for password from user input
                                newData = password[0]; //The new data is stored in the newData variable

                                goBackEdit = true;
                                editInDB(user_id,name,weight,height,goal,password,5,newData); //Call the editInDB method to update the password field
                                break;
                        }

                    }
                }

                else{
                    System.out.println("Invalid password");
                }
            }

            // catch block for handling exceptions
            catch(Exception error){

                // Check if user entered "E" or "e"
                if(optionEdit.equals("E") || optionEdit.equals("e"))
                {
                    // Clear the console/terminal screen
                    System.out.print('\u000C');

                    // set goBackEdit flag to true
                    goBackEdit = true;

                    // Call the DataMenu function
                    DataMenu(conn,user_id,name,goal,weight,height,password);
                }

                // If user entered a different value than "E" or "e"
                else
                {   
                    // Print "Invalid option" message
                    System.out.println("Invalid option");
                }
            }
        }
    }

    public static String tableToEdit(int option){
        String table = "";

        switch(option){
            case 1:
                table = "nome";
                break;
            case 2:
                table = "objetivo";
                break;
            case 3:
                table = "peso";
                break;
            case 4:
                table = "altura";
                break;
            case 5:
                table = "passe";
                break;
        }

        return table;
    }
    // editInDB method updates the information of the user in the database. 
    public static void editInDB(int user_id, String name, String weight, String height, String goal,String[] password, int tableOption, String newData){
        try
        {
            // Connect to the database
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/training_app", "root", "" );

            // Create a Statement object for executing SQL statements
            Statement stmt = conn.createStatement();

            // Call tableToEdit method to get the table name
            String table = tableToEdit(tableOption);

            // Prepare an SQL statement for executing
            PreparedStatement prepared_statement = conn.prepareStatement("UPDATE utilizador SET "+table+" = ? where id_user= ?");
            prepared_statement.setString(1, newData);
            prepared_statement.setInt(2, user_id);

            // Execute the prepared statement
            int result_set = prepared_statement.executeUpdate();

            // Check if the data was updated successfully
            if (result_set > 0)
            {
                System.out.println("Data updated");
                DataMenu(conn,user_id,name,goal,weight,height,password);
            }
            else{
                System.out.println("Can't update the data");
                DataMenu(conn,user_id,name,goal,weight,height,password);
            }

            // Close the statement and the connection
            stmt.close();
            conn.close();

        }    
        catch(SQLException se){
            System.out.println("SQL Exception:");

            // Handle SQL exceptions
            while(se != null)
            {
                System.out.println("State  : " + se.getSQLState());
                System.out.println("Message: " + se.getMessage());
                System.out.println("Error  : " + se.getErrorCode());

                se = se.getNextException();
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}
