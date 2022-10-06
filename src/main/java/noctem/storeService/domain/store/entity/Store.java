package noctem.storeService.domain.store.entity;

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

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;
    private String name;
    private Long managerId; // 점장
    private Long supervisorId; // 본사의 매장 관리자, 감독자
    private Timestamp openDate; // 오픈 날짜
    @ElementCollection
    private List<Long> staffIdList = new ArrayList<>();
    private String mainImg;
    @ElementCollection
    private List<String> imgList = new ArrayList<>();
    private String address;
    private String wayToCome; // 오시는 길
    private String businessHours; // 영업시간
    private Boolean isParking;
    private Boolean isEcoStore;
    private Boolean isDriveThrough;
    private String contactNumber; // 매장 연락처
    private Double latitude; // 위도
    private Double longitude; // 경도

    @JsonIgnore
    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<OrderRequest> orderRequestList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<SoldOutMenu> soldOutMenuList = new ArrayList<>();

    @Builder
    public Store(String name, Long managerId, Long supervisorId, Timestamp openDate, List<Long> staffIdList, String mainImg, List<String> imgList, String address, String wayToCome, String businessHours, Boolean isParking, Boolean isEcoStore, Boolean isDriveThrough, String contactNumber, Double latitude, Double longitude) {
        this.name = name;
        this.managerId = managerId;
        this.supervisorId = supervisorId;
        this.openDate = openDate;
        this.staffIdList = staffIdList;
        this.mainImg = mainImg;
        this.imgList = imgList;
        this.address = address;
        this.wayToCome = wayToCome;
        this.businessHours = businessHours;
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
}
