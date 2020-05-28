package io.github.pameladilly.domain.repository;

import io.github.pameladilly.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface Clientes extends JpaRepository<Cliente, Integer> {

    @Query(value = " select c from Cliente c where c.nome like %:nome% ")
    List<Cliente> encontrarPorNome(@Param("nome") String nome);

    @Transactional
    @Query(value = " delete from Cliente where nome like  %:nome% ")
    @Modifying
    void deleteByNome(@Param("nome") String nome);

    boolean existsByNome(String nome);

    @Query(value =  " select c from Cliente c left join fetch c.pedidos p where c.id = :id")
    Cliente findClienteFetchPedidos(@Param("id") Integer id);

}
