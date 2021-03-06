package interpreter.expr;

public class NotBoolExpr extends BoolExpr{
    
    private BoolExpr expr;

    public NotBoolExpr (int line, BoolExpr expr) {
        super(line);
        this.expr = expr;
    }

    public Boolean expr () {
        return !expr.expr();
    }
    
}
