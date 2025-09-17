package com.primaria.app.Service;
import com.primaria.app.Model.Director;
import com.primaria.app.repository.DirectorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DirectorService {

    private final DirectorRepository directorRepository;

    public DirectorService(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    public List<Director> findAll() {
        return directorRepository.findAll();
    }

    public Optional<Director> findById(UUID id) {
        return directorRepository.findById(id);
    }

    public Director save(Director director) {
        return directorRepository.save(director);
    }

    public void deleteById(UUID id) {
        directorRepository.deleteById(id);
    }
}