import interpreter.command.Command;
import lexical.LexicalAnalysis;
import syntatic.SyntaticAnalysis;

public class miniRuby {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java mrbi [miniRuby file]");
            return;
        }

        try (LexicalAnalysis l = new LexicalAnalysis(args[0])) {
            // O código a seguir é dado para testar o interpretador.
            // TODO: descomentar depois que o analisador léxico estiver OK.
			
            SyntaticAnalysis s = new SyntaticAnalysis(l);
            Command c = s.start();
            c.execute();
        } catch (Exception e) {
            System.err.println("Internal error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
