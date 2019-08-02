package com.martarey.worldpay.store;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface OfferStore extends JpaRepository<OfferDto,String>, JpaSpecificationExecutor {

    static Specification<OfferDto> withExpiredTimeInFuture() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("expiredTime"),LocalDateTime.now());
    }

}
