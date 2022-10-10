package noctem.storeService.kafkaTest;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_id")
    private Long id;
    private String name;
    private Integer number;

    @Builder
    public Test(String name, Integer number) {
        this.name = name;
        this.number = number;
    }
}
