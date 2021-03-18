package br.edu.ifrn.projeto.controladores;

//classe responsável por realizar a busca do cliente produtor

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
import br.edu.ifrn.projeto.agrox.repository.ProdutorRepository;
import br.edu.ifrn.projeto.crud.dominio.Consumidor;
import br.edu.ifrn.projeto.crud.dominio.Produtor;

@Controller
@RequestMapping("/produtor")
public class buscaProdutorController {

	@Autowired
	private ProdutorRepository produtorRepository;

	@GetMapping("/buscaProdutor")
	public String buscaProdutor(ModelMap model) {
		model.addAttribute("usuario", new Produtor());

		return "usuario/buscaProdutor";
	}

	// método de busca do cliente produtor

	@GetMapping("/buscar")
	public String buscar(@RequestParam(name = "nome", required = false) String nome,
			@RequestParam(name = "email", required = false) String email,
			@RequestParam(name = "mostrarTodosDados", required = false) Boolean mostrarTodosDados, HttpSession sessao,
			ModelMap model) {

		List<Produtor> usuariosEncontrados = produtorRepository.findByEmailAndNome(email, nome);
		model.addAttribute("usuariosEncontrados", usuariosEncontrados);

		if (mostrarTodosDados != null) {
			model.addAttribute("mostrarTodosDados", true);
		}

		return "usuario/buscaProdutor";
	}

	// faz a remoção do usuário produtor

	@GetMapping("/remover/{id}")
	public String remover(@PathVariable("id") Integer idUsuario, HttpSession sessao, RedirectAttributes attr) {

		produtorRepository.deleteById(idUsuario);
		attr.addFlashAttribute("msgSucesso", "Usuário removido com sucesso!");

		return "redirect:/produtor/buscar";
	}

	// faz a edição do usuário produtor

	@GetMapping("/editar/{id}")
	public String iniciarEdicao(@PathVariable("id") Integer idUsuario, ModelMap model, HttpSession sessao) {

		Produtor u = produtorRepository.findById(idUsuario).get();
		model.addAttribute("usuario", u);

		return "/usuario/produtor";
	}
}