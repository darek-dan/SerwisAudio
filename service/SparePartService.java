package serwisAudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import serwisAudio.model.PartType;
import serwisAudio.model.Repair;
import serwisAudio.model.SparePart;
import serwisAudio.repository.SparePartRepository;

import javax.swing.*;
import java.util.List;

@Service
public class SparePartService {
    @Autowired
    SparePartRepository sparePartRepository;

    public void addPart(String symbol, String name, PartType type, int quantity, float price) {
        sparePartRepository.save(new SparePart(symbol, name, type, quantity, price));
    }

    public List<SparePart> getAllSpareParts() {
        return sparePartRepository.findAll(Sort.by(Sort.Direction.DESC, "symbol"));
    }

}
