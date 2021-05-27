JFLAGS = -g
JC = javac

.SUFFIXES: .java .class
.java.class:
        $(JC) $(JFLAGS) $*.java

CLASSES = \
        miniRuby.java \
        Lexeme.java \
        LexicalAnalysis.java \
        LexicalException.java \
		SymbolTable.java \
		TokenType.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
        $(RM) *.class
