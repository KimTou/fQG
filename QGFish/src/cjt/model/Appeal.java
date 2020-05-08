package cjt.model;

/**
 * @author cjt
 */
public class Appeal {
    //申诉编号
    private int id;

    //申诉用户的id
    private int userId;

    //申诉标题
    private String appealTitle;

    //申诉内容
    private String appealContent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAppealTitle() {
        return appealTitle;
    }

    public void setAppealTitle(String appealTitle) {
        this.appealTitle = appealTitle;
    }

    public String getAppealContent() {
        return appealContent;
    }

    public void setAppealContent(String appealContent) {
        this.appealContent = appealContent;
    }

    @Override
    public String toString() {
        return "Appeal{" +
                "id=" + id +
                ", userId=" + userId +
                ", appealTitle='" + appealTitle + '\'' +
                ", appealContent='" + appealContent + '\'' +
                '}';
    }
}
