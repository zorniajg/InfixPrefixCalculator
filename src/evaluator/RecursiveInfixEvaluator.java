package evaluator;

/**
 * Use recursion to evaluate an infix expression.
 * 
 * An infix expression is either a number, or an infix expression
 * enclosed in parentheses, or number followed by an operator, followed
 * by an infix expression.
 * 
 * @author C. Fox
 * @version 9/18
 *
 */
public class RecursiveInfixEvaluator extends Evaluator {

   
   /**
    * Record the applier.
    * @param applier the functor instance with the apply method in it
    */
   public RecursiveInfixEvaluator(Applier applier) {
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
    * Evaluate an infix expression.
    * 
    * Strategy: scan left to right, accumulating the result (this is
    * a left associative evaluator). If a left-parenthesis is found,
    * then recurse to evaluate the expression within the parentheses.
    * The complexity of this code is mostly due to the error checking.
    * 
    * @param input Tokenizer with the tokens in the expression
    * @return the result of evaluation, or an error message
    */
   public String eval(Tokenizer input) {
      String leftArg = null;  // null iff no left argument found yet
      String op = null;       // null iff no operator found yet

      while (input.tokenType() != Tokenizer.EOS) {
         switch (input.tokenType()) {
            case Tokenizer.NUMBER:
               if (leftArg == null && op == null)
                  leftArg = input.tokenText();
               else if (leftArg == null)
                  return error(input, "Missing argument for operator "+ op);
               else if (op == null)
                  return error(input, "Missing operator");
               else {
                  leftArg = applier.apply(op, leftArg, input.tokenText());
                  op = null;
               }
            break;
         case Tokenizer.OPERATOR:
            if (op != null)
               return error(input, "Two operators in a row: "+ op +" and "+ input.tokenText());
            op = input.tokenText();
            break;
         case Tokenizer.LEFT_PAREN:
            input.next();
            if (leftArg == null)
               leftArg = eval(input);
            else if (op == null)
               return error(input, "Missing operator");
            else {
               leftArg = applier.apply(op, leftArg, eval(input));
               op = null;
            }
            if (isError) return leftArg;
            if (!input.tokenText().equals(")"))
               return error(input, "Missing right parenthesis");
            break;
         case Tokenizer.RIGHT_PAREN:
            if (op != null)
               return error(input, "Missing argument for operator "+ op);
            return leftArg;
         default:
            return error(input, "Invalid character "+ input.tokenText());
         }
         input.next();
      }
      if (leftArg == null) return error(input, "Missing expression");
      if (op != null) return error(input, "Missing argument for operator "+ op);
      return leftArg;
   }

} // StackInfixEvaluator
