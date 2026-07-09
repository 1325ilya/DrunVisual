package drunvisual.settings;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public class ModeSetting extends Setting<Integer> {
    private final String[] values;
    private final boolean multiSelect;
    private final Set<Integer> selectedIndices;

    public ModeSetting(String str, String str2, String[] strArr, int i) {
        super(str, str2, Integer.valueOf(clampIndex(strArr, i)));
        this.values = strArr;
        this.multiSelect = false;
        this.selectedIndices = new HashSet();
    }

    public ModeSetting(String str, String[] strArr, int i) {
        this(str, "", strArr, i);
    }

    public ModeSetting(String str, String[] strArr, String str2) {
        this(str, "", strArr, indexOf(strArr, str2));
    }

    public ModeSetting(String str, String str2, String[] strArr, int[] iArr) {
        super(str, str2, Integer.valueOf(firstSelectedIndex(strArr, iArr)));
        this.values = strArr;
        this.multiSelect = true;
        this.selectedIndices = new HashSet();
        for (int i : iArr) {
            if (isValidIndex(strArr, i)) {
                this.selectedIndices.add(Integer.valueOf(i));
            }
        }
    }

    public ModeSetting(String str, String[] strArr, int[] iArr) {
        this(str, "", strArr, iArr);
    }

    public String[] values() {
        return this.values;
    }

    public List<String> valueList() {
        return Arrays.asList(this.values);
    }

    public boolean isMultiSelect() {
        return this.multiSelect;
    }

    public String selectedValue() {
        int iIntValue = k().intValue();
        return isValidIndex(this.values, iIntValue) ? this.values[iIntValue] : "";
    }

    public void select(String str) {
        select(indexOf(this.values, str));
    }

    public Set<Integer> selectedIndices() {
        HashSet hashSet = new HashSet(this.selectedIndices);
        if (!this.multiSelect) {
            hashSet.add(k());
        }
        return hashSet;
    }

    public boolean isSelected(int i) {
        return this.multiSelect ? this.selectedIndices.contains(Integer.valueOf(i)) : k().intValue() == i;
    }

    public void select(int i) {
        if (!this.multiSelect) {
            super.a(Integer.valueOf(clampIndex(this.values, i)));
        } else if (this.selectedIndices.contains(Integer.valueOf(i))) {
            this.selectedIndices.remove(Integer.valueOf(i));
        } else if (isValidIndex(this.values, i)) {
            this.selectedIndices.add(Integer.valueOf(i));
        }
    }

    public void setSelectedIndices(Set<Integer> set) {
        this.selectedIndices.clear();
        Iterator<Integer> it = set.iterator();
        while (it.hasNext()) {
            int iIntValue = it.next().intValue();
            if (isValidIndex(this.values, iIntValue)) {
                this.selectedIndices.add(Integer.valueOf(iIntValue));
            }
        }
    }

    public boolean isSelected(String str) {
        return this.multiSelect ? this.selectedIndices.contains(Integer.valueOf(indexOf(this.values, str))) : selectedValue().equals(str);
    }

    @Override
    public Setting<Integer> visibleWhen(Supplier<Boolean> supplier) {
        super.visibleWhen(supplier);
        return this;
    }

    private static int indexOf(String[] strArr, String str) {
        for (int i = 0; i < strArr.length; i++) {
            if (strArr[i].equals(str)) {
                return i;
            }
        }
        return 0;
    }

    private static int firstSelectedIndex(String[] strArr, int[] iArr) {
        if (iArr.length > 0) {
            return clampIndex(strArr, iArr[0]);
        }
        return 0;
    }

    private static int clampIndex(String[] strArr, int i) {
        if (strArr.length == 0) {
            return 0;
        }
        return Math.max(0, Math.min(i, strArr.length - 1));
    }

    private static boolean isValidIndex(String[] strArr, int i) {
        return i >= 0 && i < strArr.length;
    }

    public String[] a() {
        return values();
    }

    public List<String> b() {
        return valueList();
    }

    public boolean c() {
        return isMultiSelect();
    }

    public String d() {
        return selectedValue();
    }

    public void a(String str) {
        select(str);
    }

    public Set<Integer> e() {
        return selectedIndices();
    }

    public boolean a(int i) {
        return isSelected(i);
    }

    public void b(int i) {
        select(i);
    }

    public void a(Set<Integer> set) {
        setSelectedIndices(set);
    }

    public boolean b(String str) {
        return isSelected(str);
    }

    public boolean c(int i) {
        return isSelected(i);
    }

    public boolean c(String str) {
        return this.selectedIndices.contains(Integer.valueOf(indexOf(this.values, str)));
    }

    public ModeSetting a(Supplier<Boolean> supplier) {
        return (ModeSetting) visibleWhen(supplier);
    }

    public /* bridge */ /* synthetic */ Setting<Integer> visibleWhen2(Supplier supplier) {
        return visibleWhen((Supplier<Boolean>) supplier);
    }
}
