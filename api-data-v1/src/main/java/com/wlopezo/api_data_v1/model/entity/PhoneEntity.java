package com.wlopezo.api_data_v1.model.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "phones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhoneEntity {
    @Id
    private Long phoneId;
    private String number;
    private String cityCode;
    private String countryCode;
    private Long userId;
}
