import lexical.LexicalAnalysis;
import syntatic.SyntaticAnalysis;

public class mjar {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java mrbi [miniRuby file]");
            return;
        }

        try (LexicalAnalysis l = new LexicalAnalysis(args[0])) {
            
            SyntaticAnalysis s = new SyntaticAnalysis(l);
            s.start();
            
            System.out.println("Sim");

            // Lexeme lex = l.nextToken();
            // while (checkType(lex.type)) {
            //     System.out.printf("(\"%s\", %s)\n", lex.token, lex.type);
            //     lex = l.nextToken();
            // }

        } catch (Exception e) {
            System.err.println("Nao");
        }
    }
}
