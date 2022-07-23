package com.example.demo.controller;

import com.example.demo.dto.RequestBodyDTO;
import com.example.demo.dto.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("test") // 리소스
public class TestController {

  @GetMapping("/requestParam")
  public String testController1(@RequestParam int id) {
    return "get World!" + id;
  }

  @PutMapping("/put")
  public String testController2() {
    return "put World!";
  }

  @GetMapping("/requestBody")
  public ResponseDTO<String> testRequestBody(@RequestBody RequestBodyDTO dto) {
    List<String> list = new ArrayList<>();
    list.add("Hello dto! " + dto.getId() + ", \nMessage: " + dto.getMessage());
    ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
    return response;
  }

  @GetMapping("responseEntity")
  public ResponseEntity<?> testResponseEntity() {
    List<String> list = new ArrayList<>();
    list.add("u've got 400");
    ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
//    return ResponseEntity.badRequest().body(response);
    return ResponseEntity.ok().body(response);
  }
}
