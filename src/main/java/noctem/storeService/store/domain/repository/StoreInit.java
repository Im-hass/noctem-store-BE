package noctem.storeService.store.domain.repository;

import lombok.RequiredArgsConstructor;
import noctem.storeService.store.domain.entity.SoldOutMenu;
import noctem.storeService.store.domain.entity.Store;
import noctem.storeService.store.domain.entity.StoreAccount;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Value("${spring.datasource-store.hbm2ddl.auto}")
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
        private final PasswordEncoder passwordEncoder;

        public void storeAndSoldOutMenuInit() {
            // 1
            Store store1 = Store.builder()
                    .name("본점")
                    .managerId(3L)
                    .openDate(new Timestamp(System.currentTimeMillis()))
                    .staffIdList(Arrays.asList(1L, 3L, 4L, 5L))
                    .mainImg("https://user-images.githubusercontent.com/68591616/195770575-89847853-179e-4fee-bc13-628f2950ee86.jpg")
                    .imgList(Arrays.asList("https://user-images.githubusercontent.com/68591616/195770575-89847853-179e-4fee-bc13-628f2950ee86.jpg"))
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
                    .build();

            StoreAccount storeAccount1 = StoreAccount.builder()
                    .loginId("spharos1")
                    .password(passwordEncoder.encode("spharos"))
                    .build()
                    .linkToStoreInfoFromOwner(store1);


            // 2
            Store store2 = Store.builder()
                    .name("센텀시티역점")
                    .managerId(3L)
                    .openDate(new Timestamp(System.currentTimeMillis()))
                    .mainImg("https://user-images.githubusercontent.com/68591616/195770567-1d2bc27a-0622-4d8b-98e2-5cc30d09014b.jpg")
                    .imgList(Arrays.asList("https://user-images.githubusercontent.com/68591616/195770567-1d2bc27a-0622-4d8b-98e2-5cc30d09014b.jpg"))
                    .address("부산 해운대구 센텀동로 9")
                    .wayToCome("센텀시티역 2번 출구 앞")
                    .businessOpenHours("01:00")
                    .businessCloseHours("09:00")
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

            StoreAccount storeAccount2 = StoreAccount.builder()
                    .loginId("spharos2")
                    .password(passwordEncoder.encode("spharos"))
                    .build()
                    .linkToStoreInfoFromOwner(store2);

            // 3
            Store store3 = Store.builder()
                    .name("센텀드림월드점")
                    .managerId(3L)
                    .openDate(new Timestamp(System.currentTimeMillis()))
                    .mainImg("https://user-images.githubusercontent.com/68591616/195770570-191ce19d-3469-4544-9359-7d9391c5bd87.jpg")
                    .imgList(Arrays.asList("https://user-images.githubusercontent.com/68591616/195770570-191ce19d-3469-4544-9359-7d9391c5bd87.jpg"))
                    .address("부산 해운대구 센텀2로 25")
                    .wayToCome("부산지하철 2호선 센텀시티역, 11번 출구에서 도보 2분")
                    .businessOpenHours("18:00")
                    .businessCloseHours("22:00")
                    .isParking(false)
                    .isEcoStore(false)
                    .isDriveThrough(true)
                    .contactNumber("051-0000-0000")
                    .latitude(35.16704007051328)
                    .longitude(129.13277373161017)
                    .build();
            StoreAccount storeAccount3 = StoreAccount.builder()
                    .loginId("spharos3")
                    .password(passwordEncoder.encode("spharos"))
                    .build()
                    .linkToStoreInfoFromOwner(store3);

            // 4
            Store store4 = Store.builder()
                    .name("센텀로점")
                    .managerId(3L)
                    .openDate(new Timestamp(System.currentTimeMillis()))
                    .mainImg("https://user-images.githubusercontent.com/68591616/195770576-827198a2-3b73-4780-9d60-92c26f9566fe.jpg")
                    .imgList(Arrays.asList("https://user-images.githubusercontent.com/68591616/195770576-827198a2-3b73-4780-9d60-92c26f9566fe.jpg"))
                    .address("부산 해운대구 센텀2로 10")
                    .wayToCome("센텀시티역 11번 출구에서 50m")
                    .businessOpenHours("18:00")
                    .businessCloseHours("23:59")
                    .isParking(false)
                    .isEcoStore(false)
                    .isDriveThrough(false)
                    .contactNumber("051-0000-0000")
                    .latitude(35.167924203133474)
                    .longitude(129.13156497092996)
                    .build();
            StoreAccount storeAccount4 = StoreAccount.builder()
                    .loginId("spharos4")
                    .password(passwordEncoder.encode("spharos"))
                    .build()
                    .linkToStoreInfoFromOwner(store4);

            // 5
            Store store5 = Store.builder()
                    .name("센텀신세계F1점")
                    .managerId(3L)
                    .openDate(new Timestamp(System.currentTimeMillis()))
                    .mainImg("https://user-images.githubusercontent.com/68591616/195770577-b5c9a986-e156-4d01-9a16-4e5194fe4ab4.jpg")
                    .imgList(Arrays.asList("https://user-images.githubusercontent.com/68591616/195770577-b5c9a986-e156-4d01-9a16-4e5194fe4ab4.jpg"))
                    .address("부산 해운대구 센텀남대로 35")
                    .wayToCome("센텀시티역 12번 출구에서 60m")
                    .businessOpenHours("00:00")
                    .businessCloseHours("09:00")
                    .isParking(true)
                    .isEcoStore(true)
                    .isDriveThrough(false)
                    .contactNumber("051-0000-0000")
                    .latitude(35.16865314749039)
                    .longitude(129.12905990871567)
                    .build();
            StoreAccount storeAccount5 = StoreAccount.builder()
                    .loginId("spharos5")
                    .password(passwordEncoder.encode("spharos"))
                    .build()
                    .linkToStoreInfoFromOwner(store5);

            // 6
            Store store6 = Store.builder()
                    .name("벡스코점")
                    .managerId(3L)
                    .openDate(new Timestamp(System.currentTimeMillis()))
                    .mainImg("https://user-images.githubusercontent.com/68591616/195770579-3665bb24-dc7a-4033-a533-513b606d737c.jpg")
                    .imgList(Arrays.asList("https://user-images.githubusercontent.com/68591616/195770579-3665bb24-dc7a-4033-a533-513b606d737c.jpg"))
                    .address("부산 해운대구 APEC로 55 벡스코")
                    .wayToCome("벡스코역 9번 출구에서 30m")
                    .businessOpenHours("01:00")
                    .businessCloseHours("02:00")
                    .isParking(true)
                    .isEcoStore(false)
                    .isDriveThrough(true)
                    .contactNumber("051-0000-0000")
                    .latitude(35.168849613520585)
                    .longitude(129.13543014930673)
                    .build();
            StoreAccount storeAccount6 = StoreAccount.builder()
                    .loginId("spharos6")
                    .password(passwordEncoder.encode("spharos"))
                    .build()
                    .linkToStoreInfoFromOwner(store6);

            // 7
            Store store7 = Store.builder()
                    .name("센텀KNN점")
                    .managerId(3L)
                    .openDate(new Timestamp(System.currentTimeMillis()))
                    .mainImg("https://user-images.githubusercontent.com/68591616/195770583-240185bd-938d-4eb7-b8b7-bdb0eb8415b5.jpg")
                    .imgList(Arrays.asList("https://user-images.githubusercontent.com/68591616/195770583-240185bd-938d-4eb7-b8b7-bdb0eb8415b5.jpg"))
                    .address("부산 해운대구 센텀서로 30")
                    .wayToCome("센텀시티역 6번 출구 도보 500m KNN타워 1층")
                    .businessOpenHours("18:00")
                    .businessCloseHours("23:00")
                    .isParking(false)
                    .isEcoStore(false)
                    .isDriveThrough(false)
                    .contactNumber("051-0000-0000")
                    .latitude(35.17186308252351)
                    .longitude(129.1285701904376)
                    .build();
            StoreAccount storeAccount7 = StoreAccount.builder()
                    .loginId("spharos7")
                    .password(passwordEncoder.encode("spharos"))
                    .build()
                    .linkToStoreInfoFromOwner(store7);

            entityManager.persist(storeAccount1);
            entityManager.persist(storeAccount2);
            entityManager.persist(storeAccount3);
            entityManager.persist(storeAccount4);
            entityManager.persist(storeAccount5);
            entityManager.persist(storeAccount6);
            entityManager.persist(storeAccount7);
        }
    }
}
