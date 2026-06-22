package com.example.domain;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Lot {

    @Id
    @GeneratedValue(strategy =
            GenerationType.IDENTITY)

    private Long id;

    private String lotNumber;

    private String location;

    // One lot contains many assets
    @OneToMany(mappedBy = "lot" ,fetch = FetchType.LAZY,cascade=CascadeType.REMOVE)
    private List<Asset> assets=new ArrayList<>();

    // GETTERS & SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Asset> getAssets() {
        return assets;
    }

    public void setAssets(List<Asset> assets) {
        this.assets = assets;
    }
}