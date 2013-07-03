package jet.com.modules;

import java.util.ArrayList;

import org.springframework.context.ApplicationContext;

import jet.com.Jet;
import jet.com.Module;
import jet.com.modules.jsystem.JSystem;
import jet.com.modules.menu.Menu;
import jet.com.modules.menu.MenuItem;

public class Modules {
	
	private Jet jet;
	private ApplicationContext ctx;
	

	private ArrayList<String> modulesNamesList;
	
	private HooksList hooks = new HooksList();
	
	private JSystem jetSystem;
	private Menu menuModule;
	
	
	public Modules(Jet jet) {
		this.jet = jet;
		modulesNamesList = new ArrayList<>();
		jetSystem = (JSystem) getModule("system");
		menuModule = (Menu) getModule("menu");
		ctx = this.jet.getCtx();
	}
	

	public void setModulesNamesList() {
		String [] beanDefinitionNames = ctx.getBeanDefinitionNames();
		for(int i = 0 ; i < beanDefinitionNames.length ; i++) {
			modulesNamesList.add(beanDefinitionNames[i]);
		}
	}

	public void initModules() {
		for(String moduleName : modulesNamesList) {
			Module module = (Module) ctx.getBean(moduleName);
			if(module.isStatus())
				module.init();
		}
	}
	
	public void loadModules(String [] moduleNames) {
		
	}
	
	public void enableModule(String name) {
		Module m = getModule(name);
		m.init();
		
		ArrayList<String> schema = m.schemaResource();
		if(schema != null) {
			for(String resource : schema) {
				jet.databaseApi.getConfiguration().addResource(resource);
			}
		}
		jet.databaseApi.reBuildFactory();
		
		// If menu module is enabled.
		if(menuModule.isStatus()) {
			HookResult hookRes = invoke(Menu.HOOK_MENU, null);
			for(Object menuItems : hookRes.getResults()) {
				for(MenuItem menuItem : (ArrayList<MenuItem>)menuItems){
					menuModule.menuItemUpdate(menuItem);
				}
			}
		}
		
		
		ArrayList<Object> params = new ArrayList<>();
		params.add(m);
		m.setStatus(true);
		invoke(Jet.HOOK_ENABLE, params);
	}
	
	public void disableModule(String name) {
		Module m = getModule(name);
		m.setStatus(false);
		jetSystem.registerModule(m);
	}
	
	public Module getModule(String name) {
		return (Module) jet.getCtx().getBean(name);
	}
	
	/**
	 * Register moduleName to listen to hook invocation.
	 * 
	 * Implement it with {@link Module#runHook(String, ArrayList)}
	 * 
	 * The hook is invoked
	 * @param hook
	 * @param moduleName
	 */
	public void addHookListener(String hook, String moduleName) {
		hooks.addHookListener(hook, moduleName);
	}
	
	/**
	 * Register a hook.
	 * 
	 * Modules can listen to invocation of this hook with
	 * {@link #addHookListener(String, String)}
	 * @param hook
	 */
	public void addHook(String hook) {
		hooks.addHook(hook);
	}
	
	public HookResult invoke(String hook, ArrayList<Object> params) {
		HookResult hookResult = new HookResult();
		ArrayList<String> implementingModules;
		implementingModules = hooks.getImplementingModules(hook);
		if(implementingModules != null) {
			for(String moduleName : implementingModules) {
				Module module = getModule(moduleName);
				hookResult.addResult(module.runHook(hook, params));
			}
		}
		return hookResult;
	}
	
	/**
	 * 
	 * @param moduleName
	 * @param hook
	 * @param params
	 * @return
	 */
	public HookResult invoke(String moduleName, String hook, ArrayList<Object> params) {
		HookResult hookResult = new HookResult();
		Module module = getModule(moduleName);
		hookResult.addResult(module.runHook(hook, params));
		
		return hookResult;
		
	}
	
	public void registerModule(String moduleName) {
		jetSystem.registerModule(getModule(moduleName));
	}
	
	public void registerModules() {
		for(String moduleName : modulesNamesList) {
			registerModule(moduleName);
		}
	}
	
	/**
	 * Allow module to restore itself from database.
	 */
	public void restoreModule(String moduleName) {
		Module module = getModule(moduleName);

		ArrayList<String> schema = module.schemaResource();
		for(String resource : schema) {
			jet.databaseApi.getConfiguration().addResource(resource);
		}
		jet.databaseApi.reBuildFactory();
		module.restore();
	}
	
	
	
	
}
