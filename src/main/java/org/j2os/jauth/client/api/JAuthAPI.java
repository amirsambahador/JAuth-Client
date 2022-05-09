package org.j2os.jauth.client.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.j2os.jauth.common.infrastructure.OS;
import org.j2os.jauth.entity.Role;
import org.j2os.jauth.entity.User;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JAuthAPI {

    private String token;
    private String server;

    public JAuthAPI(String server, String token) {
        this.token = token;
        this.server = server;
    }

    public Object saveUser(User user) throws IOException {
        String json = OS.get(String.format("%s/api/user/save?jauth-token=%s&username=%s&password=%s", this.server, this.token, user.getUsername(), user.getPassword()));
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            user = objectMapper.readValue(json, User.class);
            return user;
        } catch (JsonMappingException e) {
            Map map = objectMapper.readValue(json, Map.class);
            return map;
        }
    }

    public void removeUser(User user) throws IOException {
        OS.get(String.format("%s/api/user/remove?jauth-token=%s&userId=%s", this.server, this.token, user.getUserId()));
    }

    public Object addRole(User user, Role role) throws IOException {
        String json = OS.get(String.format("%S/api/user/addRole?jauth-token=%s&userId=%s&roleId=%s", this.server, this.token, user.getUserId(), role.getRoleId()));
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            user = objectMapper.readValue(json, User.class);
            return user;
        } catch (JsonMappingException e) {
            Map map = objectMapper.readValue(json, Map.class);
            return map;
        }
    }

    public String login(User user) throws IOException {
        return OS.get(String.format("%s/api/user/login?username=%s&password=%s", this.server, user.getUsername(), user.getPassword()));
    }

    public Object findByToken(String token) throws IOException {
        String json = OS.get(String.format("%s/api/user/findByToken?jauth-token=%s&token=%s", this.server, this.token, token));
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            User user = objectMapper.readValue(json, User.class);
            return user;
        } catch (JsonMappingException e) {
            Map map = objectMapper.readValue(json, Map.class);
            return map;
        }
    }

    public Object findByUsername(User user) throws IOException {
        String json = OS.get(String.format("%s/api/user/findByUsername?jauth-token=%s&username=%s", this.server, this.token, user.getUsername()));
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            user = objectMapper.readValue(json, User.class);
            return user;
        } catch (JsonMappingException e) {
            Map map = objectMapper.readValue(json, Map.class);
            return map;
        }
    }

    public Object findAllUsers() throws IOException {
        String json = OS.get(String.format("%s/api/user/findAll?jauth-token=%s", this.server, this.token));
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<User> users = objectMapper.readValue(json, new TypeReference<List<User>>() {
            });
            return users;
        } catch (JsonMappingException e) {
            Map map = objectMapper.readValue(json, Map.class);
            return map;
        }
    }

    public Object saveRole(Role role) throws IOException {
        String json = OS.get(String.format("%s/api/role/save?jauth-token=%s&roleName=%s", this.server, this.token, role.getRoleName()));
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            role = objectMapper.readValue(json, Role.class);
            return role;
        } catch (JsonMappingException e) {
            Map map = objectMapper.readValue(json, Map.class);
            return map;
        }
    }

    public void removeRole(Role role) throws IOException {
        OS.get(String.format("%s/api/role/remove?jauth-token=%s&roleId=%s", this.server, this.token, role.getRoleId()));
    }

    public Object findAllRoles() throws IOException {
        String json = OS.get(String.format("%s/api/role/findAll?jauth-token=%s", this.server, this.token));
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Role> roles = objectMapper.readValue(json, new TypeReference<List<Role>>() {
            });
            return roles;
        } catch (JsonMappingException e) {
            Map map = objectMapper.readValue(json, Map.class);
            return map;
        }
    }

    public Object findByRoleName(Role role) throws IOException {
        String json = OS.get(String.format("%s/api/role/findByRoleName?jauth-token=%s&roleName=%s", this.server, this.token, role.getRoleName()));
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            role = objectMapper.readValue(json, Role.class);
            return role;
        } catch (JsonMappingException e) {
            Map map = objectMapper.readValue(json, Map.class);
            return map;
        }
    }


}
