package com.example.mobile_store.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class OrderRequest {
    private Long orderId;
    private String cartDetailIds;
    private Long userId;
    private String address;
    private Long status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date updatedAt;
}
