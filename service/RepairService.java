package serwisAudio.service;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RepairService {
    @Autowired
    RepairRepository repairRepository;
    private final String UPLOAD_DIR = ".\\src\\main\\resources\\static\\img\\uploads\\";

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

    public void addRepair(String serial, String brand, String model, String userFailDescription,
                          String additionalInfo, User client, MultipartFile repairImage) {
        Repair repairToAdd = new Repair(serial, brand, model, userFailDescription, additionalInfo, client,
                LocalDateTime.now(), repairImage);
        repairRepository.save(repairToAdd);
        String fileName = StringUtils.cleanPath(repairImage.getOriginalFilename());
        try {
            Path path = Paths.get(UPLOAD_DIR + "rId_" + repairToAdd.getRepairId() + "_" + fileName);
            Files.copy(repairImage.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        repairToAdd.setRepairImagePath("/img/uploads/rId_" + repairToAdd.getRepairId() + "_" + fileName);
        repairRepository.save(repairToAdd);
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
