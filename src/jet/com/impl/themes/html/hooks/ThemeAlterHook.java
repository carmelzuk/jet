package jet.com.impl.themes.html.hooks;

import java.util.ArrayList;

import jet.com.modules.HookImplementor;
import jet.com.modules.HookResult;
import jet.com.modules.theme.ThemeItem;

public class ThemeAlterHook implements HookImplementor {

	@Override
	public Object implement(String name, ArrayList<Object> params) {
//		HookResult themeHookResults = (HookResult) params.get(0);
//		for(Object itemsOb : themeHookResults.getResults()) {
//			ArrayList<ThemeItem> items = (ArrayList<ThemeItem>) itemsOb;
//			for(ThemeItem item : (ArrayList<ThemeItem>) itemsOb) {
//				if(item.getHook().compareTo("testpage") == 0) {
//					item.setModule("htmlTheme");
//				}
//			}
//		}
//		System.out.println("");
		return null;
	}

}
