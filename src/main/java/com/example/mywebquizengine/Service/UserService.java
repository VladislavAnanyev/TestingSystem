package com.example.mywebquizengine.Service;

import com.example.mywebquizengine.Model.Order;
import com.example.mywebquizengine.Model.Photo;
import com.example.mywebquizengine.Model.Projection.ProfileView;
import com.example.mywebquizengine.Model.Projection.UserCommonView;
import com.example.mywebquizengine.Model.Projection.UserView;
import com.example.mywebquizengine.Model.Role;
import com.example.mywebquizengine.Model.User;
import com.example.mywebquizengine.Model.UserInfo.AuthRequest;
import com.example.mywebquizengine.Model.UserInfo.AuthResponse;
import com.example.mywebquizengine.Model.UserInfo.GoogleToken;
import com.example.mywebquizengine.Repos.PhotoRepository;
import com.example.mywebquizengine.Repos.UserRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.*;


@Service
public class UserService implements UserDetailsService {

    private static final HttpTransport transport = new NetHttpTransport();
    private static final JsonFactory jsonFactory = new JacksonFactory();
    @Value("${notification-secret}")
    String notification_secret;
    @Value("${androidGoogleClientId}")
    private String CLIENT_ID;
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
    @Autowired
    private PaymentServices paymentServices;
    @Autowired
    private PhotoRepository photoRepository;

    @Override
    public User loadUserByUsername(String username) throws ResponseStatusException {
        Optional<User> user = userRepository.findById(username);

        if (user.isPresent()) {
            return user.get();
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }



    public User loadUserByUsernameProxy(String username) throws UsernameNotFoundException {
        return userRepository.getOne(username);
    }

    private void saveUser(User user, String type) {

        Optional<User> optionalUser = userRepository.findById(user.getUsername());
        if (type.equals("OAUTH2")) {
            if (optionalUser.isEmpty()) {
                userRepository.save(user);
            }
        } else if (type.equals("BASIC")) {
            if (optionalUser.isEmpty()) {
                userRepository.save(user);
            } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }

    public void updateUser(String lastName, String firstName, String username) {
        userRepository.updateUserInfo(firstName, lastName, username);
    }

    public void sendCodeForChangePasswordFromPhone(String username) {

        User user = loadUserByUsername(username);

        Random random = new Random();
        int rage = 9999;
        int code = 1000 + random.nextInt(rage - 1000);

        userRepository.setChangePasswordCode(user.getUsername(), String.valueOf(code));

        try {
            mailSender.send(user.getEmail(), "Смена пароля в WebQuizzes",
                    """
                            Для смены пароля в WebQuizzes
                            введите в приложении код: """ + code +
                            """
                                    . 
                                     Если вы не меняли пароль на данном ресурсе, то проигнорируйте сообщение
                                    """);

        } catch (Exception e) {
            System.out.println("Не отправлено");
        }
    }

    public void sendCodeForChangePassword(String username) {

        User user = loadUserByUsername(username);

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
    public void updatePassword(User user, String changePasswordCode) {

        User savedUser = getUserViaChangePasswordCode(changePasswordCode);

        user.setUsername(savedUser.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setChangePasswordCode(UUID.randomUUID().toString());

        userRepository.changePassword(user.getPassword(), user.getUsername(), user.getChangePasswordCode());
        savedUser.setChangePasswordCode(UUID.randomUUID().toString());
    }

    public void activateAccount(String activationCode) {
        User user = userRepository.findByActivationCode(activationCode);
        if (user != null) {
            user.setStatus(true);
            userRepository.activateAccount(user.getUsername());
        }

    }

    public User getUserViaChangePasswordCode(String changePasswordCode) {

        if (userRepository.findByChangePasswordCode(changePasswordCode).isPresent()) {
            return userRepository.findByChangePasswordCode(changePasswordCode).get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

    public User castToUserFromOauth(OAuth2AuthenticationToken authentication) {

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
                user.setPhotos(Collections.singletonList((String) authentication.getPrincipal().getAttributes().get("picture")));

                user.setUsername(((String) authentication.getPrincipal().getAttributes()
                        .get("email")).replace("@gmail.com", ""));

            }


        } else if (authentication.getAuthorizedClientRegistrationId().equals("github")) {
            user.setUsername(authentication.getPrincipal().getAttributes().get("login").toString());
            user.setFirstName(authentication.getPrincipal().getAttributes().get("name").toString());
            user.setLastName(authentication.getPrincipal().getAttributes().get("name").toString());
            user.setPhotos(Collections.singletonList(authentication.getPrincipal().getAttributes().get("avatar_url").toString()));

            if (authentication.getPrincipal().getAttributes().get("email") != null) {
                user.setEmail(authentication.getPrincipal().getAttributes().get("email").toString());
            } else {
                user.setEmail("default@default.com");
            }
        }

        //doInitialize(user);

        return user;
    }

    public void doBasicUserInitializationBeforeSave(User user) {

        Queue queue = new Queue(user.getUsername(), true, false, false);

        Binding binding = new Binding(user.getUsername(), Binding.DestinationType.QUEUE,
                "message-exchange", user.getUsername(), null);

        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareBinding(binding);

        //if (user.getPhotos() == null) {

        user.setPhotos(Collections.singletonList("https://" + hostname + "/img/default.jpg"));
        //}

        user.setEnabled(true);
        user.setBalance(0);
        user.grantAuthority(Role.ROLE_USER);

    }

    public void updateBalance(Integer coins, String username) {
        User user = userRepository.findById(username).get();
        user.setBalance(user.getBalance() + coins);
    }

    public void processPayment(String notification_type, String operation_id, Number amount, Number withdraw_amount, String currency, String datetime, String sender, Boolean codepro, String label, String sha1_hash, Boolean test_notification, Boolean unaccepted, String lastname, String firstname, String fathersname, String email, String phone, String city, String street, String building, String suite, String flat, String zip) throws NoSuchAlgorithmException {
        String mySha = notification_type + "&" + operation_id + "&" + amount + "&" + currency + "&" +
                datetime + "&" + sender + "&" + codepro + "&" + notification_secret + "&" + label;

        //System.out.println(mySha);

        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(mySha.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }


        if (sb.toString().equals(sha1_hash)) {
            System.out.println("Пришло уведомление");
            System.out.println("notification_type = " + notification_type + ", operation_id = " + operation_id +
                    ", amount = " + amount + ", withdraw_amount = " + withdraw_amount + ", currency = " + currency +
                    ", datetime = " + datetime + ", sender = " + sender + ", codepro = " + codepro + ", label = " + label +
                    ", sha1_hash = " + sha1_hash + ", test_notification = " + test_notification + ", unaccepted = " + unaccepted + ", lastname = " + lastname + ", firstname = " + firstname + ", fathersname = " + fathersname + ", email = " + email + ", phone = " + phone + ", city = " + city + ", street = " + street + ", building = " + building + ", suite = " + suite + ", flat = " + flat + ", zip = " + zip);
            System.out.println();

            Order order = new Order();

            order.setAmount(String.valueOf(amount));
            order.setCoins((int) (Double.parseDouble(String.valueOf(withdraw_amount)) * 100.0));
            order.setOperation_id(operation_id);
            order.setOrder_id(Integer.valueOf(label));

            order = paymentServices.saveFinalOrder(order);
            updateBalance(order.getCoins(), order.getUser().getUsername());

        } else {
            System.out.println("Неправильный хэш");
        }
    }

    public ArrayList<User> getUserList() {
        return (ArrayList<User>) userRepository.findAll();
    }

    public List<UserCommonView> findMyFriends(Principal principal) {
        return userRepository.findUsersByFriendsUsername(principal.getName());
    }

    public UserCommonView getUserView(String username) {
        return userRepository.findByUsername(username);
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

    public AuthResponse signinViaGoogleToken(GoogleToken token) throws GeneralSecurityException, IOException {
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
            List<String> pictureUrl = Collections.singletonList((String) payload.get("picture"));
            String locale = (String) payload.get("locale");
            String familyName = (String) payload.get("family_name");
            String givenName = (String) payload.get("given_name");


            String username = email.replace("@gmail.com", "");

            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setFirstName(givenName);
            user.setLastName(familyName);
            user.setPhotos(pictureUrl);

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
    }

    public UserView getAuthUser(String username) {
        if (userRepository.findAllByUsername(username) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            return userRepository.findAllByUsername(username);
        }
    }

    @Transactional
    public void uploadPhoto(MultipartFile file, String username) {
        if (!file.isEmpty()) {
            try {
                String uuid = UUID.randomUUID().toString();
                uuid = uuid.substring(0, 8);
                byte[] bytes = file.getBytes();

                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File("img/" +
                                uuid + ".jpg")));
                stream.write(bytes);
                stream.close();


                String photoUrl = "https://" + hostname + "/img/" + uuid + ".jpg";


                User user = loadUserByUsername(username);

                Photo photo = new Photo();
                photo.setUrl(photoUrl);
                photo.setPosition(user.getPhotos().size());
                user.addPhoto(photo);


            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        }
    }

    public void processCheckIn(User user, String type) {
        if (type.equals("BASIC")) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setStatus(false);
            user.setActivationCode(UUID.randomUUID().toString());
            String mes = user.getFirstName() + " " + user.getLastName() + ", Добро пожаловать в WebQuizzes! "
                    + "Для активации аккаунта перейдите по ссылке: https://" + hostname + "/activate/" + user.getActivationCode()
                    + " Если вы не регистрировались на данном ресурсе, то проигнорируйте это сообщение";

            try {
                mailSender.send(user.getEmail(), "Активация аккаунта в WebQuizzes", mes);
            } catch (Exception e) {
                System.out.println("Отключено");
            }

        } else if (type.equals("OAUTH2")) {
            user.setStatus(true);
        }
        doBasicUserInitializationBeforeSave(user);
        saveUser(user, type);
    }

    public Boolean checkForExistUser(String username) {
        return userRepository.existsById(username);
    }

    @Transactional
    public ProfileView getUserProfileById(String username) {
        ProfileView profileView = userRepository.findUserByUsernameOrderByUsernameAscPhotosAsc(username);
        Collections.sort(profileView.getPhotos());
        return profileView;
    }

    @Transactional
    public void swapPhoto(Photo photo, String name) {

        Photo savedPhoto = photoRepository.findById(photo.getId()).get();
        List<Photo> photos = photoRepository.findByUser_Username(name);

        photos.remove(((int) savedPhoto.getPosition()));
        photos.add(photo.getPosition(), savedPhoto);

        for (int i = 0; i < photos.size(); i++) {
            photos.get(i).setPosition(i);
        }


    }

    public void getUserViaChangePasswordCodePhoneApi(String username, String code) {

        Optional<User> user = userRepository.findByChangePasswordCode(code);

        if (user.isPresent() && user.get().getUsername().equals(username)) {
            return;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
