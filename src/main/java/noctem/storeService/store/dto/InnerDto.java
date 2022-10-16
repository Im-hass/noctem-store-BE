package noctem.storeService.store.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import noctem.storeService.purchase.domain.entity.PurchaseMenu;
import noctem.storeService.purchase.domain.entity.PurchasePersonalOption;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InnerDto {
    @Data
    @NoArgsConstructor
    public static class MenuResDto {
        private Integer index;
        private Long sizeId;
        private String menuName; // temp테이블의 name
        // private String menuImg; // sizeId를 이용해서 메뉴서비스에 api보내기
        private Integer qty;
        private List<PersonalOptionResDto> optionList; // ICED, Tall, 매장컵, 얼음 적게 등

        public MenuResDto(PurchaseMenu purchaseMenu) {
            this.sizeId = purchaseMenu.getSizeId();
            this.menuName = purchaseMenu.getMenuFullName();
            this.qty = purchaseMenu.getQty();
            this.optionList = new ArrayList<>();
            this.optionList.add(new PersonalOptionResDto(purchaseMenu.getTemperature()));
            this.optionList.add(new PersonalOptionResDto(purchaseMenu.getSize()));
            this.optionList.addAll(purchaseMenu.getPurchasePersonalOptionList().stream().map(PersonalOptionResDto::new).collect(Collectors.toList()));
            optionList.forEach(e -> e.setIndex(optionList.indexOf(e)));
        }
    }

    @Data
    @NoArgsConstructor
    public static class PersonalOptionResDto {
        private Integer index;
        private String personalOptionNameAndAmount;

        public PersonalOptionResDto(PurchasePersonalOption personalOption) {
            this.personalOptionNameAndAmount = String.format("%s %s", personalOption.getPersonalOptionName(), personalOption.getAmount().getValue());
        }

        public PersonalOptionResDto(String personalOptionNameAndAmount) {
            this.personalOptionNameAndAmount = personalOptionNameAndAmount;
        }
    }
}
