package com.spring.user.UserApp.model;

import lombok.Builder;

@Builder
public record Car(
        int id,
        String model,
        String company,
        int price,
        int mfgYear,
        String modelType
)

{



    public Car(int id, String model, String company, int price, int mfgYear, String modelType) {
        this.id = id;
        this.model = model;
        this.company = "Toyota";
        this.price = price;
        this.mfgYear = mfgYear;
        this.modelType = modelType;
    }
}
