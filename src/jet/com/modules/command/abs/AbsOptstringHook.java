package jet.com.modules.command.abs;

import java.util.ArrayList;

import jet.com.modules.HookImplementor;

public abstract class AbsOptstringHook implements HookImplementor {

	@Override
	public Object implement(String name, ArrayList<Object> params) {
		String[] optstrings = optstrings();
		
		return optstrings;
	}
	
	public abstract String[] optstrings();

}
