package jet.com.modules.theme.absHooks;

import java.util.ArrayList;

import jet.com.modules.HookImplementor;
import jet.com.modules.theme.ThemeItem;

public abstract class AbsThemeHook implements HookImplementor {

	protected ArrayList<ThemeItem> themeItems = new ArrayList<>();
	
	@Override
	public Object implement(String name, ArrayList<Object> params) {
		hookTheme();
		return themeItems;
	}
	
	public abstract void hookTheme();

}
