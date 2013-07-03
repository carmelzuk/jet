package jet.com.impl.modules.test.hooks;

import jet.com.modules.theme.ThemeItem;
import jet.com.modules.theme.absHooks.AbsThemeHook;

public class ThemeHook extends AbsThemeHook {

	@Override
	public void hookTheme() {

		ThemeItem page = new ThemeItem();
		page.setHook("testpage");
		page.setModule("testModule");
		themeItems.add(page);
	}

}
