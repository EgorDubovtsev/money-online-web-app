package com.web.app.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
public class TransactionDto {
    @JsonProperty
    private String userLoginFrom;
    @JsonProperty
    private String userLoginTo;
    @JsonProperty
    private String amount;
}
