package evaluator;

/**
 * A recursive implementation of an evaluator that 
 * solves prefix expressions.
 * 
 * @author Jacob Zorniak
 * @version 9/2018
 */
public class RecursivePrefixEvaluator extends Evaluator
{

    /**
     * Record the applier.
     * 
     * @param applier instance holding a method that applies an operator to two arguments
     */
    public RecursivePrefixEvaluator(Applier applier)
    {
        super(applier);
    }

    /**
     * Evaluate a prefix expression using recursion.
     * 
     * Strategy: Recurse through operators until a number is found.
     * A number must be followed by another number, so the first operator
     * is applied and the value is returned. This is repeated until the 
     * method has recursed through the entire input.
     * 
     * @param input the stream of tokens in the expression
     */
    @Override
    public String evaluate(Tokenizer input)
    {
        isError = false;
        String result = eval(input);
        if (isError) return result;
        if (input.tokenType() != Tokenizer.EOS)
           return error(input, "Extra characters at end of input");
        return result;
    }
    
    public String eval(Tokenizer input)
    {
        switch(input.tokenType())
        {
            case Tokenizer.OPERATOR:
                String op = input.tokenText();
                input.next();
                return applier.apply(op, eval(input), eval(input));
                
                
                /*String op = input.tokenText();
                input.next();
                String leftArg = eval(input);
                String rightArg = eval(input);
                String result = applier.apply(op, leftArg, rightArg);
                return result;*/
            case Tokenizer.NUMBER:
                String leftArg = input.tokenText();
                input.next();
                return leftArg;
                
                /*String arg = input.tokenText();
                input.next();
                return arg;*/
            default:
                return error(input, "Invalid character "+ input.tokenText());
        }
    }
}
