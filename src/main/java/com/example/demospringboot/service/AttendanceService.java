package com.example.demospringboot.service;

import com.example.demospringboot.converter.DataObjectConverter;
import com.example.demospringboot.persistence.mapper.AttendanceMapper;
import com.example.demospringboot.vo.ClockingPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttendanceService {

    @Autowired
    private DataObjectConverter dataObjectConverter;

    @Autowired
    private AttendanceMapper mapper;

    public ClockingPayload clockIn(final long userId) {
        Date now = new Date(System.currentTimeMillis());
        long id = mapper.clockIn(userId, now);
        return ClockingPayload.init().id(id).userId(userId).clockInAt(now).build();
    }

    public ClockingPayload clockOut(final long userId) {
        Date now = new Date(System.currentTimeMillis());
        long id = mapper.findTop1IdByUserIdAndClockInTimeBetween(userId, startOfTheDay(now), now);
        mapper.clockOut(id, now);
        return ClockingPayload.init().id(id).userId(userId).clockOutAt(now).build();
    }

    private Date startOfTheDay(final Date dateToConvert) {
        return Date.from(
                dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
                        .withHour(0).withMinute(0).withSecond(0).withNano(0)
                        .atZone(ZoneId.systemDefault()).toInstant());
    }

    public List<ClockingPayload> getAttendanceRecordsOf(final long userId) {
        return mapper.findByUserId(userId).stream().map(dataObjectConverter::convertDomainToPayload).collect(Collectors.toList());
    }
}
