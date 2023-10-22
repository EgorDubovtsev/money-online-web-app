package org.currency.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.currency.controller.response.dto.CurrencyDto;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CurrenciesResponse {
    private List<CurrencyDto> currencies;
}
