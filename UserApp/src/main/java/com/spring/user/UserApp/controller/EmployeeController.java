package com.spring.user.UserApp.controller;

import com.spring.user.UserApp.model.EmployeeV1;
import com.spring.user.UserApp.model.EmplyeeV2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EmployeeController {
    @GetMapping("/employee/v1")
    public EmployeeV1 getV1Employee(){
        return EmployeeV1.builder()
                .eId("1")
                .eName("Mersal")
                .eAddress("Mettu theru")
                .build();
    }

    @GetMapping("/employee/v2")
    public EmplyeeV2 getV2Employee(){
        return EmplyeeV2.builder()
                .eId("1")
                .eName("Mersal")
                .eAddress(EmplyeeV2.Address.builder()
                        .streetName("Merku masi veedhi")
                        .district("Madurai")
                        .state("Tamil Nadu")
                        .country("INDIA")
                        .build())
                .build();
    }

    @GetMapping(path = "/employee", params = "version=v1")
    public EmployeeV1 getV1EmpParam(){
        return EmployeeV1.builder()
                .eId("1")
                .eName("Velu")
                .eAddress("Therkku theru")
                .build();
    }

    @GetMapping(path = "/employee", params = "version=v2")
    public EmplyeeV2 getV2EmpParam(){
        return EmplyeeV2.builder()
                .eId("1")
                .eName("Siluva")
                .eAddress(EmplyeeV2.Address.builder()
                        .streetName("Kilakku masi veedhi")
                        .district("Madurai")
                        .state("Tamil Nadu")
                        .country("INDIA")
                        .build())
                .build();
    }
    @GetMapping(path = "/employee", headers = "version=v1")
    public EmployeeV1 getV1EmpHeader(){
        return EmployeeV1.builder()
                .eId("1")
                .eName("Velu")
                .eAddress("Therkku theru")
                .build();
    }

    @GetMapping(path = "/employee", headers = "version=v2")
    public EmplyeeV2 getV2EmpHeader(){
        return EmplyeeV2.builder()
                .eId("1")
                .eName("Siluva")
                .eAddress(EmplyeeV2.Address.builder()
                        .streetName("Kilakku masi veedhi")
                        .district("Madurai")
                        .state("Tamil Nadu")
                        .country("INDIA")
                        .build())
                .build();
    }
}
