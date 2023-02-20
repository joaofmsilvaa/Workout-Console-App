import java.util.Scanner;
import java.sql.*;

public class Log_in_admin
{
    //Method to display admin options
    public static void admin (Connection conn,int user_id,String name,String Originalpassword)
    {
        boolean goBackMenu2 = false;

        System.out.print('\u000C'); //Clear the console
        System.out.println("Welcome to the training app " + name);
        System.out.println("(1) List data              ");
        System.out.println("(2) Remove data            ");
        System.out.println("(3) Insert data            ");
        System.out.println("(4) Add data to Sets or Plans");
        System.out.println("(5) Remove data from Sets or Plans");
        System.out.println("(6) Update data            ");
        System.out.println("");
        System.out.println("(E) Exit                   ");

        /*
         * Loop until goBackMenu1 is true
         */
        while(!goBackMenu2)
        {
            System.out.println("Option:");
            Scanner sc1 = new Scanner(System.in);
            String option = sc1.nextLine();

            // If "option" is 1, go to the data menu
            if(option.equals("1"))
            {
                goBackMenu2 = true;
                select.selectMenu(conn,user_id,name,Originalpassword);

            }
            // If "option" is 2, go to the delete menu
            else if(option.equals("2"))
            {
                goBackMenu2 = true;
                delete.deleteMenu(conn,user_id,name,Originalpassword);

            }

            // If "option" is 3, go to the insert menu
            else if(option.equals("3")){
                goBackMenu2 = true;
                insert.insertMenu(conn,user_id,name,Originalpassword);

            }

            // If "option" is 4, go to the add menu
            else if(option.equals("4")){
                goBackMenu2 = true;
                add.addMenu(conn,user_id,name,Originalpassword);

            }

            // If "option" is 5, go to the remove menu
            else if(option.equals("5")){
                goBackMenu2 = true;
                remove.removeMenu(conn,user_id,name,Originalpassword);
            }

            // If "option" is 6, go to the update menu
            else if(option.equals("6")){
                goBackMenu2 = true;
                update.updateMenu(conn,user_id,name,Originalpassword);
            }

            // If "option" is "E" or "e", closes the program
            else if(option.equals("E") || option.equals("e"))
            {

                goBackMenu2 = true;
                System.out.println("Closing program...");

            }
        }
    }
}
