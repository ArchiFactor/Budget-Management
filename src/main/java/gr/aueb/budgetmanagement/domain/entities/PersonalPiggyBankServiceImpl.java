package gr.aueb.budgetmanagement.domain.entities;

import io.quarkus.arc.Unremovable;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import java.util.List;

@Unremovable
@Singleton
public class PersonalPiggyBankServiceImpl implements PersonalPiggyBankService {
    @Inject
    private EntityManager entityManager;

    @Override
    public List<PersonalPiggyBank> getPersonalPiggyBanksByUserId(Long userId) {
        if (userId == null) {
            return List.of();
        }
        return entityManager.createQuery("SELECT e FROM PersonalPiggyBank e WHERE e.userId = :userId", PersonalPiggyBank.class)
                .setParameter("userId", userId)
                .getResultList();
    }

}
