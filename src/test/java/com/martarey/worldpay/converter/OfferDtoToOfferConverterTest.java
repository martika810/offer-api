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

public class OfferDtoToOfferConverterTest {

    private OfferDtoToOfferConverter converter;

    @Before
    public void setup() {
        converter = new OfferDtoToOfferConverter();
    }

    @Test
    public void convert(){
        OfferDto dto= new OfferDto();
        dto.setId("randomId");
        dto.setDescription("description");
        dto.setPrice(new BigDecimal(5));
        dto.setCurrency("GBP");
        dto.setStartTime(LocalDateTime.now());
        dto.setExpiredTime(LocalDateTime.now().plus(1, ChronoUnit.DAYS));

        Offer domain = converter.convert(dto);

        assertEquals("randomId", domain.getId());
        assertEquals("description", domain.getDescription());
        assertEquals(BigDecimal.valueOf(5) , domain.getPrice());
        assertEquals(Currency.getInstance("GBP"), domain.getCurrency());
        assertEquals(LocalDateTime.now().getDayOfMonth(), domain.getStartTime().getDayOfMonth());
        assertEquals(LocalDateTime.now().getMonth(), dto.getStartTime().getMonth());
        assertEquals(LocalDateTime.now().getYear(), dto.getStartTime().getYear());
        assertEquals(LocalDateTime.now().plus(1, ChronoUnit.DAYS).getDayOfMonth(),dto.getExpiredTime().getDayOfMonth());
        assertEquals(LocalDateTime.now().plus(1, ChronoUnit.DAYS).getMonth(), dto.getExpiredTime().getMonth());
        assertEquals(LocalDateTime.now().plus(1, ChronoUnit.DAYS).getYear(),dto.getExpiredTime().getYear());

    }

    @Test(expected = IllegalArgumentException.class)
    public void convert_ifInvalidArguments(){
        OfferDto dto= new OfferDto();
        dto.setId("randomId");
        dto.setDescription("");
        dto.setPrice(new BigDecimal(5));
        dto.setCurrency("GBP");
        dto.setStartTime(LocalDateTime.now());
        dto.setExpiredTime(LocalDateTime.now().plus(1, ChronoUnit.DAYS));

        converter.convert(dto);
    }


}
