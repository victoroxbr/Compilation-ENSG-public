package Demo_Languages;

import java.util.List;

public class Main {
    public static void main(String[] args) {

    	// Result = 5
        Source s = new Source("    2 * 7 -5 + 8 / 2 + 2-10 ");

        List<Token> tokens = Lexer.ParseExpr(s);

        tokens.forEach(System.out::println);
        
        System.out.println(Parser.E(tokens));
    }
}
