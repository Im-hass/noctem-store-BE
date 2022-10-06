package noctem.storeService.domain.store.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import noctem.storeService.domain.store.entity.Store;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchStoreDto {
    private Store store;
    private Integer distance; // 단위 미터(m)
}
