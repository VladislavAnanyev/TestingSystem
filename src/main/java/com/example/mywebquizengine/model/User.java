package com.example.mywebquizengine.model;

import com.example.mywebquizengine.MywebquizengineApplication;
import com.example.mywebquizengine.model.chat.Dialog;
import com.example.mywebquizengine.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@Entity(name = "USERS")
public class User implements UserDetails, OAuth2User {

    private static final long serialVersionUID = -7422293274841574951L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long userId;

    @NotBlank
    @NotNull
    @Email
    private String email;
    private String activationCode;
    private String changePasswordCode;
    @NotBlank
    @Column(nullable = false)
    private String firstName;
    @NotBlank
    private String lastName;
    @Size(min = 5)
    private String password;
    @NotNull
    private String avatar;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "courses_members",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> courses = new HashSet<>();

    public String getChangePasswordCode() {
        return changePasswordCode;
    }

    public void setChangePasswordCode(String changePasswordCode) {
        this.changePasswordCode = changePasswordCode;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "GROUP_ID")
    private Group group;

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

    @Enumerated(EnumType.STRING)
    private Role role;

    public User() {
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
    }

    public User(String username, String email, String firstName, String lastName, String password) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public User(Long userId, String firstName, String lastName, String avatar, String email) {
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.avatar = avatar;
    }

    public User(String username, String firstName, String lastName, String avatar) {
        this.email = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.avatar = avatar;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<Dialog> getDialogs() {
        return dialogs;
    }

    public void setDialogs(List<Dialog> dialogs) {
        this.dialogs = dialogs;
    }

    @Override
    public List<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.toString()));
        return authorities;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
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
                .loadUserByUsername(name).getRole().equals(Role.ROLE_ADMIN);
    }

    public void grantAuthority(Role authority) {
        this.role = authority;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role roles) {
        this.role = roles;
    }

    @Override
    public String getName() {
        return String.valueOf(userId);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }
}