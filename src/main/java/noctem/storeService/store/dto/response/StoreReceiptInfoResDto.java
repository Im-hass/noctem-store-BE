package noctem.storeService.store.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StoreReceiptInfoResDto {
    private String storeName;
    private String storeAddress;
    private String storeContactNumber;
}
