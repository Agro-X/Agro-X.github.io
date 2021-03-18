package br.edu.ifrn.projeto.agrox.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.ifrn.projeto.crud.dominio.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

	// metodo para realizar a busca do produto através da categoria

	@Query("select c from Categoria c where c.nome like %:nome% ")
	List<Categoria> findByNome(@Param("nome") String nome);
}
