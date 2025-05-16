package com.example.mobile_store.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ColorRequest {
    private String color;
    private Long price;
}
