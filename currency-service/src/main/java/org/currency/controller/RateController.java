package org.currency.controller;

import com.moneyonline.commons.annotation.Profiling;
import org.currency.controller.request.ConvertRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/rate")
public class RateController {

    /**
     * @return актуальный курс валюты
     */
    @Profiling
    @GetMapping("/{currencyCode}")
    public ResponseEntity<String> currencyRate(@PathParam("currencyCode") String currencyCode) {
        return ResponseEntity.ok("100.00");
    }

    /**
     * @return сумму, переведенную в целевую валюту
     */
    @Profiling
    @PostMapping("/convert")
    public ResponseEntity<String> convertCurrency(ConvertRequest convertRequest) {
        return ResponseEntity.ok("971.20");
    }


}
