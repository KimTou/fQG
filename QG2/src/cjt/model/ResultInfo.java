package cjt.model;

/**
 * @author cjt
 */
public class ResultInfo {
    private boolean status;

    private String message;

    public ResultInfo(boolean status,String message) {
        setStatus(status);
        setMessage(message);
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
