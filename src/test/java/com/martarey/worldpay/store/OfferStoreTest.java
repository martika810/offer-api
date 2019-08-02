package com.martarey.worldpay.store;

import com.martarey.worldpay.converter.OfferToOfferDtoConverter;
import com.martarey.worldpay.converter.OfferToOfferDtoConverterTest;
import com.martarey.worldpay.domain.Offer;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class OfferStoreTest {

    @Autowired
    private OfferStore offerStore;

    @Autowired
    private TestEntityManager entityManger;

    private OfferToOfferDtoConverter converter = new OfferToOfferDtoConverter();

    @Test
    public void saveOffer(){

        Offer expectedOffer = new Offer.Builder("Offer1", BigDecimal.valueOf(5,2))
                .withStartTime(LocalDateTime.now())
                .withExpiredTime(LocalDateTime.now().plus(1, ChronoUnit.DAYS))
                .build();
        OfferDto expectedOfferDto = converter.convert(expectedOffer);
        entityManger.persist(expectedOfferDto);

        Iterable<OfferDto> offer = offerStore.findAll();
        assertTrue(((List<OfferDto>) offer).contains(expectedOfferDto));
    }

    @Test
    public void getNonExpiredOffers() throws InterruptedException {
        Offer expectedOffer = new Offer.Builder("Offer1", BigDecimal.valueOf(5,2))
                .withStartTime(LocalDateTime.now())
                .withExpiredTime(LocalDateTime.now().plus(1, ChronoUnit.DAYS))
                .build();

        Offer expectedPastOffer = new Offer.Builder("Offer2", BigDecimal.valueOf(5,2))
                .withStartTime(LocalDateTime.now().minus(1,ChronoUnit.YEARS))
                .withExpiredTime(LocalDateTime.now().minus(1,ChronoUnit.YEARS))
                .build();

        OfferDto expectedOfferDto = converter.convert(expectedOffer);
        OfferDto expectedPastOfferDto = converter.convert(expectedPastOffer);
        entityManger.persist(expectedOfferDto);
        entityManger.persist(expectedPastOfferDto);
        Specification<OfferDto> conditionToFilterExpiredOffers = Specification.where(OfferStore.withExpiredTimeInFuture());
        List<OfferDto> offerDtoList = offerStore.findAll(conditionToFilterExpiredOffers);
        assertEquals(1, offerDtoList.size());

    }

    @Test
    public void updateOffer(){
        Offer expectedOffer = new Offer.Builder("randomId","Offer1", BigDecimal.valueOf(5,2))
                .withStartTime(LocalDateTime.now())
                .withExpiredTime(LocalDateTime.now().plus(4, ChronoUnit.DAYS))
                .build();
        OfferDto expectedOfferDto = converter.convert(expectedOffer);
        entityManger.persist(expectedOfferDto);

        OfferDto createdOffer = offerStore.findById("randomId").get();
        createdOffer.setExpiredTime(LocalDateTime.now());
        offerStore.save(createdOffer);
        OfferDto updatedOffer = offerStore.findById("randomId").get();
        assertEquals(LocalDateTime.now().getDayOfMonth(),updatedOffer.getExpiredTime().getDayOfMonth());
        assertEquals(LocalDateTime.now().getMonth(), updatedOffer.getExpiredTime().getMonth());
        assertEquals(LocalDateTime.now().getYear(), updatedOffer.getExpiredTime().getYear());



    }


}
