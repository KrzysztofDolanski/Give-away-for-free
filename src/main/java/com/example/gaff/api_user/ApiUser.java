package com.example.gaff.api_user;


import com.example.gaff.article.Article;
import com.example.gaff.booking.Booking;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(length = 512, nullable = false, unique = true)
    private String username;
    private String password;
    private String email;
    private String region;
    private String city;
    private String street;
    private String streetNo;
    private String zipCode;
    private String dateOfRegistration;
    private boolean isActive;
    @Column(length = 45, nullable = true)
    private String logotype;

    @Builder.Default
    private ApiUserRole userRole = ApiUserRole.USER;

    @Builder.Default
    private Boolean enabled = false;

    @Builder.Default
    private Boolean locked = false;


    @OneToMany
    private List<Article> article;
    @OneToMany
    private List<Booking> booking;

    @OneToOne
    ConfirmationToken confirmationToken;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(userRole.name());
        return Collections.singletonList(simpleGrantedAuthority);
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }


}
