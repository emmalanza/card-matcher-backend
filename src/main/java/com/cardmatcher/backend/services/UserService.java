package com.cardmatcher.backend.services;

import com.cardmatcher.backend.models.CardList;
import com.cardmatcher.backend.models.User;
import com.cardmatcher.backend.repositories.UserRepository;
import com.cardmatcher.backend.repositories.CardListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // @Autowired
    // private RoleRepository roleRepository;

    @Autowired
    private CardListRepository cardListRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User registerUser(User user) throws Exception {

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new Exception("El nombre de usuario ya está en uso.");
        }
    
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new Exception("El correo electrónico ya está en uso.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
    
        user.setActive(true);

        // Set<Role> roles = new HashSet<>();
        // Role userRole = roleRepository.findByName(Role.RoleName.USER);
        // if (userRole != null) {
        //     roles.add(userRole);
        // }
        // user.setRoles(roles);

        User savedUser = userRepository.save(user);
        
        CardList wantedList = new CardList();
        CardList offeredList = new CardList();
        CardList collectionList = new CardList();
        
        wantedList.setListType(CardList.ListType.WANTED);
        offeredList.setListType(CardList.ListType.OFFERED);
        collectionList.setListType(CardList.ListType.COLLECTION);

        wantedList.setUser(savedUser);
        offeredList.setUser(savedUser);
        collectionList.setUser(savedUser);
    
        cardListRepository.save(wantedList);
        cardListRepository.save(offeredList);
        cardListRepository.save(collectionList);
    
        return savedUser;
    }
    
}
