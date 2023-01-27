package com.kull.citylist.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document(collection = "user")
@AllArgsConstructor
@Data
public class User {
    @Id
    private String Id;
    private String username;
    private String password;
    private boolean enabled;
    @DBRef
    private Set<Role> roles;
}
