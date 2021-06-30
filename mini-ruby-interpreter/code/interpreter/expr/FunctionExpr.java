package interpreter.expr;

import java.util.Vector;

import interpreter.util.Utils;
import interpreter.value.ArrayValue;
import interpreter.value.IntegerValue;
import interpreter.value.StringValue;
import interpreter.value.Value;

public class FunctionExpr extends Expr{
    
    private Expr expr;
    private FunctionOp op;

    public FunctionExpr (int line, Expr expr, FunctionOp op) {
        super(line);
        this.expr = expr;
        this.op = op;
    }

    @Override
    public Value<?> expr () {
        Value<?> v = expr.expr();

        if (op == FunctionOp.LengthOp) {
            if (!(v instanceof ArrayValue)) {
                Utils.abort(super.getLine());
            } else {
                ArrayValue av = (ArrayValue) v;
                Vector<Value<?>> n = av.value();
                int size = n.size();
                IntegerValue nsize = new IntegerValue(size);
                v = nsize;
            }

        } else if (op == FunctionOp.ToIntOp) {
            if (!(v instanceof StringValue)) {
                Utils.abort(super.getLine());
            } else {
                int n = Integer.parseInt(v.toString());                
                IntegerValue niv = new IntegerValue(n);
                v = niv;
            }

        } else if (op == FunctionOp.ToStringOp) {
            String s = v.toString();
            StringValue nsv = new StringValue (s);
            v = nsv;
        }

        return v;
    }

}
