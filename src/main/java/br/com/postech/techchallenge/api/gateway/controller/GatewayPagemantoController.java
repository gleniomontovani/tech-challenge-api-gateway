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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.postech.techchallenge.api.gateway.model.response.pagamento.PagamentoResponse;
import br.com.postech.techchallenge.api.gateway.service.integracao.client.pagamento.GatewayPagamentoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/pagamentos")
@RequiredArgsConstructor
public class GatewayPagemantoController {

	private final GatewayPagamentoService service;

	@GetMapping(path = "/{numeroPedido}", produces = MediaType.APPLICATION_JSON)
	@PreAuthorize("hasAuthority('SCOPE_fiap/postech/techchallenge')")
	public ResponseEntity<PagamentoResponse> consultarPagamentoPorPedido(@AuthenticationPrincipal Jwt principal,
			@PathVariable Long numeroPedido) throws Exception {

		return new ResponseEntity<>(service.consultarPagamentoPorPedido(principal, numeroPedido), HttpStatus.OK);
	}

	@GetMapping(path = "/historico/{numeroPedido}", produces = MediaType.APPLICATION_JSON)
	@PreAuthorize("hasAuthority('SCOPE_fiap/postech/techchallenge')")
	public ResponseEntity<List<PagamentoResponse>> consultarHistoricoPagamentoPorPedido(@AuthenticationPrincipal Jwt principal,
			@PathVariable Long numeroPedido) throws Exception {

		return new ResponseEntity<>(service.consultarHistoricoPagamentoPorPedido(principal, numeroPedido),
				HttpStatus.OK);
	}
}