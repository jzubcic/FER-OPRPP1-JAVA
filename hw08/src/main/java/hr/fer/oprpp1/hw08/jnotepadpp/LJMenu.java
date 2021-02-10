package hr.fer.oprpp1.hw08.jnotepadpp;

import javax.swing.JMenu;

import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationListener;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;

public class LJMenu extends JMenu {

	private static final long serialVersionUID = 1L;
	
	public LJMenu(String key, ILocalizationProvider lp) {
		setText(lp.getString(key));
		lp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				setText(lp.getString(key));
			}
		});
	}
}
