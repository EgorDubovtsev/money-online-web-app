package com.web.app.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class CurrenciesDto {
    @JsonProperty
    private List<Currency> currencies;
}
