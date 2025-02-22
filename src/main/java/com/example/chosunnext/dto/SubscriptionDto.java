package com.example.chosunnext.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SubscriptionDto {

    private int subscriptionId;
    private String userId;
    private String reporterId;
    private Date subscriptionDate;
    private int subscriptionStatus;

}
