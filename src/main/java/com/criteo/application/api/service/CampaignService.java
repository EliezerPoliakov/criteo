package com.criteo.application.api.service;

import com.criteo.application.api.entity.Campaign;
import com.criteo.application.api.repository.CampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

/**
 * @author poliakoveliezer Campaign Service
 */
@Service
public class CampaignService {

    private static final int START_DELAY = 1;
    private static final int DURATION = 10;
    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private ProductService productService;

    /**
     * create a new campaign
     */
    public Campaign createCampaign(Campaign campaign) {
        productService.updateBidForCampaignProducts(campaign.getCodes(), campaign.getBid());
        return campaignRepository.save(campaign);
    }

    /**
     * update campaigns every day at midnight
     * 1. find campaigns that start date is 1 day before today and add bids to their products
     * 2. find campaigns that start date is 11 days before today and subtract bids from their products, then delete them
     */
    @Scheduled(cron = "@daily")
    public void updateCampaigns() {
        campaignRepository.findByStartDate(Date.from(Instant.now().minus(START_DELAY, ChronoUnit.DAYS))).forEach(campaign -> productService.updateBidForCampaignProducts(campaign.getCodes(), campaign.getBid()));
        List<Campaign> campaignsToDelete = campaignRepository.findByStartDate(Date.from(Instant.now().minus(START_DELAY + DURATION, ChronoUnit.DAYS)));
        campaignsToDelete.forEach(campaign -> productService.updateBidForCampaignProducts(campaign.getCodes(), -campaign.getBid()));
        campaignRepository.deleteAll(campaignsToDelete);
    }

}
