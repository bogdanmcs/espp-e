package com.example.springbootex.service;

import com.example.springbootex.entity.Store;
import com.example.springbootex.exception.NotFoundException;
import com.example.springbootex.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService {

    private final StoreRepository storeRepository;

    @Autowired
    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public Store get(int id) {
        return getStoreOrThrow(id);
    }

    public List<Store> getAll() {
        return storeRepository.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void add(Store store) {
        storeRepository.save(store);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void update(int id, Store store) {
        Store updatedStore = getStoreOrThrow(id);
        updatedStore.setName(store.getName());
        storeRepository.save(updatedStore);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void delete(int id) {
        Store store = getStoreOrThrow(id);
        storeRepository.delete(store);
    }

    public Store getStoreOrThrow(int id) {
        return storeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cannot find store with id " + id));
    }
}
