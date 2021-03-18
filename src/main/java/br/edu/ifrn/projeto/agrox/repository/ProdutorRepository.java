package br.edu.ifrn.projeto.agrox.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.ifrn.projeto.crud.dominio.Produtor;

public interface ProdutorRepository extends JpaRepository<Produtor, Integer> {

	// metodo para busca dos dados do produtor cadastrado através do email e senha

	@Query("select u from Produtor u where u.email like %:email% " + "and u.nome like %:nome% ")
	List<Produtor> findByEmailAndNome(@Param("email") String email, @Param("nome") String nome);

	// método para busca do login através do username do produtor cadastrado

	@Query("select u from Produtor u where u.email like %:email% ")
	Optional<Produtor> findByEmail(@Param("email") String email);
}
