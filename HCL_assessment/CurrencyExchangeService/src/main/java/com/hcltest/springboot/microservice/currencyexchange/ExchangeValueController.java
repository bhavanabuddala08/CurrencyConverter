package com.hcltest.springboot.microservice.currencyexchange;

import java.util.Optional;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExchangeValueController {
	
	  private final Environment environment;
	  
	  private final  ExchangeValueRepository exchangeValueRepository;
	  
	  ExchangeValueController(Environment environment, ExchangeValueRepository exchangeValueRepository) {
		  this.environment = environment;
		  this.exchangeValueRepository = exchangeValueRepository;
	  }
	  
	  
	  @GetMapping("/currency-exchange/from/{from}/to/{to}")
	  public ResponseEntity<ExchangeValue> retrieveExchangeValue
	    (@PathVariable String from, @PathVariable String to){
	    
	    Optional<ExchangeValue> exchangeValue = 
	    		exchangeValueRepository.findByFromAndTo(from, to);
	    if(exchangeValue.isPresent()) {
	    	ExchangeValue ev = exchangeValue.get();
	    	ev.setPort(
	    	        Integer.parseInt(environment.getProperty("local.server.port")));
	    	    
	    	    return new ResponseEntity(ev, HttpStatus.OK);
	    } else {
	    	return new ResponseEntity(null, HttpStatus.NOT_FOUND);
	    }
	    
	    
	  }
}
