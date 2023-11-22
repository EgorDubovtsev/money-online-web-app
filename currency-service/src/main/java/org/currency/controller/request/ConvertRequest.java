package org.currency.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ConvertRequest {
    @JsonProperty
    private Double amount;
    @JsonProperty
    private String currencyCodeFrom;
    @JsonProperty
    private String currencyCodeTo;
}
