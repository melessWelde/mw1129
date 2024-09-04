package com.tools.point.of.sale.repository;

import com.tools.point.of.sale.entity.RentalAgreement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalAgreementRepository extends JpaRepository<RentalAgreement, Long> {
}
