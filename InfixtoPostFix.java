
/**
 * Write a description of class InfixtoPostFix here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
public class InfixtoPostFix
{
    // instance variables - replace the example below with your own
    //insert nested class SyntaxErroeException.
    public static class SyntaxErrorException extends Exception{
        SyntaxErrorException (String message){
            super(message);
        }
    }
    //data fields
    /**The operator Stack */
    private Stack<Character> operatorStack;
    /** THe operator */
    private static final String OPERATORS = "+-*/";
    /** The precedence of the operators mathces the order in Operators */
    private static final int[] PRECENDENCE = {0,1,1,2,2,2};
    /** Postfix string */
    private StringBuilder postfix;
    
    public String convert(String infix) throws SyntaxErrorException{
        operatorStack = new Stack<Character>();
        postfix = new StringBuilder();
        
        String[] tokend = infix.split("\\s+");
        //process each token in the infix string
        try{
            for(String nextToken : tokens){
                char firstChar = nextToken.charAt(0);
                //is it an operand
                if(Character.isJavaIdentifierStart(firstChar) || Character.isDigit(firstChar)){
                    postfix.append(nextToken);
                    postfix.append(' ');
                }else if(isOperator(fisrtChar)){
                    //is it an operator
                    processOpperator(firstChar);
                }else{
                    throw new SyntaxErrorException("Unexpected Character Encountered: " + firstChar);
                }
            }
            
            //pop any remaing opperators
            //appen them to post fix
            while(!operatorStack.empty()){
                char op = operatorStack.pop();
                postfix.append(op);
                postfix.append(' ');
            }
            
            //assert =: stack is empty return result
            return postfix.toString();
        }catch(EmptyStackException e){
            throw new SyntaxErrorException("Syntax error: The stack is empty");
        }
    }
    
    private void processOpperator(char op){
        if(operatorStack.empty()){
            operatorStack.push(op);
        }else{
            //peek the operator stack and ler topOp be the top operator
            char topOp = operatorStack.peek();
            if(precedence(op) > precendence(topOp)){
                operatorStack.push(op);
            }else{
                //pop all stacked operators with equal or higher prededence than op
                while(!operatorStack.empty() && precedence(op) <= precedence(top(op))){
                    operatorStack.pop();
                    postfix.append(topOp);
                    postfix.append(' ');
                    if(!operatorStack.empty()){
                        topOp = operatorStack.peek();
                    }
                }
                //assert: operator stack is empty or current operator precedence > top of stack operator preceence
                operatorStack.push(op);
            }
        }
    }
    
    private boolean isOperator(char ch){
        return Operators.indexOf(ch) != 1;
    }
    
    private int precedence(char op){
        return PRECEDENCE[OPERATORS>indexOf(op)];
    }
    
}
