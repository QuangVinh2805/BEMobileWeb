package com.example.mobile_store.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "product_detail")
public class ProductDetail {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "product_id", nullable = false)
    @JsonIgnore
    private Product product;

    @Lob
    @Column(name = "description")
    private String description;

    @Size(max = 225)
    @Column(name = "screen", length = 225)
    private String screen;

    @Size(max = 225)
    @Column(name = "frequency", length = 225)
    private String frequency;

    @Size(max = 225)
    @Column(name = "resolution", length = 225)
    private String resolution;

    @Column(name = "screen_size")
    private Double screenSize;

    @Size(max = 225)
    @Column(name = "screen_brightness", length = 225)
    private String screenBrightness;

    @Lob
    @Column(name = "rear_camera_resolution")
    private String rearCameraResolution;

    @Lob
    @Column(name = "rear_camera_film")
    private String rearCameraFilm;

    @Lob
    @Column(name = "rear_camera_feature")
    private String rearCameraFeature;

    @Size(max = 225)
    @Column(name = "flash", length = 225)
    private String flash;

    @Lob
    @Column(name = "front_camera_resolution")
    private String frontCameraResolution;

    @Lob
    @Column(name = "front_camera_film")
    private String frontCameraFilm;

    @Lob
    @Column(name = "front_camera_feature")
    private String frontCameraFeature;

    @Lob
    @Column(name = "microprocessor")
    private String microprocessor;

    @Lob
    @Column(name = "cpu_speed")
    private String cpuSpeed;

    @Size(max = 225)
    @Column(name = "graphics_processor", length = 225)
    private String graphicsProcessor;

    @Size(max = 225)
    @Column(name = "operating_system", length = 225)
    private String operatingSystem;

    @Size(max = 225)
    @Column(name = "external_memory_card", length = 225)
    private String externalMemoryCard;

    @Size(max = 225)
    @Column(name = "ram", length = 225)
    private String ram;

    @Size(max = 225)
    @Column(name = "nfc", length = 225)
    private String nfc;

    @Size(max = 225)
    @Column(name = "network", length = 225)
    private String network;

    @Size(max = 225)
    @Column(name = "sim_slot", length = 225)
    private String simSlot;

    @Lob
    @Column(name = "wifi")
    private String wifi;

    @Lob
    @Column(name = "positioning")
    private String positioning;

    @Size(max = 225)
    @Column(name = "bluetooth", length = 225)
    private String bluetooth;

    @Size(max = 225)
    @Column(name = "jack_earphone", length = 225)
    private String jackEarphone;

    @Size(max = 225)
    @Column(name = "charger", length = 225)
    private String charger;

    @Lob
    @Column(name = "sensor")
    private String sensor;

    @Lob
    @Column(name = "size")
    private String size;

    @Column(name = "weight")
    private String weight;

    @Lob
    @Column(name = "material")
    private String material;

    @Lob
    @Column(name = "design")
    private String design;

    @Column(name = "battery_capacity")
    private Double batteryCapacity;

    @Lob
    @Column(name = "battery_Technology")
    private String batteryTechnology;

    @Lob
    @Column(name = "battery_type")
    private String batteryType;

    @Column(name = "maximum_charge")
    private String maximumCharge;

    @Lob
    @Column(name = "special_features")
    private String specialFeatures;

    @Lob
    @Column(name = "security")
    private String security;

    @Lob
    @Column(name = "resistant")
    private String resistant;

    @Column(name = "launch_time")
    private Date launchTime;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

}