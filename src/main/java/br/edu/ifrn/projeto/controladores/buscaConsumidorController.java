package br.edu.ifrn.projeto.controladores;

//classe responsável por fazer a busca do cliente consumidor 

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifrn.projeto.agrox.repository.ConsumidorRepository;
import br.edu.ifrn.projeto.crud.dominio.Consumidor;

@Controller
@RequestMapping("/consumidor")
public class buscaConsumidorController {

	@Autowired
	private ConsumidorRepository consumidorRepository;

	@GetMapping("/buscaConsumidor")
	public String buscaConsumidor(ModelMap model) {
		model.addAttribute("usuario", new Consumidor());

		return "usuario/buscaConsumidor";
	}

	// método de busca do cliente consumidor

	@GetMapping("/buscar")
	public String buscar(@RequestParam(name = "nome", required = false) String nome,
			@RequestParam(name = "email", required = false) String email,
			@RequestParam(name = "mostrarTodosDados", required = false) Boolean mostrarTodosDados, HttpSession sessao,
			ModelMap model) {

		List<Consumidor> usuariosEncontrados = consumidorRepository.findByEmailAndNome(email, nome);
		model.addAttribute("usuariosEncontrados", usuariosEncontrados);

		if (mostrarTodosDados != null) {
			model.addAttribute("mostrarTodosDados", true);
		}

		return "usuario/buscaConsumidor";
	}

	// faz a remoção do usuário consumidor

	@GetMapping("/remover/{id}")
	public String remover(@PathVariable("id") Integer idUsuario, HttpSession sessao, RedirectAttributes attr) {

		consumidorRepository.deleteById(idUsuario);
		attr.addFlashAttribute("msgSucesso", "Usuário removido com sucesso!");

		return "redirect:/consumidor/buscar";
	}

	// faz a edição do usuário consumidor

	@GetMapping("/editar/{id}")
	public String iniciarEdicao(@PathVariable("id") Integer idUsuario, ModelMap model, HttpSession sessao) {

		Consumidor u = consumidorRepository.findById(idUsuario).get();
		model.addAttribute("usuario", u);

		return "/usuario/consumidor";
	}
}
