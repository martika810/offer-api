package com.martarey.worldpay.converter;

import com.martarey.worldpay.domain.Offer;
import com.martarey.worldpay.store.OfferDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Currency;

@Component
public class OfferDtoToOfferConverter implements Converter<OfferDto,Offer> {
    @Override
    public Offer convert(OfferDto offerDto) {
        Offer offer = new Offer.Builder(offerDto.getId(), offerDto.getDescription(), offerDto.getPrice())
                .withCurrency(Currency.getInstance(offerDto.getCurrency()))
                .withStartTime(offerDto.getStartTime())
                .withExpiredTime(offerDto.getExpiredTime())
                .build();
        return offer;
    }
}
