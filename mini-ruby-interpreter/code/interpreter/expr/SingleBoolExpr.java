package interpreter.expr;

import java.util.Vector;

import interpreter.util.Utils;
import interpreter.value.ArrayValue;
import interpreter.value.IntegerValue;
import interpreter.value.StringValue;
import interpreter.value.Value;

public class SingleBoolExpr extends BoolExpr{
    
    private Expr left;
    private Expr right;
    private RelOp op;

    public SingleBoolExpr (int line, Expr left, Expr right, RelOp op) {
        super(line);
        this.left = left;
        this.right = right;
        this.op = op;
    }

    public Boolean expr() {
        Boolean b = null;
        Value<?> left = this.left.expr();
        Value<?> right = this.right.expr();

        switch (op) {
            case EqualsOp:
                    if (left instanceof IntegerValue && right instanceof IntegerValue) {
                        int lv = Integer.parseInt(left.toString());
                        int rv = Integer.parseInt(right.toString());
                        if (lv == rv) b = true;
                        else b = false;

                    } else if (left instanceof StringValue && right instanceof StringValue) {
                        String lv = left.toString();
                        String rv = right.toString();
                        if (lv == rv) b = true;                        
                        else b = false;

                    } else {
                        Utils.abort(super.getLine());
                    }    

                break;
            case NotEqualsOp:
                    if (left instanceof IntegerValue && right instanceof IntegerValue) {
                        int lv = Integer.parseInt(left.toString());
                        int rv = Integer.parseInt(right.toString());
                        if (lv != rv) b = true;
                        else b = false;

                    } else if (left instanceof StringValue && right instanceof StringValue) {
                        String lv = left.toString();
                        String rv = right.toString();
                        if (lv != rv) b = true;  
                        else b = false;

                    } else {
                        Utils.abort(super.getLine());
                    }

                break;
            case LowerThanOp:
                    if (left instanceof IntegerValue && right instanceof IntegerValue) {
                        int lv = Integer.parseInt(left.toString());
                        int rv = Integer.parseInt(right.toString());
                        if (lv < rv) b = true;
                        else b = false;

                    } else {
                        Utils.abort(super.getLine());
                    }

                break;
            case LowerEqualOp:
                    if (left instanceof IntegerValue && right instanceof IntegerValue) {
                        int lv = Integer.parseInt(left.toString());
                        int rv = Integer.parseInt(right.toString());
                        if (lv <= rv) b = true;
                        else b = false;

                    } else {
                        Utils.abort(super.getLine());
                    }

                break;
            case GreaterThanOp:
                    if (left instanceof IntegerValue && right instanceof IntegerValue) {
                        int lv = Integer.parseInt(left.toString());
                        int rv = Integer.parseInt(right.toString());
                        if (lv > rv) b = true;
                        else b = false;

                    } else {
                        Utils.abort(super.getLine());
                    }

                break;
            case GreaterEqualOp:
                    if (left instanceof IntegerValue && right instanceof IntegerValue) {
                        int lv = Integer.parseInt(left.toString());
                        int rv = Integer.parseInt(right.toString());
                        if (lv >= rv) b = true;
                        else b = false;

                    } else {
                        Utils.abort(super.getLine());
                    }
                    
                break;
            case ContainsOp:
                    if (left instanceof IntegerValue && right instanceof ArrayValue) {
                        //int lv = Integer.parseInt(left.toString());
                        ArrayValue arv = (ArrayValue) right;
                        Vector<Value<?> > rvec = arv.value();
                        if (rvec.contains(left)) b = true;
                        else b = false;
                    } else if (left instanceof StringValue && right instanceof ArrayValue) {
                        //String lv = left.toString();
                        ArrayValue arv = (ArrayValue) right;
                        Vector<Value<?> > rvec = arv.value();
                        if (rvec.contains(left)) b = true;
                        else b = false;
                    } else {
                        Utils.abort(super.getLine());
                    }
                break;
        }

        return b;
    }

}
