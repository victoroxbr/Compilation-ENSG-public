package Demo_Languages;

import java.util.List;

public class Main {
    public static void main(String[] args) {

    	// Result = 26.0
        Source s = new Source("((3+3)*4-2)+8/2");

        List<Token> tokens = Lexer.ParseExpr(s);

        tokens.forEach(System.out::println);
        Parser parser = new Parser(tokens);
        parser.E();
        System.out.println(parser.getResult());
    }
}
