package serwisAudio.model;

import org.springframework.beans.factory.annotation.Autowired;

public enum PartType {
    TRANSISTOR(1, "tranzystor"),
    DIODE(2, "dioda"),
    RES(3, "rezystor"),
    CAP(4, "kondensator"),
    COIL(5, "cewka"),
    FUSE(6, "bezpiecznik"),
    BOARD(7, "płyta - moduł"),
    SCREEN(8, "wyświetlacz"),
    CONN(9, "złącze"),
    POT(10,"potencjometr"),
    OTHER(11,"inny"),
    TRANSFORMER(12, "transformator");

    private int partTypeId;
    private String partTypeName;

    PartType(int partTypeId, String partTypeName) {
        this.partTypeId = partTypeId;
        this.partTypeName = partTypeName;
    }

    public int getPartTypeId() {
        return partTypeId;
    }

    public void setPartTypeId(int partTypeId) {
        this.partTypeId = partTypeId;
    }

    public String getPartTypeName() {
        return partTypeName;
    }

    public void setPartTypeName(String partTypeName) {
        this.partTypeName = partTypeName;
    }
}
