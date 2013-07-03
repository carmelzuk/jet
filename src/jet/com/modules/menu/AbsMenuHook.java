package jet.com.modules.menu;

import java.util.ArrayList;

import jet.com.modules.HookImplementor;

public abstract class AbsMenuHook implements HookImplementor {

	protected ArrayList<MenuItem> menuItems = new ArrayList<>();
	
	@Override
	public Object implement(String name, ArrayList<Object> params) {
		hookMenu();
		return menuItems;
	}
	
	/**
	 * Fill menu items to {@link #menuItems}
	 */
	public abstract void hookMenu();

}
