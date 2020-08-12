package com.floyd.eCigManagement.repositories;

import com.floyd.eCigManagement.model.Arome;
import com.floyd.eCigManagement.model.Preparation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface PreparationRepository extends JpaRepository<Preparation, Integer> {
    @Transactional
    Long deleteByArome(Arome id);
}
