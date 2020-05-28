package io.github.pameladilly.domain.repository;

import io.github.pameladilly.domain.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItensPedido extends JpaRepository<ItemPedido, Integer>{

}
