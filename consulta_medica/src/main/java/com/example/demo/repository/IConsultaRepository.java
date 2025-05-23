//Douglas José

package com.example.demo.repository;

import java.util.List;

import org.apache.logging.log4j.status.StatusData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entities.Consulta;

@Repository
public interface IConsultaRepository extends JpaRepository<Consulta, Long> {

    List<Consulta> findByPacienteId(Long pacienteId);

    List<Consulta> findByStatus(StatusData status);
}
