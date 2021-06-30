package interpreter.expr;


public class CompositeBoolExpr extends BoolExpr {
    
    private BoolExpr left;
    private BoolExpr right;
    private BoolOp op;

    public CompositeBoolExpr (int line, BoolExpr left, BoolExpr right, BoolOp op) {
        super(line);
        this.left = left;
        this.right = right;
        this.op = op;
    }

    public Boolean expr() {
        Boolean b = null;
        Boolean bleft = left.expr();
        Boolean bright = right.expr();

        switch (op) {
            case And:
                    if (bleft && bright) b = true;
                    else b = false;
                break;
            case Or:
                    if (bleft || bright) b = true;
                    else b = false;
                break;
        }

        return b;
    }
}
