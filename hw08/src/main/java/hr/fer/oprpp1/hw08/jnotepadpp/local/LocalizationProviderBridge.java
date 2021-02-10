package hr.fer.oprpp1.hw08.jnotepadpp.local;

public class LocalizationProviderBridge extends AbstractLocalizationProvider implements ILocalizationListener {

	private boolean connected; 
	private ILocalizationProvider lp; 

	
	public LocalizationProviderBridge(ILocalizationProvider lp) {
		this.lp = lp; 
	}
	
	public void disconnect() {
		if (connected) {
			connected = false; 
			lp.removeLocalizationListener(this);
		}
	}
	
	public void connect() {
		if (!connected) {
			connected = true;
			lp.addLocalizationListener(this);
		}
	}

	@Override
	public String getString(String string) {
		return lp.getString(string);
	}

	@Override
	public void localizationChanged() {
		fire(); 	
	}
	

	
}
