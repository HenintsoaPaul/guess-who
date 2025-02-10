package itu.crypto.repository.transaction.fund;

import itu.crypto.entity.fund.PendingState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PendingStateRepository extends JpaRepository<PendingState, Integer> {
}
