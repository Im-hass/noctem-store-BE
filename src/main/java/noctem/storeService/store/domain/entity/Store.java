package noctem.storeService.store.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import noctem.storeService.global.common.BaseEntity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/***
 * managerId: 점장id
 * supervisorId: 본사의 매장 관리 담당자
 * openDate: 매장 오픈 날짜
 * wayToCome: 오시는 길
 * businessOpenHours: 영업 시작시간
 * businessCloseHours: 영업 마감시간
 * isOpen: 현재 영업상태 true: 영업중, false: 마감됨
 * contactNumber: 매장 연락처
 * latitude: 매장 위도
 * longitude: 매장 경도
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;
    private String name;
    private Long managerId;
    private Long supervisorId;
    private Timestamp openDate;
    @ElementCollection
    private List<Long> staffIdList = new ArrayList<>();
    private String mainImg;
    @ElementCollection
    private List<String> imgList = new ArrayList<>();
    private String address;
    private String wayToCome;
    private String businessOpenHours;
    private String businessCloseHours;
    private Boolean isParking;
    private Boolean isEcoStore;
    private Boolean isDriveThrough;
    private String contactNumber;
    private Double latitude;
    private Double longitude;

    @JsonIgnore
    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<OrderRequest> orderRequestList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SoldOutMenu> soldOutMenuList = new ArrayList<>();

    @Builder
    public Store(String name, Long managerId, Long supervisorId, Timestamp openDate, List<Long> staffIdList, String mainImg, List<String> imgList, String address, String wayToCome, String businessOpenHours, String businessCloseHours, Boolean isParking, Boolean isEcoStore, Boolean isDriveThrough, String contactNumber, Double latitude, Double longitude) {
        this.name = name;
        this.managerId = managerId;
        this.supervisorId = supervisorId;
        this.openDate = openDate;
        this.staffIdList = staffIdList;
        this.mainImg = mainImg;
        this.imgList = imgList;
        this.address = address;
        this.wayToCome = wayToCome;
        this.businessOpenHours = businessOpenHours;
        this.businessCloseHours = businessCloseHours;
        this.isParking = isParking;
        this.isEcoStore = isEcoStore;
        this.isDriveThrough = isDriveThrough;
        this.contactNumber = contactNumber;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Store linkToOrderRequest(OrderRequest orderRequest) {
        this.orderRequestList.add(orderRequest);
        return this;
    }

    public Store linkToSoldOutMenu(SoldOutMenu soldOutMenu) {
        this.soldOutMenuList.add(soldOutMenu);
        return this;
    }

    public Store delSoldOutMenu(SoldOutMenu soldOutMenu) {
        this.soldOutMenuList.remove(soldOutMenu);
        return this;
    }
}
