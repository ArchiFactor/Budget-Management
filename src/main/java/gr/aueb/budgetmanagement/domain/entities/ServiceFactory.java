package gr.aueb.budgetmanagement.domain.entities;

import jakarta.enterprise.inject.spi.CDI;

import gr.aueb.budgetmanagement.domain.entities.UserService;
import gr.aueb.budgetmanagement.domain.entities.GroupService;
import gr.aueb.budgetmanagement.domain.entities.GroupPiggyBankService;
import gr.aueb.budgetmanagement.domain.entities.PersonalPiggyBankService;

public class ServiceFactory {

    public static boolean isContainerAvailable() {
        try {
            CDI.current();
            return true;
        } catch (IllegalStateException e) {
            return false;
        }
    }

    public static UserService getUserService() {
        return CDI.current().select(UserService.class).get();
    }

    public static GroupService getGroupService() {
        return CDI.current().select(GroupService.class).get();
    }

    public static GroupPiggyBankService getGroupPiggyBankService() {
        return CDI.current().select(GroupPiggyBankService.class).get();
    }

    public static PersonalPiggyBankService getPersonalPiggyBankService() {
        return CDI.current().select(PersonalPiggyBankService.class).get();
    }

}
