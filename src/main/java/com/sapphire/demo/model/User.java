package com.sapphire.demo.model;

import lombok.Data;

@Data
public class User {
    private Integer Id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatar_url;
    private String bio;
}
