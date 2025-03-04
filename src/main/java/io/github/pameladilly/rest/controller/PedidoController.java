package io.github.pameladilly.rest.controller;

import io.github.pameladilly.domain.entity.ItemPedido;
import io.github.pameladilly.domain.entity.Pedido;
import io.github.pameladilly.rest.dto.InformacoesItensPedidoDTO;
import io.github.pameladilly.rest.dto.InformacoesPedidoDTO;
import io.github.pameladilly.rest.dto.PedidoDTO;
import io.github.pameladilly.service.PedidoService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save(@RequestBody PedidoDTO dto) {
        Pedido pedido = service.salvar(dto);
        return pedido.getId();
    }


    @GetMapping("{id}")
    public InformacoesPedidoDTO getById(@PathVariable Integer id){

        return service
                .obterPedidoCompleto(id)
                .map( pedido ->  {
                    return converter(pedido);
                })
                .orElseThrow(
                        () -> new ResponseStatusException(NOT_FOUND, "Pedido nao encontrado")
                );
        //teste
    }

    private InformacoesPedidoDTO converter(Pedido pedido){
        return InformacoesPedidoDTO
                .builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .itens(converter(pedido.getItens()))
                .build();
    }

    private List<InformacoesItensPedidoDTO> converter(List<ItemPedido> itens){
        if(CollectionUtils.isEmpty(itens)) {
            return Collections.emptyList();
        }

        return itens.stream().map(
                itemPedido
                        -> InformacoesItensPedidoDTO
                            .builder()
                            .descricaoProduto(itemPedido.getProduto().getDescricao())
                            .precoUnitario(itemPedido.getProduto().getPreco())
                            .quantidade(itemPedido.getQuantidade())
                            .build()
        ).collect(Collectors.toList());
    }
}
