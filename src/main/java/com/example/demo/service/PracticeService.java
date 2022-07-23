package com.example.demo.service;

import com.example.demo.dto.PracticeDTO;
import com.example.demo.entity.PracticeEntity;
import com.example.demo.persistance.PracticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PracticeService {

  @Autowired
  PracticeRepository repo;

  public String createPractice(PracticeDTO dto) {
    PracticeEntity entity = new PracticeEntity().builder().name(dto.getName()).sex(dto.getSex()).build();
    try {
      repo.save(entity);
      return "Success Creating Practice";
    } catch (Exception e) {
      return "Can't Create Practice";
    }
  }

  public List<PracticeDTO> getTodoList() {
    List<PracticeEntity> list = repo.findAll();
    List<PracticeDTO> resultList = new ArrayList<>();
    list.forEach(entity -> {
      resultList.add(new PracticeDTO().builder().id(entity.getId()).name(entity.getName()).sex(entity.getSex()).build());
    });
    return resultList;
  }

  public PracticeEntity updatePractice(PracticeDTO dto) {
    try {
      Optional<PracticeEntity> entity = repo.findById(dto.getId());
      if (entity.isEmpty()) {
        return null;
      }
      PracticeEntity get = entity.get();
      get.setName(dto.getName());
      get.setSex(dto.getSex());
      repo.save(get);
      return get;
    } catch (Exception e) {
      return null;
    }
  }

  public String deletePractice(Integer id) {
    try {
      Optional<PracticeEntity> entity = repo.findById(id);
      repo.delete(entity.get());
      return "Success deleting id = " + id;
    } catch (Exception e) {
      return "Failed deleteing id = " + id;
    }
  }
}
