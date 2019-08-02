package com.martarey.worldpay.converter;

import com.martarey.worldpay.domain.Offer;
import com.martarey.worldpay.store.OfferDto;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Currency;

import static org.junit.Assert.assertEquals;

public class OfferToOfferDtoConverterTest {

    private OfferToOfferDtoConverter converter;

    @Before
    public void setup(){
        converter = new OfferToOfferDtoConverter();
    }

    @Test
    public void converter(){
        Offer domain = new Offer.Builder("randomId","Offer1", BigDecimal.valueOf(5,2))
                .withCurrency(Currency.getInstance("GBP"))
                .withStartTime(LocalDateTime.now())
                .withExpiredTime(LocalDateTime.now().plus(1, ChronoUnit.DAYS))
                .build();

        OfferDto dto = converter.convert(domain);
        assertEquals("randomId", dto.getId());
        assertEquals("Offer1", dto.getDescription());
        assertEquals(BigDecimal.valueOf(5,2) , dto.getPrice());
        assertEquals(Currency.getInstance("GBP").toString(), dto.getCurrency());
        assertEquals(LocalDateTime.now().getDayOfMonth(), dto.getStartTime().getDayOfMonth());
        assertEquals(LocalDateTime.now().getMonth(), dto.getStartTime().getMonth());
        assertEquals(LocalDateTime.now().getYear(), dto.getStartTime().getYear());
        assertEquals(LocalDateTime.now().plus(1, ChronoUnit.DAYS).getDayOfMonth(),dto.getExpiredTime().getDayOfMonth());
        assertEquals(LocalDateTime.now().plus(1, ChronoUnit.DAYS).getMonth(), dto.getExpiredTime().getMonth());
        assertEquals(LocalDateTime.now().plus(1, ChronoUnit.DAYS).getYear(),dto.getExpiredTime().getYear());

    }
}
