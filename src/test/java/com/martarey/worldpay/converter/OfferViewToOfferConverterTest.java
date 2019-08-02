package com.martarey.worldpay.converter;

import com.martarey.worldpay.controller.view.OfferView;
import com.martarey.worldpay.domain.Offer;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Currency;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OfferViewToOfferConverterTest {

    private OfferViewToOfferConverter converter;

    @Before
    public void setup(){
        converter = new OfferViewToOfferConverter();
    }

    @Test
    public void convert(){
        OfferView view = new OfferView();
        view.setDescription("description");
        view.setPrice("5.00");
        view.setCurrency("GBP");
        view.setDuration("2");
        view.setDurationUnit("DAYS");

        Offer domain= converter.convert(view);
        assertTrue(domain.getId() != null);
        assertEquals(view.getDescription(),domain.getDescription());
        assertEquals(new BigDecimal(view.getPrice()),domain.getPrice());
        assertEquals(Currency.getInstance(view.getCurrency()),domain.getCurrency());
        assertEquals(domain.getStartTime().getDayOfMonth(),LocalDateTime.now().getDayOfMonth());
        assertEquals(domain.getStartTime().getMonth(),LocalDateTime.now().getMonth());
        assertEquals(domain.getStartTime().getYear(),LocalDateTime.now().getYear());
        assertEquals(domain.getExpiredTime().getDayOfMonth(),LocalDateTime.now().plus(2,ChronoUnit.DAYS).getDayOfMonth());
        assertEquals(domain.getExpiredTime().getMonth(),LocalDateTime.now().plus(2,ChronoUnit.DAYS).getMonth());
        assertEquals(domain.getExpiredTime().getYear(),LocalDateTime.now().plus(2,ChronoUnit.DAYS).getYear());
    }

    @Test(expected = IllegalArgumentException.class)
    public void convert_ifDurationUnitInvalid(){
        OfferView view = new OfferView();
        view.setDescription("description");
        view.setPrice("5.00");
        view.setCurrency("GBP");
        view.setDuration("2");
        view.setDurationUnit("DAYTH");

        converter.convert(view);

    }

}
