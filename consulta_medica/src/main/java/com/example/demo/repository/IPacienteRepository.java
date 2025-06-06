// Mateus Spoliante

package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entities.Paciente;

@Repository
public interface IPacienteRepository extends JpaRepository<Paciente, Long> {

    boolean existsByCpf(String cpf);
    // busca um paciente pelo cpf
    Optional<Paciente> findByCpf(String cpf);
}
