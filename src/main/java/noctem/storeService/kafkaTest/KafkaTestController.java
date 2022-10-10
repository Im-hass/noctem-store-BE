package noctem.storeService.kafkaTest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import noctem.storeService.store.domain.entity.SoldOutMenu;
import noctem.storeService.store.domain.repository.SoldOutMenuRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("${global.api.base-path}/store/test")
@RequiredArgsConstructor
public class KafkaTestController {
    private final SoldOutMenuRepository soldOutMenuRepository;
    private final KafkaProducer kafkaProducer;

    @PostMapping("")
    public Boolean test() {
        soldOutMenuRepository.save(SoldOutMenu.builder().menuId(1L).build());
        kafkaProducer.sendMessage("응~ 메뉴 저장했어~ ㅋ");
        return true;
    }

    @PostMapping("/db")
    public Boolean dbTest() {
        kafkaProducer.addSoldOutMenu();
        soldOutMenuRepository.save(SoldOutMenu.builder().menuId(101L).build());
        log.info("ㅋㅋ");
        return true;
    }

    @PostMapping("/db-test")
    public Boolean addtest() {
        kafkaProducer.addTest();
        log.info("ㅋㅋ");
        return true;
    }
}
