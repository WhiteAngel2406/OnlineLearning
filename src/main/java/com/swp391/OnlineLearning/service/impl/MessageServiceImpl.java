package com.swp391.OnlineLearning.service.impl;

import com.swp391.OnlineLearning.model.Message;
import com.swp391.OnlineLearning.repository.MessageRepository;
import com.swp391.OnlineLearning.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }


    @Override
    public void save(Message message) {
        messageRepository.save(message);
    }

    @Override
    public Message findById(Long messageId) {
        return messageRepository.findById(messageId).orElse(null);
    }

    @Override
    public void delete(Message message) {
        messageRepository.delete(message);
    }

    @Override
    public List<Message> findAllByChatId(long chatId) {
        return List.of();
    }

    @Override
    public List<Message> findAll() {
        return messageRepository.findAll();
    }
}
