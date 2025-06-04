package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entities.Medico;

@Repository
public interface IMedicoRepository extends JpaRepository<Medico, Long> {
}
