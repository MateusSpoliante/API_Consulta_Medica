package com.example.demo.service;

import com.example.demo.dto.MedicoRequestDTO;
import com.example.demo.dto.MedicoResponseDTO;
import com.example.demo.Entities.Especialidade;
import com.example.demo.Entities.Medico;
import com.example.demo.mapper.MedicoMapper;
import com.example.demo.repository.IEspecialidadeRepository;
import com.example.demo.repository.IMedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicoService {
    @Autowired
    private IMedicoRepository medicoRepository;

    @Autowired
    private IEspecialidadeRepository especialidadeRepository;

    @Autowired
    private MedicoMapper MedicoMapper;

    public List<MedicoResponseDTO> listarTodos(){
        return MedicoMapper.toDTOList(medicoRepository.findAll());
    }

    public Optional<MedicoResponseDTO> buscarPorId(Long id){
        return medicoRepository.findById(id)
                .map(MedicoMapper::toDTO);
    }
    public MedicoResponseDTO salvar(MedicoRequestDTO dto){
        Especialidade especialidade = especialidadeRepository.findById(dto.getEspecialidadeId())
                .orElseThrow(() -> new RuntimeException("Especialidade não encontrada"));

                Medico medico = MedicoMapper.toEntity(dto);
                medico.setEspecialidade(especialidade);

                medico = medicoRepository.save(medico);
                return MedicoMapper.toDTO(medico);
    }
    
    public MedicoResponseDTO atualizar(Long id, MedicoRequestDTO dto) {
    Medico medicoExistente = medicoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Médico não encontrado com ID: " + id));

    Especialidade especialidade = especialidadeRepository.findById(dto.getEspecialidadeId())
            .orElseThrow(() -> new RuntimeException("Especialidade não encontrada com ID: " + dto.getEspecialidadeId()));

    medicoExistente.setNome(dto.getNome());
    medicoExistente.setCrm(dto.getCrm());
    medicoExistente.setEspecialidade(especialidade);

    Medico atualizado = medicoRepository.save(medicoExistente);
    return MedicoMapper.toDTO(atualizado);
}


    public void deletar(Long id){
        medicoRepository.deleteById(id);
    }
}
