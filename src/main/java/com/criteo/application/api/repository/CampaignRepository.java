package com.criteo.application.api.repository;

import com.criteo.application.api.entity.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * @author poliakoveliezer CampaignRepository
 */
public interface CampaignRepository extends JpaRepository<Campaign, Long> {

    List<Campaign> findByStartDate(Date startDate);
}
