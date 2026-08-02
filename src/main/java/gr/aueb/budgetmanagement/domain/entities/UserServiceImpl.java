package gr.aueb.budgetmanagement.domain.entities;

import io.quarkus.arc.Unremovable;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;

@Unremovable
@Singleton
public class UserServiceImpl implements UserService {
    @Inject
    private EntityManager entityManager;

    @Override
    public User getUserById(Long id) {
        return entityManager.find(User.class, id);
    }

}
