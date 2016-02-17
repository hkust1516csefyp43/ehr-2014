package sight;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;

/**
*
* @author Alton
* @version 1.0
* @date 22/05/2015
* 
*/

public class TextChangeListener implements ChangeListener<String>
{
	protected long type;
	public static final long TYPE_TF = 0, TYPE_TA = 1, TYPE_INT = 2, TYPE_FLOAT = 3;
	public static final String DATE_REG_EX = "[0-9]{1,2}[/][0-9]{1,2}[/][0-9][0-9][0-9][0-9]";
	protected TextField tf;
	protected TextArea ta;
	protected ComboBox<? extends String> cb; 
	protected long maxLength = -1;
	private String RegEx = "";
	private String intRegEx = "[0-9]{0,4}";
	private String floatRegEx = "[0-9]*(\\.[0-9]*)?";
	private float lowerBoundary = 0, upperBoundary = 1000;
	private float lowerAbsoluteBoundary = 0, upperAbsoluteBoundary = 1000;
	private boolean isBoundaryExist = false, isAbsoluteBoundaryExist = false, isFixedLength = false;
	protected Object nextObj = null;

	public TextChangeListener( Object o, long type )
	{
		this.type = type;
		if( type == TYPE_TF || type == TYPE_INT || type == TYPE_FLOAT )
			tf = (TextField) o;
		else if( type == TYPE_TA )
			ta = (TextArea) o;
	}

	public void changed( ObservableValue<? extends String> ov, String oldText, String newText )
	{
		if( newText == null || newText.trim().equals( "" ) )
			return;
		if( type == TYPE_INT && (newText.charAt(newText.length()-1) < 48 || newText.charAt(newText.length()-1) > 57) )
		{
			tf.setText( oldText );
			return;
		}
		else if( type == TYPE_FLOAT && !((newText.charAt(newText.length()-1) >= 48 && newText.charAt(newText.length()-1) <= 57) || newText.charAt(newText.length()-1) == '.') )
		{
			tf.setText( oldText );
			return;
		}
		if( isFixedLength )
		{
			if( newText.trim().length() > maxLength )
			{
				tf.setText( oldText );
				return;
			}
			else if( newText.trim().length() < maxLength )
				return;
		}
		if( type == TYPE_INT )
		{
			if( !newText.trim().matches( intRegEx ) || newText.charAt(newText.length()-1) == '.' )
			{
				tf.setText( oldText );
				return;
			}
			long newValue = Integer.parseInt( newText.trim() );
			if( isBoundaryExist )
			{
				/*If the input falls out of the boundaries,
				 * the text color will change to red but the system still allows the change
				 */
				if( newValue < lowerBoundary || newValue > upperBoundary )
					tf.setStyle( "-fx-text-inner-color: red;" );
				else
					tf.setStyle( "-fx-text-inner-color: black;" );
			}
			if( isAbsoluteBoundaryExist )
				if( newValue < lowerAbsoluteBoundary || newValue > upperAbsoluteBoundary )
					tf.setText( oldText );
		}
		else if( type == TYPE_FLOAT )
		{
			//Fail to handle special char at the end
			if( !newText.trim().matches( floatRegEx ) && newText.charAt(newText.length()-1) != '.' )
				tf.setText( oldText );
			float newValue = Float.parseFloat( newText.trim() );
			if( isBoundaryExist )
			{
				/*If the input falls out of the boundaries,
				 * the text color will change to red but the system still allows the change
				 */
				if( newValue < lowerBoundary || newValue > upperBoundary )
					tf.setStyle( "-fx-text-inner-color: red;" );
				else
					tf.setStyle( "-fx-text-inner-color: black;" );
			}
			if( isAbsoluteBoundaryExist )
				if( newValue < lowerAbsoluteBoundary || newValue > upperAbsoluteBoundary )
					tf.setText( oldText );
		}
		if( maxLength > 0 )
			if( newText.length() > maxLength )
				if( type == TYPE_TF )
					tf.setText( oldText );
				else if( type == TYPE_TA )
					ta.setText( oldText );

		if( !RegEx.equals( "" ) )
			if ( !newText.matches(RegEx) )
				if( type == TYPE_TF )
					tf.setText( oldText );
				else if( type == TYPE_TA )
					ta.setText( oldText );
		if(nextObj != null)
			if(newText.length() == maxLength)
				((TextField)nextObj).requestFocus();
	}

	public TextChangeListener setMaxLength( long len )
	{
		maxLength = len;
		return this;
	}

	public TextChangeListener setRegEx( String RegEx )
	{
		this.RegEx = RegEx;
		return this;
	}

	public TextChangeListener setBoundaries( float lowerBoundary, float upperBoundary )
	{
		isBoundaryExist = true;
		this.lowerBoundary = lowerBoundary;
		this.upperBoundary = upperBoundary;
		return this;
	}

	public TextChangeListener setAbsoluteBoundaries( float lowerAbsoluteBoundary, float upperAbsoluteBoundary )
	{
		isAbsoluteBoundaryExist = true;
		this.lowerAbsoluteBoundary = lowerAbsoluteBoundary;
		this.upperAbsoluteBoundary = upperAbsoluteBoundary;
		return this;
	}

	public TextChangeListener setIsFixedLength( boolean isFixedLength )
	{
		this.isFixedLength = isFixedLength;
		return this;
	}

	public TextChangeListener setNextObject(Object obj)
	{
		this.nextObj = obj;
		return this;
	}
}
