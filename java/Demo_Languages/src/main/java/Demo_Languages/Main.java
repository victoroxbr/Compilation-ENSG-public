package Demo_Languages;

import java.util.List;

public class Main {
    public static void main(String[] args) {

    	// Result = 20
        Source s = new Source("    2 * 7 + 8 / 2 + 2");

        List<Token> tokens = Lexer.ParseExpr(s);

        tokens.forEach(System.out::println);
        Parser parser = new Parser(tokens);
        parser.E();
        System.out.println(parser.getResult());
    }
}
