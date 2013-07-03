package jet.com.impl.themes.html.hooks;

import java.util.ArrayList;

import jet.com.modules.HookImplementor;
import jet.com.modules.theme.ThemeVars;
import jet.com.modules.theme.absHooks.AbsThemeImpl;

public class ThemePage extends AbsThemeImpl {

	public ThemePage(String themeName) {
		super(themeName);
	}


	@Override
	public Object theme(ArrayList<Object> params) {
		ThemeVars vars = (ThemeVars) params.get(0);
		String out = "";
		out += "<html>\n";
		out += "<body>\n";
		out += vars.getVars().get("content") + "\n";
		out += "</body>\n";
		out += "</html>\n";
		return out;
	}

	@Override
	public Object preProcess(ArrayList<Object> params) {
		ThemeVars vars = (ThemeVars) params.get(0);
		vars.getVars().put("themeOverride", "htmlTheme");
		return null;
	}

}
