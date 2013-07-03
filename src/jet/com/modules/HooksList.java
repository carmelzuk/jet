package jet.com.modules;

import java.util.ArrayList;
import java.util.HashMap;

import jet.com.Module;

public class HooksList extends HashMap<String, ArrayList<String>>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void addHook(String hook) {
		if(!containsKey(hook)){
			put(hook, new ArrayList<String>());
		}
	}
	
	public void addHookListener(String hook, String moduleName) {
		try{
			ArrayList<String> l = get(hook);
			if(!l.contains(moduleName)) {
				l.add(moduleName);
			}
		}catch (NullPointerException e) {
			addHook(hook);
		}
	}
	
	public ArrayList<String> getImplementingModules(String hook) {
		if(containsKey(hook)){
			return get(hook);
		}
		else{
			return null;
		}
	}
	public void invoke(String hook, ArrayList<Object> params) {
		if(containsKey(hook)) {
			for(String moduleName : get(hook)) {
//				Module module = 
			}
		}
	}
	
	
	
}
