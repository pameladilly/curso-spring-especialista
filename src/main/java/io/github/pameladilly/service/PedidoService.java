package io.github.pameladilly.service;

import io.github.pameladilly.domain.entity.Pedido;
import io.github.pameladilly.domain.enums.StatusPedido;
import io.github.pameladilly.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {

    Pedido salvar(PedidoDTO dto);

    Optional<Pedido> obterPedidoCompleto(Integer id);

    void atualizaStatus(Integer id, StatusPedido statusPedido);

}
