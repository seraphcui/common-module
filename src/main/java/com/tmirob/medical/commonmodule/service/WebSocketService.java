package com.tmirob.medical.commonmodule.service;

import com.tmirob.medical.commonmodule.model.dal.PersonRepository;
import com.tmirob.medical.commonmodule.model.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author seraph(yao.cui@tmirob.com)
 * @date 2018/11/1
 */
@Service
public class WebSocketService {

    @Autowired
    protected SimpMessagingTemplate webSocket;

    @Autowired
    PersonRepository personRepository;

    @SendToUser
    public void reportErrorByPositionName(String warehouseName, String errorMessage) {
        if (errorMessage != null) {
            List<Person> warehouseAdmin = personRepository.findByPositionName(warehouseName);
            for (Person person : warehouseAdmin) {
                reportErrorToUser(person.getUsername(), errorMessage);
            }
        }
    }

    @SendToUser
    public void reportErrorToUser(String personName, String errorMessage) {
        webSocket.convertAndSendToUser(personName, "/error", errorMessage);
    }

}
