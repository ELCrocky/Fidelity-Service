package com.fidelite.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import com.fidelite.models.Fidelite;

@Repository
public interface FideliteRepository extends JpaRepository<Fidelite, String> {

    Optional<Fidelite> findByMailClient(String mail);

    Optional<Fidelite> findByBarcodeFidelite(String barcode);

}
