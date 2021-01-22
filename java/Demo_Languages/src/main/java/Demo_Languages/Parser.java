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
	
	
//	public static Stack<Token> stack = new Stack<>();
//	
//	static int E(List<Token> lexems) {
//		if(lexems.size() > 0) {
//			if(T(lexems)) {
//				if(lexems.size() > 0) {
//					if(lexems.get(0).getWord().equals("+")) {
//						stack.add(lexems.get(0));
//						lexems.remove(0);
//						E(lexems);
//					} else if(lexems.get(0).getWord().equals("-")) {
//						stack.add(lexems.get(0));
//						lexems.remove(0);
//						E(lexems);
//					}
//				}
//			}
//		}
//		
//		int somme = Integer.parseInt(stack.get(0).getWord());
//		for(int i = 1; i < stack.size()-1; i += 2) {
//			if(stack.get(i).getWord().equals("+")) {
//				somme += Integer.parseInt(stack.get(i+1).getWord());
//			} else if(stack.get(i).getWord().equals("-")) {
//				somme -= Integer.parseInt(stack.get(i+1).getWord());
//			}
//		}
//		
//		return somme;
//	}
//	
//	
//	static boolean T(List<Token> lexems) {
//		if(lexems.size() > 0) {
//			if(lexems.get(0).getType() == LexemType.NUMBER) {
//				F(lexems);
//			} else if(lexems.get(0).getWord().equals("*")) {
//				int mult = Integer.parseInt(stack.get(stack.size() - 1).getWord()) * Integer.parseInt(lexems.get(1).getWord());
//				Token token = new Token(Integer.toString(mult), LexemType.NUMBER);
//				stack.pop();
//				stack.add(token);
//				lexems.remove(0);
//				lexems.remove(0);
//				E(lexems);
//			} else if(lexems.get(0).getWord().equals("/")) {
//				int div = Integer.parseInt(stack.get(stack.size() - 1).getWord()) / Integer.parseInt(lexems.get(1).getWord());
//				Token token = new Token(Integer.toString(div), LexemType.NUMBER);
//				stack.pop();
//				stack.add(token);
//				lexems.remove(0);
//				lexems.remove(0);
//				E(lexems);
//			}
//		}
//		
//		return true;
//	}
//	
//	static void F(List<Token> lexems) {
//		if(lexems.size() > 0) {
//			stack.add(lexems.get(0));
//			lexems.remove(0);
//			T(lexems);
//		}
//	}
		
	private Stack<Integer> stack = new Stack<>();
	private List<Token> lexems;
	private int cursorLexems = 0;
	
	public Parser(List<Token> lexems) {
		this.lexems = lexems;
	}
	
	boolean E() {
		if(cursorLexems < lexems.size()) {
			T();
			if(cursorLexems < lexems.size()) {
				if("+".contains(lexems.get(cursorLexems).getWord())) {
					cursorLexems++;
					E();
					int second = stack.pop();
					int first = stack.pop();
					stack.add(first + second);
				} else if("-".contains(lexems.get(cursorLexems).getWord())) {
					cursorLexems++;
					E();
					int secondTerme = stack.pop();
					int firstTerme = stack.pop();
					stack.add(firstTerme - secondTerme);
				}
			}
		}
		
		return true;
	}
	
	boolean T() {
		if(cursorLexems < lexems.size()) {
			F();
			if(cursorLexems < lexems.size()) {
				if("*".contains(lexems.get(cursorLexems).getWord())) {
					cursorLexems++;
					T();
					int second = stack.pop();
					int first = stack.pop();
					stack.add(first * second);
				} else if("/".contains(lexems.get(cursorLexems).getWord())) {
					cursorLexems++;
					T();
					int second = stack.pop();
					int first = stack.pop();
					stack.add(first / second);
				}
			}
		}
		return true;
	}
	
	boolean F() {
		if(cursorLexems < lexems.size()) {
			if(lexems.get(cursorLexems).getType() == LexemType.NUMBER) {
				stack.add(Integer.parseInt(lexems.get(cursorLexems).getWord()));
				cursorLexems++;
			} else {
				cursorLexems++;
				E();
				cursorLexems++;
			}
		}
		return true;
	}
	
	
	int getResult() {
		return stack.pop();
	}
	
	
}
