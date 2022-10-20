package noctem.storeService.store.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

/***
 * Boolean 타입 필드 true : 해당 조건에 만족하는 데이터만 리턴, false: 해당 조건은 무시.
 */
@Data
@NoArgsConstructor
public class SearchStoreFilterReqDto {
    private Boolean open;
    private Boolean parking;
    private Boolean ecoStore;
    private Boolean driveThrough;
    private Integer offset;

    public SearchStoreFilterReqDto setDefault() {
        this.offset = offset == null ? 0 : offset;
        this.open = open == null ? false : open;
        this.parking = parking == null ? false : parking;
        this.ecoStore = ecoStore == null ? false : ecoStore;
        this.driveThrough = driveThrough == null ? false : driveThrough;
        return this;
    }
}
