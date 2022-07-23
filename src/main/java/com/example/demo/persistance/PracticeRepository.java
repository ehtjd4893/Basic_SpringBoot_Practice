package com.example.demo.persistance;

import com.example.demo.entity.PracticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PracticeRepository extends JpaRepository<PracticeEntity, Integer> {
  public List<PracticeEntity> findAll();
}
