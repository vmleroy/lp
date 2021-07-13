package lexical;

import java.util.Map;
import java.util.HashMap;

public class SymbolTable {

    private Map<String, TokenType> st;

    public SymbolTable() {
        st = new HashMap<String, TokenType>();

        // SYMBOLS
        st.put(";", TokenType.SEMI_COLON);
        st.put(",", TokenType.COMMA);
        st.put("(", TokenType.OPEN_PAR);
        st.put(")", TokenType.CLOSE_PAR);
        st.put("{", TokenType.OPEN_BRA);
        st.put("}", TokenType.CLOSE_BRA);

        // KEYWORDS
        st.put("void", TokenType.VOID);
        st.put("int", TokenType.INTEGER);
        st.put("boolean", TokenType.BOOLEAN);
        st.put("String", TokenType.STRING);
        st.put("interface", TokenType.INTERFACE);
        st.put("extends", TokenType.EXTENDS);
        
    }

    public boolean contains(String token) {
        return st.containsKey(token);
    }

    public TokenType find(String token) {
        return this.contains(token) ?
            st.get(token) : TokenType.ID;
    }
}
