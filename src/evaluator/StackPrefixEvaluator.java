package evaluator;

import containers.LinkedStack;

/**
 * Evaluate a prefix expression using a stack.
 * 
 * @author C. Fox
 * @version 9/2018
 */
public class StackPrefixEvaluator extends Evaluator {

   /**
    * Rercord the applier.
    * 
    * @param applier instance holding a method that applies an operator to two arguments
    */
   public StackPrefixEvaluator(Applier applier) {
      super(applier);
   }

   /**
    * Evaluate a prefix expression with a stack.
    * 
    * Strategy: Push all operators on the stack. When a number comes in, if the
    * stack is empty or has an operator on it, then push the number. Otherwise,
    * if there is a number on the top of the stack, then pop it and the operator
    * beneath it, apply the operator to generate another result. Do the same thing
    * with this number as a number that comes in from the input.
    * 
    * @param input the stream of tokens int he expression
    */
   @Override
   public String evaluate(Tokenizer input) {
      LinkedStack<String> stack = new LinkedStack<>();
      while (input.hasMore()) {
         switch (input.tokenType()) {
            case Tokenizer.NUMBER:
               if (stack.isEmpty() || isOperator(stack.top())) stack.push(input.tokenText());
               else {
                  String rightArg = input.tokenText();
                  while (!stack.isEmpty() && !isOperator(stack.top())) {
                     String leftArg = stack.pop();
                     if (stack.isEmpty()) return error(input, "Too many arguments");
                     String op = stack.pop();
                     rightArg = applier.apply(op, leftArg, rightArg);
                  }
                  stack.push(rightArg);
               }
               break;
            case Tokenizer.OPERATOR:
               stack.push(input.tokenText());
               break;
            default:
               return error(input, "Invalid character "+ input.tokenText());
         }
         input.next();
      }
      if (stack.isEmpty()) return error(input, "Missing operator");
      String result = stack.pop();
      if (isOperator(result)) return error(input, "Missing arguments for operator "+result);
      if (!stack.isEmpty()) return error(input, "Missing arguments");
      return result;
   }

} // StackPrefixEvaluator
