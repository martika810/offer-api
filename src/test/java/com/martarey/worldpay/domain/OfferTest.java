package com.martarey.worldpay.domain;

import org.h2.tools.Server;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Currency;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

public class OfferTest {

    @Test
    public void createOffer(){
        Offer offer = new Offer.Builder("Offer1", BigDecimal.valueOf(5,2)).build();
        assertEquals(offer.getDescription(),"Offer1");
        assertEquals(offer.getPrice(),BigDecimal.valueOf(5,2));
        assertEquals(offer.getCurrency(), Currency.getInstance(Locale.UK));
    }

    @Test
    public void createOfferWithDuration(){
        Offer offer = new Offer.Builder("Offer1", BigDecimal.valueOf(5,2))
                .withStartTime(LocalDateTime.now())
                .withExpiredTime(LocalDateTime.now().plus(1, ChronoUnit.DAYS))
                .build();
        assertEquals(offer.getStartTime().getDayOfMonth(), LocalDate.now().getDayOfMonth());
        assertEquals(offer.getStartTime().getMonth(), LocalDateTime.now().getMonth());
        assertEquals(offer.getStartTime().getYear(), LocalDateTime.now().getYear());

        assertEquals(offer.getExpiredTime().getDayOfMonth(),
                LocalDate.now().plus(1,ChronoUnit.DAYS).getDayOfMonth());
        assertEquals(offer.getExpiredTime().getMonth(), LocalDateTime.now().plus(1,ChronoUnit.DAYS).getMonth());
        assertEquals(offer.getExpiredTime().getYear(), LocalDateTime.now().plus(1,ChronoUnit.DAYS).getYear());
    }

    @Test(expected = NullPointerException.class)
    public void createOfferWithInvalidArguments(){
        new Offer.Builder(null, BigDecimal.valueOf(5,2))
                .withStartTime(LocalDateTime.now())
                .withExpiredTime(LocalDateTime.now().plus(1, ChronoUnit.DAYS))
                .build();
    }



}
