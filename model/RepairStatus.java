package serwisAudio.model;

public enum RepairStatus {
    REGISTERED("zgłoszenie zarejestrowane"),
    DELIVERED("sprzęt w serwisie, oczekuje na diagnozę"),
    DIAGNOSED("jest diagnoza, oczekiwanie na akceptację kosztów"),
    PARTS_ORDERED("oczekiwanie na części"),
    DONE("gotowe do odbioru");

    private String repairStatusName;

    RepairStatus(String repairStatusName) {
        this.repairStatusName = repairStatusName;
    }

    public String getRepairStatusName() {
        return repairStatusName;
    }
}

