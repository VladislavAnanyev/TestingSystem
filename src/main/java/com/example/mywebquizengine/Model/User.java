package com.example.mywebquizengine.Model;

import com.example.mywebquizengine.Model.Chat.Dialog;
import com.example.mywebquizengine.MywebquizengineApplication;
import com.example.mywebquizengine.Service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;

@Entity(name = "USERS")
public class User implements UserDetails, OAuth2User  {

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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_username")
    @OrderBy("position ASC")
    private List<Photo> photos;

    private String avatar;

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "users_dialogs",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "dialog_id")
    )
    private List<Dialog> dialogs = new ArrayList<>();


    public List<Dialog> getDialogs() {
        return dialogs;
    }

    public void setDialogs(List<Dialog> dialogs) {
        this.dialogs = dialogs;
    }

    @Transient
    private boolean accountNonExpired;

    @Transient
    private boolean accountNonLocked;

    @Transient
    private boolean credentialsNonExpired;

    @Transient
    private boolean enabled;

    private boolean status;

    private Integer balance;

    private static final long serialVersionUID = -7422293274841574951L;

    public User(){

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

    public User(String username, String firstName, String lastName, List<Photo> photos) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.photos = photos;
    }

    public User(String username, String firstName, String lastName, String avatar) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        Photo photo = new Photo();
        photo.setUrl(avatar);
        List<Photo> photos = new ArrayList<>();
        photos.add(photo);
        this.photos = photos;
    }


    private String online;


    public void setOnline(String online) {
        this.online = online;
    }

    public String getOnline() {
        return online;
    }

    @ManyToMany
    private List<User> friends;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Role> roles;

    @Override
    public List<GrantedAuthority> getAuthorities(){
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

    public boolean isAdmin(String name) {
        return MywebquizengineApplication.ctx.getBean(UserService.class)
                .loadUserByUsername(name).getRoles().contains(Role.ROLE_ADMIN);
    }

    public void grantAuthority(Role authority) {
        if ( roles == null ) roles = new ArrayList<>();
        this.roles.add(authority);
    }


    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> avatarName) {
        Photo photo = new Photo();
        photo.setUrl(avatarName.get(0));
        photo.setPosition(0);
        this.photos = Collections.singletonList(photo);
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
    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getBalance() {
        return balance;
    }


    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = new ArrayList<>();
        this.friends.addAll(friends);
    }

    public void addFriend(User user) {
        this.friends.add(user);
        user.getFriends().add(this);
    }

    @Override
    public String getName() {
        return username;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    public void addPhoto(Photo photo) {
        this.photos.add(photo);
        //photo.setUser(this);
        //photo.getUser().setPhotos(this.photos);
    }
}