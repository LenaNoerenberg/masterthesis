package master.sedemo.model;

public class Report {


    private String testLevel;
    private String report;

    public Report(String testLevel, String report) {
        this.testLevel = testLevel;
        this.report = report;
    }

    public String getTestLevel() {
        return testLevel;
    }

    public String getReport() {
        return report;
    }

    public void setTestLevel() {
        this.testLevel = testLevel;
    }

    public void setReport(String report) {
        this.report = report;
    }

}
