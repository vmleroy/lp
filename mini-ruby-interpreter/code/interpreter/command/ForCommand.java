package interpreter.command;

import java.util.Vector;

import interpreter.expr.Expr;
import interpreter.expr.Variable;
import interpreter.util.Utils;
import interpreter.value.ArrayValue;
import interpreter.value.IntegerValue;
import interpreter.value.Value;

public class ForCommand extends Command{
    
    private Variable var;
    private Expr expr;
    private Command cmds;

    public ForCommand (int line, Variable var, Expr expr, Command cmds) {
        super(line);
        this.var = var;
        this.expr = expr;
        this.cmds = cmds;
    }

    @Override
    public void execute () {           
        Value<?> v = expr.expr();

        if (v instanceof IntegerValue) {            
            var.setValue(new IntegerValue(0));
            int i = Integer.parseInt(var.expr().toString());
            int iv = Integer.parseInt(v.toString());
            while (i < iv) {
                cmds.execute();
                i++;
                var.setValue(new IntegerValue(i));
            }
        } else if (v instanceof ArrayValue) {                       
            ArrayValue av = (ArrayValue) v;
            Vector<Value<?>> vec = av.value();
            int i = Integer.parseInt(vec.firstElement().toString());
            var.setValue(new IntegerValue(i));
            int last = Integer.parseInt(vec.lastElement().toString());              
            while (i < last) {
                cmds.execute();
                i++;
                var.setValue(new IntegerValue(i));
            }            
        } else {
            Utils.abort(super.getLine());
        }
        
    }
}
