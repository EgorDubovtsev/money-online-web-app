package org.currency.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ConvertRequest {
    @JsonProperty
    private BigDecimal amount;
    @JsonProperty
    private String currencyCodeFrom;
    @JsonProperty
    private String currencyTo;
}
