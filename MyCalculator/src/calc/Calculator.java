package calc;

public class Calculator {
	public final static char DIVIDE_SYMBOL = '÷';
	public final static char MULTIPLY_SYMBOL = '×';
	public final static char SQUARE_ROOT_SYMBOL = '√';
	public final static char MINUS_SYMBOL = '−';
	public final static char PI_SYMBOL = 'π';
	
	double result = 0;
	String expression;
	
	public Calculator() {
		this.expression = "";
	}
	
	public Calculator(String exp) {
		this.expression = exp;
		this.expression = this.expression.replaceAll(" ", "");
	}
	
	public static void main(String[] args) {
		Calculator calc = new Calculator();
		System.out.println(calc.validate("1+1"));
	}
	
	public void express(String exp) {
		this.expression = exp;
		this.expression = this.expression.replaceAll(" ", "");
	}
	
	public double solver() {
		return solver(this.expression);
	}
	
	public boolean validate(String exp) {
		// searches through the expression if there were any open brackets
		for(char x: exp.toCharArray()) {
			if(checkOperator(x) == OPERATOR_TYPE.PARENTHESE) {
				boolean bracketPair = false;
				for(char X:exp.substring(exp.indexOf(x), exp.length()).toCharArray()) {
					if(exp.charAt(exp.indexOf(X)) == ')') {
						bracketPair = true;
						break;
					}
				}
				if(!bracketPair) return false;
			}
		}
		
		for(char x: exp.toCharArray()) {
			if(x == ')') {
				boolean bracketPair = false;
				for(int i = exp.indexOf(x)-1; i >= 0; i--) {
					if(exp.charAt(i) == '(') {
						bracketPair = true;
						break;
					}
				}
				if(!bracketPair) return false;
			}
		}
		
		//searches through the expression if there were operators or inverses next to each other
		for(int i = 0; i < exp.length(); i++) {
			if(checkOperator(exp.charAt(i)) != null) {
				if(i+1 >= exp.length()) return false;
				else {
					if(checkOperator(exp.charAt(i+1)) != null && checkOperator(exp.charAt(i+1)) != OPERATOR_TYPE.PARENTHESE && checkOperator(exp.charAt(i+1)) != OPERATOR_TYPE.SQUARE_ROOT) {
						return false;
					}
				}
			} else if(exp.charAt(i) == 'i') {
				if(i+1 >= exp.length()) return false;
				else {
					if((i+1 < exp.length()) && (exp.charAt(i+1) == 'i')) {
						return false;
					}
				}
			} else if(exp.charAt(i) == PI_SYMBOL) {
				if(i+1 >= exp.length()) {}
				else if(exp.charAt(i+1) == PI_SYMBOL) return false;
			}
		}
		
		//checks if there are valid operator
		for(int i = 0; i < exp.length(); i++) {
			if(checkOperator(exp.charAt(i)) == OPERATOR_TYPE.SQUARE_ROOT) {
				if(i+1 >= exp.length()) return false;
				if(exp.charAt(i+1) == '(' || Character.isDigit(exp.charAt(i+1))) {
					continue;
				} else return false;
			} else if(checkOperator(exp.charAt(i)) == OPERATOR_TYPE.PARENTHESE) {
				if(i+1 >= exp.length()) return false;
				if(checkOperator(exp.charAt(i+1)) != null) return false;
			} else if(checkOperator(exp.charAt(i)) != null) {
				if(i-1 < 0 || i+1 >= exp.length()) return false;
				if(!(Character.isDigit(exp.charAt(i-1)) || exp.charAt(i-1) == ')')) return false;
				if(!(Character.isDigit(exp.charAt(i+1)) || exp.charAt(i+1) == '(')) return false;
			}
		}
		
		//checks if there are valid numbers
		for(char x: exp.toCharArray()) {
			if(x == '.') {
				for(char X:exp.substring(exp.indexOf(x)+1, exp.length()).toCharArray()) {
					if(X == '.') {
						return false;
					} else if(checkOperator(X) != null){
						break;
					}
				}
			}
		}
		
		
		return true;
	}
	
	public double solver(String exp) {
		// checking for square root
		for(char x:exp.toCharArray()) {
			if(checkOperator(x) == OPERATOR_TYPE.SQUARE_ROOT) {
				int frontInd = exp.indexOf(x), backInd = -1;
				
				if(exp.charAt(frontInd+1) == '(') {
					for(int i = frontInd+1; i < exp.length(); i++) {
						if(exp.charAt(i) == ')') {
							backInd = i;
							break;
						}
					}
				} else {
					for(int i = frontInd+1; i < exp.length(); i++) {
						if(checkOperator(exp.charAt(i)) != null || i == exp.length() - 1) {
							backInd = i;
							break;
						}
					}
				}
				
				
				if(backInd == -1)continue;
				else {
					if(frontInd == 0 && backInd == exp.length() - 1) {
						if(exp.charAt(frontInd+1) == '(') {
							return eval(exp.charAt(0)+ invert(solver(exp.substring(frontInd+2, backInd))));
						} else {
							return eval(exp);
						}
					} else if(frontInd == 0) {
						
						if(exp.charAt(frontInd+1) == '(') {
							String part = exp.substring(frontInd,backInd+1);
							return solver(invert(eval(part.charAt(0)+invert(solver(part.substring(2,part.length()-1))))) + exp.substring(backInd+1,exp.length()));
						} else {
							String part = exp.substring(frontInd,backInd);
							return solver(invert(eval(part)) + exp.substring(backInd,exp.length()));
						}
					} else if(backInd == exp.length() - 1) {
						
						if(exp.charAt(frontInd+1) == '(') {
							String part = exp.substring(frontInd, backInd+1);
							return solver(exp.substring(0,frontInd) + invert(eval(part.charAt(0) + invert(solver(part.substring(2,part.length()-1))))));
						} else {
							String part = exp.substring(frontInd, backInd+1);
							return solver(exp.substring(0,frontInd) +  invert(eval(part)));
						}
					} else {
						
						if(exp.charAt(frontInd+1) == '(') {
							String part = exp.substring(frontInd, backInd+1);
							return solver(exp.substring(0,frontInd) + part.charAt(0) + invert(solver(part.substring(2,part.length()-1))) + exp.substring(backInd+1,exp.length()));
						} else {
							String part = exp.substring(frontInd, backInd);
							return solver(exp.substring(0,frontInd) + invert(eval(part)) + exp.substring(backInd,exp.length()));
						}
					}
				}
			}
				
		}
		
		//check for parentheses
		for(char x: exp.toCharArray()) {
			if(checkOperator(x) == OPERATOR_TYPE.PARENTHESE) {
				int frontInd = exp.indexOf(x), backInd = -1;
				
				for(char X: exp.substring(exp.indexOf(x), exp.length()).toCharArray()) {
					if(exp.charAt(exp.indexOf(X)) == ')') {
						backInd = exp.indexOf(X);
						break;
					}
				}
				
				if(backInd == -1) continue;
				else {
					if(frontInd == 0 && backInd == exp.length() - 1) {
						String part = exp.substring(frontInd+1, exp.length() - 1);
						return solver(part);
					} else if(frontInd == 0){
						String part = exp.substring(frontInd+1,backInd);
						return solver(invert(solver(part)) + exp.substring(backInd+1,exp.length()));
					} else if(backInd == exp.length()-1) {
						String part = exp.substring(frontInd+1,backInd);
						return solver(exp.substring(0,frontInd) + invert(solver(part)));
					} else {
						String part = exp.substring(frontInd+1,backInd);
						return solver(exp.substring(0,frontInd) + invert(solver(part)) + exp.substring(backInd+1,exp.length()));
					}
				}
			}
		}
		
		// check for exponent
		for(int j = exp.length() - 1; j >= 0; j--) {
			if(checkOperator(exp.charAt(j)) == OPERATOR_TYPE.POWER) {
				int frontInd = -1, backInd = -1;
				
				for(int i = j-1; i >= 0; i--) {
					if(checkOperator(exp.charAt(i)) != null || i == 0) {
						frontInd = i;
						break;
					}
				}
				
				for(int i = j+1; i < exp.length(); i++) {
					if(checkOperator(exp.charAt(i)) != null || i == exp.length() - 1) {
						backInd = i;
						break;
					}
				}
				
				if(frontInd == 0 && backInd == exp.length() - 1) {
					return eval(exp);
				} else if(frontInd == 0){
					String part = exp.substring(frontInd,backInd);
					return solver(invert(eval(part)) + exp.substring(backInd,exp.length()));
				} else if(backInd == exp.length()-1) {
					String part = exp.substring(frontInd+1,backInd+1);
					return solver(exp.substring(0,frontInd+1) + invert(eval(part)));
				} else {
					String part = exp.substring(frontInd,backInd);
					return solver(exp.substring(0,frontInd) + invert(eval(part)) + exp.substring(backInd,exp.length()));
				}
			}
		}
		
		// check for multiplier or divider
		for(char c: exp.toCharArray()) {
			if(checkOperator(c) == OPERATOR_TYPE.MULTIPLY || checkOperator(c) == OPERATOR_TYPE.DIVIDE) {
				int frontInd = -1, backInd = -1;
				
				for(int i = exp.indexOf(c)-1; i >= 0; i--) {
					if(checkOperator(exp.charAt(i)) != null || i == 0) {
						frontInd = i;
						break;
					}
				}
				
				for(int i = exp.indexOf(c)+1; i < exp.length(); i++) {
					if(checkOperator(exp.charAt(i)) != null || i == exp.length() - 1) {
						backInd = i;
						break;
					}
				}
				
				if(frontInd == 0 && backInd == exp.length() - 1) {
					return eval(exp);
				} else if(frontInd == 0){
					String part = exp.substring(frontInd,backInd);
					return solver(invert(eval(part)) + exp.substring(backInd,exp.length()));
				} else if(backInd == exp.length()-1) {
					String part = exp.substring(frontInd+1,backInd+1);
					return solver(exp.substring(0,frontInd+1) + invert(eval(part)));
				} else {
					String part = exp.substring(frontInd,backInd);
					return solver(exp.substring(0,frontInd) + invert(eval(part)) + exp.substring(backInd,exp.length()));
				}
			}
		}
		
		// check for addition or subtraction
		for(char c: exp.toCharArray()) {
			if(checkOperator(c) == OPERATOR_TYPE.PLUS || checkOperator(c) == OPERATOR_TYPE.MINUS) {
				int frontInd = -1, backInd = -1;
				
				for(int i = exp.indexOf(c)-1; i >= 0; i--) {
					if(checkOperator(exp.charAt(i)) != null || i == 0) {
						frontInd = i;
						break;
					}
				}
				
				for(int i = exp.indexOf(c)+1; i < exp.length(); i++) {
					if(checkOperator(exp.charAt(i)) != null || i == exp.length() - 1) {
						backInd = i;
						break;
					}
				}
				 
				if(frontInd == 0 && backInd == exp.length() - 1) {
					return eval(exp);
				} else if(frontInd == 0){
					String part = exp.substring(frontInd,backInd);
					return solver(invert(eval(part)) + exp.substring(backInd,exp.length()));
				} else if(backInd == exp.length()-1) {
					String part = exp.substring(frontInd+1,backInd+1);
					return solver(exp.substring(0,frontInd+1) + invert(eval(part)));
				} else {
					String part = exp.substring(frontInd,backInd);
					return solver(exp.substring(0,frontInd) + invert(eval(part)) + exp.substring(backInd,exp.length()));
				}
			}
		}
		
		return invert(exp);
	}
	
	// inverting functions
	public String invert(double inp) {
		if(inp < 0) {
			inp *= -1;
			return "i"+inp;
		} else return inp+"";
	}
	
	public double invert(String inp) {
		if(inp.charAt(0) == 'i') return Double.parseDouble(inp.substring(1, inp.length())) * -1;
		else if(inp.charAt(0) == PI_SYMBOL) return Math.PI;
		else return Double.parseDouble(inp);
	}
	
	// inverse the last number of the expression
	public String inverse(String input) {
		if(checkOperator(input.charAt(input.length()-1)) != null) return null;
		else if(input.charAt(input.length()-1) == ' ') return null;
		boolean negative = false;
		int frontInd = -1;
		
		for(int i = input.length()-1; i >= 0; i--) {
			if(input.charAt(i) == ' ' || checkOperator(input.charAt(i)) != null || input.charAt(i)=='i' || i == 0) {
				if(input.charAt(i) == 'i') negative = true;
				if(i == 0) frontInd = i;
				else frontInd = i+1;
				break;
			}
		}
		String part = input.substring(frontInd, input.length());
		part = part.replaceAll(" ", "");
		if(!negative) {
			part = 'i'+part;
			if(frontInd == 0) return part;
			else return input.substring(0,frontInd) + part;
		} else {
			if(frontInd == 0) return part.substring(1,part.length());
			else return input.substring(0,frontInd-1) + part;
		}
		
	}
	
	public String inverse(String input, char c) {
		if(checkOperator(input.charAt(input.length()-1)) != null) return null;
		else if(input.charAt(input.length()-1) == ' ') return null;
		boolean negative = false;
		int frontInd = -1;
		
		for(int i = input.length()-1; i >= 0; i--) {
			if(input.charAt(i) == ' ' || checkOperator(input.charAt(i)) != null || input.charAt(i)==c || i == 0) {
				if(input.charAt(i) == c) negative = true;
				if(i == 0) frontInd = i;
				else frontInd = i+1;
				break;
			}
		}
		String part = input.substring(frontInd, input.length());
		part = part.replaceAll(" ", "");
		if(!negative) {
			part = c+part;
			if(frontInd == 0) return part;
			else return input.substring(0,frontInd) + part;
		} else {
			if(frontInd == 0) return part.substring(1,part.length());
			else return input.substring(0,frontInd-1) + part;
		}
		
	}
	
	// evaluates the functions
	public double eval(String input) {
		int index = findOps(input);
		
		if(checkOperator(input.charAt(index)) == OPERATOR_TYPE.SQUARE_ROOT) {
			return Math.sqrt(invert(input.substring(index+1,input.length())));
		}
		
		String firstTerm = input.substring(0, index);
		String secondTerm = input.substring(index+1, input.length());
		if(checkOperator(input.charAt(index)) == OPERATOR_TYPE.PLUS) {
			return invert(firstTerm) + invert(secondTerm);
		} else if(checkOperator(input.charAt(index)) == OPERATOR_TYPE.MINUS) {
			return invert(firstTerm) - invert(secondTerm);
		} else if(checkOperator(input.charAt(index)) == OPERATOR_TYPE.MULTIPLY) {
			return invert(firstTerm) * invert(secondTerm);
		} else if(checkOperator(input.charAt(index)) == OPERATOR_TYPE.DIVIDE) {
			return invert(firstTerm) / invert(secondTerm);
		} else if(checkOperator(input.charAt(index)) == OPERATOR_TYPE.POWER) {
			return Math.pow(invert(firstTerm),invert(secondTerm));
		} else {
			return Double.NaN;
		}
	}
	
	public OPERATOR_TYPE checkOperator(char c) {
		switch(c) {
			case '+':
				return OPERATOR_TYPE.PLUS;
			case MINUS_SYMBOL:
				return OPERATOR_TYPE.MINUS;
			case MULTIPLY_SYMBOL:
				return OPERATOR_TYPE.MULTIPLY;
			case DIVIDE_SYMBOL:
				return OPERATOR_TYPE.DIVIDE;
			case '(':
				return OPERATOR_TYPE.PARENTHESE;
			case '^':
				return OPERATOR_TYPE.POWER;
			case SQUARE_ROOT_SYMBOL:
				return OPERATOR_TYPE.SQUARE_ROOT;
			default:
				return null;
		}
	}
	
	public int findOps(String input) {
		for(char c:input.toCharArray()) {
			if(checkOperator(c) != null) {
				return input.indexOf(c);
			}
		}
		return -1;
	}
	
	
}

enum OPERATOR_TYPE {
	MINUS,
	PLUS,
	DIVIDE,
	MULTIPLY,
	PARENTHESE,
	POWER,
	SQUARE_ROOT
}