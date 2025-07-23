package com.globalogic.GestorUsuarios.util.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PhoneDto {
    private long number;
    private int citycode;
    private String countrycode;
}
