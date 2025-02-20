package com.example.excelexport.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "RECON_RECORD") // Ensure only one table is created
public class ReconRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LSI_esop_ID") // Correct column name
    private Long lsiEsopId;

    @Column(name = "CMT_Item_ID", nullable = false)
    private String cmtItemId;

    @Column(name = "Item_Type", nullable = false)
    private String itemType;

    @Column(name = "Created_Updated", nullable = false)
    private LocalDateTime createdUpdated;

    // Getters and Setters
    public Long getId() {
        return lsiEsopId;
    }

    public void setId(Long id) {
        this.lsiEsopId = lsiEsopId;
    }

    public String getCmtItemId() {
        return cmtItemId;
    }

    public void setCmtItemId(String cmtItemId) {
        this.cmtItemId = cmtItemId;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public LocalDateTime getCreatedUpdated() {
        return createdUpdated;
    }

    public void setCreatedUpdated(LocalDateTime createdUpdated) {
        this.createdUpdated = createdUpdated;
    }
}