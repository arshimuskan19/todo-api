package com.arshi.todo_api.service;
import com.arshi.todo_api.config.JwtUtil;
import com.arshi.todo_api.exception.IncorrectPasswordException;
import com.arshi.todo_api.exception.UserNameAlreadyExistsException;
import com.arshi.todo_api.exception.UserNameNotFoundException;
import com.arshi.todo_api.model.User;
import com.arshi.todo_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public User findUserByUsername(String userName) {
        return userRepository.findByUserName(userName).orElseThrow(()->new UserNameNotFoundException("Username entered doesn't exist"));
    }

    public User registerUser(User user) {
        if(userRepository.findByUserName(user.getUserName()).isPresent()) {
            throw new UserNameAlreadyExistsException("User with "+user.getUserName()+ " username already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public String loginUser(String userName, String password) {
        User user = findUserByUsername(userName);
        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new IncorrectPasswordException("Password entered is incorrect");
        }
        return jwtUtil.generateToken(user.getUserName());
    }
}
