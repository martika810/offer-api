package com.martarey.worldpay.service;

import com.martarey.worldpay.converter.OfferDtoToOfferConverter;
import com.martarey.worldpay.converter.OfferToOfferDtoConverter;
import com.martarey.worldpay.domain.Offer;
import com.martarey.worldpay.store.OfferDto;
import com.martarey.worldpay.store.OfferStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OfferServiceImpl implements OfferService {

    @Autowired
    private OfferStore offerStore;

    @Autowired
    private OfferDtoToOfferConverter converterFromDatabase;

    @Autowired
    private OfferToOfferDtoConverter converterToDatabase;

    public OfferServiceImpl(OfferStore offerStore, OfferToOfferDtoConverter converterToDatabase, OfferDtoToOfferConverter converterFromDatabase ) {
        this.offerStore = offerStore;
        this.converterFromDatabase = converterFromDatabase;
        this.converterToDatabase = converterToDatabase;
    }

    @Override
    public List<Offer> getActive() {
        Specification<OfferDto> conditionToFilterExpiredOffers = Specification.where(OfferStore.withExpiredTimeInFuture());
        List<OfferDto> offerDtoList = offerStore.findAll(conditionToFilterExpiredOffers);
        List<Offer> offer = offerDtoList.stream().map(converterFromDatabase::convert).collect(Collectors.toList());
        return offer;

    }

    @Override
    public void create(Offer offer) {
        OfferDto offerDto = converterToDatabase.convert(offer);
        offerStore.save(offerDto);
    }

    @Override
    public void cancel(String offerId) {
        OfferDto offer = offerStore.findById(offerId).get();
        offer.setExpiredTime(LocalDateTime.now());
        offerStore.save(offer);

    }
}
