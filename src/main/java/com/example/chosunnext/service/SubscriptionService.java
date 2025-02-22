package com.example.chosunnext.service;

import com.example.chosunnext.dao.SubscriptionDao;
import com.example.chosunnext.dto.SubscriptionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionService {

    final SubscriptionDao subscriptionDao;

    public int subscribe(SubscriptionDto subscriptionDto) {
        int subscription = subscriptionDao.insertSubscription(subscriptionDto);
        return subscription;
    }

    public int deleteSubscrioption(SubscriptionDto subscriptionDto) {
        int subscription = subscriptionDao.deleteSubscription(subscriptionDto);
        return subscription;
    }

    public SubscriptionDto getStatus(SubscriptionDto subscriptionDto) {
        SubscriptionDto status = subscriptionDao.getStatus(subscriptionDto);
                return status;
    }
}
