package com.kull.citylist.model;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cities")
@RequiredArgsConstructor
@Data
public class City {
    @Id
    private String id;
    private String name;
    private byte[] photo;


}
