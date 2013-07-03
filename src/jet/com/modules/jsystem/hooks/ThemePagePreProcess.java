package jet.com.modules.jsystem.hooks;

import java.util.ArrayList;

import jet.com.Module;
import jet.com.modules.HookImplementor;
import jet.com.modules.theme.ThemeVars;

public class ThemePagePreProcess implements HookImplementor {

	@Override
	public Object implement(String name, ArrayList<Object> params) {
		Object result = null;
		if(name.compareTo(Module.jet.themeApi.buildThemeHookName("page")) == 0) {
			result = themePage(params);
		}
		else if(name.compareTo(Module.jet.themeApi.buildPreProcessHookName("page")) == 0) {
			result = preProcessPage(params);
		}
		return result;
	}
	
	/**
	 * 
	 * @param params
	 * @return
	 */
	private Object themePage(ArrayList<Object> params) {
		ThemeVars vars = (ThemeVars) params.get(0);
		String out = "";
		out += "-------------page ----------------------\n";
		out += vars.getVars().get("content") + "\n";
		out += "-------------END page ----------------------\n";
		return out;
	}
	
	/**
	 * 
	 * @param params
	 * @return
	 */
	private Object preProcessPage(ArrayList<Object> params) {
		ThemeVars vars = (ThemeVars) params.get(0);
		Object l = params.get(1);
		vars.getVars().put("content", ((ArrayList<Object>)params.get(1)).get(0));
		return null;
	}

}
