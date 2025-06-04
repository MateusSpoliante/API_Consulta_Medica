package com.example.demo.service;

import com.example.demo.Entities.Usuario;
import com.example.demo.dto.UsuarioDTO;
import com.example.demo.mapper.UsuarioMapper;
import com.example.demo.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    public List<UsuarioDTO> listarTodos() {
        return usuarioMapper.toDTOList(usuarioRepository.findAll());
    }

    public Optional<UsuarioDTO> buscarPorId(Long id) {
        return usuarioRepository.findById(id).map(usuarioMapper::toDTO);
    }

    public UsuarioDTO salvar(UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        return usuarioMapper.toDTO(usuarioRepository.save(usuario));
    }

    public void deletar(Long id) {
        usuarioRepository.deleteById(id);
    }

    public UsuarioDTO atualizar(UsuarioDTO usuarioDTO) {

        Usuario usuarioExistente = usuarioRepository.findById(usuarioDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        // Verifica se o CPF já está em uso por outro usuário (evita conflito com ele
        // mesmo)
        if (usuarioRepository.existsById(usuarioDTO.getId()) &&
                !usuarioExistente.getId().equals(usuarioDTO.getId())) {
            throw new IllegalArgumentException("CPF já cadastrado");
        }

        usuarioExistente.setNome(usuarioDTO.getNome());
        usuarioExistente.setCpf(usuarioDTO.getCpf());
        usuarioExistente.setEmail(usuarioDTO.getEmail());
        usuarioExistente.setTelefone(usuarioDTO.getTelefone());
        usuarioExistente.setSenha(usuarioDTO.getSenha());
        usuarioExistente.setDataNascimento(usuarioDTO.getDataNascimento());

        Usuario atualizado = usuarioRepository.save(usuarioExistente);
        return usuarioMapper.toDTO(atualizado);
    }
}
