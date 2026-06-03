package com.fidelite.component;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fidelite.exceptions.NotFoundElementException;
import com.fidelite.models.Fidelite;
import com.fidelite.repository.FideliteRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FideliteComponent {
    private final FideliteRepository fideliteRepository;


    public Fidelite save(Fidelite fidelite) {
        return fideliteRepository.save(fidelite);
    }

    public Fidelite findById(String id) throws NotFoundElementException{
        return fideliteRepository.findById(id)
            .orElseThrow(() -> new NotFoundElementException(String.format("fidelite with id [%s] not found", id)));
    }

    public List<Fidelite> findAll(){
        return fideliteRepository.findAll();
    }

    public void deleteById(String id) {
        fideliteRepository.deleteById(id);
    }

    public Fidelite findByMailClient(String mail) throws NotFoundElementException {
        return fideliteRepository.findByMailClient(mail)
            .orElseThrow(() -> new NotFoundElementException(String.format("fidelite with mail [%s] not found", mail)));
    }

    public Fidelite findByBarcodeFidelite(String barcode) throws NotFoundElementException {
        return fideliteRepository.findByBarcodeFidelite(barcode)
            .orElseThrow(() -> new NotFoundElementException(String.format("fidelite with barcode [%s] not found", barcode)));
    }

    public boolean existsByBarcodeFidelite(String barcode) {
        return fideliteRepository.findByBarcodeFidelite(barcode).isPresent();
    }

}
