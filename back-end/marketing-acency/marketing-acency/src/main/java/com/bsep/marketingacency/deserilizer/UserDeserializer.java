package com.bsep.marketingacency.deserilizer;

import com.bsep.marketingacency.model.Role;
import com.bsep.marketingacency.model.User;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;

import java.util.HashSet;

import java.io.IOException;
import java.util.List;
import java.util.Set;
@Component
public class UserDeserializer extends JsonDeserializer<User> {

    @Override
    public User deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectMapper mapper = (ObjectMapper) p.getCodec();
        JsonNode node = mapper.readTree(p);

        // Extract user properties from JSON node
        Long id = node.get("id").asLong();
        String mail = node.get("mail").asText();
        String password = node.get("password").asText();
        Boolean isActivated = node.get("isActivated").asBoolean();
        Boolean isBlocked = node.get("isBlocked").asBoolean();

        // Deserialize roles
        JsonNode rolesNode = node.get("roles");
        List<Role> roles = mapper.readValue(rolesNode.traverse(mapper), new TypeReference<List<Role>>(){});

        // Deserialize authorities
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        // Create and return User object
        User user = new User();
        user.setId(id);
        user.setMail(mail);
        user.setPassword(password);
        user.setRoles(roles);;
        user.setIsBlocked(isBlocked);
        user.setIsActivated(isActivated);

        return user;
    }
}
