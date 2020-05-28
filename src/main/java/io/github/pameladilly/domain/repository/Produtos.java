package io.github.pameladilly.domain.repository;

import io.github.pameladilly.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Produtos extends JpaRepository<Produto, Integer> {

}
