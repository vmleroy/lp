package lexical;

public enum TokenType {

    // SPECIALS
    UNEXPECTED_EOF,
    INVALID_TOKEN,
    END_OF_FILE,

    // SYMBOLS
    SEMI_COLON,    // ;
    COMMA,         // ,
    OPEN_PAR,      // (
    CLOSE_PAR,     // )
    OPEN_BRA,      // {
    CLOSE_BRA,     // }
    
    // KEYWORDS
    INTERFACE,     // interface
    EXTENDS,       // extends

    // TYPES & RETURN
    INTEGER,       // integer
    STRING,        // string
    BOOLEAN,       // boolean
    VOID,           // void

    // OTHERS
    ID            // identifier
    
};
