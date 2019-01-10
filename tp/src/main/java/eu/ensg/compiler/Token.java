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

/**
 * The lexer token. It contains more information than just the lexem string itself.
 * Typically, it gives the lexem, the type (for parsing or syntax coloring purposes), the positions in the source code.
 * @author cyann for ENSG
 */
public class Token {
    
    private final TokenType type;
    private final String lexem;

    /**
     * Constructor with final parameters.
     * @param type the lexem type of the lexem. Useful for syntax coloring and parsing purpose
     * @param lexem the lexem string
     */
    public Token(TokenType type, String lexem) {
        this.lexem = lexem;
        this.type = type;
    }

    public String getLexem() {
        return lexem;
    }

    public TokenType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Token{" + "type=" + type + ", lexem='" + lexem + "'}";
    }
    
}
