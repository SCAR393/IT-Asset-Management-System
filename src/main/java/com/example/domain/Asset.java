package com.example.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "assettt")
public class Asset {

    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "assettt_seq"
    )

    @SequenceGenerator(
        name = "assettt_seq",
        sequenceName = "assettt_seq",
        allocationSize = 1
    )

    @Column(name = "ASSET_ID")
    private Long assetId;

    /* ================= PRODUCT ================= */

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
        name = "PRODUCT_ID",
        referencedColumnName = "productId"
    )
    private Product product;

    /* ================= EMPLOYEE ================= */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMPLOYEE_ID")
    private Employee employee;

    /* ================= LOCATION ================= */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "LOCATION_ID"
    )
    private Location location;

    /* ================= STATUS ================= */

    @Column(nullable = true)
    private String status;
    
    /* ================= DEPARTMENT ================= */

    @Column(name = "DEPARTMENT")
    private String department;

    /* ================= PURCHASE DATE ================= */

    @Column(name = "PURCHASE_DATE")
    private LocalDate purchaseDate;

    /* ================= WARRANTY ================= */

    @Column(name = "WARRANTY_YEARS")
    private Integer warrantyYears;

    /* ================= LOT ================= */

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "LOT_ID")
    private Lot lot;

    /* ========================================================= */

    public Asset() {
        super();
    }

    /* ================= ASSET ID ================= */

    public Long getAssetId() {
        return assetId;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    /* ================= PRODUCT ================= */

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    /* ================= EMPLOYEE ================= */

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    /* ================= LOCATION ================= */

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    /* ================= STATUS ================= */

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    /* ================= DEPARTMENT ================= */

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    /* ================= PURCHASE DATE ================= */

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(
            LocalDate purchaseDate) {

        this.purchaseDate = purchaseDate;
    }

    /* ================= WARRANTY YEARS ================= */

    public Integer getWarrantyYears() {
        return warrantyYears;
    }

    public void setWarrantyYears(
            Integer warrantyYears) {

        this.warrantyYears = warrantyYears;
    }

    /* ================= LOT ================= */

    public Lot getLot() {
        return lot;
    }

    public void setLot(Lot lot) {
        this.lot = lot;
    }

    /* ================= WARRANTY STATUS ================= */

    public boolean isExpired() {

        if (purchaseDate == null ||
            warrantyYears == null) {

            return false;
        }

        LocalDate expiryDate =
                purchaseDate.plusYears(
                        warrantyYears);

        return LocalDate.now()
                .isAfter(expiryDate);
    }
    @Column(name = "LAST_SERVICE_DATE")
    private LocalDate lastServiceDate;
}