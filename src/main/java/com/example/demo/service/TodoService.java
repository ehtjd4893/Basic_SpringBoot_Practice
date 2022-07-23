package com.example.demo.service;

import com.example.demo.entity.TodoEntity;
import com.example.demo.persistance.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TodoService {

  @Autowired
  TodoRepository todoRepository;

  public String testService() {
    TodoEntity entity = TodoEntity.builder().title("My First todo item").userId("dosung_park").build();
    todoRepository.save(entity);
    String x = todoRepository.findByUserId("dosung_park").toString();
    TodoEntity savedEntity = todoRepository.findById(entity.getId()).get();
    return savedEntity.toString() + "\n" + x;
  }
}
