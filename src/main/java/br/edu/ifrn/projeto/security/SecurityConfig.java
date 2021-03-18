package br.edu.ifrn.projeto.security;

//classe que configura a segurança do sistema

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.edu.ifrn.projeto.crud.dominio.Produtor;
import br.edu.ifrn.projeto.service.LoginService;

//classe que configura a segurança do sistema

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private LoginService service;

	// metodo para realizar a autorização restrita dos usuários em realção as URL's

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/css/**", "/imagens/**", "/js/**").permitAll().antMatchers("/publico/**")
				.permitAll()
				.antMatchers("/usuarioConsumidor/consumidor/**", "/usuarioProdutor/produtor/**",
						"/usuarioConsumidor/salvar/**", "/usuarioProdutor/salvar/**")
				.permitAll()

				.antMatchers("/cdProduto/cadastrarProduto", "/cdProduto/salvar", "/cdProduto/editar/**",
						"/cdProduto/remover/**")
				.hasAnyAuthority(Produtor.PRODUTOR)

				.anyRequest().authenticated().and().formLogin().loginPage("/login").defaultSuccessUrl("/", true)
				.failureUrl("/login-error").permitAll().and().logout().logoutSuccessUrl("/").and().rememberMe();
	}

	// método para informar que a senha está criptografada

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(service).passwordEncoder(new BCryptPasswordEncoder());
	}
}
