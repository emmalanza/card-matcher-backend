package com.cardmatcher.backend.services;

import com.cardmatcher.backend.models.dtos.cardLists.CardListsDTO;
import com.cardmatcher.backend.models.dtos.users.UserDTO;
import com.cardmatcher.backend.models.dtos.users.UserLoginDTO;
import com.cardmatcher.backend.models.dtos.users.UserRegisterDTO;
import com.cardmatcher.backend.models.CardList;
import com.cardmatcher.backend.models.User;
import com.cardmatcher.backend.repositories.UserRepository;
import com.cardmatcher.backend.security.JwtUtil;

import java.util.stream.Collectors;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    @Autowired
    public AuthService(UserRepository userRepository,
            AuthenticationManager authenticationManager,
            JwtUtil jwtUtil,
            BCryptPasswordEncoder passwordEncoder,
            UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    public UserDTO registerUser(UserRegisterDTO userRegisterDTO) throws Exception {
        if (userRepository.existsByUsername(userRegisterDTO.getUsername())) {
            throw new Exception("El nombre de usuario ya está en uso.");
        }

        if (userRepository.existsByEmail(userRegisterDTO.getEmail())) {
            throw new Exception("El correo electrónico ya está en uso.");
        }

        User user = new User();
        user.setUsername(userRegisterDTO.getUsername());
        user.setEmail(userRegisterDTO.getEmail());
        System.out.println(userRegisterDTO.getPassword());
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        user.setActive(true);
        user.setPlayerId(userRegisterDTO.getPlayerId());

        CardList wantedList = new CardList();
        wantedList.setListType(CardList.ListType.WANTED);
        wantedList.setUser(user);
        user.addCardList(wantedList);

        CardList offeredList = new CardList();
        offeredList.setListType(CardList.ListType.OFFERED);
        offeredList.setUser(user);
        user.addCardList(offeredList);

        CardList collectionList = new CardList();
        collectionList.setListType(CardList.ListType.COLLECTION);
        collectionList.setUser(user);
        user.addCardList(collectionList);

        User savedUser = userRepository.save(user);

        List<CardListsDTO> cardListsDTOs = savedUser.getCardLists().stream()
                .map(CardListsDTO::fromCardList)
                .collect(Collectors.toList());

        UserDTO userDTO = new UserDTO(savedUser.getId(), savedUser.getUsername(),
                savedUser.getEmail(), savedUser.isActive(), savedUser.getPlayerId(), cardListsDTOs);

        return userDTO;
    }

    public String loginUser(UserLoginDTO userLoginDTO) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLoginDTO.getUsername(), userLoginDTO.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails userDetails = userDetailsService.loadUserByUsername(userLoginDTO.getUsername());

            String token = jwtUtil.generateToken(userDetails.getUsername());

            return token;

        } catch (BadCredentialsException e) {
            throw new Exception("Credenciales inválidas", e);
        }
    }
}
