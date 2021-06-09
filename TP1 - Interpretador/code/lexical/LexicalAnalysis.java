/* "State machine" to recover tokens and discovers the syntax */

package code.lexical;

import java.io.FileInputStream;		//Classe feita para manipular arquivos
import java.io.PushbackInputStream; //Classe feita para ler os dados do arquivo alem de possuir mais funcoes


//AutoCloseable para fechar o arquivo automatico
public class LexicalAnalysis implements AutoCloseable {

	private int line;
	private SymbolTable st;
	private PushbackInputStream input;

	// Constructor -> Will open the file and start others variables
	public LexicalAnalysis(String file_name) {
		try {
			input = new PushbackInputStream(new FileInputStream(file_name));
		} catch (Exception e) {
			throw new LexicalException("Unable to open file: " + file_name);
		}

		st = new SymbolTable();
		line = 1;
	}

	// Close file
	public void close() {
		try {
			input.close();
		} catch (Exception e) {
			throw new LexicalException("Unable to close file");
		}
	}

	// Return the line that is beeign read at the moment
	public int getLine() {
		return this.line;
	}

	public Lexeme nextToken() {
		Lexeme lex = new Lexeme("", TokenType.END_OF_FILE);

		int state = 1;
		while (state != 12 && state != 13) {
			int c = getc();
			switch (state) {
				case 1:
					if (c == ' ' || c == '\t' || c == '\r' || c == '\n') {
						state = 1;
					} else if (c == '\n') {
						this.line ++;
						state = 1;
					} else if (c == '#') {
						state = 2;
					} else if (c == '.') {
						lex.token += (char) c;
						state = 3;
					} else if (c == '=') {
						lex.token += (char) c;
						state = 5;
					} else if (c == '<' || c == '>') {
						lex.token += (char) c;
						state = 6;
					} else if (c == '*') {
						lex.token += (char) c;
						state = 7;
					} else if (c == '!') {
						lex.token += (char) c;
						state = 8;
					} else if (c == ';' || c == ',' || c == '+' || c == '-' || c == '%' || c == '/' || c == '[' || c == ']' || c == '(' || c == ')') {
						lex.token += (char) c;
						state = 12;
					} else if (Character.isLetter(c) || c == '_') {
						lex.token += (char) c;
						state = 9;
					} else if (Character.isDigit(c)) {
						lex.token += (char) c;
						state = 10;
					} else if (c == '\'') {
						lex.token += (char) c;
						state = 11;
					} else if (c == -1) {
						lex.type = TokenType.END_OF_FILE;
						state = 13;
					} else {
						lex.token += (char) c;
						lex.type = TokenType.INVALID_TOKEN;
						state = 13;
					}
				break;

				case 2:
					if (c == '\n') {
						this.line ++;
						state = 1;
					} else if (c == -1) {
						lex.type = TokenType.END_OF_FILE;
						state = 13;
					} else {
						state = 2;
					}
				break;

				case 3:
					if (c == '.') {
						lex.token += (char) c;
						state = 4;
					} else {
						ungetc(c);
						state = 12;
					}
				break;

				case 4:
					if (c == '.') {
						lex.token += (char) c;
						state = 12;
					} else {
						ungetc(c);
						state = 12;
					}
				break;

				case 5:
					if (c == '=') {
						lex.token += (char) c;
						state = 6;
					} else {
						ungetc(c);
						state = 12;
					}
				break;

				case 6:
					if (c == '=') {
						lex.token += (char) c;
						state = 12;
					} else {
						ungetc(c);
						state = 12;
					}
				break;

				case 7:
					if (c == '*') {
						lex.token += (char) c;
						state = 12;
					} else {
						ungetc(c);
						state = 12;
					}
				break;

				case 8:
					if (c == '=') {
						lex.token += (char) c;
						state = 12;
					} else if (c == -1) {
						lex.type = TokenType.UNEXPECTED_EOF;
						state = 13;
					} else {
						ungetc(c);
						lex.type = TokenType.INVALID_TOKEN;
						state = 13;
					}
				break;

				case 9:
					if (Character.isLetter(c) || c == '_' || Character.isDigit(c)) {
						lex.token += (char) c;
						state = 9;
					} else {
						ungetc(c);
						state = 12;
					}
				break;

				case 10:
					if (Character.isDigit(c)) {
						lex.token += (char) c;
						state = 10;
					} else if (c == -1) {
						lex.type = TokenType.UNEXPECTED_EOF;
						state = 13;
					} else {
						ungetc(c);
						lex.type = TokenType.INTEGER;
						state = 13;
					}
				break;

				case 11:
					if (c != '\'') {
						lex.token += (char) c;
						state = 11;
					} else if (c == -1) {
						lex.type = TokenType.UNEXPECTED_EOF;
						state = 13;
					} else {
						lex.token += (char) c;
						lex.type = TokenType.STRING;
						state = 13;
					}
				break;

				default:
				throw new LexicalException("Unreachable");
			}
		}
		if (state == 12)
			lex.type = st.findInTable(lex.token);

		return lex;
	}




	// Read a letter that is in the program to create the token
	private int getc() {
		try {
			return input.read();
		} catch (Exception e) {
			throw new LexicalException("Unable to read file");
		}
	}

	// "Return" the letter to the buffer
	private void ungetc(int c) {
		if (c != -1) {
			try {
				input.unread(c);
			} catch (Exception e) {
				throw new LexicalException("Unable to ungetc");
			}
		}
	}


}
