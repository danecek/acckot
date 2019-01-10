package acc.business.balance;

import acc.business.Facade;
import acc.model.AbstrGroup;
import acc.model.AnalAcc;
import acc.model.Transaction;
import acc.util.AccException;
import acc.util.Global;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class Balance {

    public static final Balance instance = new Balance();

    private Map<AbstrGroup, BalanceItem> bitems;
    private BalanceItem root;

    public List<BalanceItem> createBalance(Month month) throws AccException {
        bitems = new TreeMap<>();
        root = new BalanceItem();
        Facade.INSTANCE.getBalanceAccounts()
                .forEach(ba -> insertToTree(Optional.of(ba)));
        sumTransactions(month);
        root.sum();
        return root.appendItemsTo(new ArrayList<>());
    }

    private BalanceItem insertToTree(Optional<AbstrGroup> insertedItemGroup) {
        if (!insertedItemGroup.isPresent()) {
            return root;
        }
        AbstrGroup iGroup = insertedItemGroup.get();
        BalanceItem parentItem = insertToTree(Optional.ofNullable(iGroup.getParent()));
        Map<String, BalanceItem> siblings = parentItem.getChildren();
        BalanceItem newItem = siblings.get(iGroup.getNumber());
        if (newItem == null) {
            newItem = new BalanceItem(iGroup);
            bitems.put(iGroup, newItem);
            siblings.put(iGroup.getNumber(), newItem);
        }
        return newItem;
    }

    void add(AnalAcc acc, boolean inMonth, boolean isInit, long amount) {
        if (!acc.getBalanced()) {
            return;
        }
        BalanceItem item = bitems.get(acc);
        if (acc.isActive()) {
            if (isInit) {
                item.addInitAssets(amount);
                item.addFinalAssets(amount);
            } else {
                item.addAssetsSum(amount);
                item.addFinalAssets(amount);
                if (inMonth) {
                    item.addAssets(amount);
                }
            }
        } else {
            if (isInit) {
                item.addInitLiabilities(amount);
                item.addFinalLiabilities(amount);
            } else {
                item.addLiabilitiesSum(amount);
                item.addFinalLiabilities(amount);
                if (inMonth) {
                    item.addLiabilities(amount);
                }
            }
        }
    }

    private void sumTransactions(Month month) throws AccException {
        for (Transaction trans : Facade.INSTANCE.getAllTransactions()) {
            LocalDate begin = LocalDate.of(Global.INSTANCE.getYear(), month, 1).minusDays(1);
            LocalDate end = begin.plusMonths(1);
            boolean isInit = !trans.getDate().isPresent();
            boolean inMonth = !isInit && trans.getDate().get().isAfter(begin)
                    && trans.getDate().get().isBefore(end);

            add(trans.getDal(), inMonth, isInit, -trans.getAmount());
            add(trans.getMaDati(), inMonth, isInit, +trans.getAmount());

        }

    }

}
