package com.shreyas.SAS.DTO;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LowAttendanceDTO {
    String email;
    String subject;
    float attendance;
}
