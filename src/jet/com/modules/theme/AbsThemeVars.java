package jet.com.modules.theme;

import java.util.HashMap;

public abstract class AbsThemeVars {

	protected HashMap<String, Object> vars;
	
	public AbsThemeVars() {
		vars = new HashMap<>();
	}

	public HashMap<String, Object> getVars() {
		return vars;
	}

	public void setVars(HashMap<String, Object> vars) {
		this.vars = vars;
	}
	
	
}
