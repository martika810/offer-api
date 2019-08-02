package com.martarey.worldpay.converter;

import com.martarey.worldpay.domain.Offer;
import com.martarey.worldpay.store.OfferDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OfferToOfferDtoConverter implements Converter<Offer,OfferDto> {
    @Override
    public OfferDto convert(Offer offer) {
        OfferDto dto= new OfferDto();
        dto.setId(offer.getId());
        dto.setDescription(offer.getDescription());
        dto.setPrice(offer.getPrice());
        dto.setCurrency(offer.getCurrency().toString());
        dto.setStartTime(offer.getStartTime());
        dto.setExpiredTime(offer.getExpiredTime());
        return dto;
    }
}
