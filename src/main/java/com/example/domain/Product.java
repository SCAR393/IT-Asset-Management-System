package com.example.domain;

import java.math.BigDecimal;
import java.sql.Date;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "Product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(name = "product_name", nullable = false, length = 255)
    private String productName;

    @Column(name = "seller_id", nullable = false, length = 50)
    private String sellerId;

    @Column(name = "brand_type", nullable = false, length = 100)
    private String brandType;

    @Column(name = "catalogue_status", nullable = false, length = 50)
    private String catalogueStatus;

    @Column(name = "selling_as", nullable = false, length = 100)
    private String sellingAs;

    @Column(name = "category_name", nullable = false, length = 100)
    private String categoryName;

    @Column(name = "model", nullable = false, length = 100)
    private String model;

    @Column(name = "hsn_code", nullable = false, length = 50)
    private String hsnCode;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "warranty", precision = 2)
    private int warranty;

    @Column(name = "taxes", precision = 10, scale = 2)
    private BigDecimal taxes;

    @Column(name = "total", precision = 10, scale = 2)
    private BigDecimal total;

    @Column(name = "purchase_date")
    private Date purchaseDate;

    /* ---------- LARGE OBJECTS ---------- */

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] report1;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] report2;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] image;

    /* ---------- CONSTRUCTOR ---------- */

    public Product() {
        super();
    }

    /* ---------- GETTERS & SETTERS ---------- */

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getBrandType() {
        return brandType;
    }

    public void setBrandType(String brandType) {
        this.brandType = brandType;
    }

    public String getCatalogueStatus() {
        return catalogueStatus;
    }

    public void setCatalogueStatus(String catalogueStatus) {
        this.catalogueStatus = catalogueStatus;
    }

    public String getSellingAs() {
        return sellingAs;
    }

    public void setSellingAs(String sellingAs) {
        this.sellingAs = sellingAs;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getHsnCode() {
        return hsnCode;
    }

    public void setHsnCode(String hsnCode) {
        this.hsnCode = hsnCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getWarranty() {
        return warranty;
    }

    public void setWarranty(int warranty) {
        this.warranty = warranty;
    }

    public BigDecimal getTaxes() {
        return taxes;
    }

    public void setTaxes(BigDecimal taxes) {
        this.taxes = taxes;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public byte[] getReport1() {
        return report1;
    }

    public void setReport1(byte[] report1) {
        this.report1 = report1;
    }

    public byte[] getReport2() {
        return report2;
    }

    public void setReport2(byte[] report2) {
        this.report2 = report2;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}