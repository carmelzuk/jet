package jet.com.modules.jsystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.TransactionException;
import org.hibernate.TransientObjectException;

import jet.com.Jet;
import jet.com.Module;
import jet.com.modules.HookImplementor;
import jet.com.modules.jsystem.hooks.EnableImplementor;
import jet.com.modules.jsystem.hooks.MenuImplementor;
import jet.com.modules.jsystem.hooks.ThemeHook;
import jet.com.modules.jsystem.hooks.ThemePagePreProcess;
import jet.com.modules.menu.Menu;
import jet.com.modules.menu.MenuItem;
import jet.com.themes.Theme;

public class JSystem extends Module{



	protected HashMap<String, HookImplementor> hookList = new HashMap<>();
	
	public JSystem(){
		name = "system";
	}
	
	@Override
	public void init() {

		addHookImplement(Jet.HOOK_ENABLE, new EnableImplementor(), name);
		addHookImplement(Menu.HOOK_MENU, new MenuImplementor(), name);
		addHookImplement("theme", new ThemeHook(), name);
		addHookImplement(jet.themeApi.buildPreProcessHookName("page"),
				new ThemePagePreProcess(), 
				name);
		addHookImplement(jet.themeApi.buildThemeHookName("page"), new ThemePagePreProcess(), name);
	}

	@Override
	public ArrayList<String> schemaResource() {
		ArrayList<String> schema = new ArrayList<>();
		schema.add("jet/com/modules/jsystem/SystemSchema.hbm.xml");
		return schema;
	}

	@Override
	public void restore() {
		SystemSchema systemSchema = getModuleFromDB(name);
		this.status = systemSchema.isStatus();
		System.out.println(systemSchema);
	}

	
	
	@Override
	public Object theme() {
		System.out.println("jfldf");
		return super.theme();
	}
	
	

	@Override
	public Object getThemeImplementor(String hook) {
		
		return super.getThemeImplementor(hook);
	}

	/**
	 * Update the module's fields in the SystemSchema table.
	 * 
	 * If the module isn't yet in the table then it will be added.
	 * 
	 * @param module
	 *   The module to register.
	 */
	public void registerModule(Module module) {
		SystemSchema systemSchema;
		Session session = jet.databaseApi.getSessionFactory().getCurrentSession();
//		try{

		session.beginTransaction();
		Query q = session.createQuery("FROM SystemSchema WHERE name = :name");
		q.setParameter("name", module.getName());
		List<SystemSchema> list = q.list();
		if(list.size() == 0){
			systemSchema = new SystemSchema();
		}
		else{
			systemSchema = list.get(0);
		}
		systemSchema.setName(module.getName());
		systemSchema.setStatus(module.isStatus());
		systemSchema.setType("module");
		try{
			session.update(systemSchema);
		} catch (TransientObjectException e) {
			session.save(systemSchema);
		}
		
		session.getTransaction().commit();
	}

	/**
	 * 
	 * @param themeName
	 */
	public void registerTheme(Theme themeName) {
		SystemSchema systemSchema;
		Session session = jet.databaseApi.getSessionFactory().getCurrentSession();
//		try{

		session.beginTransaction();
//		} catch (TransactionException e) {
//			session.getTransaction().commit();
//			session = jet.databaseApi.getSessionFactory().getCurrentSession();
//			session.beginTransaction();
//		}
		Query q = session.createQuery("FROM SystemSchema WHERE name = :name");
		q.setParameter("name", themeName.getName());
		List<SystemSchema> list = q.list();
		if(list.size() == 0){
			systemSchema = new SystemSchema();
		}
		else{
			systemSchema = list.get(0);
		}
		systemSchema.setName(themeName.getName());
		systemSchema.setStatus(themeName.isStatus());
		systemSchema.setType("theme");
		try{
			session.update(systemSchema);
		} catch (TransientObjectException e) {
			session.save(systemSchema);
		}
		
		session.getTransaction().commit();
	}
	
	/**
	 * Query the SystemSchema table.
	 * @return
	 */
	public List<SystemSchema> selectSystemSchema() {

		SystemSchema systemSchema;
		Session session = jet.databaseApi.getSessionFactory().getCurrentSession();

		session.beginTransaction();
		Query q = session.createQuery("FROM SystemSchema");
		List<SystemSchema> list = q.list();

		session.getTransaction().commit();
		return list;
		
	}
	
	
	public SystemSchema getModuleFromDB(String moduleName) {

		Module module = jet.modulesApi.getModule(moduleName);
		SystemSchema systemSchema;
		Session session = jet.databaseApi.getSessionFactory().getCurrentSession();

		session.beginTransaction();
		Query q = session.createQuery("FROM SystemSchema WHERE name = :name");
		q.setParameter("name", module.getName());
		List<SystemSchema> list = q.list();
		if(list.size() == 0){
			systemSchema = new SystemSchema();
		}
		else{
			systemSchema = list.get(0);
		}

		session.getTransaction().commit();
		
		return systemSchema;
	}

	@Override
	public Object runMenuItemCallback(String path, ArrayList<Object> params) {
		return "dldlfl";
	}


	
	
}
