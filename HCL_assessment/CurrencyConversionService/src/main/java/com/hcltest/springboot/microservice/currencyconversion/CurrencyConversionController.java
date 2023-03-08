package com.hcltest.springboot.microservice.currencyconversion;

import java.math.BigDecimal;
import java.util.*;

import org.slf4j.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {

  private Logger logger = LoggerFactory.getLogger(this.getClass());
  
  private final CurrencyExchangeServiceProxy currencyExchangeServiceProxy;
  
  CurrencyConversionController(CurrencyExchangeServiceProxy currencyExchangeServiceProxy) {
	  this.currencyExchangeServiceProxy = currencyExchangeServiceProxy;
  }

  @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
  public CurrencyConversion convertCurrency(@PathVariable String from, @PathVariable String to,
      @PathVariable BigDecimal quantity) {

    Map<String, String> uriVariables = new HashMap<>();
    uriVariables.put("from", from);
    uriVariables.put("to", to);

    ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity(
        "http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversion.class,
        uriVariables);

    CurrencyConversion response = responseEntity.getBody();

    return new CurrencyConversion(response.getId(), from, to, response.getConversionMultiple(), quantity,
        quantity.multiply(response.getConversionMultiple()), response.getPort());
  }
  
  @GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
  public CurrencyConversion convertCurrencyFeign(@PathVariable String from,
                                                     @PathVariable String to,
                                                     @PathVariable BigDecimal quantity) {

      CurrencyConversion response = currencyExchangeServiceProxy.retrieveExchangeValue(from, to);

      logger.info("{}", response);

      return new CurrencyConversion(response.getId(),
              from,
              to,
              response.getConversionMultiple(),
              quantity,
              quantity.multiply(response.getConversionMultiple()),
              response.getPort());
  }
}
