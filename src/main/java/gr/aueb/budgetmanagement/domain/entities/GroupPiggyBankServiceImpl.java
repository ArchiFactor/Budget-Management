package gr.aueb.budgetmanagement.domain.entities;

import io.quarkus.arc.Unremovable;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import java.util.List;

@Unremovable
@Singleton
public class GroupPiggyBankServiceImpl implements GroupPiggyBankService {
    @Inject
    private EntityManager entityManager;

    @Override
    public List<GroupPiggyBank> getGroupPiggyBanksByGroupId(Long groupId) {
        if (groupId == null) {
            return List.of();
        }
        return entityManager.createQuery("SELECT e FROM GroupPiggyBank e WHERE e.groupId = :groupId", GroupPiggyBank.class)
                .setParameter("groupId", groupId)
                .getResultList();
    }

}
