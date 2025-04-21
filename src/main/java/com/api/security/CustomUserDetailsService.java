package com.api.security;

import com.api.exception.EmailNoEncontradoException;
import com.api.model.Usuario;
import com.api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNoEncontradoException(email));

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().getNombre()));

        return new CustomUserDetails(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getPassword(),
                authorities
        );
    }

}
