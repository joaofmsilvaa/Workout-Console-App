
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLWarning;
import java.util.Scanner;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class update
{

    static String ans;
    static Scanner input = new Scanner(System.in);

    public static void updateMenu(Connection conn,int user_id,String name,String Originalpassword){
        boolean goBackListing = false;
        String[] databaseFields = new String[4];

        databaseFields[0] = "utilizador";
        databaseFields[1] = "exercicios";
        databaseFields[2] = "conjunto_exercicios";
        databaseFields[3] = "plano_treino";

        System.out.println("(1) Edit users     ");
        System.out.println("(2) Edit exercises     ");
        System.out.println("(3) Edit exercise sets ");
        System.out.println("(4) Edit workout plans ");
        System.out.println("                         ");
        System.out.println("(E) Exit                 ");

        while(!goBackListing){
            System.out.println("Option:");
            Scanner optionScanner = new Scanner(System.in);
            String option = optionScanner.nextLine();

            int fieldNum = 0;

            if(option.equals("E") || option.equals("e")){
                goBackListing = true;
                System.out.print('\u000C');
                Log_in_admin.admin(conn,user_id,name,Originalpassword);
            }

            else if(option.equals("1") || option.equals("2") || option.equals("3") || option.equals("4")){
                fieldNum = Integer.parseInt(option); 

                if(fieldNum <= 4 && fieldNum > 0){
                    goBackListing = true;
                    System.out.print('\u000C');

                    main(conn,user_id,name,Originalpassword,databaseFields[fieldNum - 1]);
                }
                else{
                    System.out.println("The input does not correspond with any valid option");
                }

            }
            else{
                System.out.println("Invalid option");
            }
        }
    }

    public static void main(Connection conn,int user_id,String name,String Originalpassword, String table)
    {
        updateData(conn,user_id,name,Originalpassword,table);
        System.out.println("");
        System.out.println("If you wish to reload enter \"R\" if you wish to exit click \"E\"");
        ans = input.nextLine();
        boolean cicle = true;

        if(ans.equals("r") || ans.equals("R")|| ans.equals("e") || ans.equals("E")){
            while(ans.equals("r") || ans.equals("R")){

                updateData(conn,user_id,name,Originalpassword,table);
                System.out.println("");
                System.out.println("If you wish to reload enter \"R\" if you wish to exit click \"E\"");
                ans = input.nextLine();
            }
            while(ans.equals("e") || ans.equals("E")){
                ans = "";
                System.out.print('\u000C');
                Log_in_admin.admin(conn,user_id,name,Originalpassword);

            }
        }
    }

    public static void updateData(Connection conn,int user_id,String name,String Originalpassword, String table)
    {
        try
        {
            String[] data = select.tablesInfo(table);

            System.out.print('\u000C');
            Statement stmt = conn.createStatement();
            select.selectData(conn,table);

            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/training_app", "root", "");

            // Execute the query
            System.out.println("") ;
            int result_set = 0;

            if(table.equals("utilizador")){
                result_set = updateData("utilizador");
            }
            else if(table.equals("exercicios")){
                result_set = updateData("exercicios");
            }
            else if(table.equals("conjunto_exercicios")){
                result_set = updateData("conjunto_exercicios");
            }
            else if(table.equals("plano_treino")){
                result_set = updateData("plano_treino");
            }

            if (result_set > 0)
            {
                System.out.println("Updated successfully");
            }
            else
            {
                System.out.println("Can't update data");
            }
            // Close the result set, statement and the connection
            stmt.close();
            conn.close();

        }
        catch(SQLException se)
        {
            System.out.println("SQL Exception:");

            // Loop through the SQL Exceptions
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

    public static String[] columnsToUpdate(Connection conn,String table){
        String[] columns = new String[7];

        if(table.equals("utilizador")){
            String id = registerUser.idGetter(conn,"id_user",table);

            columns = dataForUser(columns,id);
        }
        else if(table.equals("exercicios")){
            String id = registerUser.idGetter(conn,"id_exercicio",table);

            columns = dataForExercise(columns,id);
        }
        else if(table.equals("conjunto_exercicios")){
            String id = registerUser.idGetter(conn,"id_conjunto",table);

            columns = dataForSet(columns,id);
        }
        else{
            String id = registerUser.idGetter(conn,"id_treino",table);

            columns = dataForPlan(columns,id);
        }

        return columns;
    }

    public static String[] dataForUser(String[] columns, String id){
        System.out.println("Insert the id of the user that you want to edit:");
        Scanner userIdScanner = new Scanner(System.in);
        String userId = userIdScanner.next();

        System.out.println("Insert a new training id for your user:");
        Scanner trainingIdScanner = new Scanner(System.in);
        String trainingId = trainingIdScanner.nextLine();

        System.out.println("Insert a new name for your user:");
        Scanner nameScanner = new Scanner(System.in);
        String name = nameScanner.next();

        System.out.println("Insert a new weight for your user:");
        Scanner weightScanner = new Scanner(System.in);
        String weight = weightScanner.next();

        System.out.println("Insert a new height for your user:");
        Scanner heightScanner = new Scanner(System.in);
        String height = heightScanner.next();

        System.out.println("Choose the new objective for your user:");
        System.out.println("(1) Gain muscle");
        System.out.println("(2) Lose weight");
        Scanner objScanner = new Scanner(System.in);
        String objective = objScanner.nextLine();

        if(objective.equals("1") || objective.equals("Gain muscle")){
            objective = "ganhar massa";

        }
        else if(objective.equals("2") || objective.equals("Lose weight")){
            objective = "Perder peso";
        }

        System.out.println("Insert a new password for your user:");
        Scanner passwordScanner = new Scanner(System.in);
        String password = passwordScanner.next();

        columns[0] = userId;
        columns[1] = trainingId;
        columns[2] = name;
        columns[3] = weight;
        columns[4] = height;
        columns[5] = objective;
        columns[6] = password;

        return columns;
    }

    public static String[] dataForExercise(String[] columns, String id){
        System.out.println("Insert the id of the exercise that you want to edit:");
        Scanner userIdScanner = new Scanner(System.in);
        String userId = userIdScanner.next();

        System.out.println("Insert a new name for your exercise:");
        Scanner nameScanner = new Scanner(System.in);
        String name = nameScanner.nextLine();

        System.out.println("Insert a new zone for your exercise:");
        Scanner zoneScanner = new Scanner(System.in);
        String zone = zoneScanner.next();

        System.out.println("Choose the new type of your exercise:");
        System.out.println("(1) Gain muscle");
        System.out.println("(2) Lose weight");
        Scanner typeScanner = new Scanner(System.in);
        String type = typeScanner.nextLine();

        if(type.equals("1") || type.equals("Gain muscle")){
            type = "massa";

        }
        else if(type.equals("2") || type.equals("Lose weight")){
            type = "peso";
        }

        System.out.println("Insert the new difficulty of your exercise:");
        Scanner difScanner = new Scanner(System.in);
        String difficulty = difScanner.next();

        columns[0] =  userId;
        columns[1] =  name;
        columns[2] = zone;
        columns[3] = type;
        columns[4] = difficulty;

        return columns;
    }

    public static String[] dataForSet(String[] columns, String id){
        System.out.println("Insert the id of the exercise set that you want to edit:");
        Scanner exerciseSetScanner = new Scanner(System.in);
        String exerciseSetId = exerciseSetScanner.next();

        System.out.println("Insert the id of the exercise that you want to edit in the exercise set:");
        Scanner exerciseIdScanner = new Scanner(System.in);
        String exerciseId = exerciseIdScanner.next();

        System.out.println("Insert \"null\" if the exercise dosen't have any repetitions");
        System.out.println("Insert the new ammount of repetitions:");
        Scanner repsScanner = new Scanner(System.in);
        String repetitions = repsScanner.next();

        if(repetitions.equals("null")){
            repetitions = null;
        }

        System.out.println("Insert \"null\" if the exercise can't be measured by time");
        System.out.println("Insert the new time of the exercise:");
        Scanner timeScanner = new Scanner(System.in);
        String time = timeScanner.next();

        if(time.equals("null")){
            time = null;
        }

        columns[0] = exerciseSetId;
        columns[1] = exerciseId;
        columns[2] = repetitions;
        columns[3] = time;

        return columns;
    }

    public static String[] dataForPlan(String[] columns, String id){
        System.out.println("Insert the id of the workout plan that you want to edit:");
        Scanner planIdScanner = new Scanner(System.in);
        String planId = planIdScanner.next();

        System.out.println("Insert the id of the exercise set that you want to edit in the workout plan:");
        Scanner setIdScanner = new Scanner(System.in);
        String setId = setIdScanner.next();

        System.out.println("Insert the new day that the user is suppoused to do the exercise set:");
        Scanner dayScanner = new Scanner(System.in);
        String day = dayScanner.next();

        System.out.println("Choose the new type of your exercise plan:");
        System.out.println("(1) Gain muscle");
        System.out.println("(2) Lose weight");
        Scanner typeScanner = new Scanner(System.in);
        String type = typeScanner.nextLine();

        if(type.equals("1")){
            type = "ganhar massa";

        }
        else{
            type = "Perder peso";
        }

        columns[0] =  planId;
        columns[1] =  setId;
        columns[2] =  day;
        columns[3] =  type;

        return columns;
    }

    public static int updateData(String table){
        int result_set = 0;

        try{

            if(table.equals("utilizador")){
                System.out.println();

                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/training_app", "root", "");

                String[] data = columnsToUpdate(conn,table);

                PreparedStatement user_update = conn.prepareStatement("UPDATE utilizador SET id_treino = ?, nome = ?, peso = ?, altura = ?, objetivo = ?, passe = ? WHERE id_user = ?;");
                user_update.setString(1, data[1]);
                user_update.setString(2, data[2]);
                user_update.setString(3, data[3]);
                user_update.setString(4, data[4]);
                user_update.setString(5, data[5]);
                user_update.setString(6, data[6]);
                user_update.setString(7, data[0]);

                result_set = user_update.executeUpdate();
            }

            else if(table.equals("exercicios")){
                System.out.println();

                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/training_app", "root", "");

                String[] data = columnsToUpdate(conn,table);

                PreparedStatement exercise_insert = conn.prepareStatement("UPDATE exercicios SET nome = ?, zona = ?, tipo = ?, dificuldade = ? WHERE id_exercicio = ?;");
                exercise_insert.setString(1, data[1]);
                exercise_insert.setString(2, data[2]);
                exercise_insert.setString(3, data[3]);
                exercise_insert.setString(4, data[4]);
                exercise_insert.setString(5, data[0]);

                result_set = exercise_insert.executeUpdate();
            }
            else if(table.equals("conjunto_exercicios")){
                System.out.println();

                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/training_app", "root", "");

                String[] data = columnsToUpdate(conn,table);

                PreparedStatement exerciseSetOrPlan_insert = conn.prepareStatement("UPDATE conjunto_exercicios SET repeticoes = ?, tempo = ? WHERE id_conjunto = ? AND id_exercicio = ?");
                exerciseSetOrPlan_insert.setString(1, data[2]);
                exerciseSetOrPlan_insert.setString(2, data[3]);
                exerciseSetOrPlan_insert.setString(3, data[0]);
                exerciseSetOrPlan_insert.setString(4, data[1]);

                result_set = exerciseSetOrPlan_insert.executeUpdate();
            }            

            else if(table.equals("plano_treino")){
                System.out.println();

                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/training_app", "root", "");

                String[] data = columnsToUpdate(conn,table);

                PreparedStatement exerciseSetOrPlan_update = conn.prepareStatement("UPDATE plano_treino SET dia = ?, tipo = ? WHERE id_treino = ? AND id_conjunto = ?");
                exerciseSetOrPlan_update.setString(1, data[2]);
                exerciseSetOrPlan_update.setString(2, data[3]);
                exerciseSetOrPlan_update.setString(3, data[0]);
                exerciseSetOrPlan_update.setString(4, data[1]);

                result_set = exerciseSetOrPlan_update.executeUpdate();
            }     

        }
        catch(SQLException se)
        {
            System.out.println("SQL Exception:");

            // Loop through the SQL Exceptions
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

        return result_set;
    }
}