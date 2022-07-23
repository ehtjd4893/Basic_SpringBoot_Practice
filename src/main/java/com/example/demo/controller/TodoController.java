package com.example.demo.controller;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TodoDTO;
import com.example.demo.entity.TodoEntity;
import com.example.demo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("todo")
public class TodoController {

  @Autowired
  TodoService todoService;

  @GetMapping("test")
  public ResponseEntity<?> testTodo() {
    String str = todoService.testService();
    List<String> list = new ArrayList<>();
    list.add(str);
    ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
    return ResponseEntity.ok().body(response);
  }

  @PostMapping
  public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto) {
    try {
      String temporaryUserId = "temporary-user";  // temporary user id

      // 1. TodoEntity로 변환
      TodoEntity entity = TodoDTO.toEntity(dto);

      // 2. id를 null로 초기화한다. 생성 당시에는 ID가 없어야 하기 때문
      entity.setId(null);

      // 3. 임시 사용자 아이디 설정.
      entity.setUserId(temporaryUserId);

      // 4. 서비스를 통해 TodoEntity 생성
      List<TodoEntity> entities = todoService.create(entity);

      // 5. 쟈바 스트림을 이용해 리턴받은 List<TodoEntity>를 List<TodoDTO>로 변환한다.
      List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

      // 6. 변환된 List<TodoDTO>를 이용해 ResponseDTO를 초기화한다.
      ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

      // 7. ResponseDTO를 리턴한다.
      return ResponseEntity.ok().body(response);
    } catch(Exception e) {
      // 8. 혹시 예외가 있는 경우 dto 대신 error에 message를 넣어 반환한다.
      String error = e.getMessage();

      ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
      return ResponseEntity.badRequest().body(response);
    }
  }

  @GetMapping
  public ResponseEntity<?> retrieveTodoList() {
    String temporaryUserId = "temporary-user";  // temporary user id

    // 1. 서비스의 retrieve()를 이용해 TodoList를 가져옴.
    List<TodoEntity> entities = todoService.retrieve(temporaryUserId);

    // 2. 자바 스트림을 이용해 리턴된 엔터티 리스트를 TodoList로 변환
    List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

    // 3. 반환된 TodoDTO 리스트를 이용해 responseDTO를 초기화
    ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

    return ResponseEntity.ok().body(response);
  }

  @PutMapping
  public ResponseEntity<?> updateTodo(@RequestBody TodoDTO dto) {
    String temporaryUserId = "temporary-user";

    // 1. dto를 Entity로 변환
    TodoEntity entity = TodoDTO.toEntity(dto);

    // 2. id를 TemporaryUserID로 초기화.
    entity.setUserId(temporaryUserId);

    // 3. 서비스를 이용해 Entity를 업데이트한다.
    List<TodoEntity> entities = todoService.update(entity);

    // 4. 자바 스트림을 이용해 리턴된 entity list를 todoDTO list로 변환한다.
    List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

    // 5. 변환된 TodoDTO List를 이용해 responseDTO 생성
    ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

    // 6. responseDTO를 리턴
    return ResponseEntity.ok().body(response);
  }

  @DeleteMapping
  public ResponseEntity<?> deleteTodo(@RequestBody TodoDTO dto) {
    String temporaryUserId = "temporary-user";

    TodoEntity entity = TodoDTO.toEntity(dto);
    entity.setUserId(temporaryUserId);
    entity.setId(dto.getId());

    List<TodoEntity> entities = todoService.delete(entity);

    List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

    ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

    return ResponseEntity.ok().body(response);
  }
}
