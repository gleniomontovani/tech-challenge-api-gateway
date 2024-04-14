package br.com.postech.techchallenge.api.gateway.controller;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.postech.techchallenge.api.gateway.model.Produto;
import br.com.postech.techchallenge.api.gateway.service.integracao.client.ProdutoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/produtos")
@RequiredArgsConstructor
public class GatewayProdutoController {

	private final ProdutoService service;

	@GetMapping(produces = MediaType.APPLICATION_JSON)
	@PreAuthorize("hasAuthority('SCOPE_fiap/postech/techchallenge')")
	public <T> ResponseEntity<List<T>> listarProdutos(@AuthenticationPrincipal Jwt principal, Integer categoria)
			throws Exception {

		return new ResponseEntity<>(service.listarProdutos(principal, categoria), HttpStatus.OK);
	}

	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON)
	@PreAuthorize("hasAuthority('SCOPE_fiap/postech/techchallenge')")
	public <T> ResponseEntity<T> buscarProdutoPorId(@AuthenticationPrincipal Jwt principal, @PathVariable Long id)
			throws Exception {

		return new ResponseEntity<>(service.buscarProdutoPorId(principal, id), HttpStatus.OK);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@PreAuthorize("hasAuthority('SCOPE_fiap/postech/techchallenge')")
	public <T> ResponseEntity<T> salvar(@AuthenticationPrincipal Jwt principal, @RequestBody Produto produto)
			throws Exception {

		return new ResponseEntity<>(service.salvar(principal, produto), HttpStatus.OK);
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@PreAuthorize("hasAuthority('SCOPE_fiap/postech/techchallenge')")
	public <T> ResponseEntity<T> atualizar(@AuthenticationPrincipal Jwt principal, @PathVariable Integer id,
			@RequestBody Produto produto) throws Exception {

		return new ResponseEntity<>(service.atualizar(principal, id, produto), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('SCOPE_fiap/postech/techchallenge')")
	public <T> ResponseEntity<T> deleteById(@AuthenticationPrincipal Jwt principal, @PathVariable Long id)
			throws Exception {

		return new ResponseEntity<>(service.deleteById(principal, id), HttpStatus.OK);
	}
}
