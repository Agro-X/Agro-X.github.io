package br.edu.ifrn.projeto.controladores;

//classe responsável por realizar o cadastro do produto

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifrn.projeto.agrox.repository.ArquivoRepository;
import br.edu.ifrn.projeto.agrox.repository.CategoriaRepository;
import br.edu.ifrn.projeto.agrox.repository.ConsumidorRepository;
import br.edu.ifrn.projeto.agrox.repository.PdtRepository;
import br.edu.ifrn.projeto.agrox.repository.ProdutorRepository;
import br.edu.ifrn.projeto.crud.dominio.Arquivo;
import br.edu.ifrn.projeto.crud.dominio.Categoria;
import br.edu.ifrn.projeto.crud.dominio.Consumidor;
import br.edu.ifrn.projeto.crud.dominio.Produto;
import br.edu.ifrn.projeto.crud.dominio.Produtor;
import br.edu.ifrn.projeto.crud.dto.AutocompleteDTO;

@Controller
@RequestMapping("/cdProduto")
public class cadastroProdutoController {

	@Autowired
	private PdtRepository pdtRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ArquivoRepository arquivoRepository;

	@Autowired
	private ProdutorRepository produtorRepository;

	@GetMapping("/cadastrarProduto")
	public String cadastroProduto(ModelMap model) {
		model.addAttribute("usuario", new Produto());

		return "usuario/cadastrarProduto";
	}

	// método para salvar os dados do cadastro, chamar o método de validação e
	// realizar upload da imagem

	@PostMapping("/salvar")
	@Transactional(readOnly = false)
	public String salvar(Produto usuario, ModelMap model, RedirectAttributes attr,
			@RequestParam("file") MultipartFile arquivo, HttpSession sessao) {

		List<String> msgValidacao = validarDados(usuario);

		if (!msgValidacao.isEmpty()) {
			attr.addFlashAttribute("msgsErro", msgValidacao);
			return "redirect:/cdProduto/cadastrarProduto";

		}

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		Produtor produtor = produtorRepository.findByEmail(email).get();
		usuario.setProdutor(produtor);

		try {

			if (arquivo != null && !arquivo.isEmpty()) {

				// Normalizando o nome do arquivo

				String nomeArquivo = StringUtils.cleanPath(arquivo.getOriginalFilename());
				Arquivo arquivoBD = new Arquivo(null, nomeArquivo, arquivo.getContentType(), arquivo.getBytes());

				// Salvando a foto no banco de dados

				arquivoRepository.save(arquivoBD);

				if (usuario.getFoto() != null && usuario.getFoto().getId() != null && usuario.getFoto().getId() > 0) {
					arquivoRepository.delete(usuario.getFoto());
				}

				usuario.setFoto(arquivoBD);

			} else {
				usuario.setFoto(null);
			}

			// serve para cadastro e edição

			pdtRepository.save(usuario);
			attr.addFlashAttribute("msgSucesso", "Operação realizada com sucesso");

		} catch (IOException e) {
			e.printStackTrace();
		}

		return "redirect:/cdProduto/cadastrarProduto";

	}

	// faz a edição dos dados do cadastro

	@GetMapping("/editar/{id}")
	public String iniciarEdicao(@PathVariable("id") Integer idUsuario, ModelMap model, HttpSession sessao) {

		Produto u = pdtRepository.findById(idUsuario).get();
		model.addAttribute("usuario", u);

		return "cadastroProduto/cadastro";
	}

	// método responsável pelo auto complete

	@GetMapping("/autocompleteCategoria")
	@Transactional(readOnly = true)
	@ResponseBody
	public List<AutocompleteDTO> autocompleteCategoria(@RequestParam("term") String termo) {

		List<Categoria> categoria = categoriaRepository.findByNome(termo);
		List<AutocompleteDTO> resultados = new ArrayList<>();
		categoria.forEach(c -> resultados.add(new AutocompleteDTO(c.getNome(), c.getId())

		));

		return resultados;
	}

	// método para validação dos dados

	private List<String> validarDados(Produto usuario) {

		List<String> msgs = new ArrayList<>();

		if (usuario.getNome() == null || usuario.getNome().isEmpty()) {
			msgs.add("O campo Nome é obrigatório.");
		}

		if (usuario.getDescricao() == null || usuario.getDescricao().isEmpty()) {
			msgs.add("O campo Descrição é obrigatório.");
		}

		if (usuario.getCategoria() == null || usuario.getCategoria().getId() == 0) {
			msgs.add("O campo Categoria é obrigatório.");
		}

		if (usuario.getCep() == null || usuario.getCep().isEmpty()) {
			msgs.add("O campo CEP é obrigatório.");
		}

		return msgs;

	}
}