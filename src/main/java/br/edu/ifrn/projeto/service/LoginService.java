package br.edu.ifrn.projeto.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.edu.ifrn.projeto.agrox.repository.ConsumidorRepository;
import br.edu.ifrn.projeto.agrox.repository.ProdutorRepository;
import br.edu.ifrn.projeto.crud.dominio.Consumidor;
import br.edu.ifrn.projeto.crud.dominio.Produtor;

//classe de serviço para a relização do login pelo usuário

@Service
public class LoginService implements UserDetailsService {

	@Autowired
	private ConsumidorRepository consumidorRepository;

	@Autowired
	private ProdutorRepository produtorRepository;

	// método que realiza a busca do usuário para a realização do login

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Consumidor consumidor = consumidorRepository.findByEmail(username).orElse(null);
		Produtor produtor = produtorRepository.findByEmail(username).orElse(null);

		if (consumidor == null && produtor == null) {
			throw new UsernameNotFoundException("Usuário não encontrado");
		}

		if (consumidor != null) {

			return new User(consumidor.getEmail(), consumidor.getSenha(),
					AuthorityUtils.createAuthorityList("CONSUMIDOR"));

		} else {

			return new User(produtor.getEmail(), produtor.getSenha(), AuthorityUtils.createAuthorityList("PRODUTOR"));

		}
	}
}
