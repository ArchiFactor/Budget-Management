package gr.aueb.budgetmanagement.domain.entities;

import gr.aueb.budgetmanagement.domain.enums.ExpenseCategory;
import gr.aueb.budgetmanagement.domain.exceptions.InvalidDomainArgumentException;
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
@DiscriminatorValue("PERSONAL")
public class PersonalPiggyBank extends PiggyBank {
    @Transient
	private User user;
	@Column(name = "user_id")
	private Long userId;

    protected PersonalPiggyBank() {

    }

    private PersonalPiggyBank(String name, Money targetAmount, ExpenseCategory category, User user) {
        super(name, targetAmount, category);
        this.user = user;
		this.userId = user != null ? user.getId() : null;
        user.addPiggyBank(this);
    }

    public static PersonalPiggyBank create(String name, Money targetAmount, ExpenseCategory category, User user) {
        if (user == null) {
            throw new InvalidDomainArgumentException("User cannot be null");
        }

        return new PersonalPiggyBank(name, targetAmount, category, user);
    }

    public User getUser() {
		if (user == null && userId != null && ServiceFactory.isContainerAvailable()) {
			UserService userService = ServiceFactory.getUserService();
			user = userService.getUserById(userId);
		}
		return user;
	}

    @Override
    public boolean isAuthorizedUser(User user) {
        return this.getUser().equals(user);
    }

    @Override
    public boolean canBeDissolvedBy(User user) {
        return this.getUser().equals(user);
    }

	@PrePersist
	@PreUpdate
	private void syncUnmappedForeignKeys() {
		if (this.user != null) {
			this.userId = this.user.getId();
		}
	}
}
