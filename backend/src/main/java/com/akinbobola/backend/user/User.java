package com.akinbobola.backend.user;

import com.akinbobola.backend.listing.Listing;
import com.akinbobola.backend.role.Role;
import com.akinbobola.backend.viewing.Viewing;
import com.akinbobola.backend.viewingSchedule.ViewingSchedule;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "_user")
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails, Principal {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique = true)
    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private boolean accountLocked;

    private boolean enabled;

    @OneToMany(mappedBy = "agent")
    private List<Listing> listings;

    @OneToMany(mappedBy = "agent", cascade = CascadeType.ALL)
    private List<Viewing> viewings;

    @OneToMany(mappedBy = "user")
    private List<ViewingSchedule> viewingSchedules;

    @ManyToMany(fetch = FetchType.EAGER)
    private List <Role> roles;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedDate;

    @Override
    public Collection <? extends GrantedAuthority> getAuthorities () {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList();
    }

    @Override
    public String getPassword () {
        return password;
    }

    @Override
    public String getUsername () {
        return email;
    }

    @Override
    public boolean isAccountNonExpired () {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked () {
        return !accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired () {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled () {
        return enabled;
    }

    @Override
    public String getName () {
        return email;
    }

    public String getFullName () {
        return firstName + " " + lastName;
    }
}
