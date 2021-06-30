package interpreter.expr;

import interpreter.util.Memory;
import interpreter.value.Value;

public class Variable extends SetExpr {
    
    private String name;

    public Variable (int line, String name) {
        super(line);
        this.name = name;
    }

    public String getName () {
        return this.name;
    }

    @Override
    public Value<?> expr () {
        return Memory.read(name);
    }

    @Override
    public void setValue (Value<?> value) {
        //System.out.println("Variable" + " - " + value.value() + " - " + name);
        Memory.write(name, value);
    }
}
