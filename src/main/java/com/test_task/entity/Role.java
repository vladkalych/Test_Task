package com.test_task.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "t_role")
@Data
@NoArgsConstructor
public class Role implements GrantedAuthority {

    @Id
    private Long id;
    private String name;

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    // See in original
    @Override
    public String getAuthority() {
        return getName();
    }
}