package jet.com.impl.themes.html;

import jet.com.impl.themes.html.hooks.ThemeAlterHook;
import jet.com.impl.themes.html.hooks.ThemePage;
import jet.com.impl.themes.html.hooks.ThemeTestPage;
import jet.com.themes.Theme;

public class HtmlTheme extends Theme{

	public HtmlTheme() {
		name = "htmlTheme";
	}

	@Override
	public void init() {
		ThemePage themePage = new ThemePage("page");
		addHookImplement(jet.themeApi.buildPreProcessHookName("testpage"), new ThemeTestPage(), name);
		addHookImplement(jet.themeApi.buildThemeHookName("testpage"), new ThemeTestPage(), name);
		addHookImplement(jet.themeApi.buildPreProcessHookName("page"), themePage, name);
		addHookImplement(jet.themeApi.buildThemeHookName("page"), themePage, name);
		
	}
	
	
	
}
