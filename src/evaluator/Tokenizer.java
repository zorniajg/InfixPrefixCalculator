package evaluator;

/**
 * Convert a string holding an expression into a stream of tokens.
 * 
 * @author C. Fox
 * @version 9/2018
 */
public class Tokenizer {
   
   // Token types
   public static final int UNKNOWN     = -1;
   public static final int EOS         = 0;
   public static final int OPERATOR    = 1;
   public static final int NUMBER      = 2;
   public static final int LEFT_PAREN  = 3;
   public static final int RIGHT_PAREN = 4;

   private String source;     // the input tokenized
   private char[] chars;      // to be tokenized
   private int current;       // index of the current character
   private int tokenType;     // one of the types above
   private String tokenText;  // characters of the current token
   private int tokenIndex;    // offset from the start of input string
   
   /**
    * Initialize the tokenizer.
    * @param s the string converted to a stream of tokens
    */
   public Tokenizer(String s) {
      if (s == null) s = "";
      source = s;
      reset();
   }
   
   /**
    * Start tokenizing from the start of the string.
    */
   public void reset() {
      chars = source.toCharArray();
      current = 0;
      next();
   }
      
   /**
    * Move on the next token, and return its type.
    * @return the next token in the stream
    */
   public int next() {
      tokenType = EOS; tokenText = "";
      while (current < chars.length && Character.isWhitespace(chars[current])) current++;
      tokenIndex = current;
      if (chars.length <= current) return tokenType;
      switch (chars[current]) {
         case '+': case '-':
         case '*': case '/': case '%':
            tokenType = OPERATOR;
            tokenText = Character.toString(chars[current++]);
            break;
         case '(':
            tokenType = LEFT_PAREN;
            tokenText = Character.toString(chars[current++]);
            break;
         case ')':
            tokenType = RIGHT_PAREN;
            tokenText = Character.toString(chars[current++]);
            break;
         case '0': case '1': case '2': case '3': case '4':
         case '5': case '6': case '7': case '8': case '9':
         case '~':
            StringBuilder number = new StringBuilder();
            number.append(chars[current]);
            if (chars[current++] == '~')
               if (chars.length <= current || !Character.isDigit(chars[current])) {
                  tokenType = UNKNOWN;
                  tokenText = "~";
                  break;
               }
            while (current < chars.length && Character.isDigit(chars[current])) {
               number.append(chars[current]);
               current++;
            }
            tokenType = NUMBER;
            tokenText = number.toString();
            break;
         default:
            tokenType = UNKNOWN;
            tokenText = Character.toString(chars[current++]);
            break;
      }
      return tokenType;
   } // next

   /**
    * Fetch the string used as the source for this stream of tokens.
    * @return the source string
    */
   public String source() { return source; }
   
   /**
    * Fetch the text of the current token.
    * @return the current token's text
    */
   public String tokenText() { return tokenText; }
   
   /**
    * Fetch the type of the current token.
    * @return the current token's type
    */
   public int tokenType() { return tokenType; }
   
   /**
    * Fetch the index in the source string of the start of the current token.
    * @return the current token's index
    */
   public int tokenIndex() { return tokenIndex; }
   
   /**
    * Say whether there are more tokens in this stream.
    * @return true iff there are more tokens in this stream
    */
   public boolean hasMore() { return tokenType != EOS; }
}
