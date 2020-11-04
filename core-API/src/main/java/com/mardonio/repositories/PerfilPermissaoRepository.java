package com.mardonio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mardonio.domain.PerfilPermissao;
import com.mardonio.domain.PerfilPermissaoPK;

public interface PerfilPermissaoRepository extends JpaRepository<PerfilPermissao, PerfilPermissaoPK> {

}
