package gr.aueb.budgetmanagement.domain.entities;

import gr.aueb.budgetmanagement.domain.enums.ExpenseCategory;
import gr.aueb.budgetmanagement.domain.exceptions.InvalidDomainArgumentException;
import gr.aueb.budgetmanagement.domain.exceptions.ForbiddenOperationDomainException;
import gr.aueb.budgetmanagement.domain.valueobjects.Money;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Transient;

@Entity
@DiscriminatorValue("GROUP")
public class GroupPiggyBank extends PiggyBank {
    @Transient
	private Group group;
	@Column(name = "group_id")
	private Long groupId;

    protected GroupPiggyBank() {

    }

    private GroupPiggyBank(String name, Money targetAmount, ExpenseCategory category, Group group) {
        super(name, targetAmount, category);
        this.group = group;
		this.groupId = group != null ? group.getId() : null;
        group.addPiggyBank(this);
    }

    public static GroupPiggyBank create(
        String name, 
        Money targetAmount, 
        ExpenseCategory category, 
        Group group,
        User user
    ) {
        if (group == null) {
            throw new InvalidDomainArgumentException("Group cannot be null");
        }

        if (!group.isAdmin(user)) {
            throw new ForbiddenOperationDomainException("Only group admin can create group piggy banks");
        }

        return new GroupPiggyBank(name, targetAmount, category, group);
    }

    public Group getGroup() {
		if (group == null && groupId != null && ServiceFactory.isContainerAvailable()) {
			GroupService groupService = ServiceFactory.getGroupService();
			group = groupService.getGroupById(groupId);
		}
		return group;
	}

    @Override
    public boolean isAuthorizedUser(User user) {
        return this.getGroup().getMembers().contains(user);
    }

    @Override
    public boolean canBeDissolvedBy(User user) {
        return this.getGroup().getAdmin().equals(user);
    }

	@PrePersist
	@PreUpdate
	private void syncUnmappedForeignKeys() {
		if (this.group != null) {
			this.groupId = this.group.getId();
		}
	}
}
