package com.martarey.worldpay.domain;

import org.assertj.core.util.Preconditions;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

public final class Offer {

    private final String id;
    private final String description;
    private final BigDecimal price;
    private final Currency currency;
    private final LocalDateTime startTime;
    private final LocalDateTime expiredTime;

    private Offer(Builder builder) {
        Preconditions.checkNotNullOrEmpty(builder.description);
        Preconditions.checkNotNull(builder.price);
        if(builder.id == null || builder.id.isEmpty()) {
            this.id = UUID.randomUUID().toString();
        }else{
            this.id = builder.id;
        }
        this.description = builder.description;
        this.price = builder.price;
        if(builder.currency != null) {
            this.currency = builder.currency;
        }else{
            this.currency = Currency.getInstance("GBP");
        }

        this.startTime = builder.startTime;
        this.expiredTime = builder.expiredTime;
    }

    public static class Builder {
        private String id;
        private String description;
        private BigDecimal price;
        private Currency currency;
        private LocalDateTime startTime;
        private LocalDateTime expiredTime;

        public Builder(String description, BigDecimal price){
            Preconditions.checkNotNullOrEmpty(description);
            Preconditions.checkNotNull(price);
            this.description = description;
            this.price = price;
        }

        public Builder(String id, String description, BigDecimal price){
            this.id = id;

            this.description = description;
            this.price = price;
            this.currency = Currency.getInstance(Locale.UK);
        }

        public Builder withCurrency(Currency currency){
            this.currency = currency;
            return this;
        }

        public Builder withStartTime(LocalDateTime startTime){
            this.startTime = startTime;
            return this;
        }

        public Builder withExpiredTime(LocalDateTime expiredTime){
            this.expiredTime = expiredTime;
            return this;
        }

        public Offer build() {
            return new Offer(this);
        }
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getExpiredTime() {
        return expiredTime;
    }
}
