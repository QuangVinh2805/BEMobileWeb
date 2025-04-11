package com.example.mobile_store.request;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
@Data
@Getter
@Setter
public class ProductRequest {
        private Long id;

        @Size(max = 100)
        private String productName;

        private Date createdAt;
        private Date updatedAt;

        @Size(max = 100)
        private String avatar;

        private Long status;

        @NotNull
        private Long price;

        @Size(max = 100)
        private String microprocessor;

        @Size(max = 100)
        private String batteryCapacity;

        @Size(max = 100)
        private String ram;

        // ProductDetail fields
        private Long productDetailId;

        private String description;

        @Size(max = 225)
        private String screen;

        @Size(max = 225)
        private String frequency;

        @Size(max = 225)
        private String resolution;

        private Double screenSize;

        @Size(max = 225)
        private String screenBrightness;

        private String rearCameraResolution;
        private String rearCameraFilm;
        private String rearCameraFeature;

        @Size(max = 225)
        private String flash;

        private String frontCameraResolution;
        private String frontCameraFilm;
        private String frontCameraFeature;

        private String cpuSpeed;

        @Size(max = 225)
        private String graphicsProcessor;

        @Size(max = 225)
        private String operatingSystem;

        @Size(max = 225)
        private String externalMemoryCard;

        @Size(max = 225)
        private String nfc;

        @Size(max = 225)
        private String network;

        @Size(max = 225)
        private String simSlot;

        private String wifi;
        private String positioning;

        @Size(max = 225)
        private String bluetooth;

        @Size(max = 225)
        private String jackEarphone;

        @Size(max = 225)
        private String charger;

        private String sensor;
        private String size;

        private String weight;
        private String material;
        private String design;

        private Double batteryCapacityDetail;

        private String batteryTechnology;
        private String batteryType;

        private String maximumCharge;
        private String specialFeatures;
        private String security;
        private String resistant;

        private Date launchTime;
    }

