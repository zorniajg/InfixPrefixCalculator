package evaluator;

import containers.LinkedStack;
/**
 * An evaluator that solves infix expressions using a stack.
 * 
 * @author Jacob Zorniak
 * @version 9/2018
 */
public class StackPostfixEvaluator extends Evaluator
{
    /**
     * Record the applier.
     * @param applier instance holding the method that applies an operator to arguments
     */
    public StackPostfixEvaluator(Applier applier)
    {
        super(applier);
    }

    /**
     * Evaluate a postfix expression using a stack.
     * 
     * Strategy: Keep pushing numbers onto the stack until an operator is reached.
     * When an operator is reached, pop the two previous numbers on the stack and apply
     * the operator to them. Then push the result back onto the stack. Repeat this until
     * the entire input has been accounted for. 
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
                    String rightArg = stack.pop();
                    String leftArg = stack.pop();
                    stack.push(applier.apply(input.tokenText(), leftArg, rightArg));
                    input.next();
                    break;
                default:
                    return error(input, "Invalid character "+ input.tokenText());             
            }
        }
        if (stack.isEmpty()) return error(input, "Missing operator");
        String result = stack.pop();
        if (isOperator(result)) return error(input, "Missing arguments for operator "+result);
        if (!stack.isEmpty()) return error(input, "Missing arguments");
        return result;
    }

}
