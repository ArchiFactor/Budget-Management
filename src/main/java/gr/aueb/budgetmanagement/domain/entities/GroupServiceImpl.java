package gr.aueb.budgetmanagement.domain.entities;

import io.quarkus.arc.Unremovable;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;

@Unremovable
@Singleton
public class GroupServiceImpl implements GroupService {
    @Inject
    private EntityManager entityManager;

    @Override
    public Group getGroupById(Long id) {
        return entityManager.find(Group.class, id);
    }

}
