package jet.com.themes;

import java.util.ArrayList;
import java.util.HashMap;

import jet.com.Jet;
import jet.com.Module;
import jet.com.modules.HookImplementor;

public abstract class Theme {

	public static Jet jet;
	
	protected String name;
	protected boolean status = false;
	protected HashMap<String, HookImplementor> hookList = new HashMap<>();

	public void init() {
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object runHook(String hook, ArrayList<Object> params) {
		Object result = null;
		if(hookList.containsKey(hook)) {
			result = ((HookImplementor) hookList.get(hook)).implement(hook, params);
		}
		return result;
	}

	public void addHookImplement(String hook, HookImplementor implementor, String themeName) {
//		Module.jet.modulesApi.addHookListener(hook, moduleName);
		jet.themeApi.addHookListener(hook, themeName);
		hookList.put(hook, implementor);
	}

	
	
	
	
}
