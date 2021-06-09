package code.syntatic;

import java.io.IOException;

import code.lexical.Lexeme;
import code.lexical.LexicalAnalysis;
import code.lexical.LexicalException;
import code.lexical.TokenType;


public class SyntaticAnalysis {
	private LexicalAnalysis lex;
	private Lexeme current;

	public SyntaticAnalysis(LexicalAnalysis lex) throws LexicalException, IOException {
		this.lex = lex;
		this.current = lex.nextToken();
	}

	public void start() throws LexicalException, IOException {
		procCode();
		eat(TokenType.END_OF_FILE);
	}

	/* Syntatic Analysis principal functions */
	private void advance() throws LexicalException, IOException {
		System.out.println("Advanced (\"" + current.token + "\", " +
            current.type + ")" + "\n");
		current = lex.nextToken();
		//System.out.println("Actual token: " + current.type);
	}

	private void eat(TokenType type) throws LexicalException, IOException {
		System.out.println("Expected (..., " + type + "), found (\"" +
            current.token + "\", " + current.type + ")");
        if (type == current.type) {
            advance();
        } else {
            showError();
        }
    }

	private void showError() {
        System.out.printf("%02d: ", lex.getLine());

        switch (current.type) {
            case INVALID_TOKEN:
                System.out.printf("Lexema inválido [%s]\n", current.token);
                break;
            case UNEXPECTED_EOF:
            case END_OF_FILE:
                System.out.printf("Fim de arquivo inesperado\n");
                break;
            default:
                System.out.printf("Lexema não esperado [%s]\n", current.token);
                break;
        }

        System.exit(1);
    }

	/* Syntatic Analysis methods */
	// <code> ::= { <cmd> }
	private void procCode() throws LexicalException, IOException {
		while (current.type == TokenType.IF || current.type == TokenType.UNLESS || current.type == TokenType.WHILE || current.type == TokenType.UNTIL
		|| current.type == TokenType.FOR 	|| current.type == TokenType.PRINT  || current.type == TokenType.PUTS || current.type == TokenType.ID 
		|| current.type == TokenType.BRACKETS_OP)  {

			procCmd();
		}
	}

	// <cmd> ::= <if> | <unless> | <while> | <until> | <for> | <output>| <assign>
	private void procCmd() throws LexicalException, IOException {
		if (current.type == TokenType.IF) {
			procIf();
		} else if (current.type == TokenType.UNLESS) {
			procUnless();
		} else if (current.type == TokenType.WHILE) {
			procWhile();
		} else if (current.type == TokenType.UNTIL) {
			procUntil();
		} else if (current.type == TokenType.FOR) {
			procFor();
		} else if (current.type == TokenType.PRINT || current.type == TokenType.PUTS) {
			procOutput();
		} else if (current.type == TokenType.ID || current.type == TokenType.BRACKETS_OP) {
			procAssingn();
		} else {
			showError();
		}
	}

	// <if>	::= if <boolexpr> [then] <code> { elsif <boolexpr> [ then ] <code> } [ else <code> ] end
	private void procIf() throws LexicalException, IOException {
		eat(TokenType.IF);

		procBoolexpr();

		if(current.type == TokenType.THEN)
			advance();

		procCode();

		while(current.type == TokenType.ELSIF) {
			advance();
			procBoolexpr();
			if(current.type == TokenType.THEN)
				advance();
			procCode();
		}

		if (current.type == TokenType.ELSE) {
			advance();
			procCode();
		}

		eat(TokenType.END);
	}
	

	// <unless> ::= unless <boolexpr> [ then ] <code> [ else <code> ] end
	private void procUnless() throws LexicalException, IOException {
		eat(TokenType.UNLESS);

		procBoolexpr();

		if(current.type == TokenType.THEN) {
			advance();
		}

		procCode();

		if(current.type == TokenType.ELSE) {
			advance();
			procCode();
		}

		eat(TokenType.END);
	}

	// <while> ::= while <boolexpr> [ do ] <code> end
	private void procWhile( )throws LexicalException, IOException {
		eat(TokenType.WHILE);

		procBoolexpr();

		if(current.type == TokenType.DO)
			advance();

		procCode();

		eat(TokenType.END);
	}

	// <until> ::= until <boolexpr> [ do ] <code> end
	private void procUntil() throws LexicalException, IOException {
		eat(TokenType.UNTIL);

		procBoolexpr();

		if(current.type == TokenType.DO)
			advance();

		procCode();

		eat(TokenType.END);
	}

	// <for> ::= for <id> in <expr> [ do ] <code> end
	private void procFor() throws LexicalException, IOException {
		eat(TokenType.FOR);

		procId();

		eat(TokenType.IN);

		procExpr();

		if (current.type == TokenType.DO)
			advance();

		procCode();

		eat(TokenType.END);
	}

	// <output> ::= ( puts | print ) [ <expr> ] [ <post> ] ';'
	private void procOutput() throws LexicalException, IOException {
		if (current.type == TokenType.PUTS) {
            advance();
        } else if (current.type == TokenType.PRINT) {
            advance();
        } else {
            showError();
        }

        if (current.type == TokenType.ADD 	 || current.type == TokenType.SUB 		  || current.type == TokenType.INTEGER  ||
            current.type == TokenType.STRING || current.type == TokenType.BRACKETS_OP || current.type == TokenType.GETS 	||
            current.type == TokenType.RAND 	 || current.type == TokenType.ID 		  || current.type == TokenType.SQR_BRACKETS_OP) {

            procExpr();
        }

        if (current.type == TokenType.IF || current.type == TokenType.UNLESS) {
            procPost();
        }

        eat(TokenType.SEMI_COLON);
	}

	// <assign> ::= <access> { ',' <access> } '=' <expr> { ',' <expr>} [ <post> ] ';'
	private void procAssingn() throws LexicalException, IOException {
		procAccess();

		while (current.type == TokenType.COMMA) {
			advance();
			procAccess();
		}

		eat(TokenType.ASSIGN);

		procExpr();

		while (current.type == TokenType.COMMA) {
			advance();
			procAccess();
		}

		if (current.type == TokenType.IF || current.type == TokenType.UNLESS) {
            procPost();
        }

		eat(TokenType.SEMI_COLON);
	}

	// <post> ::= ( if | unless ) <boolexpr>
	private void procPost() throws LexicalException, IOException {
		if (current.type == TokenType.IF) {
			advance();
		} else if (current.type == TokenType.UNLESS) {
			advance();
		} else {
			showError();
		}

		procBoolexpr();
	}

	// <boolexpr> ::= [ not ] <cmpexpr> [ ( and | or ) <boolexpr> ]
	private void procBoolexpr() throws LexicalException, IOException {
		if (current.type == TokenType.NOT)
			advance();

		procCmpExpr();

		if (current.type == TokenType.AND || current.type == TokenType.OR) {
			if (current.type == TokenType.AND){	
				advance();
				procBoolexpr();
			} else if (current.type == TokenType.OR) {
				advance();
				procBoolexpr();
			} else {
				showError();
			}
		}
		
	}

	// <cmpexpr> ::= <expr> ( '==' | '!=' | '<' | '<=' | '>' | '>='| '===' ) <expr>
	private void procCmpExpr() throws LexicalException, IOException {
		procExpr();
		if (current.type == TokenType.EQUAL   || current.type == TokenType.NOT_EQUAL || current.type == TokenType.LESS  	 || 
			current.type == TokenType.LESS_EQ || current.type == TokenType.GREATER   || current.type == TokenType.GREATER_EQ ||
			current.type == TokenType.CONTAIN) {
				advance();
		} else {
			showError();
		}
		procExpr();
	}

	// <expr> ::= <arith> [ ( '..' | '...' ) <arith> ]
	private void procExpr() throws LexicalException, IOException {
		procArith();
		if (current.type == TokenType.INCL || current.type == TokenType.EXCL) {
			if (current.type == TokenType.INCL) { 
				advance();
				procArith();
			} else if (current.type == TokenType.EXCL) {
				advance();
				procArith();
			} else {
				showError();
			}
		}
	}

	// <arith> ::= <term> { ('+' | '-') <term> }
	private void procArith() throws LexicalException, IOException {
		procTerm();
		while (current.type == TokenType.ADD || current.type == TokenType.SUB) {
			advance();
			procTerm();
		}
	}

	// <term> ::= <power> { ('*' | '/' | '%') <power> }
	private void procTerm() throws LexicalException, IOException {
		procPower();
		while (current.type == TokenType.MULT || current.type == TokenType.DIV || current.type == TokenType.MOD) {
			advance();
			procPower();
		}
	}

	// <power> ::= <factor> { '**' <factor> }
	private void procPower() throws LexicalException, IOException {
		procFactor();
		while (current.type == TokenType.POW) {
			advance();
			procFactor();
		}
	}

	// <factor> ::= [ '+' | '-' ] ( <const> | <input> | <access> ) [ <function> ]
	private void procFactor() throws LexicalException, IOException {
		if (current.type == TokenType.ADD || current.type == TokenType.SUB) {
			advance();
		}

		if (current.type == TokenType.INTEGER || current.type == TokenType.STRING || current.type == TokenType.SQR_BRACKETS_OP) {
			procConst();
		} else if (current.type == TokenType.GETS || current.type == TokenType.RAND) {
			procInput();
		} else if (current.type == TokenType.ID || current.type == TokenType.BRACKETS_OP) {
			procAccess();
		} else {
			showError();
		}

		if (current.type == TokenType.POINT) {
			procFunction();
		}

	}

	// <const> ::= <integer> | <string> | <array>
	private void procConst() throws LexicalException, IOException {
		if (current.type == TokenType.INTEGER) {
			procInteger();
		} else if (current.type == TokenType.STRING) {
			procString();
		} else if (current.type == TokenType.SQR_BRACKETS_OP) {
			procArray();
		} else {			
			showError();
		}
	}

	// <input> ::= gets | rand
	private void procInput() throws LexicalException, IOException {
		if(current.type == TokenType.GETS) {
			advance();
		} else if (current.type == TokenType.RAND) {
			advance();
		} else {
			showError();
		}

	}

	// <array> ::= '[' [ <expr> { ',' <expr> } ] ']'
	private void procArray() throws LexicalException, IOException {
		eat(TokenType.SQR_BRACKETS_OP);
		if (current.type == TokenType.ADD 	 || current.type == TokenType.SUB 		  || current.type == TokenType.INTEGER  ||
			current.type == TokenType.STRING || current.type == TokenType.BRACKETS_OP || current.type == TokenType.GETS 	||
			current.type == TokenType.RAND 	 || current.type == TokenType.ID 		  || current.type == TokenType.SQR_BRACKETS_OP) {
            
			procExpr();

    		while (current.type == TokenType.COMMA) {
				advance();
				procExpr();
			}
		}

		eat(TokenType.SQR_BRACKETS_CL);
	}

	// <access> ::= ( <id> | '(' <expr> ')' ) [ '[' <expr> ']' ]
	private void procAccess() throws LexicalException, IOException {
		if (current.type == TokenType.ID) {
			procId();
		} else if (current.type == TokenType.BRACKETS_OP) {
			advance();
			procExpr();
			eat(TokenType.BRACKETS_CL);
	   } else {
		   showError();
	   }

	   if (current.type == TokenType.SQR_BRACKETS_OP) {
		   advance();
		   procExpr();
		   eat(TokenType.SQR_BRACKETS_CL);
	   }

	}

	// <function> ::= '.' ( length| to_i| to_s )
	private void procFunction() throws LexicalException, IOException {
		eat(TokenType.POINT);

		if (current.type == TokenType.LENGTH) {
			advance();
		} else if (current.type == TokenType.TOI) {
			advance();
		} else if (current.type == TokenType.TOS) {
			advance();
		} else {
			showError();
		}
	}

	// <integer>
	private void procInteger() throws LexicalException, IOException {
		eat(TokenType.INTEGER);
	}

	// <string>
	private void procString() throws LexicalException, IOException {
		eat(TokenType.STRING);
	}

	// <id>
	private void procId() throws LexicalException, IOException {
		eat(TokenType.ID);
	}
}
