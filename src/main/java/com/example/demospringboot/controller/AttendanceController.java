package com.example.demospringboot.controller;

import com.example.demospringboot.dto.UserDto;
import com.example.demospringboot.service.AttendanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "Attendance")
@RestController
@RequestMapping("attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @ApiOperation(value = "Clock in.", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping("/clockIn")
    public ResponseEntity<?> clockIn(@ApiIgnore @AuthenticationPrincipal UserDto user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(attendanceService.clockIn(user.getUserId()));
    }

    @ApiOperation(value = "Clock out.", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping("/clockOut")
    public ResponseEntity<?> clockOut(@ApiIgnore @AuthenticationPrincipal UserDto user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(attendanceService.clockOut(user.getUserId()));
    }

    @ApiOperation(value = "Get user's full attendance records.", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping("/user/records")
    public ResponseEntity<?> getOneUserAttendanceRecords(@ApiIgnore @AuthenticationPrincipal UserDto user) {
        return ResponseEntity.ok(attendanceService.getAttendanceRecordsOf(user.getUserId()));
    }
}
