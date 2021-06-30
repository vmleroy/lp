package interpreter.command;

import interpreter.expr.BoolExpr;

public class IfCommand extends Command{
    
    private BoolExpr cond;
    private Command thencmds;
    private Command elsecmds;

    public IfCommand (int line, BoolExpr cond, Command thencmds, Command elsecmds) {
        super(line);
        this.cond = cond;
        this.thencmds = thencmds;
        this.elsecmds = elsecmds;
    }

    public void setElseCommands(Command elseCmds) {
        this.elsecmds = elseCmds;
    }
    
    public void execute () {
        if (cond.expr()) {
            thencmds.execute();
        } else {
            if (elsecmds != null) {
                elsecmds.execute();
            }
        }
    }


}
