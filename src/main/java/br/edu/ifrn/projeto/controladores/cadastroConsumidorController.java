package br.edu.ifrn.projeto.controladores;

//classe responsável por realizar o cadastro do cliente consumidor

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifrn.projeto.agrox.repository.ConsumidorRepository;
import br.edu.ifrn.projeto.crud.dominio.Consumidor;

@Controller
@RequestMapping("/usuarioConsumidor")
public class cadastroConsumidorController {

	@Autowired
	private ConsumidorRepository consumidorRepository;

	@GetMapping("/consumidor")
	public String cadastroConsumidor(ModelMap model) {
		model.addAttribute("usuario", new Consumidor());

		return "usuario/consumidor";
	}

	// método para salvar os dados do cadastro e chamar o método de validação

	@PostMapping("/salvar")
	public String salvar(Consumidor usuario, ModelMap model, RedirectAttributes attr, HttpSession sessao) {

		List<String> msgValidacao = validarDados(usuario);

		if (!msgValidacao.isEmpty()) {
			attr.addFlashAttribute("msgsErro", msgValidacao);
			return "redirect:/usuarioConsumidor/consumidor";
		}

		// criptografando a senha

		String senhaCriptografada = new BCryptPasswordEncoder().encode(usuario.getSenha());
		usuario.setSenha(senhaCriptografada);

		// serve para cadastro e edição

		consumidorRepository.save(usuario);
		attr.addFlashAttribute("msgSucesso", "Operação realizada com sucesso");

		return "redirect:/usuarioConsumidor/consumidor";

	}

	// faz a edição dos dados do cadastro

	@GetMapping("/editar/{id}")
	public String iniciarEdicao(@PathVariable("id") Integer idUsuario, ModelMap model, HttpSession sessao) {

		Consumidor u = consumidorRepository.findById(idUsuario).get();
		model.addAttribute("usuario", u);

		return "cadastroConsumidor/cadastro";
	}

	// método para validação dos dados

	private List<String> validarDados(Consumidor usuario) {

		List<String> msgs = new ArrayList<>();

		if (usuario.getNome() == null || usuario.getNome().isEmpty()) {
			msgs.add("O campo nome é obrigatório.");
		}
		if (usuario.getCpf() == null || usuario.getCpf().isEmpty()) {
			msgs.add("O campo cpf é obrigatório.");
		}
		if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
			msgs.add("O campo email é obrigatório.");
		}
		if (usuario.getSenha() == null || usuario.getSenha().isEmpty()) {
			msgs.add("O campo senha é obrigatório.");
		}
		if (usuario.getSexo() == null || usuario.getSexo().isEmpty()) {
			msgs.add("O campo sexo é obrigatório.");
		}
		return msgs;

	}
}
