package com.mardonio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mardonio.domain.Permissao;

@Repository
public interface PermissaoRepository extends JpaRepository<Permissao, Integer>{

}
