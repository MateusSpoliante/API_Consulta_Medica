package com.example.demo.service;

import com.example.demo.dto.EspecialidadeDTO;
import com.example.demo.Entities.Especialidade;
import com.example.demo.mapper.EspecialidadeMapper;
import com.example.demo.repository.IEspecialidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EspecialidadeService {
    @Autowired
    private IEspecialidadeRepository especialidadeRepository;

    @Autowired
    private EspecialidadeMapper especialidadeMapper;

    public List<EspecialidadeDTO> listarTodos(){
        return especialidadeMapper.toDTOList(especialidadeRepository.findAll());
    }

    public Optional<EspecialidadeDTO> buscarPorId(Long id){
        return especialidadeRepository.findById(id)
        .map(especialidadeMapper::toDTO);
    }

    public EspecialidadeDTO salvar(EspecialidadeDTO dto){
        Especialidade especialidade = especialidadeMapper.toEntity(dto);
        especialidade = especialidadeRepository.save(especialidade);
        return especialidadeMapper.toDTO(especialidade);
    }

    public EspecialidadeDTO atualizar(Long id, EspecialidadeDTO dto) {
    Especialidade especialidadeExistente = especialidadeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Especialidade não encontrada com ID: " + id));

    especialidadeExistente.setNome(dto.getNome());

    Especialidade atualizada = especialidadeRepository.save(especialidadeExistente);
    return especialidadeMapper.toDTO(atualizada);
}


    public void deletar(Long id){
        especialidadeRepository.deleteById(id);
    }
}
