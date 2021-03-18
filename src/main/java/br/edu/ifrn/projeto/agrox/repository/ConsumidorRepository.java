package br.edu.ifrn.projeto.agrox.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.ifrn.projeto.crud.dominio.Consumidor;

public interface ConsumidorRepository extends JpaRepository<Consumidor, Integer> {
	
	// metodo para busca dos dados do consumidor cadastrado através do email e senha
	
	@Query("select u from Consumidor u where u.email like %:email% " + "and u.nome like %:nome% ")
	List<Consumidor> findByEmailAndNome(@Param("email") String email, @Param("nome") String nome);

	//método para busca do login através do username do consumidor cadastrado
	
	@Query("select u from Consumidor u where u.email like %:email% ")
	Optional<Consumidor> findByEmail(@Param("email") String email);

}