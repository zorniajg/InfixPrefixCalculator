import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import evaluator.CalculateApplier;
import evaluator.Evaluator;
import evaluator.RecursiveInfixEvaluator;
import evaluator.RecursivePrefixEvaluator;
import evaluator.StackPostfixEvaluator;
import evaluator.StackInfixEvaluator;
//import evaluator.RecursivePrefixEvaluator;
import evaluator.StackPrefixEvaluator;
import evaluator.ToInfixApplier;
import evaluator.ToPostfixApplier;
import evaluator.ToPrefixApplier;
//import evaluator.ToInfixApplier;
//import evaluator.StackPostfixEvaluator;
//import evaluator.ToPostfixApplier;
//import evaluator.ToPrefixApplier;
import evaluator.Tokenizer;

/**
 * A simple calculator that accepts expressions in either infix or prefix form,
 * converts them to the other form plus to postfix, then evaluates them and
 * prints the result.
 * 
 * @author C. Fox
 * @version 9/2018
 */
public class Calculator {

   static public void main(String[] args) {
      BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
      Evaluator infixToPostfix = new RecursiveInfixEvaluator(new ToPostfixApplier());
      Evaluator infixToPrefix = new StackInfixEvaluator(new ToPrefixApplier());
      Evaluator prefixToPostfix = new RecursivePrefixEvaluator(new ToPostfixApplier());
      Evaluator prefixToInfix = new StackPrefixEvaluator(new ToInfixApplier());
      Evaluator postfixToValue = new StackPostfixEvaluator(new CalculateApplier());
      
      System.out.println("Calculator: Type a prefix or infix expression.");
      try {
         System.out.print("> ");
         String line = input.readLine();
         while (line != null) {
            if (line.equals("q")) break;
            Tokenizer in = new Tokenizer(line);
            Boolean isError = false;
            String postfixExp = null;
            if (in.tokenType() == Tokenizer.OPERATOR) {
               postfixExp = prefixToPostfix.evaluate(in);
               isError = prefixToPostfix.isError();
               if (isError) System.out.println(postfixExp);
               else {
                  in.reset();
                  System.out.println("Infix version: "+ prefixToInfix.evaluate(in));
                  System.out.println("Postfix version: "+ postfixExp);
                  System.out.println(postfixToValue.evaluate(new Tokenizer(postfixExp)));
               }
            }
            else {
               postfixExp = infixToPostfix.evaluate(in);
               isError = infixToPostfix.isError();
               if (isError) System.out.println(postfixExp);
               else {
                  in.reset();
                  System.out.println("Prefix version: "+ infixToPrefix.evaluate(in));
                  System.out.println("Postfix version: "+ postfixExp);
                  System.out.println(postfixToValue.evaluate(new Tokenizer(postfixExp)));
               }
            }
            System.out.print("> ");
            line = input.readLine();
         }
      } catch (IOException e) {
         System.out.println("IO error: must run.");
      }
      System.out.println("Bye.");
   } // main

} // Calculator
