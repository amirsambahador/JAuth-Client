package org.j2os.jauth.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role implements Serializable {

    private Long roleId;

    @JsonIgnore
    private List<User> users = new ArrayList<>();


    private String roleName;

}
