/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Demo_Languages;

import java.util.List;
import java.util.Stack;

/**
 *
 * @author Yann Caron
 */
public class Parser {
		
	private Stack<Float> stack = new Stack<>();
	private List<Token> lexems;
	private int cursorLexems = 0;
	
	public Parser(List<Token> lexems) {
		this.lexems = lexems;
	}
	
	boolean E() {
		if(cursorLexems < lexems.size()) {
			T();
			if(cursorLexems < lexems.size()) {
				while(cursorLexems < lexems.size() && "+-".contains(lexems.get(cursorLexems).getWord())) {
					String signe = lexems.get(cursorLexems).getWord();
					cursorLexems++;
					T();
					float second = stack.pop();
					float first = stack.pop();
					if(signe.equals("+")) {
						stack.add(first + second);
					} else {
						stack.add(first - second);
					}
				}
			}
		}
		
		return true;
	}
	
	boolean T() {
		if(cursorLexems < lexems.size()) {
			F();
			if(cursorLexems < lexems.size()) {
				while(cursorLexems < lexems.size() && "*/".contains(lexems.get(cursorLexems).getWord())) {
					String signe = lexems.get(cursorLexems).getWord();
					cursorLexems++;
					F();
					float second = stack.pop();
					float first = stack.pop();
					if(signe.equals("*")) {
						stack.add(first * second);
					} else {
						stack.add(first / second);
					}
					
				}
			}
		}
		return true;
	}
	
	boolean F() {
		if(cursorLexems < lexems.size()) {
			if(lexems.get(cursorLexems).getType() == LexemType.NUMBER) {
				stack.add(Float.parseFloat(lexems.get(cursorLexems).getWord()));
				cursorLexems++;
			} else {
				cursorLexems++;
				E();
				cursorLexems++;
			}
		}
		return true;
	}
	
	float getResult() {
		return stack.pop();
	}
	
}
