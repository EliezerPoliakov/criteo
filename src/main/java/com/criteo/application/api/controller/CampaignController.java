package com.criteo.application.api.controller;


import com.criteo.application.api.entity.Campaign;
import com.criteo.application.api.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * @author poliakoveliezer Campaign Controller
 */
@RestController
@RequestMapping("/campaign")
public class CampaignController {
    @Autowired
    CampaignService campaignService;

    /**
     * Create a new campaign
     */
    @PostMapping
    public @ResponseBody ResponseEntity<Campaign> createCampaign(@RequestBody Campaign campaign) {
        return ResponseEntity.status(HttpStatus.OK).body(campaignService.createCampaign(campaign));
    }
}
