
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
    private static final int[] PRECENDENCE = {1,2,3,3,4,4,4};
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
    
    public String convertToParen(String s){
        String ex;
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
                ex += i;
            }
       
        }
    }
    
    
    public Double eval(String expression) throws SyntaxErrorException{
        //create an empty stack
        opperandStack = new Stack<Double>();
        opperatorStacK = new Stack<Character>();
        char topOp = opperatorStacK.peek();
        char op;
        double rhs;
        double lhs;
        
        //process each token
        String[] tokens = expression.split("\\s+");
        if(!wellBalanced(expression)){
            throw new SyntaxErrorException("Expression is not balanced Exception");
        }
        try{
            for(String nextToken : tokens){
                char firstChar = nextToken.charAt(0);
                //does it start with a digit?
                if(Character.isDigit(firstChar)){
                    //get double value
                    Double value = Double.parseDouble(nextToken);
                    //push value onto opperand stack
                    opperandStack.push(value);
                }else if (isOperator(firstChar)){
                    //Eval the operator
                    double result = evalOp(firstChar);
                    //push result onto the opperand stack
                    if(!opperatorStacK.isEmpty()){
                        opperandStack.push(result);
                    }else{
                        if((precedence(op) > precedence(topOp)) && !opperatorStacK.isEmpty()){
                               while((precedence(op) > precedence(topOp)) && !opperatorStacK.isEmpty()){
                                   double res = evalOp(opperatorStacK.peek());
                                   opperandStack.pop();
                                   opperandStack.pop();
                                   opperandStack.push(res);
                                }
                          }else{
                              //pop a;; stacked p[eratprs wotj equla or higher precedence than op
                
                          }
                    }
                }else{
                    //invalid character
                    throw new SyntaxErrorException("Invalid character encountered: " + firstChar);
                }
                
           
            }//end for
            
            Double answer = opperandStack.pop();
            //opperand stack should be empty
            if(opperandStack.empty()){
                return answer;
            }else{
                //indicate error
                throw new SyntaxErrorException("Syntax Error: " + "Stack should be empty");
            }
        }catch(EmptyStackException ex){
            //pop was attemptedon empty stack
             throw new SyntaxErrorException("Invalid character encountered: the stack is empty");
        }
    }
    
    
    public double evaluate(String s){
        //create an empty stack
        opperandStack = new Stack<Double>();
        opperatorStacK = new Stack<Character>();
        char topOp = opperatorStacK.peek();
        double rhs;
        double lhs;
        
        //process each token
        String[] tokens = s.split("\\s+");
        if(!wellBalanced(s)){
            throw new SyntaxErrorException("Expression is not balanced Exception");
        }else{
            convertToParen(s);
        }
        
        try{
            char firstChar;
            for(int i = 0; i < s.length();i++){
                firstChar = s.charAt(i);
                if(Character.isDigit(firstChar)){
                    opperandStack.push((double)(firstChar));
                }else if(firstChar.contains(OPERATORS)){
                    if(!opperatorStacK.isEmpty && (precedence(op) > precedence(topOp))){
                        while(!opperatorStacK.isEmpty && (precedence(op) > precedence(topOp))){
                           evalOp(firstChar); 
                        }
                    }
                }
            }
        }catch(EmptyStackException ex){

        }
    }
    public static void main(String[] args){
        String s =  "(5*7)+(3+3)";
        String b = "(5*7+(3+3";
        System.out.println((new PostFixEval()).wellBalanced(s));
        (new PostFixEval()).eval("5*6");
    }
}
