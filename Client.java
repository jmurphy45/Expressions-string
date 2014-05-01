
/**
 * Write a description of class Cleint here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
//import static <the-package-for-the-class-PostFixEval>.PostFixEval.SyntaxErrorException.*;
public class Client
{

    public String askForExpression(){
        Scanner input = new Scanner(System.in);
        System.out.println("What expression would you like to evaluate");
        String ex = input.nextLine();
        return ex;
    }
    
    public static void main(String[] args){
        String ex;
        try{
            ex = (new Client()).askForExpression();
            (new PostFixEval()).eval(ex);
        }catch(SyntaxErrorException e){
            System.out.println("You have entered an invalid expression. Enter a new expression");
            ex = (new Client()).askForExpression();
            (new PostFixEval()).eval(ex);
        }
    }
} 