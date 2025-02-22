package com.example.chosunnext.dao;

import com.example.chosunnext.dto.SubscriptionDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SubscriptionDao {

    int insertSubscription(SubscriptionDto subscriptionDto);

    int deleteSubscription(SubscriptionDto subscriptionDto);

    SubscriptionDto getStatus(SubscriptionDto subscriptionDto);
}
