package syntatic;

import lexical.Lexeme;
import lexical.LexicalAnalysis;
import lexical.TokenType;

public class SyntaticAnalysis {

    private LexicalAnalysis lex;
    private Lexeme current;

    public SyntaticAnalysis(LexicalAnalysis lex) {
        this.lex = lex;
        this.current = lex.nextToken();
    }

    public void start() {
        procCode();
    }

    private void advance() {
        //System.out.println("Advanced (\"" + current.token + "\", " +
        //    current.type + ")");
        current = lex.nextToken();
    }

    private void eat(TokenType type) {
        //System.out.println("Expected (..., " + type + "), found (\"" + 
        //    current.token + "\", " + current.type + ")");
        if (type == current.type) {
            current = lex.nextToken();
        } else {
            showError();
        }
    }

    private void showError() {
        System.out.printf("Nao\n");
        System.exit(1);
    }

    // <code>	::=  <interface> 
    private void procCode() {
        if (current.type == TokenType.INTERFACE) { procInterface(); }        
        else { showError(); }
    }

    // <interface>	::=  interface <name> [  <extends>  ]  ‘ { ‘  (  { ( <func> | <interface> ) }  |  <empty> )  ‘ } ‘ 
    private void procInterface() {
        eat(TokenType.INTERFACE);
        
        if (current.type == TokenType.ID) { procName(); }
        else { showError(); }

        if (current.type == TokenType.EXTENDS) { procExtends(); }

        if (current.type == TokenType.OPEN_BRA) { advance(); }
        else { showError(); }

        if (!(current.type == TokenType.CLOSE_BRA)) {
            while (current.type == TokenType.VOID || current.type == TokenType.INTEGER || current.type == TokenType.BOOLEAN || 
                current.type == TokenType.STRING || current.type == TokenType.INTERFACE) {
                    if (current.type == TokenType.INTERFACE) {
                        procInterface();
                    } else {
                        procFunc();
                    }
                }
        }

        if (current.type == TokenType.CLOSE_BRA) { advance(); }
        else { showError(); }

    }

    // <extends>	::=  extends <name> {  ‘ , ‘  <name>  } 
    private void procExtends() {
        eat(TokenType.EXTENDS);

        if (current.type == TokenType.ID) { procName(); }
        else { showError(); }

        while (current.type == TokenType.COMMA) {
            advance();
            if (current.type == TokenType.ID) { procName(); }
            else { showError(); }
        }
    }

    // <func>	::=  <return> <name> ‘ ( ‘ [ <type> <name> [ { ‘ , ’ <type> <name> }  ]  ] ‘ ) ‘  ‘ ; ‘
    private void procFunc() {
        if ( current.type == TokenType.VOID || current.type == TokenType.INTEGER || current.type == TokenType.STRING || current.type == TokenType.BOOLEAN ) {
            procReturn();
        } else { showError(); }

        if ( current.type == TokenType.ID ) { procName(); }
        else { showError(); }

        if ( current.type == TokenType.OPEN_PAR ) { advance(); }
        else { showError(); }

        if ( current.type == TokenType.INTEGER || current.type == TokenType.STRING || current.type == TokenType.BOOLEAN ) {
            procType();
            
            if ( current.type == TokenType.ID ) { procName(); }
            else { showError(); }

            if (current.type == TokenType.COMMA) {
                while (current.type == TokenType.COMMA) {
                    advance();
                    if ( current.type == TokenType.INTEGER || current.type == TokenType.STRING || current.type == TokenType.BOOLEAN ) {
                        procType();
                        
                        if ( current.type == TokenType.ID ) { procName(); }
                        else { showError(); }
                    }
                }
            }            
        }

        if ( current.type == TokenType.CLOSE_PAR ) { advance(); }
        else { showError(); }

        if ( current.type == TokenType.SEMI_COLON ) { advance(); }
        else { showError(); }
    }

    // <return>	::=  void | <type>
    private void procReturn() {
        if ( current.type == TokenType.VOID ) { eat(TokenType.VOID); }
        else { procType(); }
    }

    // <type>	::=  int | boolean | String
    private void procType() {
        if ( current.type == TokenType.INTEGER ) { eat(TokenType.INTEGER); }
        else if ( current.type == TokenType.BOOLEAN ) { eat(TokenType.BOOLEAN); }
        else if ( current.type == TokenType.STRING  ) { eat(TokenType.STRING);  }
        else { showError(); }
    }

    // <name>	::=  <id>
    private void procName() {
        eat(TokenType.ID);
    }



}
