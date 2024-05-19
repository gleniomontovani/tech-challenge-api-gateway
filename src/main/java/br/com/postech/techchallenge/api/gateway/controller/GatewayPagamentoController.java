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
import org.springframework.web.bind.annotation.RestController;

import br.com.postech.techchallenge.api.gateway.model.request.pagamento.Pagamento;
import br.com.postech.techchallenge.api.gateway.model.response.pagamento.HistoricoPagamentoResponse;
import br.com.postech.techchallenge.api.gateway.model.response.pagamento.PagamentoResponse;
import br.com.postech.techchallenge.api.gateway.service.integracao.client.pagamento.GatewayPagamentoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/pagamentos")
@RequiredArgsConstructor
public class GatewayPagamentoController {

	private final GatewayPagamentoService service;

	@GetMapping(path = "/{numeroPedido}", produces = MediaType.APPLICATION_JSON)
	@PreAuthorize("hasAuthority('SCOPE_fiap/postech/techchallenge')")
	public ResponseEntity<PagamentoResponse> consultarPagamentoPorPedido(@AuthenticationPrincipal Jwt principal,
			@PathVariable Long numeroPedido) throws Exception {		
		return new ResponseEntity<>(service.consultarPagamentoPorPedido(principal, numeroPedido), HttpStatus.OK);
	}

	@GetMapping(path = "/historico/{numeroPedido}", produces = MediaType.APPLICATION_JSON)
	@PreAuthorize("hasAuthority('SCOPE_fiap/postech/techchallenge')")
	public ResponseEntity<List<HistoricoPagamentoResponse>> consultarHistoricoPagamentoPorPedido(
			@AuthenticationPrincipal Jwt principal, @PathVariable Long numeroPedido) throws Exception {
		return new ResponseEntity<>(service.consultarHistoricoPagamentoPorPedido(principal, numeroPedido),
				HttpStatus.OK);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<PagamentoResponse> criarPagamento(@AuthenticationPrincipal Jwt principal,
			@RequestBody Pagamento pagamento) throws Exception {
		return new ResponseEntity<>(service.criarPagamento(principal, pagamento), HttpStatus.CREATED);
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<PagamentoResponse> atualizaPagamento(@AuthenticationPrincipal Jwt principal,
			@RequestBody Pagamento pagamento) throws Exception {
		return new ResponseEntity<>(service.atualizaPagamento(principal, pagamento), HttpStatus.OK);
	}
}