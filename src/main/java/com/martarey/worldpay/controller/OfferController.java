package com.martarey.worldpay.controller;

import com.martarey.worldpay.controller.view.OfferView;
import com.martarey.worldpay.converter.OfferViewToOfferConverter;
import com.martarey.worldpay.domain.Offer;
import com.martarey.worldpay.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OfferController{

    @Autowired
    private OfferService offerService;

    @Autowired
    private OfferViewToOfferConverter converter;

    @GetMapping("/offers/active")
    public ResponseEntity getActiveOffers(){
        return ResponseEntity.ok(offerService.getActive());

    }

    @PostMapping("/offers")
    public ResponseEntity createOffer(@RequestBody OfferView offerView){
        Offer offerToCreate = converter.convert(offerView);
        offerService.create(offerToCreate);
        return ResponseEntity.created(null).build();
    }

    @PutMapping("/offer/{offerId}/cancel")
    public ResponseEntity cancel(@PathVariable String offerId) {
        offerService.cancel(offerId);
        return ResponseEntity.accepted().build();
    }


}
