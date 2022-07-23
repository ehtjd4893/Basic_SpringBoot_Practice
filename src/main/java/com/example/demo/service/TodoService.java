package com.example.demo.service;

import com.example.demo.entity.TodoEntity;
import com.example.demo.persistance.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

  public List<TodoEntity> create(final TodoEntity entity) {
    this.validate(entity);

    todoRepository.save(entity);

    log.info("Entity Id : {} is saved", entity.getId());

    return todoRepository.findByUserId(entity.getUserId());
  }

  public void validate(final TodoEntity entity) {
    if (entity == null) {
      log.warn("Entity cannot be null");
      throw new RuntimeException("Entity can't be null");
    }

    if (entity.getUserId() == null) {
      log.warn("Unknown user");
      throw new RuntimeException("Unknown user.");
    }
  }

  public List<TodoEntity> retrieve(final String userId) {
    return todoRepository.findByUserId(userId);
  }

  public List<TodoEntity> update(final TodoEntity entity) {
    // 1. 저장할 엔티티가 유효한지 확인
    validate(entity);

    // 2. 넘겨받은 엔티티 id를 이용해 TodoEntity를 가져온다. (존재하지 않는 엔티티는 업데이트 불가)
    final Optional<TodoEntity> original = todoRepository.findById(entity.getId());

    original.ifPresent(todo -> {
      // 3. 반환된 TodoEntity가 존재하면 값을 새 entity 값으로 덮어 씌운다.
      todo.setTitle(entity.getTitle());
      todo.setDone(entity.isDone());

      // 4. 데이터베이스에 새 값을 저장한다.
      todoRepository.save(todo);
    });

    return retrieve(entity.getUserId());
  }

  public List<TodoEntity> delete(final TodoEntity entity) {
    validate(entity);

    final Optional<TodoEntity> original = todoRepository.findById(entity.getId());
    if (original.isPresent()) { todoRepository.delete(entity); }

    return retrieve(entity.getUserId());
  }
}
