package com.kull.citylist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    @Id
    private String Id;
    private String username;
    private String password;
    private String role;


}
