package itu.crypto.service;

import itu.crypto.entity.Cours;
import itu.crypto.repository.CoursRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoursService {
    private final CoursRepository coursRepository;

    public List<Cours> findCurrentCours() {
        List<Cours> o = null;
        return o;
    }
}
