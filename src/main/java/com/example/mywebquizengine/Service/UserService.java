package com.example.mywebquizengine.Service;

import com.example.mywebquizengine.Model.Projection.UserCommonView;
import com.example.mywebquizengine.Model.Projection.UserView;
import com.example.mywebquizengine.Model.Role;
import com.example.mywebquizengine.Model.User;
import com.example.mywebquizengine.Model.UserInfo.AuthRequest;
import com.example.mywebquizengine.Model.UserInfo.AuthResponse;
import com.example.mywebquizengine.Repos.UserRepository;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;


@Service
public class UserService implements UserDetailsService {

    private static final HttpTransport transport = new NetHttpTransport();
    private static final JsonFactory jsonFactory = new JacksonFactory();

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTUtil jwtTokenUtil;
    @Autowired
    private RabbitAdmin rabbitAdmin;
    @Autowired
    private MailSender mailSender;
    @Value("${hostname}")
    private String hostname;

    @Override
    public User loadUserByUsername(String email) throws ResponseStatusException {
        Optional<User> user = userRepository.findUserEntityByEmail(email);

        if (user.isPresent()) {
            return user.get();
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public User loadUserByUserId(Long userId) throws ResponseStatusException {
        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            return user.get();
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public User loadUserByUserIdProxy(Long userId) throws UsernameNotFoundException {
        return userRepository.getOne(userId);
    }

    public void updateUser(String lastName, String firstName, Long userId) {
        userRepository.updateUserInfo(firstName, lastName, userId);
    }

    public void sendCodeForChangePasswordFromPhone(String username) {

        User user = loadUserByUsername(username);

        Random random = new Random();
        int rage = 9999;
        int code = 1000 + random.nextInt(rage - 1000);

        userRepository.setChangePasswordCode(user.getUsername(), String.valueOf(code));

        try {
            mailSender.send(user.getEmail(), "Смена пароля в системе дистанционного обучения",
                    """
                            Для смены пароля в системе дистанционного обучения
                            введите в приложении код: """ + code +
                            """
                                    . 
                                     Если вы не меняли пароль на данном ресурсе, то проигнорируйте сообщение
                                    """);

        } catch (Exception e) {
            System.out.println("Не отправлено");
        }
    }

    public void sendCodeForChangePassword(Long userId) {

        User user = loadUserByUserId(userId);

        String code = UUID.randomUUID().toString();
        userRepository.setChangePasswordCode(user.getUsername(), code);

        try {
            mailSender.send(user.getEmail(), "Смена пароля в WebQuizzes", "Для смены пароля в WebQuizzes" +
                    " перейдите по ссылке: https://" + hostname + "/updatepass/" + code + " Если вы не меняли пароль на данном ресурсе, то проигнорируйте сообщение");

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void updatePassword(String password, String changePasswordCode) {
        User savedUser = getUserViaChangePasswordCode(changePasswordCode);
        savedUser.setPassword(passwordEncoder.encode(password));
        savedUser.setChangePasswordCode(UUID.randomUUID().toString());
    }

    public User findUserByActivationCode(String activationCode) {
        Optional<User> optionalUser = userRepository.findByActivationCode(activationCode);
        if (optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Вы не были приглашены");
        } else return optionalUser.get();
    }

    public User getUserViaChangePasswordCode(String changePasswordCode) {

        Optional<User> optionalUser = userRepository.findByChangePasswordCode(changePasswordCode);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

    /*public User castToUserFromOauth(OAuth2AuthenticationToken authentication) {

        User user = new User();

        if (authentication.getAuthorizedClientRegistrationId().equals("google")) {

            String username = ((String) authentication.getPrincipal().getAttributes()
                    .get("email")).replace("@gmail.com", "");

            if (userRepository.findById(username).isPresent()) {
                user = userRepository.findById(username).get();
            } else {

                user.setEmail((String) authentication.getPrincipal().getAttributes().get("email"));
                user.setFirstName((String) authentication.getPrincipal().getAttributes().get("given_name"));
                user.setLastName((String) authentication.getPrincipal().getAttributes().get("family_name"));
                user.setAvatar((String) authentication.getPrincipal().getAttributes().get("picture"));

                user.setUsername(((String) authentication.getPrincipal().getAttributes()
                        .get("email")).replace("@gmail.com", ""));

            }


        } else if (authentication.getAuthorizedClientRegistrationId().equals("github")) {
            user.setUsername(authentication.getPrincipal().getAttributes().get("login").toString());
            user.setFirstName(authentication.getPrincipal().getAttributes().get("name").toString());
            user.setLastName(authentication.getPrincipal().getAttributes().get("name").toString());
            user.setAvatar(authentication.getPrincipal().getAttributes().get("avatar_url").toString());

            if (authentication.getPrincipal().getAttributes().get("email") != null) {
                user.setEmail(authentication.getPrincipal().getAttributes().get("email").toString());
            } else {
                user.setEmail("default@default.com");
            }
        }

        //doInitialize(user);

        return user;
    }*/

    private void rabbitInitialize(String username) {

        Queue queue = new Queue(username, true, false, false);

        Binding binding = new Binding(username, Binding.DestinationType.QUEUE,
                "message-exchange", username, null);

        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareBinding(binding);

    }

    public ArrayList<User> getUserList() {
        return (ArrayList<User>) userRepository.findAll();
    }

    public UserCommonView getUserView(String username) {
        return userRepository.findByEmail(username);
    }

    public AuthResponse signInViaJwt(AuthRequest authRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            //System.out.println(authentication);
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Имя или пароль неправильны", e);
        }
        // при создании токена в него кладется username как Subject claim и список authorities как кастомный claim
        String jwt = jwtTokenUtil.generateToken((UserDetails) authentication.getPrincipal());
        return new AuthResponse(jwt);
    }

    public AuthResponse getJwtToken(User user) {
        // при создании токена в него кладется username как Subject claim и список authorities как кастомный claim
        String jwt = jwtTokenUtil.generateToken(loadUserByUsername(user.getUsername()));
        return new AuthResponse(jwt);
    }

    /*public AuthResponse signinViaGoogleToken(GoogleToken token) throws GeneralSecurityException, IOException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                // Specify the CLIENT_ID of the app that accesses the backend:
                .setAudience(Collections.singletonList(CLIENT_ID))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();

        GoogleIdToken idToken = verifier.verify(token.getIdTokenString());
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();

            // Print user identifier
            String userId = payload.getSubject();
            System.out.println("User ID: " + userId);

            // Get profile information from payload
            String email = payload.getEmail();
            boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");
            String locale = (String) payload.get("locale");
            String familyName = (String) payload.get("family_name");
            String givenName = (String) payload.get("given_name");


            String username = email.replace("@gmail.com", "");

            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setFirstName(givenName);
            user.setLastName(familyName);
            user.setAvatar(pictureUrl);

            processCheckIn(user, "OAUTH2");

            User savedUser = loadUserByUsername(user.getUsername());

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            username, null, savedUser.getAuthorities()); // Проверить работу getAuthorities
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            String jwt = jwtTokenUtil.generateToken(savedUser);
            return new AuthResponse(jwt);
            // Use or store profile information
            // ...

        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        }
    }*/

    public UserView getAuthUser(Long userId) {
        UserView allByUserId = userRepository.findAllByUserId(userId);
        if (allByUserId == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            return allByUserId;
        }
    }

    @Transactional
    public void uploadPhoto(MultipartFile file, Long userId) {
        if (!file.isEmpty()) {
            try {
                String uuid = UUID.randomUUID().toString();
                byte[] bytes = file.getBytes();

                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File("img/" + uuid + ".jpg")));
                stream.write(bytes);
                stream.close();

                String photoUrl = hostname + "/img/" + uuid + ".jpg";

                User user = loadUserByUserId(userId);
                user.setAvatar(photoUrl);

            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        }
    }

    public Boolean checkForExistUser(Long userId) {
        return userRepository.existsById(userId);
    }

    /*@Transactional
    public ProfileView getUserProfileById(String username) {
        return userRepository.findUserByUsernameOrderByUsernameAsc(username);
    }*/

    public void getUserViaChangePasswordCodePhoneApi(String username, String code) {
        Optional<User> user = userRepository.findByChangePasswordCode(code);
        if (user.isPresent() && user.get().getUsername().equals(username)) {
            return;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public User loadUserByEmail(String email) {
        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isPresent()) {
            return user.get();
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public void sendRegistrationLink(String email) {
        User user = loadUserByEmail(email);

        mailSender.send(
                email,
                "Приглашение в ",
                "Для регистрации перейдите по ссылке: " + hostname + "/start/" + user.getActivationCode()
        );
    }

    public void processCheckIn(String activationCode, String email, String firstName, String lastName, String password) {

        User user;
        if (activationCode == null) {
            user = new User();
            user.setEmail(email);
        } else {
            user = findUserByActivationCode(activationCode);
        }

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(passwordEncoder.encode(password));
        user.setActivationCode(UUID.randomUUID().toString());

        user.setAvatar("https://" + hostname + "/img/default.jpg");
        user.setEnabled(true);
        user.grantAuthority(Role.ROLE_USER);
        rabbitInitialize(user.getUsername());
        userRepository.save(user);
    }

}
