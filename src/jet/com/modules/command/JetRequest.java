package jet.com.modules.command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JetRequest {

	public static final String FLAGS = "flags";
	public static final String PARAMS = "params";
	
	private ArrayList<String> params = new ArrayList<>();
//	private HashMap<String, Object> flagss;
	private JSONObject flags;
	
	public JetRequest(JSONObject json) {
		try {
			String[] paramsarr = (String[]) json.get(PARAMS);
			Collections.addAll(params, paramsarr);
			
			flags = json.getJSONObject(FLAGS);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}

	public ArrayList<String> getParams() {
		return params;
	}

	public void setParams(ArrayList<String> params) {
		this.params = params;
	}

	public JSONObject getFlags() {
		return flags;
	}

	public void setFlags(JSONObject flags) {
		this.flags = flags;
	}
	
	
	
}
