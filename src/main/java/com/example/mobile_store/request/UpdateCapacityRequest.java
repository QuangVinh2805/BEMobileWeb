package com.example.mobile_store.request;


import lombok.Data;

@Data
public class UpdateCapacityRequest {
    private String oldColor;
    private String oldCapacity;

    private String newColor;
    private String newCapacity;

    private Long price;
}
