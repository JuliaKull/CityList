package com.kull.citylist.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "role")
@AllArgsConstructor
@Data
public class Role{
    @Id
    private String Id;
    private String title;
}
