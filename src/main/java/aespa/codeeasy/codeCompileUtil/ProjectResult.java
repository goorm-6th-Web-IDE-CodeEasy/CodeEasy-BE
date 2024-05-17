package aespa.codeeasy.codeCompileUtil;

public class ProjectResult {

    private String status;
    private String data;

    public ProjectResult() {
    }

    public ProjectResult(String status, String data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ProjectResult{" +
                "status='" + status + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}