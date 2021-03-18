package br.edu.ifrn.projeto.controladores;

//classe responsável por realizar o cadastro do cliente produtor

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifrn.projeto.agrox.repository.ConsumidorRepository;
import br.edu.ifrn.projeto.agrox.repository.ProdutorRepository;
import br.edu.ifrn.projeto.crud.dominio.Consumidor;
import br.edu.ifrn.projeto.crud.dominio.Produto;
import br.edu.ifrn.projeto.crud.dominio.Produtor;

@Controller
@RequestMapping("/usuarioProdutor")
public class cadastroProdutorController {

	@Autowired
	private ProdutorRepository produtorRepository;

	@GetMapping("/produtor")
	public String cadastroProdutor(ModelMap model) {
		model.addAttribute("usuario", new Produtor());

		return "usuario/produtor";
	}

	// método para salvar os dados do cadastro e chamar o método de validação

	@PostMapping("/salvar")
	public String salvar(Produtor usuario, ModelMap model, RedirectAttributes attr, HttpSession sessao) {

		List<String> msgValidacao = validarDados(usuario);

		if (!msgValidacao.isEmpty()) {
			attr.addFlashAttribute("msgsErro", msgValidacao);
			return "redirect:/usuarioProdutor/produtor";
		}

		// criptografando a senha

		String senhaCriptografada = new BCryptPasswordEncoder().encode(usuario.getSenha());
		usuario.setSenha(senhaCriptografada);

		// serve para cadastro e edição

		produtorRepository.save(usuario);
		attr.addFlashAttribute("msgSucesso", "Operação realizada com sucesso");

		return "redirect:/usuarioProdutor/produtor";

	}

	// faz a edição dos dados do cadastro

	@GetMapping("/editar/{id}")
	public String iniciarEdicao(@PathVariable("id") Integer idUsuario, ModelMap model, HttpSession sessao) {

		Produtor u = produtorRepository.findById(idUsuario).get();
		model.addAttribute("usuario", u);

		return "cadastroProdutor/cadastro";
	}

	// método para validação dos dados

	private List<String> validarDados(Produtor usuario) {

		List<String> msgs = new ArrayList<>();

		if (usuario.getNome() == null || usuario.getNome().isEmpty()) {
			msgs.add("O campo nome é obrigatório.");
		}

		if (usuario.getCpf() == null || usuario.getCpf().isEmpty()) {
			msgs.add("O campo CPF é obrigatório.");
		}

		if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
			msgs.add("O campo Email é obrigatório.");
		}

		if (usuario.getSenha() == null || usuario.getSenha().isEmpty()) {
			msgs.add("O campo Senha é obrigatório.");
		}

		return msgs;

	}
}