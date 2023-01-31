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
public class CityDto {
    @Id
    private String id;
    private String name;
    private String photoUrl;

}
