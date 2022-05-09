package org.j2os.jauth.entity;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements Serializable {


    private Long userId;

    private Long creationTime;


    private String username;


    private String token;


    private String password;


    private List<Role> roles = new ArrayList<>();

}
