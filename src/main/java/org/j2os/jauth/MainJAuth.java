package org.j2os.jauth;

import org.j2os.jauth.client.api.JAuthAPI;
import org.j2os.jauth.entity.Role;
import org.j2os.jauth.entity.User;

import java.io.IOException;

public class MainJAuth {
    public static void main(String[] args) throws IOException {

        JAuthAPI jAuthAPI = new JAuthAPI("http://localhost:8081", "myjava123");
        jAuthAPI.saveRole(Role.builder().roleName("/modir").build());
        jAuthAPI.saveRole(Role.builder().roleName("/karbar").build());
        User user1 = (User) jAuthAPI.saveUser(User.builder().username("amirsam").password("myjava123").build());
        User user2 = (User) jAuthAPI.saveUser(User.builder().username("marjan").password("myjava123").build());
        Role managerRole = (Role) jAuthAPI.findByRoleName(Role.builder().roleName("/modir").build());
        Role userRole = (Role) jAuthAPI.findByRoleName(Role.builder().roleName("/karbar").build());
        user1 = (User) jAuthAPI.addRole(user1,  managerRole);
        user1 = (User) jAuthAPI.addRole(user1, userRole);
        user2 = (User) jAuthAPI.addRole(user2, managerRole);




        /*
        User user = User.builder().username("amir").password("amir").build();
        String token = jAuthAPI.login(user);
        user = (User) jAuthAPI.findByToken(token);
        System.out.println(user.getUsername());
        for (Role role : user.getRoles()) {
            System.out.println(role.getRoleName());
        }
        */

        /*
        List<User> users = (List<User>) jAuthAPI.findAllUsers();
        for (User user : users) {
            System.out.println(user.getUsername());
            for (Role role : user.getRoles()) {
                System.out.println(role.getRoleName());
            }
        }
        */


    }
}
