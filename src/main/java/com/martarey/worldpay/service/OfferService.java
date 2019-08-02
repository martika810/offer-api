package com.martarey.worldpay.service;

import com.martarey.worldpay.domain.Offer;

import java.util.List;

public interface OfferService {

    List<Offer> getActive();

    void create(Offer offer);

    void cancel(String offerId);

}
