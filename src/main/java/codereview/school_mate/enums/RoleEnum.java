package codereview.school_mate.enums;

public enum RoleEnum {

    ADMIN("ADMIN"), STUDENT("STUDENT"), TEACHER("TEACHER"), PARENT("PARENT");

    private final String name;

    RoleEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
