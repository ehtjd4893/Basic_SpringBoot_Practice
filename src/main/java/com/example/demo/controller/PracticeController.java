package com.example.demo.controller;

import com.example.demo.dto.PracticeDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.entity.PracticeEntity;
import com.example.demo.service.PracticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("practice")
public class PracticeController {

  @Autowired
  PracticeService service;

  @PostMapping("/createPractice")
  public String createPractice(@RequestBody PracticeDTO dto) {
    return service.createPractice(dto);
  }

  @GetMapping("/searchList")
  public ResponseDTO<PracticeDTO> getTodoList() {
    List<PracticeDTO> list = service.getTodoList();
    String err = "Here is All of List";
    if (list == null | list.size() == 0) {
      err = "There is No List";
    }
    return new ResponseDTO().<PracticeDTO>builder().data(list).error(err).build();
  }

  @PutMapping("/updatePractice")
  public PracticeEntity updatePractice(@RequestBody PracticeDTO dto) {
    return service.updatePractice(dto);
  }

  @DeleteMapping("/deletePractice")
  public String deletePractice(@RequestParam int id) {
    return service.deletePractice(id);
  }
}
