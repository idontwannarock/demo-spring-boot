package com.example.demospringboot.persistence.mapper;

import com.example.demospringboot.persistence.domain.Attendance;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface AttendanceMapper {

    @Insert("INSERT INTO spring_boot_sample_mybatis.attendance (`user_id`, `clock_in_time`) \n" +
            "VALUES (#{userId}, #{clockInTime})")
    @SelectKey(keyProperty = "id", before = false, resultType = Long.class,
            statement = "SELECT LAST_INSERT_ID()")
    long clockIn(@Param("userId") Long userId, @Param("clockInTime") Date clockInTime);

    @Select("SELECT `id` \n" +
            "FROM spring_boot_sample_mybatis.attendance \n" +
            "WHERE `user_id` = #{userId} AND `clock_in_time` IS NOT NULL AND `clock_in_time` BETWEEN #{fromTime} AND #{toTime} \n" +
            "ORDER BY `clock_in_time` DESC \n" +
            "LIMIT 1")
    long findTop1IdByUserIdAndClockInTimeBetween(@Param("userId") Long userId, @Param("fromTime") Date fromTime, @Param("toTime") Date toTime);

    @Update("UPDATE spring_boot_sample_mybatis.attendance \n" +
            "SET `clock_out_time` = #{clockOutTime} \n" +
            "WHERE `id` = #{id}")
    long clockOut(@Param("id") Long id, @Param("clockOutTime") Date clockOutTime);

    @Select("SELECT `id`, `clock_in_time`, `clock_out_time` \n" +
            "FROM spring_boot_sample_mybatis.attendance \n" +
            "WHERE `user_id` = #{userId} \n" +
            "ORDER BY `clock_in_time`")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "clockInTime", column = "clock_in_time"),
            @Result(property = "clockOutTime", column = "clock_out_time")
    })
    List<Attendance> findByUserId(@Param("userId") Long userId);
}
