package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationProvider extends AbstractLocalizationProvider {

	private static final LocalizationProvider instance = new LocalizationProvider();
	private String language; 
	private ResourceBundle bundle; 
	
	private LocalizationProvider() {
		language = "en"; 
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.local.prijevodi", locale);
	}
	
	@Override
	public String getString(String string) { 
		return bundle.getString(string);
	}
	
	public void setLanguage(String language) {	
		this.language = language; 
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.local.prijevodi", locale);
		fire(); 
	}
	
	public static LocalizationProvider getInstance() {
		return instance; 
	}
	
	

}
