package noctem.storeService.store.domain.repository;

import lombok.RequiredArgsConstructor;
import noctem.storeService.store.domain.entity.SoldOutMenu;
import noctem.storeService.store.domain.entity.Store;
import noctem.storeService.store.domain.entity.StoreAccount;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.util.Arrays;

@RestController
@RequiredArgsConstructor
public class StoreInitController {
    private final InitService initService;
    private int count = 0;

    @PostMapping("${global.api.base-path}/init")
    public void storeCreate() {
        if (count == 0) {
            initService.storeAndSoldOutMenuInit();
            count++;
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
                    .mainImg("https://user-images.githubusercontent.com/68591616/196831504-260c0068-c67d-435c-9e9e-b5e88087fecf.jpg")
                    .imgList(Arrays.asList("https://user-images.githubusercontent.com/68591616/196831504-260c0068-c67d-435c-9e9e-b5e88087fecf.jpg"))
                    .address("부산 해운대구 APEC로 17, 4층")
                    .wayToCome("센텀리더스마크 4층 스파로스 아카데미 안")
                    .businessOpenHours("00:00")
                    .businessCloseHours("23:59")
                    .isParking(true)
                    .isEcoStore(true)
                    .isDriveThrough(false)
                    .contactNumber("051-0000-0000")
                    .latitude(35.16575992548444)
                    .longitude(129.13241930658475)
                    .build();

            StoreAccount storeAccount1 = StoreAccount.builder()
                    .loginId("noctem1")
                    .password(passwordEncoder.encode("noctem"))
                    .build()
                    .linkToStoreInfoFromOwner(store1);


            // 2
            Store store2 = Store.builder()
                    .name("센텀시티역점")
                    .managerId(3L)
                    .openDate(new Timestamp(System.currentTimeMillis()))
                    .mainImg("https://user-images.githubusercontent.com/68591616/196831411-200bb204-c25b-4069-b92f-2b02919b53bb.jpg")
                    .imgList(Arrays.asList("https://user-images.githubusercontent.com/68591616/196831411-200bb204-c25b-4069-b92f-2b02919b53bb.jpg"))
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
                    .loginId("noctem2")
                    .password(passwordEncoder.encode("noctem"))
                    .build()
                    .linkToStoreInfoFromOwner(store2);

            // 3
            Store store3 = Store.builder()
                    .name("센텀드림월드점")
                    .managerId(3L)
                    .openDate(new Timestamp(System.currentTimeMillis()))
                    .mainImg("https://user-images.githubusercontent.com/68591616/196831439-5a99bb54-f72c-458c-8292-9b11f5de8a94.jpg")
                    .imgList(Arrays.asList("https://user-images.githubusercontent.com/68591616/196831439-5a99bb54-f72c-458c-8292-9b11f5de8a94.jpg"))
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
                    .loginId("noctem3")
                    .password(passwordEncoder.encode("noctem"))
                    .build()
                    .linkToStoreInfoFromOwner(store3);

            // 4
            Store store4 = Store.builder()
                    .name("센텀로점")
                    .managerId(3L)
                    .openDate(new Timestamp(System.currentTimeMillis()))
                    .mainImg("https://user-images.githubusercontent.com/68591616/196831466-794f1861-f60a-4330-bf6b-4f0312a11171.jpg")
                    .imgList(Arrays.asList("https://user-images.githubusercontent.com/68591616/196831466-794f1861-f60a-4330-bf6b-4f0312a11171.jpg"))
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
                    .loginId("noctem4")
                    .password(passwordEncoder.encode("noctem"))
                    .build()
                    .linkToStoreInfoFromOwner(store4);

            // 5
            Store store5 = Store.builder()
                    .name("센텀신세계F1점")
                    .managerId(3L)
                    .openDate(new Timestamp(System.currentTimeMillis()))
                    .mainImg("https://user-images.githubusercontent.com/68591616/196831488-e060761c-cd1d-4f7a-ab4b-0b1cf0f04847.jpg")
                    .imgList(Arrays.asList("https://user-images.githubusercontent.com/68591616/196831488-e060761c-cd1d-4f7a-ab4b-0b1cf0f04847.jpg"))
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
                    .loginId("noctem5")
                    .password(passwordEncoder.encode("noctem"))
                    .build()
                    .linkToStoreInfoFromOwner(store5);

            // 6
            Store store6 = Store.builder()
                    .name("벡스코점")
                    .managerId(3L)
                    .openDate(new Timestamp(System.currentTimeMillis()))
                    .mainImg("https://user-images.githubusercontent.com/68591616/196831523-8bc68cab-02f6-4672-ad31-0787132e5251.jpg")
                    .imgList(Arrays.asList("https://user-images.githubusercontent.com/68591616/196831523-8bc68cab-02f6-4672-ad31-0787132e5251.jpg"))
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
                    .loginId("noctem6")
                    .password(passwordEncoder.encode("noctem"))
                    .build()
                    .linkToStoreInfoFromOwner(store6);

            // 7
            Store store7 = Store.builder()
                    .name("센텀KNN점")
                    .managerId(3L)
                    .openDate(new Timestamp(System.currentTimeMillis()))
                    .mainImg("https://user-images.githubusercontent.com/68591616/196831548-e0469b0b-c77c-40b3-bc75-780dbf5117a4.jpg")
                    .imgList(Arrays.asList("https://user-images.githubusercontent.com/68591616/196831548-e0469b0b-c77c-40b3-bc75-780dbf5117a4.jpg"))
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
                    .loginId("noctem7")
                    .password(passwordEncoder.encode("noctem"))
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
