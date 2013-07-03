package jet.com.modules.jsystem.hooks;

import java.util.ArrayList;

import jet.com.modules.HookImplementor;
import jet.com.modules.theme.ThemeItem;

public class ThemeHook implements HookImplementor {

	@Override
	public Object implement(String name, ArrayList<Object> params) {
		ArrayList<ThemeItem> themeItems = new ArrayList<>();
		ThemeItem page = new ThemeItem();
		page.setHook("page");
		page.setModule("system");
		themeItems.add(page);
		return themeItems;
	}

}
