package org.yigit.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.yigit.entity.User;
import org.yigit.entity.common.UserPrincipal;
import org.yigit.repository.UserRepository;

import org.yigit.service.SecurityService;

@Service
public class SecurityServiceImpl implements SecurityService {
    private final UserRepository userRepository;

    public SecurityServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //we need to get our own user from database. how ?
        User user = userRepository.findByUsername(username);
        //return some exception if user doesn't exist
        if(user==null){
            throw new UsernameNotFoundException("This user does not exist");
        }
        //return user information as a UserDetails

        return new UserPrincipal(user);
    }
}
