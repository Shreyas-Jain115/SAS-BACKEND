package com.shreyas.SAS.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    String token;
    String role;

    @Override
    public String toString() {
        return "UserDTO{" +
                "token='" + token + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
