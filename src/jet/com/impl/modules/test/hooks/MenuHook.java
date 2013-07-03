package jet.com.impl.modules.test.hooks;

import jet.com.modules.menu.AbsMenuHook;
import jet.com.modules.menu.MenuItem;

public class MenuHook extends AbsMenuHook{


	@Override
	public void hookMenu() {
		MenuItem test = new MenuItem();
		test.setPath("test");
		test.setModule("testModule");
		menuItems.add(test);
	}

}
