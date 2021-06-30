package interpreter.command;

import interpreter.expr.BoolExpr;

public class WhileCommand extends Command{
    
    private BoolExpr cond;
    private Command cmds;

    public WhileCommand (int line, BoolExpr cond, Command cmds) {
        super(line);
        this.cmds = cmds;
        this.cond = cond;
    }

    @Override
    public void execute() {
        while (cond.expr()) {
            cmds.execute();
        }
    }
}
