package serwisAudio.model;

public enum PartType {
    TRANSISTOR("tranzystor"),
    DIODE("dioda"),
    RES("rezystor"),
    CAP("kondensator"),
    COIL("cewka"),
    FUSE("bezpiecznik"),
    BOARD("płyta - moduł"),
    SCREEN("wyświetlacz"),
    CONN("złącze"),
    POT("potencjometr"),
    OTHER("inny"),
    TRANSFORMER("transformator");

    private String partTypeName;

    PartType(String partTypeName) {
        this.partTypeName = partTypeName;
    }

    public String getPartType() {
        return partTypeName;
    }
}
