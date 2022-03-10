package evaluator;

/**
 * What every evaluator does. The error() method creates a uniform
 * error message for any evaluation error.
 * 
 * @author C. Fox
 * @version 9/2018
 */
public abstract class Evaluator {

   protected boolean isError;  // keeps track of whether an error has occurred
   protected Applier applier;  // what to do with two arguments and an operation
   
   /**
    * Force concrete classes to have an applier.
    * @param applier holds the method for applying an operator to two arguments
    */
   protected Evaluator(Applier applier) {
      this.applier = applier;
   }

   /**
    * Return as a string the result of evaluating an expression whose tokens are
    * provided by a Tokenizer. Different kinds of expressions (like infix vs.
    * postfix) require different kinds of evaluate functions.
    * 
    * @param expression a stream of tokens in an expression 
    * @return the result of the evaluation as a string
    */
   public abstract String evaluate(Tokenizer expression);
   
   /**
    * Say whether the last string evaluated contained an error.
    * @return true iff the last string evaluated had an error
    */
   public boolean isError() {
      return isError;
   }

   /**
    * Formulate an error message by fetching the original expression from the
    * Tokenizer, printing a cursor on the following line at the start of the
    * token where the error is detected, and on the following line, printing
    * a message. This method also sets the error flag.
    * 
    * @param input Tokenizer with the offending expression
    * @param msg the description of the problem
    * @return an error message formulated as indicated
    */
   protected String error(Tokenizer input, String msg) {
      isError = true;
      StringBuilder result = new StringBuilder();
      result.append(input.source()).append('\n');
      for (int i = 0; i < input.tokenIndex(); i++) result.append(' ');
      result.append("^\n");
      result.append(msg+".");
      return result.toString();
    }
   
   /**
    * Determine whether a string is an operator: needed for error checking.
    * Precondition: s is one of the kinds of strings returned as tokens
    * from the tokenizer or an error message. This is not checked.
    * 
    * @param s the string checked
    * @return true iff s is an operator
    */
   protected boolean isOperator(String s) {
      if (s.length() != 1) return false;
      switch (s.charAt(0)) {
         case '+': case '-': case '*': case '/': case '%': return true;
         default: return false;
      }
   }

   /**
    * Determine whether a string is a number: needed for error checking.
    * Precondition: s is one of the kinds of strings returned as tokens
    * from the tokenizer or an eror message. This is not checked.
    * 
    * @param s the string checked
    * @return true iff s is a number
    */
   protected boolean isNumber(String s) {
      return !isOperator(s) && !s.equals("(") && !s.equals(")");
   }

} // Evaluator
