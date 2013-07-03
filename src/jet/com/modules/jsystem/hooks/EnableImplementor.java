package jet.com.modules.jsystem.hooks;

import java.util.ArrayList;

import jet.com.Module;
import jet.com.modules.HookImplementor;
import jet.com.modules.jsystem.JSystem;
import jet.com.themes.Theme;

public class EnableImplementor implements HookImplementor {

	private JSystem systemModule;
	
	public EnableImplementor() {
		systemModule = (JSystem) Module.jet.modulesApi.getModule("system");
	}
	
	@Override
	public Object implement(String hook, ArrayList<Object> params) {

		try{
			Module module = (Module) params.get(0);
			systemModule.registerModule(module);
		} catch (ClassCastException e) {
			Theme theme = (Theme) params.get(0);
			systemModule.registerTheme(theme);
		}
		return null;
	}

}
