package jet.com;

import java.io.File;
import java.util.List;

import jet.com.database.Database;
import jet.com.modules.HookResult;
import jet.com.modules.Modules;
import jet.com.modules.command.Command;
import jet.com.modules.jsystem.JSystem;
import jet.com.modules.jsystem.SystemSchema;
import jet.com.modules.menu.Menu;
import jet.com.modules.theme.ThemeModule;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Jet {
	
	private JetConfig jetConfig;
	
	private ApplicationContext ctx;
	private ApplicationContext themesCtx;
	
	public static final String HOOK_ENABLE = "enable";
	public static final int ENABLE_HOOK_ID = 1;
	
	
	public Database databaseApi;
	public Modules modulesApi;
	public Menu menuModule;
	public Command commadApi;
	public ThemeModule themeApi;
	
	
	public Jet(JetConfig jetConfig) {

		this.jetConfig = jetConfig;
		ctx = new ClassPathXmlApplicationContext(this.jetConfig.getFilePath());
		themesCtx = new ClassPathXmlApplicationContext(this.jetConfig.getThemeContextFile());
		Module.jet = this;
		jet.com.themes.Theme.jet = this;
		
		databaseApi = new Database();
		modulesApi = new Modules(this);
		menuModule = (Menu) modulesApi.getModule("menu");
		commadApi = (Command) modulesApi.getModule("command");
		themeApi = (ThemeModule)modulesApi.getModule(ThemeModule.NAME);
		modulesApi.addHook(HOOK_ENABLE);
		modulesApi.addHook(Menu.HOOK_MENU);
		modulesApi.addHook("theme");
		
		
		
	}
	
	public void run() {
//		if (!jetConfig.isInstalled()){
//			JetInstaller installer = new JetInstaller(this);
//			installer.install(jetConfig);
//		}
		bootstrap();
		postBootstrap();
	}
	
//	private boolean isInstalled() {
//		return false;
//	}
	
	/**
	 * TODO Load modules form system table and enable / disable them.
	 */
	public void bootstrap() {

		databaseApi.setHbnXml(new File(System.getProperty("user.dir") + "/config/hibernate.cfg.xml"));
		databaseApi.connect();
		
		String[] coreModules = getCoreModules();
		for(int i = 0 ; i < coreModules.length ; i++) {
			modulesApi.enableModule(coreModules[i]);
		}
		
		// run enable Module procedure on all enabled modules that are 
		// registered in SystemSchema table.
		JSystem systemModule = (JSystem) modulesApi.getModule("system");
		List<SystemSchema> systemSchema = systemModule.selectSystemSchema();
		for(SystemSchema row : systemSchema) {
			
			if(!(inArray(row.getName(), coreModules)) && row.isStatus()) {
				if(row.getType().compareTo("theme") != 0) {
					modulesApi.enableModule(row.getName());
				} else {
					themeApi.enableTheme(row.getName());
				}
			}
		}
		modulesApi.setModulesNamesList();
		modulesApi.initModules();
		modulesApi.registerModules();
		
		themeApi.setThemesNameList();
		themeApi.initThemes();
		themeApi.registerThemes();
		
	}
	
	/**
	 * All modules are enabled.
	 */
	public void postBootstrap() {
		menuModule.rebuildMenu();
		themeApi.rebuildThemeRegistry();
	}
	
	private boolean inArray(String needle, String[] hay){
		boolean result = false;
		for(int i = 0; i < hay.length ; i++) {
			if (needle.compareTo(hay[i]) == 0) {
				result = true;
				break;
			}
		}
		return result;
	}
	
	private String[] getCoreModules() {
		return new String[]{
				"system",
				"menu",
				"command",
				"theme",
		};
	}
	
	
	
	
	
	public void install() {
		System.out.println("In install process *****************");
		databaseApi.setHbnXml(new File(System.getProperty("user.dir") + "/config/hibernate.cfg.xml"));
		databaseApi.connect();

		String[] coreModules = getCoreModules();
		for(int i = 0 ; i < coreModules.length ; i++) {
			modulesApi.enableModule(coreModules[i]);
		}
//		modulesApi.enableModule("system");
//		modulesApi.enableModule("menu");
	}
	


	public ApplicationContext getCtx() {
		return ctx;
	}
	
	public Object theme() {
		
		return null;
		
	}

	public ApplicationContext getThemesCtx() {
		return themesCtx;
	}

	public void setThemesCtx(ApplicationContext themesCtx) {
		this.themesCtx = themesCtx;
	}
	
	
	
	
	
	
	
	
}
