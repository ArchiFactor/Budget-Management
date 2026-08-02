package gr.aueb.budgetmanagement.domain.entities;

import java.util.List;

public interface GroupPiggyBankService {
    List<GroupPiggyBank> getGroupPiggyBanksByGroupId(Long groupId);
}
