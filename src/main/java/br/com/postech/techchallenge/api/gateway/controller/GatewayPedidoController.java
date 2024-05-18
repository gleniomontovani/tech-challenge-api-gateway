package br.com.postech.techchallenge.api.gateway.controller;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.postech.techchallenge.api.gateway.model.request.pedido.Pedido;
import br.com.postech.techchallenge.api.gateway.model.response.pedido.PedidoResponse;
import br.com.postech.techchallenge.api.gateway.service.integracao.client.pedido.GatewayPedidoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/pedidos")
@RequiredArgsConstructor
public class GatewayPedidoController {

	private final GatewayPedidoService service;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON)
	@PreAuthorize("hasAuthority('SCOPE_fiap/postech/techchallenge')")
	public ResponseEntity<List<PedidoResponse>> listarTodosPedidos(@AuthenticationPrincipal Jwt principal) throws Exception {

		return new ResponseEntity<>(service.listarTodosPedidos(principal), HttpStatus.OK);
	}

	@GetMapping(path = "/{numeroPedido}", produces = MediaType.APPLICATION_JSON)
	@PreAuthorize("hasAuthority('SCOPE_fiap/postech/techchallenge')")
	public ResponseEntity<PedidoResponse> buscarPedido(@AuthenticationPrincipal Jwt principal, @PathVariable Long numeroPedido)
			throws Exception {

		return new ResponseEntity<>(service.buscarPedido(principal, numeroPedido), HttpStatus.OK);
	}

	@PostMapping(path = "/checkout", produces = MediaType.APPLICATION_JSON)
	@PreAuthorize("hasAuthority('SCOPE_fiap/postech/techchallenge')")
	public ResponseEntity<PedidoResponse> fazerCheckoutFake(@AuthenticationPrincipal Jwt principal, @RequestBody Pedido pedido)
			throws Exception {

		return new ResponseEntity<>(service.fazerCheckoutFake(principal, pedido), HttpStatus.OK);
	}

	@PutMapping(path = "/status", produces = MediaType.APPLICATION_JSON, params = { "numeroPedido", "statusPedido" })
	@PreAuthorize("hasAuthority('SCOPE_fiap/postech/techchallenge')")
	public ResponseEntity<PedidoResponse> atualizarStatusPedido(@AuthenticationPrincipal Jwt principal,
			@RequestParam Long numeroPedido, @RequestParam Integer statusPedido) throws Exception {

		return new ResponseEntity<>(service.atualizarStatusPedido(principal, numeroPedido, statusPedido), HttpStatus.OK);
	}
}