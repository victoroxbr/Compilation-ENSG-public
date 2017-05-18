/*
 * Copyright (C) 2017 Yann Caron aka CyaNn
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package eu.ensg.compiler;

import java.util.LinkedList;
import java.util.List;

/**
 * The well known text lexer
 *
 * @author cyann for ENSG
 */
public class WktLexer {

    /**
     * Lexer entry point
     *
     * @param source the source code to parse
     * @return an chronological list of tokens
     */
    public static List<Token> parse(String source) {
        List<Token> production = new LinkedList<>();
        lexer(new StringBuilder(source), production);
        return production;
    }

    /**
     * Lexer<br>
     * lexem ::= keyword | symbol | number | null; <br>
     * lexemList ::= { lexem };
     *
     * @param source the source code to parse
     * @param production the resulting list of tokens
     */
    private static void lexer(StringBuilder source, List<Token> production) {
        boolean produce = true;

        // loop over lexems equivalent to BNF symbol: *
        while (!empty(source) && produce) {
            produce = false;

            if (nullSymbol(source, production)) {
                produce = true;
            }
            if (keyword(source, production)) {
                produce = true;
            }
            if (symbol(source, production)) {
                produce = true;
            }
            if (number(source, production)) {
                produce = true;
            }
        }

        if (!produce) {
            throw new RuntimeException(String.format("Lexing error source [%s] not parsable !", source.toString()));
        }
    }

    private static final String ALPHA = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * Parse the keywords<br>
     * keyword ::= { 'a' .. 'b' | 'A' .. 'B' };
     * @param source the source code to parse
     * @param production the resulting list of tokens
     */
    private static boolean keyword(StringBuilder source, List<Token> production) {
        // test if it is a keyword
        if (empty(source) || !ALPHA.contains(String.valueOf(source.charAt(0)))) {
            return false;
        }

        // produce it
        StringBuilder lexem = new StringBuilder();
        while (!empty(source) && ALPHA.contains(String.valueOf(source.charAt(0)))) {
            lexem.append(source.charAt(0));
            source.deleteCharAt(0);
        }

        // add to production
        production.add(new Token(TokenType.KEYWORD, lexem.toString()));
        return true;
    }

    public static final String SYMBOL = "(),";

    /**
     * Parse the sympbols<br>
     * symbol ::= '(' | ')' | ',';
     * @param source the source code to parse
     * @param production the resulting list of tokens
     */
    private static boolean symbol(StringBuilder source, List<Token> production) {
        // test if it is a symbol
        if (empty(source) || !SYMBOL.contains(String.valueOf(source.charAt(0)))) {
            return false;
        }

        // produce it
        String lexem = String.valueOf(source.charAt(0));
        source.deleteCharAt(0);

        // add to production
        production.add(new Token(TokenType.SYMBOL, lexem));
        return true;
    }

    public static final String NUM_EXT = "0123456789.";

    /**
     * Parse the numbers<br>
     * number ::= { '0' .. '9' };
     * @param source the source code to parse
     * @param production the resulting list of tokens
     */
    private static boolean number(StringBuilder source, List<Token> production) {
        // test if it is a keyword
        if (empty(source) || !NUM_EXT.contains(String.valueOf(source.charAt(0)))) {
            return false;
        }

        // produce it
        StringBuilder lexem = new StringBuilder();
        while (!empty(source) && NUM_EXT.contains(String.valueOf(source.charAt(0)))) {
            lexem.append(source.charAt(0));
            source.deleteCharAt(0);
        }

        // add to production
        production.add(new Token(TokenType.NUMBER, lexem.toString()));
        return true;
    }

    public static final String NULL = " \t\n\r";

    /**
     * Avoid the null symbols<br>
     * null ::= ' ' | '\t' | '\n' | '\r';
     * @param source the source code to parse
     * @param production the resulting list of tokens
     */
    private static boolean nullSymbol(StringBuilder source, List<Token> production) {
        boolean produce = false;

        // remove null character
        while (!empty(source) && NULL.contains(String.valueOf(source.charAt(0)))) {
            produce = true;
            source.deleteCharAt(0);
        }

        return produce;
    }

    /**
     * Test if source is empty.
     * @param source the source to test
     * @return true if source is empty
     */
    private static boolean empty(StringBuilder source) {
        return source.length() == 0;
    }

}
