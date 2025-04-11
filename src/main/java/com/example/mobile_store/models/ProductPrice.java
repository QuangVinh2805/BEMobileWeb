package com.example.mobile_store.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "product_price")
public class ProductPrice {
    @Id
    @Column(name = "product_price_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;

    @Size(max = 50)
    @Column(name = "color", length = 50)
    private String color;

    @Size(max = 50)
    @Column(name = "capacity", length = 50)
    private String capacity;

    @Size(max = 50)
    @Column(name = "price", length = 50)
    private Long price;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "color_id", nullable = false)
    @JsonIgnore
    private Color colorId;

}