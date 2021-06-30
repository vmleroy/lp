package syntatic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import interpreter.command.AssignCommand;
import interpreter.command.BlocksCommand;
import interpreter.command.Command;
import interpreter.command.ForCommand;
import interpreter.command.IfCommand;
import interpreter.command.OutputCommand;
import interpreter.command.OutputOp;
import interpreter.command.UnlessCommand;
import interpreter.command.UntilCommand;
import interpreter.command.WhileCommand;
import interpreter.expr.AccessExpr;
import interpreter.expr.ArrayExpr;
import interpreter.expr.BinaryExpr;
import interpreter.expr.BinaryOp;
import interpreter.expr.BoolExpr;
import interpreter.expr.BoolOp;
import interpreter.expr.CompositeBoolExpr;
import interpreter.expr.ConstExpr;
import interpreter.expr.ConvExpr;
import interpreter.expr.ConvOp;
import interpreter.expr.Expr;
import interpreter.expr.FunctionExpr;
import interpreter.expr.FunctionOp;
import interpreter.expr.InputExpr;
import interpreter.expr.InputOp;
import interpreter.expr.NotBoolExpr;
import interpreter.expr.RelOp;
import interpreter.expr.SingleBoolExpr;
import interpreter.expr.Variable;
import interpreter.value.IntegerValue;
import interpreter.value.StringValue;
import lexical.Lexeme;
import lexical.LexicalAnalysis;
import lexical.LexicalException;
import lexical.TokenType;


public class SyntaticAnalysis {
	private LexicalAnalysis lex;
	private Lexeme current;

	public SyntaticAnalysis(LexicalAnalysis lex) throws LexicalException, IOException {
		this.lex = lex;
		this.current = lex.nextToken();
	}

	public Command start() throws LexicalException, IOException {
		Command cmd = procCode();
		eat(TokenType.END_OF_FILE);
		return cmd;
	}

	/* Syntatic Analysis principal functions */
	private void advance() throws LexicalException, IOException {
		//System.out.println("Advanced (\"" + current.token + "\", " + current.type + ")" + "\n");
		current = lex.nextToken();
		//System.out.println("Actual token: " + current.type);
	}

	private void eat(TokenType type) throws LexicalException, IOException {
		//System.out.println("Expected (..., " + type + "), found (\"" + current.token + "\", " + current.type + ")");
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
                System.out.printf("Lexema invalido [%s]\n", current.token);
                break;
            case UNEXPECTED_EOF:
            case END_OF_FILE:
                System.out.printf("Fim de arquivo inesperado\n");
                break;
            default:
                System.out.printf("Lexema nao esperado [%s]\n", current.token);
                break;
        }

        System.exit(1);
    }

	/* Syntatic Analysis methods */
	// <code> ::= { <cmd> }
	private BlocksCommand procCode() throws LexicalException, IOException {
		int line = lex.getLine();
		
		List<Command> cmds = new ArrayList<Command>();
		
		while (current.type == TokenType.IF || current.type == TokenType.UNLESS || current.type == TokenType.WHILE || current.type == TokenType.UNTIL
		|| current.type == TokenType.FOR 	|| current.type == TokenType.PRINT  || current.type == TokenType.PUTS  || current.type == TokenType.ID 
		|| current.type == TokenType.OPEN_PAR)  {

			Command cmd = procCmd();			
			cmds.add(cmd);
		}
		
		BlocksCommand bcmd = new BlocksCommand(line, cmds);
		return bcmd;
	}

	// <cmd> ::= <if> | <unless> | <while> | <until> | <for> | <output>| <assign>
	private Command procCmd() throws LexicalException, IOException {
		Command cmd = null;
		if (current.type == TokenType.IF) {
			IfCommand icmd = procIf();
			cmd = icmd;
		} else if (current.type == TokenType.UNLESS) {
			UnlessCommand ulscmd = procUnless();
			cmd = ulscmd;
		} else if (current.type == TokenType.WHILE) {
			WhileCommand wcmds = procWhile();
			cmd = wcmds;
		} else if (current.type == TokenType.UNTIL) {
			UntilCommand ucmd = procUntil();
			cmd = ucmd;
		} else if (current.type == TokenType.FOR) {
			ForCommand fcmd = procFor();
			cmd = fcmd;
		} else if (current.type == TokenType.PRINT || current.type == TokenType.PUTS) {
			//OutputCommand ocmd = procOutput();
			//cmd = ocmd;
			cmd = procOutput();
		} else if (current.type == TokenType.ID || current.type == TokenType.OPEN_PAR) {
			//AssignCommand acmd = procAssign();
			//cmd = acmd;
			cmd = procAssign();
		} else {
			showError();
		}

		return cmd;
	}

	// <if>	::= if <boolexpr> [then] <code> { elsif <boolexpr> [ then ] <code> } [ else <code> ] end
	private IfCommand procIf() throws LexicalException, IOException {
		int line = lex.getLine();
		eat(TokenType.IF);

		BoolExpr cond = null;
		cond = procBoolexpr();

		if(current.type == TokenType.THEN)
			advance();

		Command thencmds = null;
		thencmds = procCode();

		IfCommand icmd = new IfCommand(line, cond, thencmds, null);
		Vector<IfCommand> aux = new Vector<IfCommand>();
		int i = 0;		
		while(current.type == TokenType.ELSIF) {
			int lineAux = lex.getLine();
			advance();
			
			BoolExpr condAux = null;
			condAux = procBoolexpr();
			
			if(current.type == TokenType.THEN)
				advance();
			
			Command thenCmdsAux = null;
			thenCmdsAux = procCode();

			aux.add(new IfCommand(lineAux, condAux, thenCmdsAux, null));
			if (aux.size() > 1) {
				aux.get(i-1).setElseCommands(aux.get(i));
			}
			i++;			
		}		
		
		Command elseCmds = null;
		if (current.type == TokenType.ELSE) {
			advance();
			elseCmds = procCode();					
		} 
		
		if (!(aux.isEmpty())) {
			aux.lastElement().setElseCommands(elseCmds);						
			icmd.setElseCommands(aux.firstElement());
		} else {
			icmd.setElseCommands(elseCmds);
		}	

		eat(TokenType.END);

		return icmd;
	}
	

	// <unless> ::= unless <boolexpr> [ then ] <code> [ else <code> ] end
	private UnlessCommand procUnless() throws LexicalException, IOException {		
		int line = lex.getLine();
		eat(TokenType.UNLESS);		

		BoolExpr bexpr = null;
		bexpr = procBoolexpr();

		Command thenCmds = null;
		Command elseCmds = null;

		if(current.type == TokenType.THEN) {
			advance();
		}

		thenCmds = procCode();

		if(current.type == TokenType.ELSE) {
			advance();
			elseCmds = procCode();
		}

		eat(TokenType.END);
		
		UnlessCommand ulscmd = new UnlessCommand(line, bexpr, thenCmds, elseCmds);
		return ulscmd;
	}

	// <while> ::= while <boolexpr> [ do ] <code> end
	private WhileCommand procWhile( )throws LexicalException, IOException {
		int line = lex.getLine();
		eat(TokenType.WHILE);

		BoolExpr cond = null;
		cond = procBoolexpr();

		if(current.type == TokenType.DO)
			advance();

		Command cmds = null;
		cmds = procCode();

		eat(TokenType.END);

		WhileCommand wcmds = new WhileCommand(line, cond, cmds);
		return wcmds;
	}

	// <until> ::= until <boolexpr> [ do ] <code> end
	private UntilCommand procUntil() throws LexicalException, IOException {
		int line = lex.getLine();

		eat(TokenType.UNTIL);

		BoolExpr cond = null;
		cond = procBoolexpr();

		if(current.type == TokenType.DO)
			advance();

		Command cmds = null;
		cmds = procCode();

		eat(TokenType.END);

		UntilCommand ucmd = new UntilCommand (line, cond, cmds);
		return ucmd;
	}

	// <for> ::= for <id> in <expr> [ do ] <code> end
	private ForCommand procFor() throws LexicalException, IOException {
		int line = lex.getLine();

		eat(TokenType.FOR);

		Variable var = procId();

		eat(TokenType.IN);

		Expr expr = procExpr();

		if (current.type == TokenType.DO)
			advance();

		Command cmd = procCode();

		eat(TokenType.END);

		ForCommand fcmd = new ForCommand (line, var, expr, cmd);
		return fcmd;
	}

	// <output> ::= ( puts | print ) [ <expr> ] [ <post> ] ';'
	private Command procOutput() throws LexicalException, IOException {
		OutputOp op = null;
		
		if (current.type == TokenType.PUTS) {
            op = OutputOp.PutsOp;
			advance();
        } else if (current.type == TokenType.PRINT) {
			op = OutputOp.PrintOp;
            advance();
        } else {
            showError();
        }

		int line = lex.getLine();

		Expr expr = null;
	
        if (current.type == TokenType.ADD 	 || current.type == TokenType.SUB 		  || current.type == TokenType.INTEGER  ||
            current.type == TokenType.STRING || current.type == TokenType.OPEN_PAR || current.type == TokenType.GETS 	||
            current.type == TokenType.RAND 	 || current.type == TokenType.ID 		  || current.type == TokenType.OPEN_BRA) {

            expr = procExpr();
        }		
		
		

        OutputCommand ocmd = new OutputCommand(line, op, expr);
		Command cmd = null;
		if (current.type == TokenType.IF || current.type == TokenType.UNLESS) {
            cmd = procPost(ocmd);
        } else {
			cmd = ocmd;
		}

        eat(TokenType.SEMI_COLON);
		
		
		return cmd;
	}

	// <assign> ::= <access> { ',' <access> } '=' <expr> { ',' <expr>} [ <post> ] ';'
	private Command procAssign() throws LexicalException, IOException {
		int line = lex.getLine();

		List<Expr> left = new ArrayList<Expr>();
		List<Expr> right = new ArrayList<Expr>();;
		
		left.add(procAccess());

		while (current.type == TokenType.COMMA) {
			advance();
			left.add(procAccess());
		}

		eat(TokenType.ASSIGN);

		right.add(procExpr());

		while (current.type == TokenType.COMMA) {
			advance();
			right.add(procExpr());
		}

		AssignCommand acmd = new AssignCommand(line, left, right);
		Command cmd = null;
		if (current.type == TokenType.IF || current.type == TokenType.UNLESS) {
            cmd = procPost(acmd);
        } else {
			cmd = acmd;
		}

		eat(TokenType.SEMI_COLON);

		
		return cmd;
	}

	// <post> ::= ( if | unless ) <boolexpr>
	private Command procPost(Command cmdIn) throws LexicalException, IOException {
		int line = lex.getLine();
		
		BoolExpr bexpr = null;
		int cmdOp = 0;

		if (current.type == TokenType.IF) {
			advance();
			cmdOp = 1;
		} else if (current.type == TokenType.UNLESS) {
			advance();
			cmdOp = 2;
		}

		bexpr = procBoolexpr();

		Command cmd = null;
		if(cmdOp == 1) {
			IfCommand icmd = new IfCommand(line, bexpr, cmdIn, null);
			cmd = icmd;
		} else if (cmdOp == 2) {
			UnlessCommand ucmd = new UnlessCommand(line, bexpr, cmdIn, null);
			cmd = ucmd;			
		}		

		return cmd;
	}

	// <boolexpr> ::= [ not ] <cmpexpr> [ ( and | or ) <boolexpr> ]
	private BoolExpr procBoolexpr() throws LexicalException, IOException {		
		int line = lex.getLine();
		
		BoolExpr bexpr = null;
		BoolExpr left = null;
		BoolExpr right = null;
		
		boolean not = false;
		BoolOp op = null;

		if (current.type == TokenType.NOT) {
			not = true;
			advance();
		}

		left = procCmpExpr();

		if (current.type == TokenType.AND || current.type == TokenType.OR) {
			if (current.type == TokenType.AND){	
				op = BoolOp.And;
			} else if (current.type == TokenType.OR) {
				op = BoolOp.Or;
			} 
			advance();
			right = procBoolexpr();
		}
		
		if (op != null) {
			CompositeBoolExpr cbexpr = new CompositeBoolExpr(line, left, right, op);
			if (not) { 
				NotBoolExpr nbexpr = new NotBoolExpr(line, cbexpr); 
				bexpr = nbexpr;
			} else {
				bexpr = cbexpr;
			}
		} else {
			bexpr = left;
		}

		return bexpr;
	}

	// <cmpexpr> ::= <expr> ( '==' | '!=' | '<' | '<=' | '>' | '>='| '===' ) <expr>
	private BoolExpr procCmpExpr() throws LexicalException, IOException {
		Expr left = null;
		Expr right = null;
		RelOp op = null;
		int line = lex.getLine();

		left = procExpr();

		if (current.type == TokenType.EQUAL   || current.type == TokenType.NOT_EQUAL || current.type == TokenType.LESS  	 || 
			current.type == TokenType.LESS_EQ || current.type == TokenType.GREATER   || current.type == TokenType.GREATER_EQ ||
			current.type == TokenType.CONTAIN) {
				if(current.type == TokenType.EQUAL) {
					op =  RelOp.EqualsOp;
				} else if (current.type == TokenType.NOT_EQUAL) {
					op = RelOp.NotEqualsOp;
				} else if (current.type == TokenType.LESS) {
					op = RelOp.LowerThanOp;
				} else if (current.type == TokenType.LESS_EQ) {
					op = RelOp.LowerEqualOp;
				} else if (current.type == TokenType.GREATER) {
					op = RelOp.GreaterThanOp;
				} else if (current.type == TokenType.GREATER_EQ) {
					op = RelOp.GreaterEqualOp;
				} else {
					op = RelOp.ContainsOp;
				}
				advance();
		} else {
			showError();
		}

		right = procExpr();

		SingleBoolExpr sexpr = new SingleBoolExpr(line, left, right, op);
		BoolExpr bexpr = sexpr;

		return bexpr;

	}

	// <expr> ::= <arith> [ ( '..' | '...' ) <arith> ]
	private Expr procExpr() throws LexicalException, IOException {
		Expr expr = null;
		Expr left = procArith();
		Expr right = null;
		BinaryOp op = null;

		if (current.type == TokenType.RANGE_WITH || current.type == TokenType.RANGE_WITHOUT) {
			if (current.type == TokenType.RANGE_WITH) { 
				op = BinaryOp.RangeWithOp;
			} else if (current.type == TokenType.RANGE_WITHOUT) {
				op = BinaryOp.RangeWithoutOp;
			} 
			advance();
			right = procArith();
		}

		int line = lex.getLine();
		
		if (op != null) {
			BinaryExpr bexpr = new BinaryExpr(line, left, right, op);
			expr = bexpr;
		} else {
			expr = left;
		}

		return expr;
	}

	// <arith> ::= <term> { ('+' | '-') <term> }
	private Expr procArith() throws LexicalException, IOException {
		Expr left = procTerm();
		Expr right = null;
		BinaryOp op = null;

		while (current.type == TokenType.ADD || current.type == TokenType.SUB) {	
			int line = lex.getLine();

			if (current.type == TokenType.ADD) {
				op = BinaryOp.AddOp;
			} else if (current.type == TokenType.SUB) {
				op = BinaryOp.SubOp;
			}

			advance();
			right = procTerm();
			
			BinaryExpr bexpr = new BinaryExpr(line, left, right, op);
			left = bexpr;
		}

		Expr expr = left;
		return expr;
	}

	// <term> ::= <power> { ('*' | '/' | '%') <power> }
	private Expr procTerm() throws LexicalException, IOException {
		Expr left = procPower();
		Expr right = null;
		BinaryOp op = null;

		while (current.type == TokenType.MULT || current.type == TokenType.DIV || current.type == TokenType.MOD) {
			int line = lex.getLine();

			if (current.type == TokenType.MULT ) {
				op = BinaryOp.MulOp;
			} else if (current.type == TokenType.DIV) {
				op = BinaryOp.DivOp;
			} else if (current.type == TokenType.MOD) {
				op = BinaryOp.ModOp;
			}

			advance();
			right = procPower();

			BinaryExpr bexpr = new BinaryExpr(line, left, right, op);
			left = bexpr;
		}

		Expr expr = left;
		return expr;
	}

	// <power> ::= <factor> { '**' <factor> }
	private Expr procPower() throws LexicalException, IOException {
		Expr left = procFactor();
		Expr right = null;
		BinaryOp op = null;

		while (current.type == TokenType.POW) {
			int line = lex.getLine();
			op = BinaryOp.ExpOp;

			advance();
			right = procFactor();

			BinaryExpr bexpr = new BinaryExpr(line, left, right, op);
			left = bexpr;
		}

		Expr expr = left;
		return expr;
	}

	// <factor> ::= [ '+' | '-' ] ( <const> | <input> | <access> ) [ <function> ]
	private Expr procFactor() throws LexicalException, IOException {
		ConvOp op = null;		

		if (current.type == TokenType.ADD) {
			op = ConvOp.PlusOp;
			advance();
		} else if (current.type == TokenType.SUB) {
			op = ConvOp.MinusOp;
			advance();
		}

		int line = lex.getLine();
		Expr expr = null;
		if (current.type == TokenType.INTEGER || current.type == TokenType.STRING || current.type == TokenType.OPEN_BRA) {
			expr = procConst();
		} else if (current.type == TokenType.GETS || current.type == TokenType.RAND) {
			expr = procInput();
		} else if (current.type == TokenType.ID || current.type == TokenType.OPEN_PAR) {
			expr = procAccess();
		} else {
			showError();
		}

		if (current.type == TokenType.DOT) {
			FunctionExpr fexpr = procFunction(expr); 
			expr = fexpr;
		}

		if (op != null ) {
			ConvExpr cexpr = new ConvExpr(line, op, expr);
			expr = cexpr;
		} 

		return expr;
	}

	// <const> ::= <integer> | <string> | <array>
	private Expr procConst() throws LexicalException, IOException {
		Expr expr = null;
		if (current.type == TokenType.INTEGER) {
			expr = procInteger();
		} else if (current.type == TokenType.STRING) {
			expr = procString();
		} else if (current.type == TokenType.OPEN_BRA) {
			expr = procArray();
		} else {			
			showError();
		}

		return expr;
	}

	// <input> ::= gets | rand
	private InputExpr procInput() throws LexicalException, IOException {
			
		InputOp op = null;
		int line = lex.getLine();

		if(current.type == TokenType.GETS) {
			op = InputOp.GetsOp;
			advance();
		} else if (current.type == TokenType.RAND) {
			op = InputOp.RandOp;
			advance();
		} else {
			showError();
		}

		InputExpr iexpr = new InputExpr(line, op);
		return iexpr;

	}

	// <array> ::= '[' [ <expr> { ',' <expr> } ] ']'
	private ArrayExpr procArray() throws LexicalException, IOException {
		List<Expr> exprs = new ArrayList<>();
		int line = lex.getLine();

		eat(TokenType.OPEN_BRA);
		if (current.type == TokenType.ADD 	 || current.type == TokenType.SUB 		  || current.type == TokenType.INTEGER  ||
			current.type == TokenType.STRING || current.type == TokenType.OPEN_PAR    || current.type == TokenType.GETS 	||
			current.type == TokenType.RAND 	 || current.type == TokenType.ID 		  || current.type == TokenType.OPEN_BRA) {
            
			exprs.add(procExpr());

    		while (current.type == TokenType.COMMA) {
				advance();
				exprs.add(procExpr());
			}
		}

		eat(TokenType.CLOSE_BRA);

		ArrayExpr aexpr = new ArrayExpr(line, exprs);
		return aexpr;
	}

	// <access> ::= ( <id> | '(' <expr> ')' ) [ '[' <expr> ']' ]
	private Expr procAccess() throws LexicalException, IOException {
		Expr base = null;
		
		int line = lex.getLine();

		if (current.type == TokenType.ID) {
			base = procId();
		} else if (current.type == TokenType.OPEN_PAR) {
			advance();
			base = procExpr();
			eat(TokenType.CLOSE_PAR);
	   } else {
		   showError();
	   }

	   Expr index = null;
	   if (current.type == TokenType.OPEN_BRA) {
		   advance();
		   index = procExpr();
		   eat(TokenType.CLOSE_BRA);
	   }

	   AccessExpr aexpr = new AccessExpr(line, base, index);
	   Expr expr = aexpr;
	   
	   return expr;
	}

	// <function> ::= '.' ( length| to_i| to_s )
	private FunctionExpr procFunction(Expr expr) throws LexicalException, IOException {				

		int line = lex.getLine();
		eat(TokenType.DOT);

		FunctionOp op = null;
		if (current.type == TokenType.LENGTH) {
			op = FunctionOp.LengthOp;
			advance();
		} else if (current.type == TokenType.TOI) {
			op = FunctionOp.ToIntOp;
			advance();
		} else if (current.type == TokenType.TOS) {
			op = FunctionOp.ToStringOp;
			advance();
		} else {
			showError();
		}
		
		FunctionExpr fexpr = new FunctionExpr(line, expr, op);

		return fexpr;
	}

	// <integer>
	private ConstExpr procInteger() throws LexicalException, IOException {
		String tmp = current.token;
		eat(TokenType.INTEGER);
		int line = lex.getLine();

		int n;
		try {
			n = Integer.parseInt(tmp);
		} catch (Exception e) {
			n = 0;
		}

		IntegerValue iv = new IntegerValue (n);
		ConstExpr cexpr = new ConstExpr (line, iv);	
		
		return cexpr;
	}

	// <string>
	private ConstExpr procString() throws LexicalException, IOException {
		String tmp = current.token;
		eat(TokenType.STRING);
		int line = lex.getLine();

		StringValue sv = new StringValue (tmp);
		ConstExpr cexpr = new ConstExpr (line, sv);	
		
		return cexpr;
	}

	// <id>
	private Variable procId() throws LexicalException, IOException {
		String tmp = current.token;
		eat(TokenType.ID);
		int line = lex.getLine();

		Variable v = new Variable (line, tmp);

		return v;
	}
}
