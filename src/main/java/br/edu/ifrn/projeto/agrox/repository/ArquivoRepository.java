package br.edu.ifrn.projeto.agrox.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifrn.projeto.crud.dominio.Arquivo;

public interface ArquivoRepository extends JpaRepository<Arquivo, Long> {

}
