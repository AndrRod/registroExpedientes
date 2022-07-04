package com.tc.registro.art1.repository;

import com.tc.registro.art1.model.Files;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
@Repository
public interface FileRepository extends JpaRepository<Files, Long> {
    @Query(value = "SELECT f FROM Files f WHERE concat(f.numberFile, ' ', f.title, ' ', f.expirationDate ) LIKE %:description% ORDER BY f.expirationDate")
    Page<Files> findByDescriptionFile(@Param("description") String description, Pageable pageable);
    @Query(value = "SELECT f FROM Files f ORDER BY f.expirationDate")
    Page<Files> findAll(Pageable pageable);
}
