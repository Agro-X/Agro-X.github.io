package br.edu.ifrn.projeto.controladores;

//classe responsável pela realização da busca do produto

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
import br.edu.ifrn.projeto.agrox.repository.PdtRepository;
import br.edu.ifrn.projeto.crud.dominio.Consumidor;
import br.edu.ifrn.projeto.crud.dominio.Produto;

@Controller
@RequestMapping("/bcProduto")
public class buscaProdutoController {

	@Autowired
	private PdtRepository pdtRepository;

	@GetMapping("/buscarProduto")
	public String buscaProduto(ModelMap model) {
		model.addAttribute("usuario", new Produto());

		return "usuario/buscarProduto";
	}

	// método de busca do produto

	@GetMapping("/buscar")
	public String buscar(@RequestParam(name = "nome", required = false) String nome,
			@RequestParam(name = "categoria", required = false) String categoria,
			@RequestParam(name = "mostrarTodosDados", required = false) Boolean mostrarTodosDados, HttpSession sessao,
			ModelMap model) {

		List<Produto> usuariosEncontrados = pdtRepository.findByCategoriaAndNome(categoria, nome);
		model.addAttribute("usuariosEncontrados", usuariosEncontrados);

		if (mostrarTodosDados != null) {
			model.addAttribute("mostrarTodosDados", true);
		}

		return "usuario/buscarProduto";
	}

	// remove o produto

	@GetMapping("/remover/{id}")
	public String remover(@PathVariable("id") Integer idUsuario, HttpSession sessao, RedirectAttributes attr) {
		pdtRepository.deleteById(idUsuario);
		attr.addFlashAttribute("msgSucesso", "Produto removido com sucesso!");

		return "redirect:/bcProduto/buscar";
	}

	// edita o produto

	@GetMapping("/editar/{id}")
	public String iniciarEdicao(@PathVariable("id") Integer idUsuario, ModelMap model, HttpSession sessao) {
		
		Produto u = pdtRepository.findById(idUsuario).get();
		model.addAttribute("usuario", u);

		return "/usuario/cadastrarProduto";
	}
}
