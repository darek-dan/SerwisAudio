package serwisAudio.service;

import serwisAudio.model.Repair;
import serwisAudio.model.RepairDto;
import serwisAudio.model.User;
import serwisAudio.repository.RepairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RepairService {
    @Autowired
    RepairRepository repairRepository;

    public boolean editRepair(int repairId, RepairDto repairDto) {
        if (getRepairById(repairId).isPresent()) {
            Repair repair = getRepairById(repairId).get();
            repair.setSerial(repairDto.getSerial());
            repair.setBrand(repairDto.getBrand());
            repair.setModel(repairDto.getModel());
            // do rozważenia co z autorem posta ???
            repairRepository.save(repair);      // update
            return true;
        }
        return false;
    }


    public void addRepair(String serial, String brand, String model, String userFailDescription, String additionalInfo, User client, String userImagePath) {
        repairRepository.save(new Repair(serial, brand, model, userFailDescription, additionalInfo, client, LocalDateTime.now(), userImagePath));
    }

    public List<Repair> getAllRepairs() {
        return repairRepository.findAll(Sort.by(Sort.Direction.DESC, "dateAdded"));
    }
    public List<Integer> generatePagesIndexes(List<Repair> repairs){
        int noOfPages = (getAllRepairs().size() / 5) + 1;
        List<Integer> pagesIndexes = new ArrayList<>();
        for (int i = 0; i < noOfPages; i++){
            pagesIndexes.add(i + 1);
        }
        return pagesIndexes;
    }

    public List<Repair> getAllRepairs(int pageIndex) {
        Pageable pageable = PageRequest.of(pageIndex, 5, Sort.by(Sort.Direction.DESC, "dateAdded"));
        Page<Repair> postPage = repairRepository.findAll(pageable);
        return postPage.getContent();
    }

    public Optional<Repair> getRepairById(int repairId) {
        return repairRepository.findById(repairId);
    }
    public void deleteRepairById(int repairId) {
        repairRepository.deleteById(repairId);
    }
}
