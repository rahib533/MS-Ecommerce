package az.rahibjafar.msidentity.dto;

import java.util.UUID;

public class RoleDto {
    private UUID id;
    private String name;

    public RoleDto(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public UUID getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
