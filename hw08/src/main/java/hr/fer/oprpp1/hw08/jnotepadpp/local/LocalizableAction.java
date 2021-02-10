package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

public class LocalizableAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	public LocalizableAction(String key, String description, ILocalizationProvider lp) {
		super(); 
		putValue(Action.NAME, lp.getString(key));
		putValue(Action.SHORT_DESCRIPTION, lp.getString(description));
		
		
		lp.addLocalizationListener(new ILocalizationListener() {			
			@Override
			public void localizationChanged() {
				putValue(Action.NAME, lp.getString(key));
				putValue(Action.SHORT_DESCRIPTION, lp.getString(description));
			}
		});
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
