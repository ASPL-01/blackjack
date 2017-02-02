package com.galvanize.services;

import com.galvanize.models.Card;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CardService {
    public Card getApiCard() {
        RestTemplate rest = new RestTemplate();
        return  rest.getForObject("http://localhost:3000/cards/draw",Card.class);
    }
}
