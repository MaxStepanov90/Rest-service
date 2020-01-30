package com.example.restservice.controller;

import com.example.restservice.entity.Message;
import com.example.restservice.entity.Views;
import com.example.restservice.repository.MessageRepository;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("message")
public class MessageController {

    private final MessageRepository messageRepository;
    @Autowired
    public MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping
    //    видим только поля помеченные этим интерфейсом
    @JsonView(Views.IdName.class)
    public List<Message> list(){
        return messageRepository.findAll();
    }
    @GetMapping("{id}")
    public Message getOne(@PathVariable("id") Message message){
        return message;
    }

    @PostMapping
    public Message create(@RequestBody Message message){
        message.setCreationDate(LocalDateTime.now());
        return messageRepository.save(message);
    }
    @PutMapping("{id}")
    public Message update(@PathVariable ("id") Message messageFromDb ,
                                     @RequestBody Message message){

//        копируем все поля из message в messageFromDb кроме поля id
        BeanUtils.copyProperties(message,messageFromDb,"id");

        return messageRepository.save(messageFromDb);
    }
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Message message){
        messageRepository.delete(message);
    }
}
