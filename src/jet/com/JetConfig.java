package jet.com;

import java.io.File;
import java.io.FileNotFoundException;

public class JetConfig extends Config{

	private boolean installed;
	private String db_name;
	private String db_host;
	private String db_uname;
	private String db_pass;
	private String db_dialect;
	private String hbn_config_file;
	
	public JetConfig(File configFile) throws FileNotFoundException {
		super(configFile);
	}

	private String filePath;
	private String themeContextFile;
	

	
	
	public String getThemeContextFile() {
		return themeContextFile;
	}

	public void setThemeContextFile(String themeContextFile) {
		this.themeContextFile = themeContextFile;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public void loadProperties() {
		
		installed = Boolean.parseBoolean(configProperties.getProperty("installed"));
		db_name = configProperties.getProperty("db_name");
		db_host = configProperties.getProperty("db_host");
		db_uname = configProperties.getProperty("db_uname");
		db_pass = configProperties.getProperty("db_pass");
		db_dialect = configProperties.getProperty("db_dialect");
	}

	public boolean isInstalled() {
		return installed;
	}

	public void setInstalled(boolean installed) {
		this.installed = installed;
		configProperties.setProperty("installed", Boolean.toString(installed));
	}

	public String getDb_name() {
		return db_name;
	}

	public void setDb_name(String db_name) {
		this.db_name = db_name;
	}

	public String getDb_host() {
		return db_host;
	}

	public void setDb_host(String db_host) {
		this.db_host = db_host;
	}

	public String getDb_uname() {
		return db_uname;
	}

	public void setDb_uname(String db_uname) {
		this.db_uname = db_uname;
	}

	public String getDb_pass() {
		return db_pass;
	}

	public void setDb_pass(String db_pass) {
		this.db_pass = db_pass;
	}

	public String getDb_dialect() {
		return db_dialect;
	}

	public void setDb_dialect(String db_dialect) {
		this.db_dialect = db_dialect;
	}

	public String getHbn_config_file() {
		return hbn_config_file;
	}

	public void setHbn_config_file(String hbn_config_file) {
		this.hbn_config_file = hbn_config_file;
	}
	
	
	
	
}
