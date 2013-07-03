package jet.com.modules;

import java.util.ArrayList;

public class HookResult {

	private ArrayList<Object> results;
	
	public HookResult() {
		results = new ArrayList<>();
	}
	public void addResult(Object result){
		results.add(result);
	}
	public ArrayList<Object> getResults() {
		return results;
	}
	public void setResults(ArrayList<Object> results) {
		this.results = results;
	}
	
	
	
	
}
