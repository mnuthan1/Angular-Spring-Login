package com.vilma.spring.service.dto;


import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapping;

public class UserDto {

    @Mapping("id")
    private Long id;

    @Mapping("firstName")
    private String firstName;

    @Mapping("familyName")
    private String familyName;

    @Mapping("email")
    private String email;

    @Mapping("login")
    private String login;

    @Mapping("password")
    private String password;


    @Mapping("authorities")
    private Set<AuthorityDto> authorities = new HashSet<AuthorityDto>();

    @Mapping("enabled")
    private String enabled;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

  

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

  
    public Set<AuthorityDto> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<AuthorityDto> authorities) {
        this.authorities = authorities;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }


    public String getAuthoritiesAsString() {
        StringBuffer sb = new StringBuffer();
        for (AuthorityDto a : this.getAuthorities()) {
            sb.append(a.getName());
            sb.append(", ");
        }
        return StringUtils.substring(sb.toString(), 0, sb.length() - 2);
    }

}
