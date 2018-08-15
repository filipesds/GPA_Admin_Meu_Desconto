package utils;

public class Summary {
	private Integer tests = 0;
	private Integer failures = 0;
	private Integer errors = 0;
	private Integer skipped = 0;
	private String successRate = "";
	private Long time = 0l;
	private String timeString = "00:00:00s";
	private String buildNumber = "";
	private String jobName = "";
	
	public Summary() {
	}
	
	public Summary(Integer tests, Integer failures, Integer errors, Integer skipped, String successRate, Long time, String buildNumber, String jobName) {
		super();
		this.tests = tests;
		this.failures = failures;
		this.errors = errors;
		this.skipped = skipped;
		this.successRate = successRate;
		this.time = time;
		this.buildNumber = buildNumber;
		this.jobName = jobName;
	}

	public Integer getTests() {
		
		return tests;
	}

	public void setTests(Integer tests) {
		this.tests = tests;
	}

	public Integer getFailures() {
		
		return failures;
	}

	public void setFailures(Integer failures) {
		this.failures = failures;
	}

	public Integer getErrors() {
		
		return errors;
	}

	public void setErrors(Integer errors) {
		this.errors = errors;
	}

	public Integer getSkipped() {
		
		return skipped;
	}

	public void setSkipped(Integer skipped) {
		this.skipped = skipped;
	}

	public String getSuccessRate() {
		
		return successRate;
	}

	public void setSuccessRate(String successRate) {
		this.successRate = successRate;
	}

	public Long getTime() {
		
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public String getTimeString() {
		
		return timeString;
	}

	public void setTimeString(String timeString) {
		this.timeString = timeString;
	}

	public String getBuildNumber() {
		
		return buildNumber;
	}

	public void setBuildNumber(String buildNumber) {
		this.buildNumber = buildNumber;
	}

	public String getJobName() {
		
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
}