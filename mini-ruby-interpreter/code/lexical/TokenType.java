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
	ELSIF,			// else if
	ELSE,			// else

	// Constants
	INTEGER,		// integer
	STRING,			// string
	// ARRAY,			// arrays

	// Values
	ID, 			// variable

	// Arithmetic Operands
	ADD,			// + (can be used to unify on arrays and strings)
	SUB,			// -
	MULT,			// *
	DIV,			// /
	MOD, 			// % (only for integers)
	POW, 			// **
	RANGE_WITH,		// ..  inclusive (ex: 3..5 = [3,4,5])
	RANGE_WITHOUT,	// ... exclusive (ex: 3...4 = [3,4])

	// Logic Operands
	EQUAL, 			// ==
	NOT_EQUAL,		// !=
	LESS,			// <
	GREATER, 		// >
	LESS_EQ,		// <=
	GREATER_EQ,		// >=
	CONTAIN, 		// ===
	NOT,			// not (!)

	// Connector
	AND,			// &&
	OR,				// ||

	// Func
	GETS,			// gets
	RAND, 			// rand
	LENGTH,			// .length
	TOI,			// .to_i (string to integer, if not possible, 0)
	TOS, 			// .to_s (interger to string)

	// Symbols
	ASSIGN,			// = (min, max = max, min)
	SEMI_COLON,		// ;
	OPEN_PAR,		// (
	CLOSE_PAR,		// )
	OPEN_BRA,		// [
	CLOSE_BRA,		// ]
	COMMA,			// ,
	DOT,			// .

	// Reserved words
	IN,				// for
	DO,				// {
	THEN,			// {
	END,			// }

	// Specials
	UNEXPECTED_EOF,
	INVALID_TOKEN,
	END_OF_FILE

}
