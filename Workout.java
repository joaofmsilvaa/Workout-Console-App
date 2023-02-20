import java.util.Scanner;
import java.sql.*;

/**
 * Escreva uma descrição da classe Workout aqui.
 * 
 * @author (seu nome) 
 * @version (um número da versão ou uma data)
 */
public class Workout
{
    // This method implements the workout plan options for the user
    public static void WorkoutPlan(Connection conn,int user_id,String name,String goal,String weight,String height,String password) {
        workoutStatus(); // Displaying workout status

        boolean goBackTraining = false; // Flag to go back to the previous menu

        // Loop until the user decides to go back to the previous menu
        while(!goBackTraining) {

            System.out.println("Option:"); // Display option prompt
            Scanner optionSelector = new Scanner(System.in); 
            String option = optionSelector.nextLine();

            // If user inputs "E" or "e", go back to the previous menu
            if(option.equals("E") || option.equals("e")) {
                System.out.print('\u000C'); // Clearing the console
                goBackTraining = true; // Setting flag to go back to the previous menu
                Menus.menu1(conn, user_id, name, goal, weight, height, password); // Going back to the previous menu
            }

            // If user inputs a number between 1 and 7, call the training method with the selected option
            else if(option.equals("1") || option.equals("2") || option.equals("3") || option.equals("4") || option.equals("5") || option.equals("6") || option.equals("7")) {
                int optionValue = Integer.parseInt(option); // Converting the user input string to integer
                training(user_id, name, goal, weight, height, password, optionValue); // Calling the training method with the selected option
                break; // Breaking out of the loop
            }
            else {
                System.out.println("Invalid input"); // Display error message for invalid input
            }
        }
    }

    public static void workoutStatus(){
        String[] workoutStatus = new String[7];

        System.out.println("Workout list for the week:");

        System.out.println("(1) Monday");
        System.out.println("(2) Tuesday");
        System.out.println("(3) Wednesday" );
        System.out.println("(4) Thursday");
        System.out.println("(5) Friday");
        System.out.println("(6) Saturday (Rest)");
        System.out.println("(7) Sunday (Rest)");
        System.out.println("");
        System.out.println("(E) Exit");
    }

    // This method displays the workout plan for the selected day
    public static void training(int user_id, String name, String goal, String weight, String height, String password, int id_conjunto) {
        System.out.print('\u000C'); // clears the console

        try {
            // Connect to the database
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/training_app", "root", "");

            int id_treino = 0;

            // Check if the selected day is 7, and if so, set id_conjunto to 1
            if (id_conjunto == 7) {
                id_conjunto = 1;
            } else {
                id_conjunto = id_conjunto + 1;
            }

            // Check if the user's goal is to gain mass, and set id_treino accordingly
            if (goal == "ganhar massa") {
                id_treino = 2;
            } else {
                id_treino = 1;
            }

            // Get a statement from the connection
            Statement stmt = conn.createStatement();

            // SELECT THE NAME, REPS, AND TIME OF THE EXERCISE THAT CORRESPONDS TO BOTH THE DAY INDICATED BY THE USER AND THE TRAINING PLAN
            ResultSet rs = stmt.executeQuery("SELECT exercicios.nome, conjunto_exercicios.repeticoes , conjunto_exercicios.tempo " +
                    "FROM exercicios,conjunto_exercicios,plano_treino " +
                    "WHERE conjunto_exercicios.id_exercicio = exercicios.id_exercicio " +
                    "AND conjunto_exercicios.id_conjunto = " + id_conjunto +
                    " AND plano_treino.id_treino = " + id_treino +
                    " AND plano_treino.id_conjunto = conjunto_exercicios.id_conjunto");

            stmt = conn.createStatement();
            String space = "    ";
            // Loop through the result set
            while (rs.next()) {
                for (int i = 1; i < 4; i++) {
                    // Check if the value is null and print an empty string, otherwise print the value
                    if (rs.getString(i) == null) {
                        System.out.print("");
                    } else {
                        System.out.print(rs.getString(i) + space);
                    }
                }
                System.out.println();
            }

            // Prompt the user to exit the menu
            System.out.println("If you want to exit this menu click \"E\"");
            Scanner optionSelector = new Scanner(System.in);
            String answer1 = optionSelector.nextLine();

            // If the user selects "E" or "e", go back to the WorkoutPlan menu
            if (answer1.equals("E") || answer1.equals("e")) {
                System.out.print('\u000C');
                Boolean goBackTrainingMon = true; // Closes the loop
                WorkoutPlan(conn,user_id,name,goal,weight,height,password); // Call the WorkoutPlan method
                
                // Close the result set, statement and the connection
                rs.close(); 
                stmt.close();
                conn.close();

            } 

        }
        
        catch( SQLException se ){
            System.out.println( "SQL Exception:" ); // Print an error message

            // Loop through the SQL Exceptions
            while( se != null )
            {
                System.out.println( "State  : " + se.getSQLState()  ); // Print the state of the exception
                System.out.println( "Message: " + se.getMessage()   ); // Print the message of the exception
                System.out.println( "Error  : " + se.getErrorCode() ); // Print the error code of the exception

                se = se.getNextException(); // Get the next exception if there is one
            }
        }
    }
}
