package interpreter.expr;

import java.util.Vector;

import interpreter.util.Utils;
import interpreter.value.ArrayValue;
import interpreter.value.IntegerValue;
import interpreter.value.StringValue;
import interpreter.value.Value;

public class AccessExpr extends SetExpr {
    
    private Expr base;
    private Expr index;

    public AccessExpr (int line, Expr base, Expr index) {
        super(line);
        this.base = base;
        this.index = index;
    }

    @Override
    public Value<?> expr() {
        Value<?> v = base.expr();
        //System.out.println("Access" + " - " + v.value());
        if (this.index != null) {            
            if (!(v instanceof ArrayValue)) {
                Utils.abort(super.getLine());
            } else {
                Value<?> ind = this.index.expr();
                int index = 0;
                ArrayValue av = (ArrayValue) v;
                Vector<Value<?> > vector = av.value();
                
                if(ind instanceof IntegerValue) {
                    index = Integer.parseInt(ind.toString());
                } else if (ind instanceof StringValue) {
                    index = Integer.parseInt(ind.toString());
                } else {
                    Utils.abort(super.getLine());
                }

                return vector.get(index);
            }            

        } 
          
        return v;
    }

    @Override
    public void setValue(Value<?> value) {
        Value<?> v = base.expr();      
        SetExpr sexpr = (SetExpr) base;     
        
        if (this.index != null) {            
            if (!(v instanceof ArrayValue)) {
                Utils.abort(super.getLine());
            } else {
                Value<?> ind = this.index.expr();
                int index = 0;
                ArrayValue av = (ArrayValue) v;
                Vector<Value<?> > vector = av.value();
                
                if(ind instanceof IntegerValue) {
                    index = Integer.parseInt(ind.toString());
                } else if (ind instanceof StringValue) {
                    index = Integer.parseInt(ind.toString());
                } else {
                    Utils.abort(super.getLine());
                }

                vector.set(index, value);                
                ArrayValue nav = new ArrayValue (vector);
                                
                //System.out.println("Access" + " - " + nav.value());
                sexpr.setValue(nav);   
                             
            }            
        } 
        
        else {
            if (value instanceof IntegerValue) {
                IntegerValue iv = (IntegerValue) value;
                int n = iv.value();
                IntegerValue niv = new IntegerValue(n);
                //System.out.println("Access Integer" + " - " + niv.value());
                sexpr.setValue(niv);
                             
            } else if (value instanceof StringValue) {
                StringValue sv = (StringValue) value;
                String n = sv.value();
                StringValue nsv = new StringValue(n);
                //System.out.println("Access String" + " - " + nsv.value());             
                sexpr.setValue(nsv);
                
                
            } else {
                //System.out.println("Access Array" + " - " + value.value());             
                sexpr.setValue(value);
                
            }
        } 
    }

}
