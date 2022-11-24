package com.whatweeat.wwe.menu.batch.chunk;

import com.whatweeat.wwe.menu.domain.Menu;
import com.whatweeat.wwe.menu.domain.MenuData;
import com.whatweeat.wwe.menu.domain.MenuMiniGameData;
import com.whatweeat.wwe.minigame.domain.form.ExpenseName;
import com.whatweeat.wwe.minigame.domain.form.FlavorName;
import com.whatweeat.wwe.minigame.domain.form.NationName;
import org.springframework.batch.item.ItemProcessor;

import java.util.HashSet;
import java.util.Set;

public class FileItemProcessor implements ItemProcessor<MenuData, Menu> {
    @Override
    public Menu process(MenuData item) throws Exception {
        ExpenseName expenseName = getExpenseName(item.getExpense());
        Set<FlavorName> flavorNames = getFlavorNames(item.getFlavors());
        flavorNames.addAll(getFlavorNames(item.getExcludes()));
        Set<NationName> nationNames = getNationNames(item.getNations());
        MenuMiniGameData miniGameData = MenuMiniGameData.of(getBoolean(item.getIsRice()), getBoolean(item.getIsNoodle()), getBoolean(item.getIsSoup()),
                getBoolean(item.getIsHealthy()), getBoolean(item.getIsAlcohol()), getBoolean(item.getIsAlcohol()),
                expenseName, flavorNames, nationNames);
        return new Menu(item.getMenuName(), item.getMenuImage(), miniGameData);
    }

    Boolean getBoolean(String isSome) {
        if(isSome==null||isSome.isEmpty()) return null;
        if(isSome.equals("TRUE")) return true;
        else return false;
    }

    Set<NationName> getNationNames(String names) {
        HashSet<NationName> result = new HashSet<>();
        if(names==null||names.isEmpty()) return result;
        for (String name : names.split(", ")) {
            result.add(NationName.lookup(name));
        }
        return result;
    }

    Set<FlavorName> getFlavorNames(String names) {
        HashSet<FlavorName> result = new HashSet<>();
        if(names==null||names.isEmpty()) return result;
        for (String name : names.split(", ")) {
            result.add(FlavorName.lookup(name));
        }
        return result;
    }

    private ExpenseName getExpenseName(String expense) {
        if(expense==null||expense.isEmpty()) return ExpenseName.CHEAP;
        else if(expense.equals(ExpenseName.EXPENSIVE1.name())) return ExpenseName.EXPENSIVE1;
        else return ExpenseName.EXPENSIVE2;
    }
}
