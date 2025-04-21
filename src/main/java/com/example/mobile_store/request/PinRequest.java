package com.example.mobile_store.request;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PinRequest {
    private Long userId;
    private String email;
    private String pin;
}
