package com.example.springbootex.service;

import com.example.springbootex.entity.Store;
import com.example.springbootex.exception.NotFoundException;
import com.example.springbootex.entity.Section;
import com.example.springbootex.repository.SectionRepository;
import com.example.springbootex.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectionService {

    private final SectionRepository sectionRepository;
    private final StoreRepository storeRepository;

    @Autowired
    public SectionService(SectionRepository sectionRepository, StoreRepository storeRepository) {
        this.sectionRepository = sectionRepository;
        this.storeRepository = storeRepository;
    }

    public Section get(int id) {
        return getSectionOrThrow(id);
    }

    public List<Section> getAll() {
        return sectionRepository.findAll();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public void add(Section section, int storeId) {
        Store store = getStoreOrThrow(storeId);
        section.setStore(store);
        sectionRepository.save(section);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public void update(int id, Section section) {
        Section updatedSection = getSectionOrThrow(id);
        updatedSection.setName(section.getName());
        updatedSection.setStore(section.getStore());
        sectionRepository.save(updatedSection);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public void delete(int id) {
        Section section = getSectionOrThrow(id);
        sectionRepository.delete(section);
    }

    public Section getSectionOrThrow(int id) {
        return sectionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cannot find section with id " + id));
    }

    public Store getStoreOrThrow(int id) {
        return storeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cannot find store with id " + id));
    }
}
