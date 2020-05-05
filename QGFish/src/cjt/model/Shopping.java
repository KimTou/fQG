package cjt.model;

/**
 * @author cjt
 */
public class Shopping {
    private int productId;

    private String productName;

    private String productKind;

    private Double productPrice;

    private int buyAmount;

    private int seller;

    private int buyer;

    private int score;

    private String comment;

    private String productCondition;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

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

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public int getBuyAmount() {
        return buyAmount;
    }

    public void setBuyAmount(int buyAmount) {
        this.buyAmount = buyAmount;
    }

    public int getSeller() {
        return seller;
    }

    public void setSeller(int seller) {
        this.seller = seller;
    }

    public int getBuyer() {
        return buyer;
    }

    public void setBuyer(int buyer) {
        this.buyer = buyer;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getProductCondition() {
        return productCondition;
    }

    public void setProductCondition(String productCondition) {
        this.productCondition = productCondition;
    }

    @Override
    public String toString() {
        return "Shopping{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productKind='" + productKind + '\'' +
                ", productPrice=" + productPrice +
                ", buyAmount=" + buyAmount +
                ", seller=" + seller +
                ", buyer=" + buyer +
                ", score=" + score +
                ", comment='" + comment + '\'' +
                ", productCondition='" + productCondition + '\'' +
                '}';
    }
}
