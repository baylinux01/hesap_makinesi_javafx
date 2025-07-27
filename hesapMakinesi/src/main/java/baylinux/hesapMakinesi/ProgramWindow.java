package baylinux.hesapMakinesi;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javafx.application.Application;
import javafx.event.ActionEvent;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;




public class ProgramWindow extends Application {
	
	public static Label label;
	public static TextField textField1, textField2;
	
	
	public static Button 
	buttonExponent, buttonPlus, buttonMinus, 
	buttonMultiply, buttonDivision, buttonEquals,
	buttonDecimal, button0, button1, button2, button3,
	button4, button5, button6, button7, button8,
	button9, buttonAC, buttonOpenParanthesis, buttonCloseParanthesis;
	
	
	
	
	public List<Token> tokenize(String input)
	{
		List<Token> tokens= new ArrayList<Token>();
		int i=0;
		while(i<input.length())
		{
				if(String.valueOf(input.charAt(i)).equalsIgnoreCase("+"))
				{
					Token token=new Token();
					token.setType("+");
					token.setValue("+");
					tokens.add(token);
					++i;
				}
				else if(String.valueOf(input.charAt(i)).equalsIgnoreCase("-"))
				{
					Token token= new Token();
					token.setType("-");
					token.setValue("-");
					tokens.add(token);
					++i;
				}
				else if(String.valueOf(input.charAt(i)).equalsIgnoreCase("x"))
				{
					Token token= new Token();
					token.setType("*");
					token.setValue("*");
					tokens.add(token);
					++i;
				}
				else if(String.valueOf(input.charAt(i)).equalsIgnoreCase("/"))
				{
					Token token= new Token();
					token.setType("/");
					token.setValue("/");
					tokens.add(token);
					++i;
				}
				else if(String.valueOf(input.charAt(i)).equalsIgnoreCase("^"))
				{
					Token token= new Token();
					token.setType("^");
					token.setValue("^");
					tokens.add(token);
					++i;
				}
				else if(String.valueOf(input.charAt(i)).equalsIgnoreCase("("))
				{
					Token token= new Token();
					token.setType("(");
					token.setValue("(");
					tokens.add(token);
					++i;
				}
				else if(String.valueOf(input.charAt(i)).equalsIgnoreCase(")"))
				{
					Token token= new Token();
					token.setType(")");
					token.setValue(")");
					tokens.add(token);
					++i;
				}
				else if(  String.valueOf(input.charAt(i)).equalsIgnoreCase(".")
						||String.valueOf(input.charAt(i)).equalsIgnoreCase("0")
						||String.valueOf(input.charAt(i)).equalsIgnoreCase("1")
						||String.valueOf(input.charAt(i)).equalsIgnoreCase("2")
						||String.valueOf(input.charAt(i)).equalsIgnoreCase("3")
						||String.valueOf(input.charAt(i)).equalsIgnoreCase("4")
						||String.valueOf(input.charAt(i)).equalsIgnoreCase("5")
						||String.valueOf(input.charAt(i)).equalsIgnoreCase("6")
						||String.valueOf(input.charAt(i)).equalsIgnoreCase("7")
						||String.valueOf(input.charAt(i)).equalsIgnoreCase("8")
						||String.valueOf(input.charAt(i)).equalsIgnoreCase("9")
						)
				{
					int start=i;
					while(  i<input.length() && (  
							  String.valueOf(input.charAt(i)).equalsIgnoreCase(".")
							||String.valueOf(input.charAt(i)).equalsIgnoreCase("0")
							||String.valueOf(input.charAt(i)).equalsIgnoreCase("1")
							||String.valueOf(input.charAt(i)).equalsIgnoreCase("2")
							||String.valueOf(input.charAt(i)).equalsIgnoreCase("3")
							||String.valueOf(input.charAt(i)).equalsIgnoreCase("4")
							||String.valueOf(input.charAt(i)).equalsIgnoreCase("5")
							||String.valueOf(input.charAt(i)).equalsIgnoreCase("6")
							||String.valueOf(input.charAt(i)).equalsIgnoreCase("7")
							||String.valueOf(input.charAt(i)).equalsIgnoreCase("8")
							||String.valueOf(input.charAt(i)).equalsIgnoreCase("9")
							))
							{
								++i;
							}
					Token token= new Token();
					token.setType("number");
					token.setValue(input.substring(start,i));
					tokens.add(token);
					
					
				}
		}
		
		return tokens;
		
	}
	
	public double evaluate(String input)
	{
		List<Token> tokens=tokenize(input);
		Cursor c=new Cursor();
		c.index=0; //Burada bu Cursor sınıfının amacı index'i metotlara referansla gönderebilmek.
		return getSayisalDeger(tokens,c);
	}
	
	public double getSayisalDeger(List<Token> tokens,Cursor c)
	{
		
		double left=getToplanacakVeyaCikarilacakDeger(tokens,c);
		
		
		while(c.index<tokens.size())
		{
			if(tokens.get(c.index).getType().equals("+"))
			{
				++c.index;
				left=left+getToplanacakVeyaCikarilacakDeger(tokens,c);
				
			}
			else if(tokens.get(c.index).getType().equals("-"))
			{
				++c.index;
				left=left-getToplanacakVeyaCikarilacakDeger(tokens,c);
				
			}
			else
			{
				break;
			}
		}
		return left;
	}
	
	public double getToplanacakVeyaCikarilacakDeger(List<Token> tokens,Cursor c)
	{
		double left=getCarpilacakVeyaBolunecekDeger(tokens,c);
		
		
		while(c.index<tokens.size())
		{
			if(tokens.get(c.index).getType().equals("*"))
			{
				++c.index;
				left=left*getCarpilacakVeyaBolunecekDeger(tokens,c);
				
			}
			else if(tokens.get(c.index).getType().equals("/"))
			{
				++c.index;
				left=left/getCarpilacakVeyaBolunecekDeger(tokens,c);
				
			}
			else
			{
				break;
			}
		}
		return left;
	}
	
	public double getCarpilacakVeyaBolunecekDeger(List<Token> tokens,Cursor c)
	{
		double left=getUssuAlinacakDeger(tokens,c);
		
		
		while(c.index<tokens.size())
		{
			if(tokens.get(c.index).getType().equals("^"))
			{
				++c.index;
				left=Math.pow(left, getUssuAlinacakDeger(tokens,c));
				
			}
			
			else
			{
				break;
			}
		}
		return left;
		
	}
	public double getUssuAlinacakDeger(List<Token> tokens,Cursor c)
	{ 		
			double icDeger=0;
			if(tokens.get(c.index).getType().equals("number"))
			{
				++c.index;
				return Double.valueOf(tokens.get(c.index-1).getValue());
				
				
				
			}
			else if(tokens.get(c.index).getType().equals("("))
			{
				++c.index;
				icDeger=getSayisalDeger(tokens,c);
				
				
				Token kapanis=tokens.get(c.index);
				++c.index;
				if(!kapanis.getType().equals(")"))
				{
					textField2.setText("hatalı ifade");
				}
				
			}
			return icDeger;
			
		
		
	}
	
	
	
	
	
	@Override
	public void start(Stage primaryStage) throws InterruptedException {
		try {
			Parent root = FXMLLoader.load(
	                getClass().getResource("/fxml-files/HesapMakinesiAnaSayfa.fxml"));
			Scene scene=new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			label = (Label) root.lookup("#label");
			textField1 = (TextField) root.lookup("#textField1");
			textField2= (TextField)  root.lookup("#textField2");
			buttonPlus        = (Button)  root.lookup("#buttonPlus");
			buttonMinus        = (Button)  root.lookup("#buttonMinus");
			buttonMultiply        = (Button)  root.lookup("#buttonMultiply");
			buttonDivision        = (Button)  root.lookup("#buttonDivision");
			buttonDecimal        = (Button)  root.lookup("#buttonDecimal");
			buttonExponent        = (Button)  root.lookup("#buttonExponent");
			buttonEquals        = (Button)  root.lookup("#buttonEquals");
			buttonOpenParanthesis        = (Button)  root.lookup("#buttonOpenParanthesis");
			buttonCloseParanthesis        = (Button)  root.lookup("#buttonCloseParanthesis");
			buttonAC        = (Button)  root.lookup("#buttonAC");
			button0        = (Button)  root.lookup("#button0");
			button1        = (Button)  root.lookup("#button1");
			button2        = (Button)  root.lookup("#button2");
			button3        = (Button)  root.lookup("#button3");
			button4        = (Button)  root.lookup("#button4");
			button5        = (Button)  root.lookup("#button5");
			button6        = (Button)  root.lookup("#button6");
			button7        = (Button)  root.lookup("#button7");
			button8        = (Button)  root.lookup("#button8");
			button9        = (Button)  root.lookup("#button9");
			
			textField1.setEditable(false);
			textField1.setText("");
			textField2.setEditable(false);
			textField2.setText("");
			
			buttonPlus.setOnAction(e->{textField1.setText(textField1.getText()+"+");textField2.setText("");});
			buttonMinus.setOnAction(e->{textField1.setText(textField1.getText()+"-");textField2.setText("");});
			buttonMultiply.setOnAction(e->{textField1.setText(textField1.getText()+"x");textField2.setText("");});
			buttonDivision.setOnAction(e->{textField1.setText(textField1.getText()+"/");textField2.setText("");});
			buttonDecimal.setOnAction(e-> {textField1.setText(textField1.getText()+".");textField2.setText("");});
			buttonExponent.setOnAction(e->{textField1.setText(textField1.getText()+"^");textField2.setText("");});
			buttonEquals.setOnAction(e -> {
			    try {
			        double sonuc = evaluate(textField1.getText());
			        textField2.setText(String.valueOf(sonuc));
			    } catch (Exception ex) {
			        textField2.setText("HATALI DEĞER");
			        ex.printStackTrace();
			    }
			});
			buttonAC.setOnAction(e->{textField1.setText("");textField2.setText("");});
			buttonOpenParanthesis.setOnAction(e->{textField1.setText(textField1.getText()+"(");textField2.setText("");});
			buttonCloseParanthesis.setOnAction(e->{textField1.setText(textField1.getText()+")");textField2.setText("");});
			button0.setOnAction(e->{textField1.setText(textField1.getText()+"0");textField2.setText("");});
			button1.setOnAction(e->{textField1.setText(textField1.getText()+"1");textField2.setText("");});
			button2.setOnAction(e->{textField1.setText(textField1.getText()+"2");textField2.setText("");});
			button3.setOnAction(e->{textField1.setText(textField1.getText()+"3");textField2.setText("");});
			button4.setOnAction(e->{textField1.setText(textField1.getText()+"4");textField2.setText("");});
			button5.setOnAction(e->{textField1.setText(textField1.getText()+"5");textField2.setText("");});
			button6.setOnAction(e->{textField1.setText(textField1.getText()+"6");textField2.setText("");});
			button7.setOnAction(e->{textField1.setText(textField1.getText()+"7");textField2.setText("");});
			button8.setOnAction(e->{textField1.setText(textField1.getText()+"8");textField2.setText("");});
			button9.setOnAction(e->{textField1.setText(textField1.getText()+"9");textField2.setText("");});
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		launch(args);
		
	}

	
}
