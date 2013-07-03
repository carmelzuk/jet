package jet.com.modules.theme.absHooks;

import java.util.ArrayList;

import jet.com.Module;
import jet.com.modules.HookImplementor;

public abstract class AbsThemeImpl implements HookImplementor {

	private String themeName;
	
	public AbsThemeImpl(String themeName) {
		this.themeName = themeName;
	}
	@Override
	public Object implement(String name, ArrayList<Object> params) {
		Object res = null;
		if(name.compareTo(Module.jet.themeApi.buildThemeHookName(themeName)) == 0) {
			res = theme(params);
		}
		else if(name.compareTo(Module.jet.themeApi.buildPreProcessHookName(themeName)) == 0) {
			preProcess(params);
		}
		return res;
	}
	
	public abstract Object theme(ArrayList<Object> params);
	public abstract Object preProcess(ArrayList<Object> params);

}
