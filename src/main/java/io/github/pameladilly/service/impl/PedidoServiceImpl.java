package io.github.pameladilly.service.impl;

import io.github.pameladilly.domain.entity.Cliente;
import io.github.pameladilly.domain.entity.ItemPedido;
import io.github.pameladilly.domain.entity.Pedido;
import io.github.pameladilly.domain.entity.Produto;
import io.github.pameladilly.domain.enums.StatusPedido;
import io.github.pameladilly.domain.repository.Clientes;
import io.github.pameladilly.domain.repository.ItensPedido;
import io.github.pameladilly.domain.repository.Pedidos;
import io.github.pameladilly.domain.repository.Produtos;
import io.github.pameladilly.exception.PedidoNaoEncontradoException;
import io.github.pameladilly.exception.RegraNegocioException;
import io.github.pameladilly.rest.dto.ItemPedidoDTO;
import io.github.pameladilly.rest.dto.PedidoDTO;
import io.github.pameladilly.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final Pedidos repository;
    private final Clientes clientesRepository;
    private final Produtos produtosRepository;
    private final ItensPedido itensPedidoRepository;

    @Override
    @Transactional
    public Pedido salvar(PedidoDTO dto) {
        Integer idCliente = dto.getCliente();

        Cliente cliente = clientesRepository
                .findById(idCliente)
                .orElseThrow(
                        () -> new RegraNegocioException("Cliente não localizado na base de dados."));


        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);

        List<ItemPedido> itensPedido = converterItens(pedido, dto.getItens());
        repository.save(pedido);
        itensPedidoRepository.saveAll(itensPedido);
        pedido.setItens(itensPedido);
        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return repository.findByIdFetchItens(id);
    }

    @Override
    @Transactional
    public void atualizaStatus(Integer id, StatusPedido statusPedido) {
        repository
                .findById(id)
                .map( pedido -> {
                    pedido.setStatus(statusPedido);
                    return repository.save(pedido);
                }).orElseThrow(
                        () -> new PedidoNaoEncontradoException() );
    }

    private List<ItemPedido> converterItens( Pedido pedido, List<ItemPedidoDTO> itens){
        if(itens.isEmpty()) {
            throw  new RegraNegocioException("Não é possível realizar um pedido sem itens.");
        }

        return itens
                .stream()
                .map( dto -> {
                    Integer idProduto = dto.getProduto();
                    Produto produtoFind = produtosRepository
                            .findById(idProduto)
                            .orElseThrow(
                                    () -> new RegraNegocioException("Produto não localizado na base de dados. Código Produto: " + idProduto));


                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produtoFind);

                    return itemPedido;
                }).collect(Collectors.toList());
    }
}
