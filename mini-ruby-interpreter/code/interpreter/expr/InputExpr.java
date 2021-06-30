package interpreter.expr;

import java.util.Random;
import java.util.Scanner;

import interpreter.util.Utils;
import interpreter.value.IntegerValue;
import interpreter.value.StringValue;
import interpreter.value.Value;

public class InputExpr extends Expr{
    
    private InputOp op;
    private static Scanner sc = new Scanner (System.in);
    private static Random rand = new Random();

    public InputExpr (int line, InputOp op) {
        super(line);
        this.op = op;
    }

    @Override
    public Value<?> expr () {
        Value<?> v = null;
        if (op == InputOp.GetsOp) {
            String str = sc.nextLine().trim();
            StringValue value = new StringValue(str);
            v = value;
            return v;
        } else if (op == InputOp.RandOp) {            
            int n = rand.nextInt();
            IntegerValue value = new IntegerValue(n);
            v = value;
            return v;
        } else {
            Utils.abort(super.getLine());
        }
        return v;
    }
    
}
