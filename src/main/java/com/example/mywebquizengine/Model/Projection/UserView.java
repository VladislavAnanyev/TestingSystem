package com.example.mywebquizengine.Model.Projection;

import com.example.mywebquizengine.Model.Photo;
import com.example.mywebquizengine.Model.Role;
import org.apache.commons.codec.language.bm.Rule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;

import javax.validation.Valid;
import java.util.List;

public interface UserView {
    String getUsername();
    String getFirstName();
    String getLastName();
    @Value("#{target.photos.get(0).url}")
    String getAvatar();
    String getEmail();
    Integer getBalance();
    List<Role> getRoles();
    boolean isStatus();
    String getOnline();
}
