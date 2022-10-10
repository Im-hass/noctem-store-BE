package noctem.storeService.store.dto.response;

import lombok.Data;

@Data
public class SoldOutMenuResDto {
    private Integer index;
    private Long soldOutMenuId;

    public SoldOutMenuResDto(Long soldOutMenuId) {
        this.soldOutMenuId = soldOutMenuId;
    }
}
