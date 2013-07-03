package jet.com;

import java.util.ArrayList;
import java.util.HashMap;

import jet.com.modules.HookImplementor;


public abstract class Module {

	protected String name;
	protected boolean status = false;
	protected HashMap<String, HookImplementor> hookList = new HashMap<>();
	
	public static Jet jet;
	
	/**
	 * Initiation function.
	 * 
	 * This function is ran before the module is enabled
	 * and before the module's schema was loaded to database.
	 * 
	 * Here you may add you hook listeners.
	 */
	public void init(){
		
	}
	
	public void addHookImplement(String hook, HookImplementor implementor) {
		hookList.put(hook, implementor);
		
	}

	public void addHookImplement(String hook, HookImplementor implementor, String moduleName) {
		Module.jet.modulesApi.addHookListener(hook, moduleName);
		hookList.put(hook, implementor);
	}
	
	public ArrayList<Class<?>> schemaClass() {
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	public ArrayList<String> schemaResource() {
		
		return null;
	}
	
	public Object runHook(String name, ArrayList<Object> params) {
		Object result = null;
		if(hookList.containsKey(name)) {
			result = ((HookImplementor) hookList.get(name)).implement(name, params);
		}
		return result;
	}
	
	public Object theme() {
		return null;
	}
	public Object getThemeImplementor(String hook){
		return null;
	}
	
	/**
	 * Restore yourself from database. This is called when you rerun the 
	 * system, NOT installing, i.e when you start the system after it
	 * was closed.
	 */
	public void restore(){}

	public Object runMenuItemCallback(String path, ArrayList<Object> params) {
		
		return null;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
	
	
	
}
