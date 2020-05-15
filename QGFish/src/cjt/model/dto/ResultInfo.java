package cjt.model.dto;

/**
 * @author cjt
 */
public class ResultInfo {
    //响应状态
    private boolean status;

    //响应信息
    private String message;

    //响应数据
    private Object data;

    public ResultInfo(boolean status, String message,Object data) {
        setStatus(status);
        setMessage(message);
        setData(data);
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
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

    @Override
    public String toString() {
        return "ResultInfo{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
