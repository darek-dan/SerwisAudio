package serwisAudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import serwisAudio.model.PartType;
import serwisAudio.model.Repair;
import serwisAudio.model.SparePart;
import serwisAudio.repository.SparePartRepository;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

@Service
public class SparePartService {
    @Autowired
    SparePartRepository sparePartRepository;

    public void addPart(String symbol, String name, int quantity, float price, Enum<PartType> partType) {
        sparePartRepository.save(new SparePart(symbol, name, quantity, price, partType));
    }

    public List<SparePart> getAllSpareParts() {
        return sparePartRepository.findAll(Sort.by(Sort.Direction.DESC, "symbol"));
    }
    
    public EnumSet<PartType> getAllPartTypes() {
        EnumSet<PartType> allPartTypes = EnumSet.allOf(PartType.class);
        System.out.println(allPartTypes);
        return allPartTypes;
    }

}
