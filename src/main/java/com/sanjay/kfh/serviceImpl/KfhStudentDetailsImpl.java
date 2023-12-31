package com.sanjay.kfh.serviceImpl;

import com.sanjay.kfh.model.Student;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @author Sanjay Vishwakarma
 */
public class KfhStudentDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 1L;
    private Long id;

    private String username;
    private String email;
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public KfhStudentDetailsImpl(Long id, String username, String email, String password,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;

    }

    public static KfhStudentDetailsImpl build(Student student) {
        List<GrantedAuthority> authorities=new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(student.getRoles().getName().name()));

        return new KfhStudentDetailsImpl(
                student.getId(),
                student.getUsername(),
                student.getEmail(),
                student.getPassword(),
                authorities);
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        KfhStudentDetailsImpl student = (KfhStudentDetailsImpl) o;
        return Objects.equals(id, student.id);
    }
}
