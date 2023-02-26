package com.spring.user.UserApp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmplyeeV2 {
    String eId;
    String eName;
    Address  eAddress;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Address{

        String streetName;
        String district;
        String state;
        String country;

    }

}
