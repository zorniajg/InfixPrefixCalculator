package evaluator;

import containers.LinkedStack;
/**
 * An evaluator that solves infix expressions using a stack.
 * 
 * @author Jacob Zorniak
 * @version 9/2018
 */
public class StackInfixEvaluator extends Evaluator
{
    /**
     * Record the applier.
     * @param applier instance holding the method that applies an operator to arguments
     */
    public StackInfixEvaluator(Applier applier)
    {
        super(applier);
    }
    
    /**
     * Evaluate an infix expression using a stack.
     * 
     * Strategy: If a number is followed by an operator, 
     * there must be a number after that operator. Therefore the operator 
     * is popped off the stack and applied to the current number being looked at 
     * in the input and the number before the operator in the stack. If parentheses are encountered,
     * the arguments inside parentheses are prioritized and the result of the parenthetical expression
     * is pushed onto the stack. The stack should be left with one argument, which is the result.
     * 
     * @param input the stream of tokens in the expression
     */
    @Override
    public String evaluate(Tokenizer input)
    {
        LinkedStack<String> stack = new LinkedStack<String>();
        while(input.hasMore())
        {
            switch(input.tokenType())
            {
                case Tokenizer.NUMBER:
                    stack.push(input.tokenText());
                    input.next();
                    break;
                case Tokenizer.OPERATOR:
                    if(stack.isEmpty() || isOperator(stack.top()))
                        return error(input, "Missing arguments for operator");
                    stack.push(input.tokenText());
                    input.next();
                    break;
                case Tokenizer.LEFT_PAREN:
                    stack.push(input.tokenText());
                    input.next();
                    break;
                case Tokenizer.RIGHT_PAREN:
                    String result = "";
                    while(!stack.isEmpty() && !stack.top().equals("("))
                    {
                        String rightArg = stack.pop();
                        String op = stack.pop();
                        String leftArg = stack.pop();
                        result += applier.apply(op, leftArg, rightArg);
                        if(!stack.top().equals("("))
                            stack.push(result);
                    } 
                    if (!stack.isEmpty() && !stack.top().equals("(")) 
                        return error(input, "Missing left paren " + input.tokenText()); // invalid expression                 
                    else
                    {
                        stack.pop();
                        stack.push(result);
                    }
                    input.next();
                    break;
                case Tokenizer.UNKNOWN:
                    return error(input, "Invalid character "+ input.tokenText());
            }
        }
        String result = stack.pop();
        if (isOperator(result)) return error(input, "Missing arguments for operator "+result);
        if (!stack.isEmpty()) return error(input, "Missing arguments");
        return result;
    }

}
