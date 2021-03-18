package br.edu.ifrn.projeto.agrox.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.ifrn.projeto.crud.dominio.Produto;
import br.edu.ifrn.projeto.crud.dominio.Produtor;

public interface PdtRepository extends JpaRepository<Produto, Integer> {

	// metodo para busca do produto cadastrado através do nome e categoria

	@Query("select u from Produto u where u.categoria.nome like %:categoria% " + "and u.nome like %:nome% ")
	List<Produto> findByCategoriaAndNome(@Param("categoria") String categoria, @Param("nome") String nome);
}
