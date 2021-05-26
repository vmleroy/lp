/* List to set token types for easier maintece and upgrade of the system */

package lexical;

public enum TokenType {
	// Commands
	IF,				// if
	UNLESS,			// else
	WHILE,			// while
	UNTIL,			// command while if expression is false
	FOR,			// for
	PRINT,			// print
	PUTS,			// puts

	// Constants
	INTEGER,		// integer
	STRING,			// string
	ARRAY,			// arrays
	BOOL, 			// boolean

	// Values
	VAR, 			// variable
	LITERAL,		// integer, string, array

	// Arithmetic Operands
	ADD,			// + (can be used to unify on arrays and strings)
	SUB,			// -
	MULT,			// *
	DIV,			// /
	MOD, 			// % (only for integers)
	POW, 			// **
	INCL,			// ..  inclusive (ex: 3..5 = [3,4,5])
	EXCL,			// ... exclusive (ex: 3...4 = [3,4])

	// Logic Operands
	EQUAL, 			// ==
	NOT_EQUAL,		// !=
	LESS,			// <
	GREATER, 		// >
	LESS_EQ,		// <=
	GREATER_EQ,		// >=
	CONTAIN, 		// ===
	NOT,			// not (!)

	// Func
	GETS,			// gets
	RAND, 			// rand
	LENGTH,			// .length
	TOI,			// .to_i (string to integer, if not possible, 0)
	TOS, 			// .to_s (interger to string)

	// Symbols
	ASSIGN,			// = (min, max = max, min)
	SEMICOLON,		// ;

	// Specials
	UNEXPECTED_EOF,
	INVALID_TOKEN,
	END_OF_FILE

}
