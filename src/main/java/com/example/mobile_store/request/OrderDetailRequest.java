package com.example.mobile_store.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class OrderDetailRequest {
    private Long userId;
    private Long orderDetailId;
    private Long status;
    private String address;
}
