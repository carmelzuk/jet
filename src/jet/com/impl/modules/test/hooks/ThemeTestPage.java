package jet.com.impl.modules.test.hooks;

import java.util.ArrayList;

import jet.com.modules.theme.absHooks.AbsThemeImpl;

public class ThemeTestPage extends AbsThemeImpl {

	public ThemeTestPage(String themeName) {
		super(themeName);
	}

	@Override
	public Object theme(ArrayList<Object> params) {
		String out = "";
		out += "**********************************************\n";
		out += "    Test menu call back \n";
		out += "**********************************************\n";
		return out;
	}

	@Override
	public Object preProcess(ArrayList<Object> params) {
		return null;
	}

}
