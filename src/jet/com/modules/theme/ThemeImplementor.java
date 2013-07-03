package jet.com.modules.theme;

import java.util.ArrayList;

import jet.com.modules.HookImplementor;

public class ThemeImplementor implements HookImplementor {

	@Override
	public Object implement(String hookName, ArrayList<Object> params) {
		
		System.out.println("in ThemeImplementor");
		
		
		return null;
	}

}
