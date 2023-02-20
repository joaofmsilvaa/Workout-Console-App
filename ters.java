import java.util.Scanner;
/**
 * Escreva uma descrição da classe ters aqui.
 * 
 * @author (seu nome) 
 * @version (um número da versão ou uma data)
 */
public class ters
{
    public static void test(){
          Scanner sc1 = new Scanner(System.in);
          String scanner1 = sc1.nextLine();
          
          int value = 0;
          String value1 = "";
          
            try{
                System.out.println("try");
              value = Integer.parseInt(scanner1);
            }
            
            catch(Exception e){
                System.out.println("catch");
                
                System.out.println("Your value is null");
    
            }
          
          
        }
}
