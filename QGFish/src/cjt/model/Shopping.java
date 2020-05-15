package cjt.model;

/**
 * @author cjt
 */
public class Shopping {
    //订单编号
    private int shoppingId;

    //商品id
    private int productId;

    //商品名
    private String productName;

    //商品种类
    private String productKind;

    //商品价格
    private Double productPrice;

    //商品数量
    private int productAmount;

    //购买数量
    private int buyAmount;

    //订单总金额
    private Double totalPrice;

    //卖家id
    private int seller;

    //买家id
    private int buyer;

    //商品图片路径
    private Object productPicture;

    //商品状态
    private String productCondition;

    //发往地址
    private String address;

    public int getShoppingId() {
        return shoppingId;
    }

    public void setShoppingId(int shoppingId) {
        this.shoppingId = shoppingId;
    }

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

    public int getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(int productAmount) {
        this.productAmount = productAmount;
    }

    public int getBuyAmount() {
        return buyAmount;
    }

    public void setBuyAmount(int buyAmount) {
        this.buyAmount = buyAmount;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
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

    public String getProductCondition() {
        return productCondition;
    }

    public void setProductCondition(String productCondition) {
        this.productCondition = productCondition;
    }

    public Object getProductPicture() {
        return productPicture;
    }

    public void setProductPicture(Object productPicture) {
        this.productPicture = productPicture;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Shopping{" +
                "shoppingId=" + shoppingId +
                ", productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productKind='" + productKind + '\'' +
                ", productPrice=" + productPrice +
                ", productAmount=" + productAmount +
                ", buyAmount=" + buyAmount +
                ", totalPrice=" + totalPrice +
                ", seller=" + seller +
                ", buyer=" + buyer +
                ", productPicture=" + productPicture +
                ", productCondition='" + productCondition + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
