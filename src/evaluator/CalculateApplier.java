package evaluator;

/**
 * Do integer arithmetic on two arguments.
 * The ~ is used to indicate a negative number.
 * 
 * @author C. Fox
 * @version 9/2018
 */
public class CalculateApplier implements Applier {

   @Override
   public String apply(String op, String leftArg, String rightArg) {
      int leftValue, rightValue;
      int leftSign = 1;
      int rightSign = 1;
      if (leftArg.charAt(0) == '~') {
         leftSign = -1;
         leftArg = leftArg.substring(1);
      }
      if (rightArg.charAt(0) == '~') {
         rightSign = -1;
         rightArg = rightArg.substring(1);
      }
      try {
         leftValue = leftSign * Integer.parseInt(leftArg);
         rightValue = rightSign * Integer.parseInt(rightArg);
      } catch (NumberFormatException e) {
         // should never happen because the tokenizer only passes along valid numbers
         throw new IllegalArgumentException("Unparsable argument "+ leftArg +" or "+ rightArg);
      }
      int result;
      switch (op) {
         case "+": result = leftValue+rightValue; break;
         case "-": result = leftValue-rightValue; break;
         case "*": result = leftValue*rightValue; break;
         case "/": result = leftValue/rightValue; break;
         case "%": result = leftValue%rightValue; break;
         default: throw new IllegalStateException("Unreachable code.");
      }
      if (result < 0) return "~"+String.valueOf(-result);
      else            return String.valueOf(result);
   }

} // CalculateApplier
