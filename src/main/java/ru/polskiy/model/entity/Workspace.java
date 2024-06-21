package ru.polskiy.model.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Workspace extends BaseEntity{

    LocalDateTime bookingDate;

    Long userId;

    LocalDateTime startDate;

    LocalDateTime endDate;

    //Duration duration; //TODO need to think about

    Long typeId;

    @Builder.Default
    Boolean isBooked =false;//TODO remove cuz we have userId to know if it is booked

}
