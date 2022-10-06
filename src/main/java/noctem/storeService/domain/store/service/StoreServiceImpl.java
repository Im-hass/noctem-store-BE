package noctem.storeService.domain.store.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import noctem.storeService.domain.store.dto.response.SearchStoreResDto;
import noctem.storeService.domain.store.repository.StoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;

    @Override
    public List<SearchStoreResDto> searchStore(Double latitude, Double longitude) {
        return storeRepository.findDtoByNativeProjections(latitude, longitude)
                .stream().map(e -> new SearchStoreResDto(e)).collect(Collectors.toList());
    }
}
