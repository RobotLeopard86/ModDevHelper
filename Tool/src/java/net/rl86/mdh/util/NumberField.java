package net.rl86.mdh.util;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class NumberField extends TextField {
	
	public NumberField(Double initialValue) {
		super();
		this.setText(String.valueOf(initialValue));
		
		NumberField text = this;
		
		this.textProperty().addListener(new ChangeListener<String>() {
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		    	if(newValue.length() > oldValue.length()) {
		    		String diff = newValue.substring(oldValue.length(), newValue.length());
			    	if(!diff.matches("[0-9]") && !diff.matches("[.]")) {
			            text.setText(oldValue);
			        }
		    	}
		    }
		});
	}

}
