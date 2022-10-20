package noctem.storeService.store.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

/***
 * Boolean 타입 필드 true : 해당 조건에 만족하는 데이터만 리턴, false: 해당 조건은 무시.
 */
@Data
@NoArgsConstructor
public class SearchStoreFilterReqDto {
    private Boolean isOpen;
    private Boolean isParking;
    private Boolean isEcoStore;
    private Boolean isDriveThrough;
    private Integer offset;

    public SearchStoreFilterReqDto setDefault() {
        this.offset = offset == null ? 0 : offset;
        this.isOpen = isOpen == null ? false : isOpen;
        this.isParking = isParking == null ? false : isParking;
        this.isEcoStore = isEcoStore == null ? false : isEcoStore;
        this.isDriveThrough = isDriveThrough == null ? false : isDriveThrough;
        return this;
    }
}
