package com.cardmatcher.backend.services;

import com.cardmatcher.backend.models.dtos.users.UserDTO;
import com.cardmatcher.backend.models.dtos.users.UserLoginDTO;
import com.cardmatcher.backend.models.dtos.users.UserRegisterDTO;
import com.cardmatcher.backend.models.CardList;
import com.cardmatcher.backend.models.User;
import com.cardmatcher.backend.repositories.UserRepository;
import com.cardmatcher.backend.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private UserDetailsService userDetailsService;

    @InjectMocks
    private AuthService authService;

    private UserRegisterDTO userRegisterDTO;
    private UserLoginDTO userLoginDTO;
    private User user;

    @BeforeEach
    void setUp() {

        userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUsername("testuser");
        userRegisterDTO.setEmail("test@example.com");
        userRegisterDTO.setPassword("mypassword");
        userRegisterDTO.setPlayerId("player123");

        userLoginDTO = new UserLoginDTO();
        userLoginDTO.setUsername("testuser");
        userLoginDTO.setPassword("mypassword");

        user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");
        user.setPlayerId("player123");
        user.setActive(true);
    }

    @Test
    void testRegisterUserSuccess() throws Exception {

        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(passwordEncoder.encode("mypassword")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        CardList wantedList = new CardList();
        wantedList.setListType(CardList.ListType.WANTED);
        user.addCardList(wantedList);

        CardList offeredList = new CardList();
        offeredList.setListType(CardList.ListType.OFFERED);
        user.addCardList(offeredList);

        CardList collectionList = new CardList();
        collectionList.setListType(CardList.ListType.COLLECTION);
        user.addCardList(collectionList);

        UserDTO result = authService.registerUser(userRegisterDTO);

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        assertEquals("test@example.com", result.getEmail());
        assertTrue(result.isActive());
        assertEquals("player123", result.getPlayerId());
    }

    @Test
    void testRegisterUserUsernameAlreadyExists() throws Exception {

        when(userRepository.existsByUsername("testuser")).thenReturn(true);

        Exception exception = assertThrows(Exception.class, () -> authService.registerUser(userRegisterDTO));
        assertEquals("El nombre de usuario ya está en uso.", exception.getMessage());
    }

    @Test
    void testRegisterUserEmailAlreadyExists() throws Exception {

        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);

        Exception exception = assertThrows(Exception.class, () -> authService.registerUser(userRegisterDTO));
        assertEquals("El correo electrónico ya está en uso.", exception.getMessage());
    }

    @Test
    void testLoginUserSuccess() throws Exception {

        when(authenticationManager.authenticate(any())).thenReturn(null); // Mock para la autenticación
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("testuser");
        when(userDetailsService.loadUserByUsername("testuser")).thenReturn(userDetails); // Simulamos el UserDetails

        String token = "jwt-token";
        when(jwtUtil.generateToken("testuser")).thenReturn(token);

        String result = authService.loginUser(userLoginDTO);

        assertEquals(token, result);
    }

    @Test
    void testLoginUserInvalidCredentials() throws Exception {

        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("Invalid credentials"));

        Exception exception = assertThrows(Exception.class, () -> authService.loginUser(userLoginDTO));
        assertEquals("Credenciales inválidas", exception.getMessage());
    }
}
