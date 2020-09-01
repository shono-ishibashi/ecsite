package com.ecsite.demo.service;

import com.ecsite.demo.domain.OrderIdUpdateParam;
import com.ecsite.demo.domain.User;
import com.ecsite.demo.domain.UserDetailsImpl;
import com.ecsite.demo.repository.OrderMybatisRepository;
import com.ecsite.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

import static java.util.Objects.isNull;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpSession session;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OrderMybatisRepository orderMybatisRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(s).getUser();


        if (!isNull(user.getId())) {
            if (isNull(session.getAttribute("userId"))) {
                session.setAttribute("userId", user.getId());
                return new UserDetailsImpl(user);
            } else {
                Integer temporaryId = (Integer) session.getAttribute("userId");
                session.setAttribute("userId", user.getId());
                OrderIdUpdateParam param = new OrderIdUpdateParam();
                param.setTemporaryId(temporaryId);
                param.setUserId(user.getId());
                orderMybatisRepository.updateOrderId(param);
                return new UserDetailsImpl(user);
            }
        } else {
            throw new UsernameNotFoundException("そのemailは登録されていません");
        }
    }

    public void userInsert(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.insert(user);
    }
}
