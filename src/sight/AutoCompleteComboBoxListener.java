package sight;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
*
* @author Alton
* @version 1.0
* @date 22/05/2015
* @see http://stackoverflow.com/questions/19924852/autocomplete-combobox-in-javafx
* 
*/

public class AutoCompleteComboBoxListener<T> implements EventHandler<KeyEvent> {

	private ComboBox<String> comboBox;
	private ObservableList<String> data;
	private ObservableList<String> original;
	private boolean moveCaretToPos = false;
	private int caretPos;
	private String prefix = "";
	private static final String DELIMITER = ",";
	private static final char DELIMITER_CHAR = DELIMITER.charAt(0);

	public AutoCompleteComboBoxListener( ComboBox<String> comboBox, ArrayList<String> list )
	{
		this.comboBox = comboBox;
		/*comboBox.addEventHandler(MouseEvent.MOUSE_CLICKED,
			new EventHandler<MouseEvent>()
			{
				@Override
				public void handle(MouseEvent e) {
				}
			}
		);*/
		original = FXCollections.observableArrayList();
		original.addAll( list );
		data = FXCollections.observableArrayList();
		data.addAll( list );

		/*this.comboBox.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent t) {
				comboBox.hide();
			}
		});
		this.comboBox.setOnKeyReleased(AutoCompleteComboBoxListener.this);*/
	}

	@Override
	public void handle(KeyEvent event)
	{
		String input = comboBox.getEditor().getText();
		if( input.contains(DELIMITER) )
			prefix = input.substring( 0, input.lastIndexOf(DELIMITER_CHAR) ) + DELIMITER;
		//System.out.println( "prefix:" + prefix + "@ KeyCode:" + event.getCode() );
		if( event.getCode() == KeyCode.ENTER )
		{
			//if( input.length() != 0 && !(input.trim().charAt( input.length()-1 ) != ';') )
				//comboBox.getEditor().setText( input+";" );
			caretPos = -1;
			int t = prefix.lastIndexOf(DELIMITER_CHAR);
			String temp;
			if( t == -1 )
				temp = "";
			else
				temp = prefix.substring(0, t+1) + " ";
			String text = temp + input.trim();
			text = text.trim();
			comboBox.getEditor().setText( text );
			moveCaret(comboBox.getEditor().getText().length());
			return;
		}
		if( prefix.trim().equals( input ) )
		{
			comboBox.hide();
			return;
		}
		if(event.getCode() == KeyCode.UP)
		{
			caretPos = -1;
			int t = prefix.lastIndexOf(DELIMITER_CHAR);
			String temp;
			if( t == -1 )
				temp = "";
			else
				temp = prefix.substring(0, t+1) + " ";
			String text = temp + input.trim();
			text = text.trim();
			comboBox.getEditor().setText( text );
			moveCaret(comboBox.getEditor().getText().length());
			return;
		}
		else if(event.getCode() == KeyCode.DOWN)
		{
			if(!comboBox.isShowing()) {
				comboBox.show();
			}
			caretPos = -1;
			int t = prefix.lastIndexOf(DELIMITER_CHAR);
			String temp;
			if( t == -1 )
				temp = "";
			else
				temp = prefix.substring(0, t+1) + " ";
			String text = temp + input.trim();
			text = text.trim();
			comboBox.getEditor().setText( text );
			moveCaret(comboBox.getEditor().getText().length());
			return;
		}
		else if(event.getCode() == KeyCode.BACK_SPACE)
		{
			moveCaretToPos = true;
			caretPos = comboBox.getEditor().getCaretPosition();
			prefix = comboBox.getEditor().getText();
		}
		else if(event.getCode() == KeyCode.DELETE)
		{
			moveCaretToPos = true;
			caretPos = comboBox.getEditor().getCaretPosition();
			prefix = comboBox.getEditor().getText();
		}
		if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT
				|| event.isControlDown() || event.getCode() == KeyCode.HOME
				|| event.getCode() == KeyCode.END || event.getCode() == KeyCode.TAB) {
			return;
		}

		ObservableList<String> list = FXCollections.observableArrayList();
		if( input.length() != 0 && !(input.charAt( input.length()-1 ) == DELIMITER_CHAR) )
		{
			String[] splitArray = input.split( DELIMITER );
			String lastInput = splitArray[splitArray.length-1].trim().toLowerCase();
			for( int i = 0; i < data.size(); i++ )
				if( data.get(i).toString().toLowerCase().startsWith( lastInput ) )
					list.add(data.get(i));
			if(lastInput.length() >=3 && lastInput.substring(0, 3).equals("no "))
				for( int i = 0; i < data.size(); i++ )
					if( data.get(i).toString().toLowerCase().startsWith( lastInput.substring(3, lastInput.length()) ) )
						list.add("no "+data.get(i));
		}
		String t = comboBox.getEditor().getText();
		comboBox.setItems(list);
		comboBox.getEditor().setText(t);
		if(!moveCaretToPos) {
			caretPos = -1;
		}
		moveCaret(t.length());
		if(!list.isEmpty()) {
			comboBox.show();
		}
	}

	private void moveCaret(int textLength) {
		if(caretPos == -1) {
			comboBox.getEditor().positionCaret(textLength);
		} else {
			comboBox.getEditor().positionCaret(caretPos);
		}
		moveCaretToPos = false;
	}
}