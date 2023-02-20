import java.util.Scanner;
import java.sql.*;

public class Menus
{

    /**
     * Menu 1 main method - asks the user which option he wants to choose and
     * redirects the user to a method located in the data class or in the
     * workout class depending on the option
     * 
     * @param conn a connection to the database and the users credentials
     */
    public static void menu1 (Connection conn,int user_id,String name,String goal,String weight,String height,String Originalpassword)
    {
        String[] userinfos = searchUser.credentials(name, Originalpassword);

        // Welcome message
        System.out.print('\u000C');
        System.out.println("Welcome to the training app " + name);
        System.out.println("(1) Data                   ");
        System.out.println("(2) Workout plan           ");
        System.out.println("                           ");
        System.out.println("(E) Exit                   ");

        boolean goBackMenu1 = false;
        
        // Main menu loop
        while(!goBackMenu1)
        {

            // Prompt for option selection
            System.out.println("Option:");
            Scanner optionSelector = new Scanner(System.in);
            String option = optionSelector.nextLine();

            // Exit if option is E or e
            if(option.equals("E") || option.equals("e"))
            {
                System.out.println("Leaving...");
                break;
            }

            // Call DataMenu if option is 1
            else if(option.equals("1"))
            {
                System.out.print('\u000C');
                String[] passwords = encript.password_Encript(Originalpassword);

                goBackMenu1 = true;
                data.DataMenu(conn,user_id,name,goal,weight,height,passwords);

            }

            // Call WorkoutPlan if option is 2
            else if(option.equals("2")){

                System.out.print('\u000C');
                goBackMenu1 = true;
                Workout.WorkoutPlan(conn,user_id,name,goal,weight,height,Originalpassword);
            }
            // Invalid option
            else
            {
                System.out.print('\u000C');
                System.out.println("Invalid value");
            }
        }

    }

    public static void credits(){
        System.out.println("Instituto Politécnico de Beja");
        System.out.println("Escola Superior de Tecnologia e Gestão");
        System.out.println("Curso Técnico Superior Profissional de Tecnologias Web e Dispositivos Móveis");
        System.out.println("");
        System.out.println("Program created by:");
        System.out.println("João Santos");
        System.out.println("João Silva");
        System.out.println("Gonçalo Rosário");

    }
}