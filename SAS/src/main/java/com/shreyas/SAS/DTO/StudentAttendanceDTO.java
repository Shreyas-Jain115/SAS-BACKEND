package com.shreyas.SAS.DTO;

import com.shreyas.SAS.Entity.Attendance;
import com.shreyas.SAS.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentAttendanceDTO {
    User user;
    List<Attendance> attendanceList;

}
