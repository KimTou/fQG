package cjt.model;

/**
 * @author cjt
 */
public class Product {
    private int productId;       //商品编号

    private String productName;    //商品名

    private String productKind;    //商品种类

    private double productPrice;   //商品价格

    private int productAmount;      //商品数量

    private Object productPicture;  //商品图片路径

    private int productSeller;     //商品卖家id

    private int productSold;      //商品出货量

    private double productStarLevel; //商品星级

    private int productScore;      //商品总得分

    private int productScoreTime;  //商品得分次数

    private String productComment;  //商品评价

    private String productCondition; //商品状态

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductKind() {
        return productKind;
    }

    public void setProductKind(String productKind) {
        this.productKind = productKind;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(int productAmount) {
        this.productAmount = productAmount;
    }

    public int getProductSeller() {
        return productSeller;
    }

    public void setProductSeller(int productSeller) {
        this.productSeller = productSeller;
    }

    public int getProductSold() {
        return productSold;
    }

    public void setProductSold(int productSold) {
        this.productSold = productSold;
    }

    public double getProductStarLevel() {
        return productStarLevel;
    }

    public void setProductStarLevel(double productStarLevel) {
        this.productStarLevel = productStarLevel;
    }

    public int getProductScore() {
        return productScore;
    }

    public void setProductScore(int productScore) {
        this.productScore = productScore;
    }

    public int getProductScoreTime() {
        return productScoreTime;
    }

    public void setProductScoreTime(int productScoreTime) {
        this.productScoreTime = productScoreTime;
    }

    public String getProductComment() {
        return productComment;
    }

    public void setProductComment(String productComment) {
        this.productComment = productComment;
    }

    public String getProductCondition() {
        return productCondition;
    }

    public void setProductCondition(String productCondition) {
        this.productCondition = productCondition;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Object getProductPicture() {
        return productPicture;
    }

    public void setProductPicture(Object productPicture) {
        this.productPicture = productPicture;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productKind='" + productKind + '\'' +
                ", productPrice=" + productPrice +
                ", productAmount=" + productAmount +
                ", productPicture=" + productPicture +
                ", productSeller=" + productSeller +
                ", productSold=" + productSold +
                ", productStarLevel=" + productStarLevel +
                ", productScore=" + productScore +
                ", productScoreTime=" + productScoreTime +
                ", productComment='" + productComment + '\'' +
                ", productCondition='" + productCondition + '\'' +
                '}';
    }
}
