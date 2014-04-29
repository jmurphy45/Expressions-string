
/**
 * Write a description of class InfixtoPostFixParens here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class InfixtoPostFixParens
{
    // instance variables - replace the example below with your own
    //data fields
    /**The operator Stack */
    private Stack<Character> operatorStack;
    /** THe operator */
    private static final String OPERATORS = "+-*/";
    /** The precedence of the operators mathces the order in Operators */
    private static final int[] PRECENDENCE = {0,1,1,2,2,2};
    /** Postfix string */
    private StringBuilder postfix;
    
    private void processOperator(char op){
        if(operatorStack.empty() || op == '('){
            operatorStack.push(op);
        }else{
            //peek the operator stack
            //and let topOp be the top operator
            char topOp = operatorStack.peek();
            if(precedence(op) > precedence(topOp)){
                operatorStack.push(op);
            }else{
                //pop a;; stacked p[eratprs wotj equla or higher precedence than op
                while(!operatorStack.empty() && precedence(op) <= precedence(topOp)){
                    operatorStack.pop();
                    if(topOp == '('){
                        //matching ( popped - exit loop
                        break;
                    }
                    postfix.append(topOp);
                    postfix.append(' ');
                    if(!operatorStack.empty()){
                        //reset topOp
                        topOp = operatorStack.peek();
                    }
                }
                //assert operator is empty ot current operator precedence > top of stack operator precedence
                if(op != ')')
                {
                    operatorStack.push(op);
                }
            }
        }
    }
    
    public String convert(String infix){
        operatorStack = new Stack<Character>();
        postfix = new Stringbuilder();
        
        try{
            //process each token in the infix epression
            String nextToken;
            Scanner scan = new Scanner(infix);
            while((nextToken = scan.findLine("[\\p{L}\\p{N}]+|[-+/\\*()]")) != null){
                    char firstChar = nextToken.charAt(0);
                    //is it an opperand
                    if(Character.isJavaIdentifierStart(firstChar)){
                        postfix.append(nextToken);
                        postfix(' ');
                    }// is it a operator
                    else if(isOperator(firstChar)){
                        processOperator(firstChar);
                    }else{
                        //throw new error
                    }
                }
                
                while(!operatorStack.empty()){
                    char op = operatorStack.pop();
                    if(op == '('){
                        //throw error
                    }
                    postfix.append(op);
                    postfix.append(' ');
                    return postfix.toString();
                }
        }catch(EmptyStackException ex){
            //throw error
        }
    }
}
