package evaluator;

/**
 * What an evaluator does with an operation and two arguments.
 * Preconditions: leftArg and rightArg are valid arguments and
 * op is a valid operator. These preconditions are not checked.
 * 
 * @author C. Fox
 * @version 9/2018
 */
public interface Applier {
  /**
   * Apply an operator to a left and right argument.
   * 
   * @param leftArg one argument
   * @param rightArg the other argument
   * @return result of applying the operator to its arguments
   */
  String apply(String op, String leftArg, String rightArg);

}
