package jet.com.modules.jsystem.hooks;

import java.util.ArrayList;

import jet.com.modules.HookImplementor;
import jet.com.modules.menu.MenuItem;

public class MenuImplementor implements HookImplementor {

	

	@Override
	public Object implement(String hookName, ArrayList<Object> params) {
		ArrayList<MenuItem> menuItems = new ArrayList<>();
		MenuItem item = new MenuItem();
		item.setPath("homepage");
		item.setModule("system");
		menuItems.add(item);
		
		return menuItems;
	}

}
