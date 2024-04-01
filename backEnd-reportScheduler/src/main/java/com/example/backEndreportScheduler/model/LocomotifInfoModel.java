package com.example.backEndreportScheduler.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
@Document(collection = "locomotive_infos")
public class LocomotifInfoModel {
    
    @Id
    @Field("Code")
    private String code;

    @Field("Name")
    private String name;

    @Field("Dimension")
    private String dimension;

    @Field("Status")
    private String status;

    @Field("DateAndTime")
    private String dateAndTime;
}
