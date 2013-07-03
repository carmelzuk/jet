package jet.com.impl.modules;

public class TestModuleSchema {

	private Long id;

    private String testName, testType;
    
    public TestModuleSchema(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getTestType() {
		return testType;
	}

	public void setTestType(String testType) {
		this.testType = testType;
	}
    
    
}
