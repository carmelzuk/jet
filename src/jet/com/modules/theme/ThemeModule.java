package jet.com.modules.theme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import jet.com.Jet;
import jet.com.Module;
import jet.com.modules.HookImplementor;
import jet.com.modules.HookResult;
import jet.com.modules.HooksList;
import jet.com.modules.jsystem.JSystem;
import jet.com.modules.menu.MenuSchema;
import jet.com.themes.Theme;

/**
 * Theme API module.
 * 
 * Provides API for Theme things.
 *
 */
public class ThemeModule extends Module {

	public static final String NAME = "theme";
	public static final String PRE_PROCESS = "preProcess";
	protected HashMap<String, HookImplementor> hookList = new HashMap<>();
	private ArrayList<String> themeNameList;
	private JSystem jetSystem;
	private HooksList hooks = new HooksList();
	
	public ThemeModule() {
		name = NAME;
		themeNameList = new ArrayList<>();
	}
	
	@Override
	public void init() {

		jetSystem = (JSystem) jet.modulesApi.getModule("system");
		jet.modulesApi.addHook("theme");
		addHookImplement("theme", new ThemeImplementor(), NAME);
	}

	@Override
	public ArrayList<String> schemaResource() {
		ArrayList<String> schema = new ArrayList<>();
		
		schema.add("jet/com/modules/theme/ThemeItem.hbm.xml");
		
		return schema;
	}

	@Override
	public void restore() {

	}
	
	/**
	 * 
	 * @param themeName
	 */
	public void enableTheme(String themeName) {
		Theme theme = getTheme(themeName);
		theme.init();
		theme.setStatus(true);
		ArrayList<Object> params = new ArrayList<>();
		params.add(theme);
//		m.setStatus(true);

		jet.modulesApi.invoke("system", Jet.HOOK_ENABLE, params);
		jet.modulesApi.invoke("themeEnable", params);
		
	}
	
	public void disableTheme(String themeName) {
		
	}
	
	/**
	 * 
	 * @param hook
	 * @param params
	 * @return
	 */
	public Object theme(String hook, ArrayList<Object> params) {
		Object result = null;
		AbsThemeVars vars = new ThemeVars();
		ArrayList<Object> varsParams = new ArrayList<>(1);
		varsParams.add(vars);
		varsParams.add(params);
		
		// params 1 vars
		// pramas 2 theme params
		jet.modulesApi.invoke(buildPreProcessHookName(hook), varsParams);
		jet.themeApi.invoke(buildPreProcessHookName(hook), varsParams);
		
		if(vars.getVars().containsKey("themeOverride")){
			Theme theme = jet.themeApi.getTheme((String) vars.getVars().get("themeOverride"));
			HookResult themeRes = jet.themeApi.invoke(theme.getName(), buildThemeHookName(hook), varsParams);
			result = themeRes.getResults().get(0);
		} else{
			ThemeItem item = getThemeItem(hook);
			if(item != null) {
				HookResult r = jet.modulesApi.invoke(item.getModule(), buildThemeHookName(hook), varsParams);
				result = r.getResults().get(0);
			}
		}
//		ThemeVars vars = (ThemeVars) params.get(0);
		
		// find theme implementor (Theme of Module)
		
		return result;
		
	}
	
	public void rebuildThemeRegistry() {
		jet.databaseApi.truncate("ThemeItem");
		HookResult res = jet.modulesApi.invoke("theme", null);
		ArrayList<Object> params = new ArrayList<>();
		params.add(res);
		jet.themeApi.invoke("themeAlter", params);
		
		for(Object item : res.getResults()) {
			if(item != null) {
				for(ThemeItem tItem : (ArrayList<ThemeItem>)item){
					
					String hook = tItem.getHook();
					tItem.getModule();
					jet.modulesApi.addHook(buildPreProcessHookName(hook));
					saveThemeItem(tItem);
				}
			}
		}
		
		System.out.println(res);
	}
	

	public void saveThemeItem(ThemeItem item) {
		Session session = jet.databaseApi.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(item);
		session.getTransaction().commit();
	}
	
	public ThemeItem getThemeItem(String hook) {
		ThemeItem result = null;
		Session session = jet.databaseApi.getSessionFactory().getCurrentSession();

		session.beginTransaction();
		Query q = session.createQuery("FROM ThemeItem WHERE hook = :hook");
		q.setParameter("hook", hook);

		List<ThemeItem> list = q.list();
		if(list.size() != 0){
			result = list.get(0);
		}
		session.getTransaction().commit();
		
		return result;
	}
	
	public String buildPreProcessHookName(String hook) {
		return PRE_PROCESS + hook;
	}
	
	public String buildThemeHookName(String hook) {
		return "theme" + hook;
	}

	/**
	 * 
	 * @param string
	 * @param params
	 */
	public HookResult invoke(String hook, ArrayList<Object> params) {
		HookResult hookResult = new HookResult();
		ArrayList<String> implementingThemes;
		implementingThemes = hooks.getImplementingModules(hook);
		if(implementingThemes != null) {
			for(String themeName : implementingThemes) {
				Theme theme = getTheme(themeName);
				hookResult.addResult(theme.runHook(hook, params));
			}
		}
		return hookResult;
	}
	
	public HookResult invoke(String themeName, String hook, ArrayList<Object> params) {
		HookResult hookResult = new HookResult();
		Theme theme = getTheme(themeName);
//		Module module = getModule(moduleName);
		hookResult.addResult(theme.runHook(hook, params));
		
		return hookResult;
		
	}
	

	public void addHookImplement(String hook, HookImplementor implementor, String moduleName) {
		addHookListener(hook, moduleName);
		hookList.put(hook, implementor);
	}

	/**
	 * Register moduleName to listen to hook invocation.
	 * 
	 * Implement it with {@link Module#runHook(String, ArrayList)}
	 * 
	 * The hook is invoked
	 * @param hook
	 * @param moduleName
	 */
	public void addHookListener(String hook, String moduleName) {
		hooks.addHookListener(hook, moduleName);
	}
	
	/**
	 * 
	 * @param themeName
	 * @return
	 */
	public Theme getTheme(String themeName) {
		return (Theme) jet.getThemesCtx().getBean(themeName);
	}

	/**
	 * 
	 */
	public void setThemesNameList() {
		String [] beanDefinitionNames = jet.getThemesCtx().getBeanDefinitionNames();
		for(int i = 0 ; i < beanDefinitionNames.length ; i++) {
			themeNameList.add(beanDefinitionNames[i]);
		}
	}

	/**
	 * 
	 */
	public void initThemes() {
		for(String moduleName : themeNameList) {
			Theme theme = (Theme) jet.getThemesCtx().getBean(moduleName);
			if(theme.isStatus())
				theme.init();
		}
	}

	public void registerThemes() {
		for(String themeName : themeNameList) {
			registerTheme(themeName);
		}
	}

	public void registerTheme(String themeName) {
		jetSystem.registerTheme(getTheme(themeName));
		
	}
	
	
	
	
	

}
