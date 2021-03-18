package br.edu.ifrn.projeto.crud.dominio;

//responsável por armazenar os atributos da classe produto

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false)
	private String nome;

	@Column(nullable = false)
	private String descricao;

	@Column(nullable = false)
	private String cep;

	@ManyToOne(optional = false)
	private Categoria categoria;

	@Column(nullable = false)
	private String tel;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Arquivo foto;

	@ManyToOne(optional = false)
	private Produtor produtor;

	public Produtor getProdutor() {
		return produtor;
	}

	public void setProdutor(Produtor produtor) {
		this.produtor = produtor;
	}

	public int getIdProduto() {
		return id;
	}

	public void setIdProduto(int idProduto) {
		this.id = idProduto;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Arquivo getFoto() {
		return foto;
	}

	public void setFoto(Arquivo foto) {
		this.foto = foto;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

}