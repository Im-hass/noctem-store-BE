package noctem.storeService.store.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import noctem.storeService.store.domain.repository.StoreAccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class StoreAccountServiceImpl implements StoreAccountService {
    private final StoreAccountRepository storeAccountRepository;

    @Override
    public void updateLastAccessTime(Long storeAccountId) {
        storeAccountRepository.findById(storeAccountId).get().updateLastAccessTime();
    }
}
