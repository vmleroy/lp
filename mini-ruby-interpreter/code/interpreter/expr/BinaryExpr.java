package interpreter.expr;

import java.util.Vector;

import interpreter.util.Utils;
import interpreter.value.ArrayValue;
import interpreter.value.IntegerValue;
import interpreter.value.StringValue;
import interpreter.value.Value;

public class BinaryExpr extends Expr {
    
    private Expr left;
    private Expr right;
    private BinaryOp op;

    public BinaryExpr (int line, Expr left, Expr right, BinaryOp op) {
        super(line);
        this.left = left;
        this.right = right;
        this.op = op;
    }

    @Override
    public Value<?> expr () {
        Value<?> v = null;
        Value<?> left = this.left.expr();
        Value<?> right = this.right.expr();

        switch (op) {
            case RangeWithOp:
                    if (left instanceof IntegerValue && right instanceof IntegerValue) {
                        int lv = Integer.parseInt(left.toString());
                        int rv = Integer.parseInt(right.toString());
                        System.out.println(lv + " " + rv);
                        Vector<Value<?>> res = new Vector<Value<?>>();
                        
                        if(lv < rv) {
                            for (int i=lv; i<=rv; i++) {
                                IntegerValue iv = new IntegerValue(i);
                                res.add(iv);
                            }
                        } else if (lv > rv) {
                            for (int i=lv; i>=rv; i--) {
                                IntegerValue iv = new IntegerValue(i);
                                res.add(iv);
                            }
                        } else {
                            int i = lv;
                            IntegerValue iv = new IntegerValue(i);
                            res.add(iv);
                        }
                        
                        ArrayValue nav = new ArrayValue(res);
                        v = nav;       

                    } else {
                        Utils.abort(super.getLine());
                    }
                break;

            case RangeWithoutOp:
                    if (left instanceof IntegerValue && right instanceof IntegerValue) {
                        int lv = Integer.parseInt(left.toString());
                        int rv = Integer.parseInt(right.toString());
                        Vector<Value<?>> res = new Vector<Value<?>>();
                        
                        if(lv < rv) {
                            for (int i=lv; i<rv; i++) {
                                IntegerValue iv = new IntegerValue(i);
                                res.add(iv);
                            }
                        } else if (lv > rv) {
                            for (int i=lv; i>rv; i--) {
                                IntegerValue iv = new IntegerValue(i);
                                res.add(iv);
                            }
                        } 
                        
                        ArrayValue nav = new ArrayValue(res);
                        v = nav;

                    } else {
                        Utils.abort(super.getLine());
                    }
                break;

            case AddOp:
                    if (left instanceof IntegerValue && right instanceof IntegerValue) {
                        int lv = Integer.parseInt(left.toString());
                        int rv = Integer.parseInt(right.toString());
                        int value = lv + rv;
                        IntegerValue niv = new IntegerValue (value);
                        v = niv;

                    } else if (left instanceof ArrayValue && right instanceof ArrayValue) {
                        Vector<Value<?>> res = new Vector<Value<?>>();
                        
                        ArrayValue lv = (ArrayValue) left;
                        Vector<Value<?> > lvec = lv.value();
                        ArrayValue rv = (ArrayValue) right;
                        Vector<Value<?> > rvec = rv.value();

                        res.addAll(lvec);
                        res.addAll(rvec);

                        ArrayValue nav = new ArrayValue(res);
                        v = nav;

                    } else if (left instanceof StringValue && right instanceof StringValue) {
                        String lv = left.toString();
                        //lv = lv.replace("\'", "");
                        String rv = right.toString();
                        //rv = rv.replace("\'", "");
                        String value = lv + rv;
                        // String cotes = "\'";
                        // value = cotes + value + cotes;
                        StringValue siv = new StringValue (value);
                        v = siv;

                    } else {
                        Utils.abort(super.getLine());
                    }
                break;

            case SubOp:
                    if (left instanceof IntegerValue && right instanceof IntegerValue) {
                        int lv = Integer.parseInt(left.toString());
                        int rv = Integer.parseInt(right.toString());
                        int value = lv - rv;
                        IntegerValue niv = new IntegerValue (value);
                        v = niv;
                    } else {
                        Utils.abort(super.getLine());
                    }
                break;

            case MulOp:
                    if (left instanceof IntegerValue && right instanceof IntegerValue) {
                        int lv = Integer.parseInt(left.toString());
                        int rv = Integer.parseInt(right.toString());
                        int value = lv * rv;
                        IntegerValue niv = new IntegerValue (value);
                        v = niv;
                    } else {
                        Utils.abort(super.getLine());
                    }
                break;

            case DivOp:
                    if (left instanceof IntegerValue && right instanceof IntegerValue) {
                        int lv = Integer.parseInt(left.toString());
                        int rv = Integer.parseInt(right.toString());
                        int value = lv / rv;
                        IntegerValue niv = new IntegerValue (value);
                        v = niv;
                    } else {
                        Utils.abort(super.getLine());
                    }
                break;

            case ModOp:
                    if (left instanceof IntegerValue && right instanceof IntegerValue) {
                        int lv = Integer.parseInt(left.toString());
                        int rv = Integer.parseInt(right.toString());
                        int value = lv % rv;
                        IntegerValue niv = new IntegerValue (value);
                        v = niv;
                    } else {
                        Utils.abort(super.getLine());
                    }
                break;

            case ExpOp:
                    if (left instanceof IntegerValue && right instanceof IntegerValue) {
                        int lv = Integer.parseInt(left.toString());
                        int rv = Integer.parseInt(right.toString());
                        int value = (int) Math.pow(lv,rv);
                        IntegerValue niv = new IntegerValue (value);
                        v = niv;
                    } else {
                        Utils.abort(super.getLine());
                    }
                break;
        }

        return v;
    }
}
