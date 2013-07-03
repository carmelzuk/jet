package jet.com.impl.themes.html.hooks;

import java.util.ArrayList;

import jet.com.Module;
import jet.com.modules.HookImplementor;
import jet.com.modules.theme.ThemeVars;

public class ThemeTestPage implements HookImplementor {

	@Override
	public Object implement(String name, ArrayList<Object> params) {
		Object result = null;
		if(name.compareTo(Module.jet.themeApi.buildPreProcessHookName("testpage")) == 0) {
			result = preProcess(params);
		}
		else if (name.compareTo(Module.jet.themeApi.buildThemeHookName("testpage")) == 0 ) {
			result = theme(params);
		}
		return result;
	}
	
	private Object preProcess(ArrayList<Object> params) {

		ThemeVars vars = (ThemeVars) params.get(0);
		vars.getVars().put("themeOverride", "htmlTheme");
		return null;
		
	}
	
	private Object theme(ArrayList<Object> params) {
		String out = "";
		out += "<div>Hello html theme</div>";
		return out;
	}

}
