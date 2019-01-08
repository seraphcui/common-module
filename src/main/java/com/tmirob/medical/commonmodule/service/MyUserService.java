/*
 * Copyright (c) 2018. All rights reserved by Taimi Robot.
 * Created by yaocui on 18-1-26 下午7:01.
 */

package com.tmirob.medical.commonmodule.service;

import com.tmirob.medical.commonmodule.model.dal.PersonRepository;
import com.tmirob.medical.commonmodule.model.entity.Person;
import com.tmirob.medical.commonmodule.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserService implements UserDetailsService {

    @Autowired
    PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String s) {
        Person user = personRepository.findOneByUsername(s);
        if(user == null){
            throw new UsernameNotFoundException("User " + s + " not found!");
        }
        return MyUserDetails.create(user);
    }
}
