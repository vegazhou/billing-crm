package com.kt.common.util;

import java.util.Hashtable;

public class SqlHelper {


	final static String INVALID_NUMBER = "String value is not a valid number";
	final static String INVALID_STRING = "String value is not a valid Oracle string";
	final static String INVALID_FIELD_NAME = "String value is not a valid Oracle DB table field name";
	final static String INVALID_PARAM = "String value is not a valid Oracle DB table SQL parameter";
	final static String INVALID_ORDER_TYPE = "String value is not a valid Oracle DB SQL order type";
	final static String INVALID_ORDERBY_LIST = "String value is not a valid Oracle DB SQL order by list";
	final static String ORDER_BY_LIST_TOO_LONG = "Order by list too long";

	final static String LIST_SPLITTER = ",";
	final static int ORACLE_FIELD_NAME_MAX_SIZE = 30;
	final static int ORDER_BY_LIST_SIZE = 256;


	/** Token type value */
	final static char INVALID_TOKEN = (char)0;
	final static char READY = (char)0;		// Ready to accept order by XXX value
	final static char IDENTIFIER = 'A';		// identifer, can followed by asc .(,||
	final static char IDENTIFIER2 = 'B';	// tableName.fieldName, can followed by asc (,||
	final static char IDENTIFIER3 = 'C';	// fieldName asc, can followed by ,
	final static char NUMBER = '0';			// similar to IDENTIFIER2
	final static char STRING = '\'';		// similar to IDENTIFIER2
	final static char FUNC_START = '(';		// Followed by field list(asc not supported)
	final static char FUNC_END = ')';		// similar to IDENTIFIER2
	final static char CONCAT = '|';			// Followed by field(asc not supported) or ,
	final static char ADD = '+';			// ...
	final static char SUB = '-';			// ...
	final static char MUL = '*';			// ...
	final static char DIV = '/';			// ...
	final static char DOT = '.';			// Followed by identifier
		final static String DOT_ESCAPE = "\\x2e";
	final static char COMMA = ',';			// Followed by field list, like READY

	final static char INVALID_CHAR = (char)-1;

	static Hashtable hIdentifierChars = null;

	/** Oracle allow letter, digit and $_# char used in field name */
	static
	{
		hIdentifierChars = new Hashtable();
		for (char c = 'A'; c <= 'Z'; c ++) {
			hIdentifierChars.put(new Character(c), new Object());
			hIdentifierChars.put(new Character((char)(c + 0x20)), new Object());
		}
		for (char c = '0'; c <= '9'; c ++) {
			hIdentifierChars.put(new Character(c), new Object());
		}
		hIdentifierChars.put(new Character('$'), new Object());
		hIdentifierChars.put(new Character('_'), new Object());
		hIdentifierChars.put(new Character('#'), new Object());
	}

	final static String[] VALID_ORDER_TYPE = new String[] {"asc", "desc"};

	public static void validateOrderType(String value) {
		if (value == null || value.trim().length() == 0) return;

		value = value.trim();

		boolean match = false;

		for (int i = 0; i < VALID_ORDER_TYPE.length; i ++)
			if (value.equalsIgnoreCase(VALID_ORDER_TYPE[i])) {
				match = true;
				break;
			}

		if (!match) {
			throw new IllegalArgumentException(INVALID_ORDER_TYPE + ": " + value);
		}

	}

	static boolean isFieldState(char state) {
		return state == IDENTIFIER || state == IDENTIFIER2 || state == NUMBER || state == STRING;
	}



	/**
	 * Following methods help to do grammar analysis on order by field list
	 * */

	static class Int {
		int v;
		public Int(int v) {this.v = v;}
	}

	static char getToken(String value, Int p) {
		skipSpace(value, p);

		if (p.v == value.length()) return INVALID_TOKEN;

		char c = nextChar(value, p);
		char token;

		// Letter started, identifier
		if (Character.isLetter(c)) {
			token = IDENTIFIER;

			c = nextChar(value, p);
			while (hIdentifierChars.get(new Character(c)) != null) {
				c = nextChar(value, p);
			}
			// return last char which should not be eaten here
			if (c != INVALID_CHAR) p.v --;

			return token;
		}
		// Digit started, number
		else if (Character.isDigit(c)) {
			token = NUMBER;

			c = nextChar(value, p);
			while (Character.isDigit(c)) {
				c = nextChar(value, p);
			}
			// return last char
			if (c != INVALID_CHAR) p.v --;

			return token;
		}
		// Single quote started, string
		else if (c == STRING) {
			token = STRING;

			boolean have1Quot = false;

			while (p.v < value.length()) {
				c = nextChar(value, p);

				// Current char is a single quot
				if (c == STRING) {
					// Two single quot K.O.
					if (have1Quot) have1Quot = false;
					// Found one single quot
					else have1Quot = true;
				}
				// Current char is not a single quot
				else {
					// Last char is single quot, return current char, string end,
					if (have1Quot) {
						p.v --;
						break;
					}
				}
			};
			// String not terminated by one single quot
			if (!have1Quot)
				throw new IllegalArgumentException(INVALID_ORDERBY_LIST);

			return token;
		}
		else if (c == FUNC_START || c == FUNC_END || c == DOT || c == COMMA
				|| c == ADD || c == SUB || c == MUL || c == DIV ) {
			return c;
		}
		else if (c == CONCAT) {
			c = nextChar(value, p);
			if ( c != CONCAT )
				throw new IllegalArgumentException(INVALID_ORDERBY_LIST);
			return CONCAT;
		}
		else throw new IllegalArgumentException(INVALID_ORDERBY_LIST);
	}

	static void skipSpace(String value, Int p) {
		char c = nextChar(value, p);

		// Space and tab are legal space chars
		while ((c == '\t' || c == ' ')) c = nextChar(value, p);

		// return last non-space cahr
		if (c != INVALID_CHAR)
			p.v --;
	}

	static char nextChar(String value, Int p) {
		if (p.v == value.length()) return INVALID_CHAR;
		return value.charAt(p.v ++);
	}


	/** This is the powerful version of validateOrderByList() which support
	 * nearly all possible values of xxx in order by xxx
	 */
	public static void validateOrderByList(String value)
	{
		validateOrderByList(value, new Int(0), 0);
	}

	public static void validateOrderByList(String value, Int p, int depth/* function call depth */)
	{
		if (value == null) return;
		value = value.trim();
		if (value.length() == 0) return;

		try {
			if (value.length() > ORDER_BY_LIST_SIZE)
				throw new IllegalArgumentException(ORDER_BY_LIST_TOO_LONG);

			char state = READY;

			// A state machine to do grammar analysis
			do {
				int tokenStart = p.v;
				char newToken = getToken(value, p);

				// At the end of "value"
				if (newToken == INVALID_TOKEN) {
					// orderBy XXX should end with complete field state
					if (isFieldState(state) || state == IDENTIFIER3 && depth == 0) return;
                    else throw new IllegalArgumentException(INVALID_ORDERBY_LIST);
				}
				// fieldName ) at inner function call, return ) to caller
				if (newToken == FUNC_END) {
					if (depth > 0 && isFieldState(state)) {
						p.v --;
						return;
					}
					else throw new IllegalArgumentException(INVALID_ORDERBY_LIST);
				}


				switch (state) {
				// First token should be identifier, number or string
				case READY:
					if (newToken == IDENTIFIER)
						state = IDENTIFIER;
					else if (newToken == STRING || newToken == NUMBER)
						state = IDENTIFIER2;
					// Fix a bug, to support +1, -3
					else if (newToken == ADD || newToken == SUB)
						state = READY;
					else
						throw new IllegalArgumentException(INVALID_ORDERBY_LIST);
					break;
				// identifier can followed by ( asc ) , || .
				case IDENTIFIER:
					switch (newToken) {
					// func(
					case FUNC_START:
						// call myself to process function parameter list as field list
						validateOrderByList(value, p, depth + 1);

						newToken = getToken(value, p);

						// Only thing after function parameter list is )
						if (newToken != FUNC_END)
							throw new IllegalArgumentException(INVALID_ORDERBY_LIST);

						// func(a, b) is very like table.field
						state = IDENTIFIER2;

						break;
					// fieldName asc
					case IDENTIFIER:
						// Only root level can have "fieldName asc"
						if (depth == 0)
							validateOrderType(value.substring(tokenStart, p.v));
						else
							throw new IllegalArgumentException(INVALID_ORDERBY_LIST);
						state = IDENTIFIER3;
						break;
					// fieldName|| or fieldName, return READY state to accept anything
					case COMMA:
					case ADD:
					case SUB:
					case MUL:
					case DIV:
					case CONCAT: state = READY; break;
					// fieldName. should only be "tableName.fieldName"
					case DOT:
						newToken = getToken(value, p);
						if (newToken != IDENTIFIER)
							throw new IllegalArgumentException(INVALID_ORDERBY_LIST);
						state = IDENTIFIER2;
						break;
					default:
						throw new IllegalArgumentException(INVALID_ORDERBY_LIST);
					}
					break;
				// table.field can followed by asc , ||
				case IDENTIFIER2:
					switch (newToken) {
					case IDENTIFIER:
						if (depth == 0)
							validateOrderType(value.substring(tokenStart, p.v));
						else
							throw new IllegalArgumentException(INVALID_ORDERBY_LIST);
						state = IDENTIFIER3;
						break;
					case COMMA:
					case ADD:
					case SUB:
					case MUL:
					case DIV:
					case CONCAT: state = READY; break;
					default:
						throw new IllegalArgumentException(INVALID_ORDERBY_LIST);
					}
					break;
				// The only thing can after order by table.field asc is ,
				case IDENTIFIER3:
					case COMMA: state = READY; break;
					default:
						throw new IllegalArgumentException(INVALID_ORDERBY_LIST);
				}
			} while (true);
		}
		catch (Exception e) {
			throw new IllegalArgumentException(INVALID_ORDERBY_LIST + ": " + value);
		}
	}




}
