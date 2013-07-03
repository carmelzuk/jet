package jet.com.database;

import java.io.File;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class Database {
	
	private File hbnXml;
	
	private static SessionFactory sessionFactory;
	private static ServiceRegistry serviceRegistry;
	
	private Configuration configuration = new Configuration();
	
	public void setHbnXml(File hbnXml) {
		this.hbnXml = hbnXml;
	}
	
	public void connect(){
		
		getSessionFactory();
		
	}
	
	public void reBuildFactory() {
	    serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();        
	    sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	}
	
	public SessionFactory getSessionFactory() {	    
		if(sessionFactory == null) {
			configuration = new Configuration();
		    configuration.configure(hbnXml);
		    serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();        
		    sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		}
		return sessionFactory;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
	
	public void truncate(String tbl) {
		Session session = getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String hql = String.format("DELETE from %s", tbl);
		
//		Query q = session.createQuery("DELETE from :tbl");
		Query q = session.createQuery(hql);
//		q.setParameter("tbl", tbl);
		int n = q.executeUpdate();
		session.getTransaction().commit();
		
		
	}
	
	
}
