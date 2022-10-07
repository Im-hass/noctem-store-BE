package noctem.storeService;

import lombok.RequiredArgsConstructor;
import noctem.storeService.domain.store.entity.SoldOutMenu;
import noctem.storeService.domain.store.entity.Store;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class StoreInit {
    private final InitService initService;
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAuto;

    @PostConstruct
    public void init() {
        if (ddlAuto.equals("create")) {
            initService.storeAndSoldOutMenuInit();
        }
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager entityManager;

        public void storeAndSoldOutMenuInit() {
            // 1
            entityManager.persist(
                    Store.builder()
                            .name("본점")
                            .managerId(1L)
                            .openDate(new Timestamp(System.currentTimeMillis()))
                            .staffIdList(Arrays.asList(2L, 3L, 4L, 5L))
                            .mainImg("https://www.shinsegaegroupnewsroom.com/wp-content/uploads/2022/06/%EC%8B%A0%EC%84%B8%EA%B3%84%EC%95%84%EC%9D%B4%EC%95%A4%EC%94%A8_%EB%B3%B8%EB%AC%B8-2.png")
                            .imgList(Arrays.asList("https://www.shinsegaegroupnewsroom.com/wp-content/uploads/2022/06/%EC%8B%A0%EC%84%B8%EA%B3%84%EC%95%84%EC%9D%B4%EC%95%A4%EC%94%A8_%EB%B3%B8%EB%AC%B8-2.png"))
                            .address("부산 해운대구 APEC로 17, 4층")
                            .wayToCome("센텀리더스마크 4층 스파로스 아카데미 안")
                            .businessOpenHours("09:00")
                            .businessCloseHours("18:00")
                            .isParking(true)
                            .isEcoStore(true)
                            .isDriveThrough(false)
                            .contactNumber("051-0000-0000")
                            .latitude(35.16575992548444)
                            .longitude(129.13241930658475)
                            .build()
            );

            // 2
            Store store2 = Store.builder()
                    .name("센텀시티역점")
                    .managerId(1L)
                    .openDate(new Timestamp(System.currentTimeMillis()))
                    .staffIdList(Arrays.asList(2L, 3L, 4L))
                    .mainImg("https://cdn.pixabay.com/photo/2017/10/27/09/38/halloween-2893710_960_720.jpg")
                    .imgList(Arrays.asList("https://cdn.pixabay.com/photo/2017/10/27/09/38/halloween-2893710_960_720.jpg",
                            "https://cdnb.artstation.com/p/assets/images/images/023/740/591/large/jinsol-choi-4.jpg?1580206994",
                            "https://cdn.pixabay.com/photo/2017/10/27/09/38/halloween-2893710_960_720.jpg",
                            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQrruHEMFu5Xc4m9PnvmAt-A_DecFgOzBRNnw&usqp=CAU"))
                    .address("부산 해운대구 센텀동로 9")
                    .wayToCome("센텀시티역 2번 출구 앞")
                    .businessOpenHours("10:00")
                    .businessCloseHours("18:00")
                    .isParking(false)
                    .isEcoStore(true)
                    .isDriveThrough(true)
                    .contactNumber("051-0000-0000")
                    .latitude(35.16993539960263)
                    .longitude(129.1324954391096)
                    .build();

            SoldOutMenu soldOutMenu1 = SoldOutMenu.builder()
                    .menuId(1L)
                    .build()
                    .linkToStore(store2);

            SoldOutMenu soldOutMenu2 = SoldOutMenu.builder()
                    .menuId(2L)
                    .build()
                    .linkToStore(store2);
            entityManager.persist(soldOutMenu1);
            entityManager.persist(soldOutMenu2);

            // 3
            entityManager.persist(
                    Store.builder()
                            .name("센텀드림월드점")
                            .managerId(1L)
                            .openDate(new Timestamp(System.currentTimeMillis()))
                            .staffIdList(Arrays.asList(3L, 4L, 5L))
                            .mainImg("https://cdn.pixabay.com/photo/2017/10/27/09/38/halloween-2893710_960_720.jpg")
                            .imgList(Arrays.asList("https://cdn.pixabay.com/photo/2017/10/27/09/38/halloween-2893710_960_720.jpg",
                                    "https://static.xlgames.com/archeage/archewiki/2022/0421_update/52908_shot_01.jpg",
                                    "https://cdna.artstation.com/p/assets/images/images/008/509/812/large/kudzu-tea-.jpg?1513215699"))
                            .address("부산 해운대구 센텀2로 25")
                            .wayToCome("부산지하철 2호선 센텀시티역, 11번 출구에서 도보 2분")
                            .businessOpenHours("8:00")
                            .businessCloseHours("14:00")
                            .isParking(false)
                            .isEcoStore(false)
                            .isDriveThrough(true)
                            .contactNumber("051-0000-0000")
                            .latitude(35.16704007051328)
                            .longitude(129.13277373161017)
                            .build()
            );

            // 4
            entityManager.persist(
                    Store.builder()
                            .name("센텀로점")
                            .managerId(1L)
                            .openDate(new Timestamp(System.currentTimeMillis()))
                            .staffIdList(Arrays.asList(3L, 4L, 5L))
                            .mainImg("https://cdn.pixabay.com/photo/2017/10/27/09/38/halloween-2893710_960_720.jpg")
                            .imgList(Arrays.asList("https://cdn.pixabay.com/photo/2017/10/27/09/38/halloween-2893710_960_720.jpg"))
                            .address("부산 해운대구 센텀2로 10")
                            .wayToCome("센텀시티역 11번 출구에서 50m")
                            .businessOpenHours("6:00")
                            .businessCloseHours("12:00")
                            .isParking(false)
                            .isEcoStore(false)
                            .isDriveThrough(false)
                            .contactNumber("051-0000-0000")
                            .latitude(35.167924203133474)
                            .longitude(129.13156497092996)
                            .build()
            );

            // 5
            entityManager.persist(
                    Store.builder()
                            .name("센텀신세계F1점")
                            .managerId(1L)
                            .openDate(new Timestamp(System.currentTimeMillis()))
                            .staffIdList(Arrays.asList(3L, 4L, 5L))
                            .mainImg("https://cdn.pixabay.com/photo/2017/10/27/09/38/halloween-2893710_960_720.jpg")
                            .imgList(Arrays.asList("https://cdn.pixabay.com/photo/2017/10/27/09/38/halloween-2893710_960_720.jpg"))
                            .address("부산 해운대구 센텀남대로 35")
                            .wayToCome("센텀시티역 12번 출구에서 60m")
                            .businessOpenHours("12:00")
                            .businessCloseHours("20:00")
                            .isParking(true)
                            .isEcoStore(true)
                            .isDriveThrough(false)
                            .contactNumber("051-0000-0000")
                            .latitude(35.16865314749039)
                            .longitude(129.12905990871567)
                            .build()
            );

            // 6
            entityManager.persist(
                    Store.builder()
                            .name("벡스코점")
                            .managerId(1L)
                            .openDate(new Timestamp(System.currentTimeMillis()))
                            .staffIdList(Arrays.asList(1L, 4L, 5L))
                            .mainImg("https://cdn.pixabay.com/photo/2017/10/27/09/38/halloween-2893710_960_720.jpg")
                            .imgList(Arrays.asList("https://cdn.pixabay.com/photo/2017/10/27/09/38/halloween-2893710_960_720.jpg"))
                            .address("부산 해운대구 APEC로 55 벡스코")
                            .wayToCome("벡스코역 9번 출구에서 30m")
                            .businessOpenHours("9:00")
                            .businessCloseHours("18:00")
                            .isParking(true)
                            .isEcoStore(false)
                            .isDriveThrough(true)
                            .contactNumber("051-0000-0000")
                            .latitude(35.168849613520585)
                            .longitude(129.13543014930673)
                            .build()
            );

            // 7
            entityManager.persist(
                    Store.builder()
                            .name("센텀KNN점")
                            .managerId(1L)
                            .openDate(new Timestamp(System.currentTimeMillis()))
                            .staffIdList(Arrays.asList(1L, 3L, 5L))
                            .mainImg("https://cdn.pixabay.com/photo/2017/10/27/09/38/halloween-2893710_960_720.jpg")
                            .imgList(Arrays.asList("https://cdn.pixabay.com/photo/2017/10/27/09/38/halloween-2893710_960_720.jpg",
                                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQrruHEMFu5Xc4m9PnvmAt-A_DecFgOzBRNnw&usqp=CAU"))
                            .address("부산 해운대구 센텀서로 30")
                            .wayToCome("센텀시티역 6번 출구 도보 500m KNN타워 1층")
                            .businessOpenHours("9:00")
                            .businessCloseHours("18:00")
                            .isParking(false)
                            .isEcoStore(false)
                            .isDriveThrough(false)
                            .contactNumber("051-0000-0000")
                            .latitude(35.17186308252351)
                            .longitude(129.1285701904376)
                            .build()
            );
        }
    }
}
