package jet.com.modules.menu;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.TransientObjectException;

import jet.com.Module;
import jet.com.modules.HookResult;
import jet.com.modules.jsystem.SystemSchema;

public class Menu extends Module{

	public static final String HOOK_MENU = "menu";
	public static final int MENU_HOOK_ID = 2;
	public static final String MENU_TBL = "MenuSchema";
	public Menu(){
		name = "menu";
	}
	@Override
	public void init() {
		
		super.init();
	}

	@Override
	public ArrayList<String> schemaResource() {
		ArrayList<String> schema = new ArrayList<>();
		schema.add("jet/com/modules/menu/MenuSchema.hbm.xml");
		return schema;
	}

	@Override
	public Object runHook(String name, ArrayList<Object> params) {
		return super.runHook(name, params);
	}
	
	public void menuItemUpdate(MenuItem item) {

		MenuSchema menuSchema;
		Session session = jet.databaseApi.getSessionFactory().getCurrentSession();

		session.beginTransaction();
		Query q = session.createQuery("FROM MenuSchema WHERE path = :path");
		q.setParameter("path", item.getPath());
		List<MenuSchema> list = q.list();
		if(list.size() == 0){
			menuSchema = new MenuSchema();
		}
		else{
			menuSchema = list.get(0);
		}
		menuSchema.setModule(item.getModule());
		menuSchema.setPath(item.getPath());

		if(list.size() == 0){
			session.save(menuSchema);
		}
		else{
			session.update(menuSchema);
		}
//		try{
//			session.update(menuSchema);
//		} catch (TransientObjectException e) {
//			session.save(menuSchema);
//		}
		
		session.getTransaction().commit();
		
	}
	@Override
	public void restore() {
		// TODO Auto-generated method stub
		
	}
	
	public Module getImplementingModule(String path) {

		Module result = null;
		MenuSchema menuSchema;
		Session session = jet.databaseApi.getSessionFactory().getCurrentSession();

		session.beginTransaction();
		Query q = session.createQuery("FROM MenuSchema WHERE path = :path");
		q.setParameter("path", path);
		List<MenuSchema> list = q.list();
		if(list.size() == 0){
			menuSchema = new MenuSchema();
		}
		else{
			menuSchema = list.get(0);
			result = jet.modulesApi.getModule(menuSchema.getModule());
		}

		session.getTransaction().commit();
		
		return result;
	}
	
	
	
	public Object goTo(String path, ArrayList<Object> params) {
		Module m = getImplementingModule(path);
		System.out.println(m.getName());
		return m.runMenuItemCallback(path, params);
	}
	
	/**
	 * Delete the menu table and call hookMenu again
	 * on all enabled modules.
	 */
	public void rebuildMenu() {
		truncateMenuTable();

		HookResult hookRes = jet.modulesApi.invoke(Menu.HOOK_MENU, null);
		for(Object menuItems : hookRes.getResults()) {
			for(MenuItem menuItem : (ArrayList<MenuItem>)menuItems){
				menuItemUpdate(menuItem);
			}
		}
		
	}
	
	private void truncateMenuTable() {
		jet.databaseApi.truncate(MENU_TBL);
	}

	
}
