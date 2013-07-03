package jet.com;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.Properties;

public abstract class Config {

	protected Properties configProperties;
	protected FileOutputStream configFileOPStream;
	protected FileInputStream configFilInputStream;
	protected OutputStreamWriter configFileOutputStreamWriter;
	
	/**
	 * The configuration file for this application.
	 */
	protected File configFile;
	

	/**
	 * @throws IOException 
	 * 
	 */
	public Config(FileInputStream in) throws IOException{
		configFilInputStream = in;
		configProperties = new Properties();
		try {
			configProperties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param configFile
	 * @throws FileNotFoundException
	 */
	public Config(File configFile) throws FileNotFoundException {
		this.configFile = configFile;
		configFilInputStream = new FileInputStream(configFile);
		configProperties = new Properties();
		try {
			configProperties.load(configFilInputStream);
			loadProperties();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Fill the properties fields with what is set in the config file.
	 */
	public abstract void loadProperties(); 
	
	/**
	 * Write the properties that are set in {@link #configProperties} to 
	 * {@link #configFile}.
	 * 
	 * @throws IOException
	 *   For file errors.
	 */
	protected void writePropertiesToFile() throws IOException {
		configFileOPStream = new FileOutputStream(configFile);
		configFileOutputStreamWriter = new OutputStreamWriter(configFileOPStream);
		configProperties.store(configFileOutputStreamWriter, "auto");
	}

	public Properties getConfigProperties() {
		return configProperties;
	}

	
	
	
}
