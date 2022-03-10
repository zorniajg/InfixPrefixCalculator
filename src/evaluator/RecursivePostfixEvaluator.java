package evaluator;

/**
 * User recursion to evaluate a postfix expression.
 * 
 * A postfix expression is either a number or two postfix expressions
 * followed by an operator.
 * 
 * Alternatively, a postfix expression is a number optionally followed
 * by another postfix expression and an operator, optionally followed
 * by other postfix expressions and operators.
 * 
 * @author C. Fox
 * @version 9/2018
 *
 */
public class RecursivePostfixEvaluator extends Evaluator {

   /**
    * Record the applier.
    * @param applier instance holding the method that applies an operator to arguments
    */
   public RecursivePostfixEvaluator(Applier applier) {
      super(applier);
   }

   @Override
   public String evaluate(Tokenizer input) {
      isError = false;
      String result = eval(input);
      if (isError) return result;
      if (input.tokenType() != Tokenizer.EOS)
         return error(input, "Extra characters at end of input");
      return result;
   }

   /**
    * Evaluate a postfix expression.
    * 
    * Strategy: Look for a number and record it as the left parameter.
    * As long as the next token is a number, recursive to get the result
    * of evaluating the next expression (the right parameter), then grab
    * the operator, apply it to the parameters, and record the result as
    * the new left parameter. Then go back to the top of the loop.
    * 
    * @param input tokenizer proiding a stream of expression tokens
    * @return result of evaluating the expression, or an error message
    */
   public String eval(Tokenizer input) {
      switch (input.tokenType()) {
         case Tokenizer.NUMBER:
            String leftArg = input.tokenText();
            input.next();
            while (input.tokenType() == Tokenizer.NUMBER) {
               String rightArg = eval(input);
               if (isError) return rightArg;
               if (input.tokenType() != Tokenizer.OPERATOR)
                  return error(input, "Missing operator");
               String op = input.tokenText();
               leftArg =  applier.apply(op, leftArg, rightArg);
               input.next();
            }
            return leftArg;
         case Tokenizer.OPERATOR:
            return error(input, "Missing arguments to operator "+ input.tokenText());
         case Tokenizer.EOS:
            return error(input, "Missing arguments");
         default:
            return error(input, "Invalid character "+ input.tokenText());
         }
   }

} // StackPostfixEvaluator
