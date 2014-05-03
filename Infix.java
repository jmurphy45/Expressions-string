/**
 * Write a description of class PostFixEval here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
public class PostFixEval
{
    // instance variables - replace the example below with your own
    //nested class
    public static class SyntaxErrorException extends Exception{
        SyntaxErrorException (String message){
            super(message);
        }
    }
    
    private static final String OPERATORS = "()^+-*/%";
    
    //Data field
    private Stack<Double> opperandStack;
    private Stack<Character> opperatorStacK;
    /** The precedence of the operators mathces the order in Operators */
    private static final int[] PRECENDENCE = {1,1,5,3,3,4,4,4};
    /** Postfix string */
    private StringBuilder postfix;
    
    
    
    private int precedence(char op){
        return PRECENDENCE[OPERATORS.indexOf(op)];
    }
    
    private boolean isOperator(char ch){
        return OPERATORS.indexOf(ch) != -1;
    }
    
    private double evalOp(char op){
        //pop the two opperand off the stack
        double rhs = opperandStack.pop();
        double lhs = opperandStack.pop();
        double result = 0;
        
        //evaluate the operator
        switch(op){
            case '+' : result = lhs + rhs;
                break;
            case '-' : result = lhs - rhs;
                break;
            case '/' : result = lhs / rhs;
                break;
            case '*' : result = lhs * rhs;
                break;
            case '^' : result = Math.pow(lhs, rhs);
                break;
            case '%' : result = lhs % rhs;
                break;
        }
        return result;
    }
    

    
    public boolean wellBalanced(String s){
        String parens = "(){}[]";
        
        Stack<Character> parensStack = new Stack<Character>();
        for(int i = 0; i < s.length(); i++)
        {
            char token = s.charAt(i);
            if(token == '[' || token == '{' || token == '(')
                parensStack.push(token);
            else if(token == ']' || token == '}' || token == ')')
            {
                if(parensStack.empty())
                    return false;
                switch(token)
                {
                    // Opening square brace
                    case ']':
                        if (parensStack.pop() != '[')
                            return false;
                        break;
                    // Opening curly brace
                    case '}':
                        if (parensStack.pop() != '{')
                            return false;
                        break;
                    // Opening paren brace
                    case ')':
                        if (parensStack.pop() != '(')
                            return false;
                        break;
                    default:
                        break;
                }
            }
        }
        
        if(parensStack.empty())
            return true;
        return false;
    }
    
    
    public String formatString(String s){
        String ex = "";
        
        for(int i = 0; i < s.length();i++){
            if(s.charAt(i) == ' '){
                ex += "";
            }else{
                ex += s.charAt(i) + " ";
            }
        }
        return ex;
    }
    
    public String convertToParen(String s){
        String ex = "";
        for(int i = 0; i < s.length();i++){
            if(s.charAt(i) == '['){
                ex += "(";
            }else if (s.charAt(i) == ']'){
                ex += ")";
            }else if(s.charAt(i) =='{'){
                ex += "(";
            }else if(s.charAt(i) =='}'){
                ex += ")";
            }else{
                ex += "" + s.charAt(i);
            }
        }
        return ex;
    }
    
    //throws SyntaxErrorException
    public Double eval(String expression) throws SyntaxErrorException {
        //create an empty stack
        opperandStack = new Stack<Double>();
        opperatorStacK = new Stack<Character>();
        char topOp;
        
        //check if it is a well balanced expression
        //else change all brackets to parans
        
        if(!wellBalanced(expression)){
            throw new SyntaxErrorException("Expression is not balanced Exception");
        }else{
            expression = convertToParen(expression);
            expression = formatString(expression);
        }
        String[] tokens = expression.split("\\s+");
        
        //process each token
        try{
            for(String nextToken : tokens){
                char firstChar = nextToken.charAt(0);
                //does it start with a digit?
                //topOp = opperatorStacK.peek();
                
                if(Character.isDigit(firstChar) && !isOperator(firstChar)){
                    //get double value
                    Double value = Double.parseDouble(nextToken);
                    //push value onto opperand stack
                    opperandStack.push(value);
                    System.out.println(opperandStack.peek());
                }else if (isOperator(firstChar) && firstChar != '(' && firstChar != ')' ){
                    opperatorStacK.push(firstChar);
                    topOp = opperatorStacK.peek();
                    //push result onto the opperand stack
                    if((precedence(firstChar) < precedence(topOp)) && !opperatorStacK.isEmpty()){
                           while((precedence(firstChar) < precedence(topOp)) && !opperatorStacK.isEmpty()){
                               double res = evalOp(opperatorStacK.peek());
                               opperandStack.push(res);
                               System.out.println(opperandStack.peek());
                            }
                      }
                }else if(isOperator(firstChar) && firstChar == '('){
                    opperatorStacK.push(firstChar);
                }else if(isOperator(firstChar) && firstChar == ')'){
                    topOp = opperatorStacK.peek();
                    while(opperatorStacK.peek() != '('){
                        if(topOp != '('){
                            double res = evalOp(firstChar);
                            opperandStack.push(res);
                            System.out.println(opperandStack.peek());
                        }
                    }
                    topOp = opperatorStacK.peek();
                    if(topOp == '('){
                        opperandStack.pop();
                    }
                }else{
                    //invalid character
                    throw new SyntaxErrorException("Invalid character encountered: " + firstChar);
                }
                
           
            }//end for
            
            Double answer = opperandStack.pop();
            //opperand stack should be empty
            if(opperandStack.empty()){
                System.out.println(answer);
                return answer;
            }else{
                //indicate error
                
                throw new SyntaxErrorException("Syntax Error: " + "Stack should be empty");
            }
        }catch(EmptyStackException ex){
            //pop was attemptedon empty stack
             System.out.println("Throwing error in empty stack exception");
             throw new SyntaxErrorException("Invalid character encountered: the stack is empty");
        }
    }
    
    
    public static void main(String[] args){
        String s =  "{(5*7)+[7+(3+3)]}";
        String s1 =  "{[5*7]+[7+(3+3)]}";
        String s2 =  "{{5*7}+[7+(3+3)]}";
        String s3 = "( 5    + 7  ) +  8  ";
        
        Stack<Double> a = new Stack<Double>();
        a.push(7.);
        a.push(5.45);
        System.out.println(a.size());
        
        System.out.println((new PostFixEval()).isOperator('^'));
        System.out.println((new PostFixEval()).formatString(s3));
        
        try{
            System.out.println((new PostFixEval()).eval("8 * (9 - 2 )"));
        }catch(SyntaxErrorException e){
            System.out.println("Error");
        }
    }
}
