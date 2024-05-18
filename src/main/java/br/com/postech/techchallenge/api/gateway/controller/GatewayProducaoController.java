package br.com.postech.techchallenge.api.gateway.controller;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.postech.techchallenge.api.gateway.model.request.producao.Producao;
import br.com.postech.techchallenge.api.gateway.model.response.producao.ProducaoResponse;
import br.com.postech.techchallenge.api.gateway.service.integracao.client.producao.GatewayProducaoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/producao")
@RequiredArgsConstructor
public class GatewayProducaoController {

	private final GatewayProducaoService service;

	@GetMapping(path = "/situacao/{situacao}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<ProducaoResponse>> listarTodasProducaoPorSituacao(@AuthenticationPrincipal Jwt principal,
			@PathVariable Integer situacao) throws Exception {
		return new ResponseEntity<>(service.listarTodasProducaoPorSituacao(principal, situacao), HttpStatus.OK);
	}

	@GetMapping(path = "/{numeroPedido}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<ProducaoResponse> buscarProducaoPorPedido(@AuthenticationPrincipal Jwt principal,
			@PathVariable Long numeroPedido) throws Exception {
		return new ResponseEntity<>(service.buscarProducaoPorNumeroPedido(principal, numeroPedido), HttpStatus.OK);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<ProducaoResponse> salvarProducaoPedido(@AuthenticationPrincipal Jwt principal,
			@RequestBody Producao producaoRequest) throws Exception {
		return new ResponseEntity<>(service.salvarProducaoPedido(principal, producaoRequest), HttpStatus.CREATED);
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<ProducaoResponse> atualizarStatusProducao(@AuthenticationPrincipal Jwt principal,
			@RequestBody Producao producaoRequest) throws Exception {
		return new ResponseEntity<>(service.atualizarStatusProducao(principal, producaoRequest), HttpStatus.OK);
	}
}
