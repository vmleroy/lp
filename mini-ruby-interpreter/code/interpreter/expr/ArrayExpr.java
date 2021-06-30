package interpreter.expr;

import java.util.List;
import java.util.Vector;

import interpreter.value.ArrayValue;
import interpreter.value.Value;

public class ArrayExpr extends Expr {
    
    private List<Expr> exprs;

    public ArrayExpr (int line, List<Expr> exprs) {
        super(line);
        this.exprs = exprs;
    }

    @Override
    public Value<?> expr () {
        Vector<Value<?>> res = new Vector<Value<?>>();
        
        for (Expr value : exprs) {  
            Value<?> v = value.expr();  
            res.add(v);
        }

        ArrayValue av = new ArrayValue(res);
        return av;
    }
}
