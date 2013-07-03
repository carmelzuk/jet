package jet.com;

import java.io.File;
import java.io.IOException;

public class JetInstaller {
	private Jet jet;
	
	public JetInstaller(Jet jet) {
		this.jet = jet;
	}
	
	public void install(JetConfig jetConfig){
		// database.
		// connect to empty database
		
		jet.install();
		// enable a module.
		
		// add its database config to the database.
		jetConfig.setInstalled(true);
		try {
			jetConfig.writePropertiesToFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
