package com.bsep.marketingacency.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "roles_perms", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id"))
    private Set<Permission> permissions = new HashSet<Permission>();


    @Override
    @JsonIgnore
    public String getAuthority() {
        return name;
    }
}
