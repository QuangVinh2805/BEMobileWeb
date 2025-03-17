package com.example.mobile_store.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 100)
    @NotNull
    @Column(name = "product_name", nullable = false, length = 100)
    private String productName;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Size(max = 100)
    @Column(name = "avatar", length = 100)
    private String avatar;

    @NotNull
    @Column(name = "price", nullable = false)
    private Double price;

    @Size(max = 100)
    @Column(name = "microprocessor", length = 100)
    private String microprocessor;

    @Size(max = 100)
    @Column(name = "battery_capacity", length = 100)
    private String batteryCapacity;

    @Size(max = 100)
    @Column(name = "ram", length = 100)
    private String ram;

}