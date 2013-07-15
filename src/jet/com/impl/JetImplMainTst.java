package jet.com.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import jet.com.Jet;
import jet.com.JetConfig;
import jet.com.modules.command.JetRequest;
import jet.com.modules.command.JetResponse;
import jet.com.modules.menu.Menu;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JetImplMainTst {


	public static String userDir = System.getProperty("user.dir");
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		JetConfig config;
		try {
			config = new JetConfig(new File(userDir + "/config/jet.ini"));
			config.setFilePath("file:/" + userDir + "/config/jet.modules.xml");
			config.setThemeContextFile("file:/" + userDir + "/config/jet.themes.xml");
			config.setHbn_config_file(userDir + "/config/hibernate.cfg.xml");
			Jet jet = new Jet(config);
			jet.run();
//			jet.themeApi.enableTheme("htmlTheme");
//			jet.modulesApi.enableModule("testModule");
			JSONObject json = new JSONObject();
			JSONObject flags = new JSONObject();
			
			try {
				json.put("params", new String[] {"test"});
				flags.put("n", "my name");
				flags.put("flag2", "long flag");
				json.put("flags", flags);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			jet.commadApi.cmd2Json(args);
			JetResponse res = jet.commadApi.handleRequest(new JetRequest(json));
			System.out.println(res.getResult());
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

}
