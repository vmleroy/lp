package code;
// import code.lexical.Lexeme;
import code.lexical.LexicalAnalysis;
// import code.lexical.TokenType;
import code.syntatic.SyntaticAnalysis;

public class miniRuby {
    public static void main (String args[]) {
		// Make sure that is only one file name in the execution
		if (args.length != 1) {
			System.out.println("Usage: java miniRuby [source file]");
			if (args.length > 1) {
				System.out.println("Note: use only one source file");
			}
			return;
    	}

		// Test if can open the archive, if not, throw exception, else run the program
		try (LexicalAnalysis l = new LexicalAnalysis(args[0])) {
			// Lexeme lex;
			// while((lex = l.nextToken()).type != TokenType.END_OF_FILE){
			// 	System.out.println("Token: " + lex.token + " || " + "Type: " + lex.type + "\n");
			// }
			
			SyntaticAnalysis syntatic = new SyntaticAnalysis(l);
			syntatic.start();
			
			//BlocksCommand program = syntatic.start();
			//program.execute();

		} catch (Exception e) {
			System.err.println("Internal error: " + e.getMessage());
		}

	}
}
