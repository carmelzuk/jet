package jet.com.impl.modules;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import jet.com.Jet;
import jet.com.Module;
import jet.com.impl.modules.test.hooks.MenuHook;
import jet.com.impl.modules.test.hooks.OptstringHook;
import jet.com.impl.modules.test.hooks.ThemeHook;
import jet.com.impl.modules.test.hooks.ThemeTestPage;
import jet.com.modules.command.abs.AbsOptstringHook;
import jet.com.modules.menu.Menu;

public class TestModule extends Module {

	public TestModule(){
		name = "testModule";
		addHookImplement(Jet.HOOK_ENABLE, new EnableImplementor());
	}
	
	@Override
	public void init() {
		ThemeTestPage testPage = new ThemeTestPage("testpage");
		System.out.println("test init");
		jet.modulesApi.addHookListener(Jet.HOOK_ENABLE, this.name);
		addHookImplement(Menu.HOOK_MENU, new MenuHook(), name);
		addHookImplement("theme", new ThemeHook(), name);
		addHookImplement(jet.themeApi.buildThemeHookName("testpage"), testPage, name);
		addHookImplement(jet.themeApi.buildPreProcessHookName("testpage"), testPage, name);
		
		addHookImplement("optstring", new OptstringHook(), name);
	}

	@Override
	public ArrayList<String> schemaResource() {
		ArrayList<String> schema = new ArrayList<>();
		schema.add("jet/com/impl/modules/TestModule.hbm.xml");
		return schema;
	}

	
	private Object hookEnable(Module module) {

		System.out.println("**********************************************");
		System.out.println("hook enabel for module: " + module.getName());
		System.out.println("**********************************************");
		
		return null;
	}

	@Override
	public void restore() {
		
	}

	@Override
	public Object runMenuItemCallback(String path, ArrayList<Object> params) {
		Object result = null;
		if(path.compareTo("test") == 0) {
			result = testCallback(params);
		}
		
		return result;
	}
	
	/**
	 * 
	 * @return
	 */
	private Object testCallback(ArrayList<Object> params) {
		JSONObject flags = (JSONObject) params.get(0);
		Object res = jet.themeApi.theme("testpage", null);
		return res;
	}
	
	

	
	

	
}
