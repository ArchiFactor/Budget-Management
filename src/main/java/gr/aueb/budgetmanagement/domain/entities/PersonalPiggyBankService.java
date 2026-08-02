package gr.aueb.budgetmanagement.domain.entities;

import java.util.List;

public interface PersonalPiggyBankService {
    List<PersonalPiggyBank> getPersonalPiggyBanksByUserId(Long userId);
}
