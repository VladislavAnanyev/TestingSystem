package com.example.mywebquizengine.Model;

import com.example.mywebquizengine.Model.Chat.Dialog;
import com.example.mywebquizengine.MywebquizengineApplication;
import com.example.mywebquizengine.Service.UserService;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity(name = "USERS")
public class User implements UserDetails, OAuth2User {

    private static final long serialVersionUID = -7422293274841574951L;
    @Id
    @NotBlank
    /*@Pattern(regexp = """
            [\S]{0,}
            """) // без пробелов*/
    private String username;
    @NotBlank
    @NotNull
    @Email
    private String email;
    private String activationCode;
    private String changePasswordCode;
    @NotBlank
    @NotNull
    private String firstName;
    @NotBlank
    @NotNull
    private String lastName;

    @Size(min = 5)
    private String password;
    private String avatar;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "users_dialogs",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "dialog_id")
    )
    private List<Dialog> dialogs = new ArrayList<>();
    @Transient
    private boolean accountNonExpired;
    @Transient
    private boolean accountNonLocked;
    @Transient
    private boolean credentialsNonExpired;
    @Transient
    private boolean enabled;
    private boolean status;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Role> roles;
    private boolean online;

    public User() {
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
    }

    public User(String username, String email, String firstName, String lastName, String password) {
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.status = false;
    }

    public User(String username, String firstName, String lastName, String avatar) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.avatar = avatar;
    }

    public List<Dialog> getDialogs() {
        return dialogs;
    }

    public void setDialogs(List<Dialog> dialogs) {
        this.dialogs = dialogs;
    }

    public boolean getOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public List<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.toString())));
        return authorities;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAdmin(String name) {
        return MywebquizengineApplication.ctx.getBean(UserService.class)
                .loadUserByUsername(name).getRoles().contains(Role.ROLE_ADMIN);
    }

    public void grantAuthority(Role authority) {
        if (roles == null) roles = new ArrayList<>();
        this.roles.add(authority);
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getChangePasswordCode() {
        return changePasswordCode;
    }

    public void setChangePasswordCode(String changePasswordCode) {
        this.changePasswordCode = changePasswordCode;
    }

    @Override
    public String getName() {
        return username;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }
}