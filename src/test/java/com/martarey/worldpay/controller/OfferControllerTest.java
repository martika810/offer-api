package com.martarey.worldpay.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.martarey.worldpay.controller.view.OfferView;
import com.martarey.worldpay.converter.OfferViewToOfferConverter;
import com.martarey.worldpay.domain.Offer;
import com.martarey.worldpay.service.OfferService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Currency;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OfferControllerTest {

    @Autowired
    private MockMvc mvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private OfferService offerService;

    @MockBean
    private OfferViewToOfferConverter converterFromViewToDomain;

    @Test
    public void getActiveOffer() throws Exception {

        // Arrange
        Offer offer = new Offer.Builder("description",new BigDecimal(5))
                .withStartTime(LocalDateTime.now())
                .withExpiredTime(LocalDateTime.now().plus(1,ChronoUnit.DAYS))
                .build();
        Offer offer2 = new Offer.Builder("description2",new BigDecimal(10))
                .withStartTime(LocalDateTime.now())
                .withExpiredTime(LocalDateTime.now().plus(1,ChronoUnit.DAYS))
                .build();
        when(offerService.getActive()).thenReturn(Stream.of(offer,offer2).collect(Collectors.toList()));

        mvc.perform(get("/offers/active"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].description", is("description")))
                .andExpect(jsonPath("$.[1].description", is("description2")));;

    }

    @Test
    public void createOffer() throws Exception {
        OfferView view = new OfferView();
        view.setDescription("Description 2");
        view.setPrice("5");
        view.setCurrency("GBP");
        view.setDuration("2");
        view.setDurationUnit("DAYS");

        Offer offer = new Offer.Builder("Description 2",new BigDecimal(5))
                .withCurrency(Currency.getInstance("GBP"))
                .withStartTime(LocalDateTime.now())
                .withExpiredTime(LocalDateTime.now().plus(2,ChronoUnit.DAYS)).build();

        when(converterFromViewToDomain.convert(eq(view))).thenReturn(offer);
        doNothing().when(offerService).create(any(Offer.class));

        mvc.perform(post("/offers")
                .content(objectMapper.writeValueAsBytes(view))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());


    }

}
