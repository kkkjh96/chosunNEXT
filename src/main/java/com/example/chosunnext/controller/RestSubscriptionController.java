package com.example.chosunnext.controller;

import com.example.chosunnext.dto.SubscriptionDto;
import com.example.chosunnext.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscribe")
@RequiredArgsConstructor
@Slf4j
public class RestSubscriptionController {

    final SubscriptionService subscriptionService;


    @PostMapping("/subscription")
    public ResponseEntity<?> addSubscription(@RequestBody SubscriptionDto subscriptionDto) {
        int subscription =   subscriptionService.subscribe(subscriptionDto);
        return ResponseEntity.ok(subscription);
    }


    @DeleteMapping("/subscription")
    public ResponseEntity<?> deleteSubscription(@RequestBody SubscriptionDto subscriptionDto) {
        int subscription =   subscriptionService.deleteSubscrioption(subscriptionDto);
        return ResponseEntity.ok(subscription);

    }

    @GetMapping("/status")
    public ResponseEntity<?> statusSubscription(@RequestBody SubscriptionDto subscriptionDto) {
        SubscriptionDto subscription =   subscriptionService.getStatus(subscriptionDto);
        return ResponseEntity.ok(subscription);
    }

}
