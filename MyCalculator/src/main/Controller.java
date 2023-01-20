package main;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import calc.Calculator;

public class Controller implements Initializable{

	@FXML
	private Label txtLabel, curAnsLabel;
	
	@FXML
	private Button backSpaceBtn, dvdBtn, multiBtn, minusBtn, plusBtn, equBtn, clrBtn, sqrtBtn, sevenBtn, fourBtn, oneBtn, powBtn, eightBtn, fiveBtn, twoBtn, zeroBtn, invBtn, nineBtn, sixBtn, threeBtn, dotBtn, openBracBtn, closeBracBtn, piBtn;

	private Calculator calc = new Calculator();
	String expression = "";
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		txtLabel.setText("");
		curAnsLabel.setText("");
		
		// number buttons
		zeroBtn.setTooltip(new Tooltip("0"));
		zeroBtn.setOnAction(e -> {
			changeText("0");
			expression += "0";
			displayCurrentAnswer();
		});
		
		oneBtn.setTooltip(new Tooltip("1"));
		oneBtn.setOnAction(e -> {
			changeText("1");
			expression += "1";
			displayCurrentAnswer();
		});
		
		twoBtn.setTooltip(new Tooltip("2"));
		twoBtn.setOnAction(e -> {
			changeText("2");
			expression += "2";
			displayCurrentAnswer();
		});
		
		threeBtn.setTooltip(new Tooltip("3"));
		threeBtn.setOnAction(e -> {
			changeText("3");
			expression += "3";
			displayCurrentAnswer();
		});
		
		fourBtn.setTooltip(new Tooltip("4"));
		fourBtn.setOnAction(e -> {
			changeText("4");
			expression += "4";
			displayCurrentAnswer();
		});;
		
		fiveBtn.setTooltip(new Tooltip("5"));
		fiveBtn.setOnAction(e -> {
			changeText("5");
			expression += "5";
			displayCurrentAnswer();
		});
		
		sixBtn.setTooltip(new Tooltip("6"));
		sixBtn.setOnAction(e -> {
			changeText("6");
			expression += "6";
			displayCurrentAnswer();
		});
		
		sevenBtn.setTooltip(new Tooltip("7"));
		sevenBtn.setOnAction(e -> {
			changeText("7");
			expression += "7";
			displayCurrentAnswer();
		});
		
		eightBtn.setTooltip(new Tooltip("8"));
		eightBtn.setOnAction(e -> {
			changeText("8");
			expression += "8";
			displayCurrentAnswer();
		});
		
		nineBtn.setTooltip(new Tooltip("9"));
		nineBtn.setOnAction(e -> {
			changeText("9");
			expression += "9";
			displayCurrentAnswer();
		});
		
		// arithemactic functions
		plusBtn.setTooltip(new Tooltip("Plus"));
		plusBtn.setOnAction(e -> {
			changeText(" + ");
			expression += " + ";
			displayCurrentAnswer();
		});
		
		minusBtn.setTooltip(new Tooltip("Minus"));
		minusBtn.setOnAction(e -> {
			changeText(" " + Calculator.MINUS_SYMBOL + " ");
			expression += " " + Calculator.MINUS_SYMBOL + " ";
			displayCurrentAnswer();
		});
		
		multiBtn.setTooltip(new Tooltip("Multiply"));
		multiBtn.setOnAction(e -> {
			changeText(" " + Calculator.MULTIPLY_SYMBOL + " ");
			expression += " " + Calculator.MULTIPLY_SYMBOL + " ";
			displayCurrentAnswer();
		});
		
		dvdBtn.setTooltip(new Tooltip("Divide"));
		dvdBtn.setOnAction(e -> {
			changeText(" " + Calculator.DIVIDE_SYMBOL + " ");
			expression += " " + Calculator.DIVIDE_SYMBOL + " ";
			displayCurrentAnswer();
		});
		
		powBtn.setTooltip(new Tooltip("Exponent"));
		powBtn.setOnAction(e -> {
			changeText(" ^ ");
			expression += " ^ ";
			displayCurrentAnswer();
		});
		
		openBracBtn.setTooltip(new Tooltip("Open Bracket"));
		openBracBtn.setOnAction(e -> {
			changeText(" ( ");
			expression += " ( ";
			displayCurrentAnswer();
		});
		
		closeBracBtn.setTooltip(new Tooltip("Close Bracket"));
		closeBracBtn.setOnAction(e -> {
			changeText(" ) ");
			expression += " ) ";
			displayCurrentAnswer();
		});
		
		sqrtBtn.setTooltip(new Tooltip("Square Root"));
		sqrtBtn.setOnAction(e -> {
			changeText(Calculator.SQUARE_ROOT_SYMBOL+"");
			expression += Calculator.SQUARE_ROOT_SYMBOL+"";
			displayCurrentAnswer();
		});
		
		equBtn.setTooltip(new Tooltip("Equals"));
		equBtn.setOnAction(e -> {
			expression = expression.replaceAll(" ","");
			if(calc.validate(expression)) {
				calc.express(expression);
				double result = calc.solver();
				txtLabel.setText(result+"");
				expression = result + "";
				curAnsLabel.setText("");
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				
				alert.setTitle("[Invalid Operation]");
				alert.setHeaderText("Can't evaluate the given expression");
				alert.setContentText("The expression that was receives isn't valid");
				alert.show();
				
				expression = "";
				txtLabel.setText("");
				curAnsLabel.setText("");
			}
		});
		
		// utility functions
		backSpaceBtn.setTooltip(new Tooltip("Backspace"));
		backSpaceBtn.setOnAction(e -> {
			try {
				txtLabel.setText(txtLabel.getText().substring(0, txtLabel.getText().length()-1));
				expression = expression.substring(0,expression.length()-1);
				displayCurrentAnswer();
			} catch(Exception ex) {
				displayCurrentAnswer();
			}
		});
		
		clrBtn.setTooltip(new Tooltip("Clear"));
		clrBtn.setOnAction(e -> {
			txtLabel.setText("");
			curAnsLabel.setText("");
			expression = "";
		});
		
		dotBtn.setTooltip(new Tooltip("Dot"));
		dotBtn.setOnAction(e -> {
			changeText(".");
			expression += ".";
			displayCurrentAnswer();
		});
		
		invBtn.setTooltip(new Tooltip("Positive / Negative"));
		invBtn.setOnAction(e -> {
			String exp = txtLabel.getText();
			txtLabel.setText(calc.inverse(exp,'-'));
			expression = calc.inverse(expression);
			displayCurrentAnswer();
		});
		
		piBtn.setTooltip(new Tooltip("Pi"));
		piBtn.setOnAction(e -> {
			changeText(Calculator.PI_SYMBOL+ "");
			expression += Calculator.PI_SYMBOL + "";
			displayCurrentAnswer();
		});
	}
	
	private void changeText(String txt) {
		txtLabel.setText(txtLabel.getText() + txt);
	}
	
	private void displayCurrentAnswer() {
		String currentExpression = expression.replaceAll(" ", "");
		if(calc.validate(currentExpression)) {
			double currrentAnswer = calc.solver(currentExpression);
			curAnsLabel.setText(currrentAnswer + "");
		} else {
			curAnsLabel.setText("");
		}
		
	}
	
	
}
