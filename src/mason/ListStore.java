package mason;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ListStore {
	ArrayList<ArrayList<Integer>> store;
	ArrayList<ArrayList<Integer>> storeLoopsComb;

	public ListStore() {
		// TODO Auto-generated constructor stub
		store = new ArrayList<ArrayList<Integer>>();
		storeLoopsComb = new ArrayList<ArrayList<Integer>>();
	}

	void addToStore(ArrayList<Integer> arrayList) {
		ArrayList<Integer> al = new ArrayList<>(arrayList);
		al.remove(al.size() - 1);
		store.add(al);
	}

	void addToStoreLoopsComb(ArrayList<Integer> arrayList) {
		ArrayList<Integer> al = new ArrayList<>(arrayList);
		Set<Integer> hs = new HashSet<>();
		hs.addAll(al);
		al.clear();
		al.addAll(hs);
		Collections.sort(al);
		storeLoopsComb.add(al);
	}

	boolean isExist(ArrayList<Integer> arrayList) {
		boolean state = false;
		ArrayList<Integer> al = new ArrayList<>(arrayList);
		al.remove(al.size() - 1);
		for (int i = 0; i < store.size() && !state; i++) {
			ArrayList<Integer> storedList = new ArrayList<>(store.get(i));
			if (al.size() == storedList.size()) {
				int index = storedList.indexOf(al.get(0));
				if (index == -1) {
					continue;
				}
				index = increaseIndex(index, storedList.size());

				int k;
				for (k = 1; k < al.size(); k++) {
					if (al.get(k) == storedList.get(index)) {
						index = increaseIndex(index, storedList.size());
						continue;
					} else {
						break;
					}
				}
				if (k == al.size()) {
					state = true;
				}
			}
		}
		return state;
	}

	private int increaseIndex(int index, int size) {
		int ind = index;
		if (ind == (size - 1)) {
			ind = 0;
		} else {
			ind++;
		}
		return ind;
	}

	boolean isExistForLoopComb(ArrayList<Integer> arrayList) {
		ArrayList<Integer> al = new ArrayList<>(arrayList);
		Set<Integer> hs = new HashSet<>();
		hs.addAll(al);
		al.clear();
		al.addAll(hs);
		Collections.sort(al);
		if (storeLoopsComb.contains(al)) {
			return true;
		}
		return false;
	}
}