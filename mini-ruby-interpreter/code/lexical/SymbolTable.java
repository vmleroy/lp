/* Class to create a map of all command and important stuff used on the program */

package lexical;

import java.util.Map;
import java.util.HashMap;

public class SymbolTable {

	private Map<String, TokenType> st;

	public SymbolTable() {
		st = new HashMap<String, TokenType>();

		// Commands
		st.put("if", TokenType.IF);
		st.put("unless", TokenType.UNLESS);
		st.put("while", TokenType.WHILE);
		st.put("until", TokenType.UNTIL);
		st.put("for", TokenType.FOR);
		st.put("print", TokenType.PRINT);
		st.put("puts", TokenType.PUTS);
		st.put("elsif", TokenType.ELSIF);
		st.put("else", TokenType.ELSE);

		// Arithmetic Operands
		st.put("+", TokenType.ADD);
		st.put("-", TokenType.SUB);
		st.put("*", TokenType.MULT);
		st.put("/", TokenType.DIV);
		st.put("%", TokenType.MOD);
		st.put("**", TokenType.POW);
		st.put("..", TokenType.RANGE_WITH);
		st.put("...", TokenType.RANGE_WITHOUT);

		// Logic Operands
		st.put("==", TokenType.EQUAL);
		st.put("!=", TokenType.NOT_EQUAL);
		st.put("<", TokenType.LESS);
		st.put(">", TokenType.GREATER);
		st.put("<=", TokenType.LESS_EQ);
		st.put(">=", TokenType.GREATER_EQ);
		st.put("===", TokenType.CONTAIN);
		st.put("!", TokenType.NOT);

		// Connectors
		st.put("and", TokenType.AND);
		st.put("or", TokenType.OR);

		// Funcs
		st.put("gets", TokenType.GETS);
		st.put("rand", TokenType.RAND);
		st.put("length", TokenType.LENGTH);
		st.put("to_i", TokenType.TOI);
		st.put("to_s", TokenType.TOS);

		// Symbols
		st.put("=", TokenType.ASSIGN);
		st.put(";", TokenType.SEMI_COLON);
		st.put("(", TokenType.OPEN_PAR);
		st.put(")", TokenType.CLOSE_PAR);
		st.put("[", TokenType.OPEN_BRA);
		st.put("]", TokenType.CLOSE_BRA);
		st.put(",", TokenType.COMMA);
		st.put(".", TokenType.DOT);

		// Reserved words
		st.put("in", TokenType.IN);
		st.put("do", TokenType.DO);
		st.put("then", TokenType.THEN);
		st.put("end", TokenType.END);
	}

	public boolean containsInTable (String token) {
		return st.containsKey(token);
	}

	public TokenType findInTable (String token) {
		return this.containsInTable(token) ? st.get(token) : TokenType.ID;
	}
}
