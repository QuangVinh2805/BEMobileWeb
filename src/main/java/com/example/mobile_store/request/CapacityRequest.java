package com.example.mobile_store.request;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CapacityRequest {
    private String capacity;
    private String color;
    private Long price;
}
