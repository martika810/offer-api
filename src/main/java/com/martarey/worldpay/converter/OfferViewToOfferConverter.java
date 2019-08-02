package com.martarey.worldpay.converter;

import com.martarey.worldpay.controller.view.OfferView;
import com.martarey.worldpay.domain.Offer;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Currency;

@Component
public class OfferViewToOfferConverter implements Converter<OfferView,Offer> {
    @Override
    public Offer convert(OfferView offerView) {
        Offer offer = new Offer.Builder(offerView.getDescription(), new BigDecimal(offerView.getPrice()))
                .withCurrency(Currency.getInstance(offerView.getCurrency()))
                .withStartTime(LocalDateTime.now())
                .withExpiredTime(LocalDateTime.now().plus(new Integer(offerView.getDuration()), convertDuration(offerView.getDurationUnit())))
                .build();
        return offer;
    }

    private ChronoUnit convertDuration(String durationUnit) {
        return ChronoUnit.valueOf(durationUnit);
    }
}
