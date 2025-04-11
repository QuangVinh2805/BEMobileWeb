package com.example.mobile_store.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "cart")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_price_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private ProductPrice productPrice;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Product product;

    @ColumnDefault("current_timestamp()")
    @Column(name = "created_at")
    private Date createdAt;

    @ColumnDefault("current_timestamp()")
    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "total_price")
    private Long totalPrice;

    @Column(name = "unit_price")
    private Long unitPrice;

    @Column(name = "quantity")
    private Long quantity = 0L;

    public Cart(Long id, User user, Date date, Date createdAt, Long price, Long totalPrice, Long quantity, Product product,ProductPrice productPrice) {
        this.id = id;
        this.user = user;
        this.createdAt = createdAt != null ? createdAt : new Date();  // Gán thời gian hiện tại nếu null
        this.updatedAt = updatedAt != null ? updatedAt : new Date();  // Gán thời gian hiện tại nếu null
        this.unitPrice = unitPrice != null ? unitPrice : 0L;
        this.totalPrice = totalPrice != null ? totalPrice : 0L;
        this.quantity = quantity != null ? quantity : 0L;  // Gán 0 nếu null
        this.product = product;
        this.productPrice = productPrice;
    }
}