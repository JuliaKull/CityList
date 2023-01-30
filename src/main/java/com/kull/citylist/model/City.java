package com.kull.citylist.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cities")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class City {
    @Id
    private String id;
    private String name;
    private String photoUrl;

    public City(String name, String photoUrl) {
        this.name = name;
        this.photoUrl = photoUrl;
    }
}
