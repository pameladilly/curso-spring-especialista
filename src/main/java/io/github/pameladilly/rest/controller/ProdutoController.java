package io.github.pameladilly.rest.controller;

import io.github.pameladilly.domain.entity.Produto;
import io.github.pameladilly.domain.repository.Produtos;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private Produtos produtos;

    public ProdutoController(Produtos produtos) {
        this.produtos = produtos;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)

    public Produto save(@RequestBody @Valid Produto produto) {

        return produtos.save(produto);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable @Valid Integer id,
                          @RequestBody Produto produto) {
         produtos
                .findById(id)
                .map(produtoExistente -> {
                    produto.setId(produtoExistente.getId());
                    produtos.save(produto);
                    return produto;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        Optional<Produto> produtoEncontrado = produtos.findById(id);
        produtoEncontrado.map(produto -> {
                    produtos.delete(produto);
                    return produtoEncontrado;
                }
        ).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));

    }

    @GetMapping("{id}")
    public Produto getProdutoById(@PathVariable Integer id) {
        return produtos
                    .findById(id)
                    .orElseThrow(
                            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }

    @GetMapping
    public List<Produto> find(  Produto filtro){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);

        return produtos.findAll(example);

    }
}
