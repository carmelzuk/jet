package jet.com.modules.command;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import jet.com.Module;

public class Command extends Module {

	public Command() {
		name = "command";
	}
	
	@Override
	public void init() {
		super.init();
	}

	@Override
	public ArrayList<String> schemaResource() {
		return super.schemaResource();
	}

	@Override
	public Object runHook(String name, ArrayList<Object> params) {
		return super.runHook(name, params);
	}

	@Override
	public void restore() {
		
	}
	
	/**
	 * 
	 * @param req
	 * @return
	 */
	public JetResponse handleRequest(JetRequest req) {

		JetResponse response = new JetResponse();
		try{
			String path = "";
			// if yes params then build a menu item
			if(req.getParams().size() != 0) {
				path = paramsToPath(req.getParams());
			}
			
			// if no params then must have q flag. menu item
			else {
				try {
					path = (String) req.getFlags().get("-q");
				} catch (Exception e) {
					e.printStackTrace();
					throw new Exception("Missing q or path argument");
				}
			}
			
			
			// get result of menu item
			ArrayList<Object> flags = new ArrayList<Object>(1);
			flags.add(req.getFlags());
			Object result = jet.menuModule.goTo(path, flags);
			
			ArrayList<Object> themeParams = new ArrayList<Object>(1);
			themeParams.add(result);
			// pass it to theme page function.
//			jet.themeApi.theme("page", themeParams);
			response.setResult(jet.themeApi.theme("page", themeParams));
			response.setCode(200);
		} catch (Exception e) {
			System.out.println("eledldledl");
			response.setCode(500);
			response.setResult(e);
			// build an exception response.
		}
		return response;
	}
	
	/**
	 * 
	 * @param json
	 * @return
	 */
	public JetResponse handleRequest(String json) {
		try {
			return handleRequest(new JSONObject(json));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param json
	 * @return
	 */
	public JetResponse handleRequest(JSONObject json) {
		return handleRequest(new JetRequest(json));
	}
	
	private String paramsToPath(ArrayList<String> params) {
		String path = "";
		int sixe = params.size();
		sixe  = sixe++;
		int i;
		for (i = 0 ; i < params.size() - 1 ; i++) {
			path += params.get(i) + "/";
		}
		path += params.get(i);
		
		return path;
	}

	
}
