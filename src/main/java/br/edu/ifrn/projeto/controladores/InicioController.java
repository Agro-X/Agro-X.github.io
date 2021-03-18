package br.edu.ifrn.projeto.controladores;

//claase responsável por chamar os métodos atravéz das urls

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import br.edu.ifrn.projeto.crud.dominio.Consumidor;
import br.edu.ifrn.projeto.crud.dominio.Produto;

@Controller
public class InicioController {

	@GetMapping("/")
	public String inicio() {
		return "Inicio";
	}

	@GetMapping("/cadastro")
	public String entrarCadastro(ModelMap model) {
		model.addAttribute("usuario", new Consumidor());

		return "usuario/cadastro";
	}

	@GetMapping("/busca")
	public String entrarBuscaConsumidor(ModelMap model) {
		model.addAttribute("usuario", new Consumidor());

		return "usuario/busca";
	}

	@GetMapping("/produto")
	public String entrarProduto(ModelMap model) {
		model.addAttribute("usuario", new Produto());

		return "usuario/produto";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/login-error")
	public String loginError(ModelMap model) {
		model.addAttribute("msgErro", "Email ou senha incorretos. Tente novamente");
		return "login";

	}
}
