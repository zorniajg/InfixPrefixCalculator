package evaluator;
/**
 * What an evaluator does with an operation and two arguments for postfix.
 * Preconditions: leftArg and rightArg are valid arguments and
 * op is a valid operator. These preconditions are not checked.
 * 
 * @author Jacob Zorniak
 * @version 9/2018
 */
public class ToPostfixApplier implements Applier
{
    /**
     * Apply an operator to a left and right argument for postfix.
     * 
     * @param leftArg one argument
     * @param rightArg the other argument
     * @return result of applying the operator to its arguments
     */
    @Override
    public String apply(String op, String leftArg, String rightArg)
    {
        return leftArg + " " + rightArg + op;
    }

}
