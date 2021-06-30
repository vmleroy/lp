package interpreter.command;

import java.util.ArrayList;
import java.util.List;

import interpreter.expr.Expr;
import interpreter.expr.SetExpr;
import interpreter.util.Utils;
import interpreter.value.Value;

public class AssignCommand extends Command {
    
    private List<Expr> left;
    private List<Expr> right;

    public AssignCommand (int line, List<Expr> left, List<Expr> right) {
        super(line);
        this.left = left;
        this.right = right;
    }

    @Override
    public void execute () {
        if (left.size() != right.size()) {
            Utils.abort(super.getLine());
        } else {
            List<Value<?>> temp = new ArrayList<Value<?>>();
            for (int i=0; i<right.size(); i++) {
                temp.add(right.get(i).expr());
                //System.out.println(right.get(i).expr() + "," + temp.get(i));
            }
        

            for (int i=0; i<left.size(); i++) {
                if (!(left.get(i) instanceof SetExpr)) {
                    Utils.abort(super.getLine());
                } else {
                    SetExpr sexpr = (SetExpr) left.get(i);
                    //System.out.println("Assign" + ": " + temp.get(i));
                    sexpr.setValue(temp.get(i));
                }
            }
        }

    }   

    
}
