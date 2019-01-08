package acc.business.balance;

import acc.model.AccGroup;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BalanceItem {

    private final Optional<AccGroup> group;
    private long initAssets;
    private long initLiabilities;
    private long assets;
    private long liabilities;
    private long assetsSum;
    private long liabilitiesSum;
    private long finalAssets;
    private long finalLiabilities;

    private final Map<String, BalanceItem> children = new HashMap<>();

    public BalanceItem(AccGroup ag) {
        this.group = Optional.of(ag);
    }

    BalanceItem() {
        group = Optional.empty();
    }

    static String sp(int l) {
        char[] s = new char[l];
        Arrays.fill(s, ' ');
        return String.copyValueOf(s);
    }

    void print(int intnd) {
        if (intnd == 0) {
            System.out.println("********************************");
        }
        System.out.print(sp(intnd));
        System.out.println(toString());
        for (BalanceItem bi : getChildren().values()) {
            bi.print(intnd + 5);
        }
    }

    public Map<String, BalanceItem> getChildren() {
        return children;
    }

    public Optional<AccGroup> getGroup() {
        return group;
    }

    public String getName() {
        if (!group.isPresent()) {
            return "SUM";
        }
        return getGroup().get().getName();
    }

    public List<BalanceItem> appendItemsTo(List<BalanceItem> items) {
        getChildren().forEach((k, v) -> v.appendItemsTo(items));
        items.add(this);
        return items;
    }

    public String getNumber() {
        if (!group.isPresent()) {
            return "    ";
        }
        return getGroup().get().getNumber();
    }

    public void sum() {
        getChildren().forEach((k, v) -> {
            v.sum();
            addInitAssets(v.getInitAssets());
            addInitLiabilities(v.getInitLiabilities());
            addAssets(v.getAssets());
            addLiabilities(v.getLiabilities());
            addAssetsSum(v.getAssetsSum());
            addLiabilitiesSum(v.getLiabilitiesSum());
            addFinalLiabilities(v.getFinalLiabilities());
        });
    }

    public long getAssets() {
        return assets;
    }

    public long getLiabilities() {
        return liabilities;
    }

    public long getAssetsSum() {
        return assetsSum;
    }

    public long getLiabilitiesSum() {
        return liabilitiesSum;
    }

    public void addAssets(long suma) {
        assets += suma;
    }

    public void addLiabilities(long suma) {
        liabilities += suma;
    }

    public void addAssetsSum(long suma) {
        assetsSum += suma;
    }

    public void addLiabilitiesSum(long suma) {
        liabilitiesSum += suma;
    }

    public void addInitAssets(long suma) {
        initAssets += suma;
    }

    public void addInitLiabilities(long suma) {
        initLiabilities += suma;
    }

    public void addFinalAssets(long suma) {
        finalAssets += suma;
    }

    public void addFinalLiabilities(long suma) {
        finalLiabilities += suma;
    }

//    public void subAssets(long suma) {
//        assets -= suma;
//    }
//
//    public void subLiabilities(long suma) {
//        liabilities -= suma;
//    }
//
//    public void subAssetsSum(long suma) {
//        assetsSum -= suma;
//    }
//
//    public void subLiabilitiesSum(long suma) {
//        liabilitiesSum -= suma;
//    }
    @Override
    public String toString() {
        return "BalanceItem{" + "group=" + getGroup() + ", assets=" + getAssets() + ", liabilities=" + getLiabilities() + ", assetsSum=" + getAssetsSum() + ", liabilitiesSum=" + getLiabilitiesSum() + '}';
    }

    /**
     * @return the initAssets
     */
    public long getInitAssets() {
        return initAssets;
    }

    /**
     * @return the initLiabilities
     */
    public long getInitLiabilities() {
        return initLiabilities;
    }

    /**
     * @return the finalAssets
     */
    public long getFinalAssets() {
        return finalAssets;
    }

    /**
     * @return the finalLiabilities
     */
    public long getFinalLiabilities() {
        return finalLiabilities;
    }
}
