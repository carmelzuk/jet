package jet.com.impl.modules;

import java.util.ArrayList;

import jet.com.Module;
import jet.com.modules.HookImplementor;

public class EnableImplementor implements HookImplementor {

	@Override
	public Object implement(String hook, ArrayList<Object> params) {

		Module module = (Module) params.get(0);
		System.out.println("**********************************************");
		System.out.println("hook enabel for module: " + module.getName());
		System.out.println("**********************************************");
		
		return null;
	}

}
