package mason;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class Mason {
	ArrayList<ArrayList<Integer>> forwardPaths;
	ArrayList<ArrayList<Integer>> loops;
	ArrayList<ArrayList<Integer>> untouchedLoops;
	ArrayList<Integer> forwardPathsGains;
	ArrayList<Integer> loopsGains;
	ArrayList<Integer> untouchedLoopsGains;
	ArrayList<Integer> untouchedLoopsCount;
	int numOfNodes;
	ListStore listStore;
	int delta;
	ArrayList<Integer> deltaFP;
	Double overAllTF;

	public Mason() {
		forwardPaths = new ArrayList<ArrayList<Integer>>();
		loops = new ArrayList<ArrayList<Integer>>();
		untouchedLoops = new ArrayList<ArrayList<Integer>>();
		forwardPathsGains = new ArrayList<Integer>();
		loopsGains = new ArrayList<Integer>();
		untouchedLoopsGains = new ArrayList<Integer>();
		untouchedLoopsCount = new ArrayList<Integer>();
		numOfNodes = Data.numOfNodes;
		delta = 0;
		deltaFP = new ArrayList<>();
	}

	void dfsFP(int lastNode, ArrayList<Integer> prevFP, int gain) {
		ArrayList<Integer> fP = new ArrayList<>(prevFP);
		fP.add(lastNode);
		if (lastNode == numOfNodes - 1) {
			forwardPaths.add(fP);
			forwardPathsGains.add(gain);
		} else {
			for (int i = lastNode + 1; i < numOfNodes; i++) {
				if (Data.gains[lastNode][i] != null) {
					dfsFP(i, fP, gain * Data.gains[lastNode][i]);
				}
			}
		}
	}

	void FindFP() {
		int i = 0;
		ArrayList<Integer> path = new ArrayList<>();
		path.add(i);
		for (int j = 1; j < numOfNodes; j++) {
			int gain = 1;
			if (Data.gains[i][j] != null) {
				gain *= Data.gains[i][j];
				dfsFP(j, path, gain);
			}
		}
	}

	public void printFP() {
		for (int j = 0; j < forwardPaths.size(); j++) {
			ArrayList<Integer> path = forwardPaths.get(j);
			System.out.print("path " + (j + 1) + ":");
			for (int k = 0; k < path.size(); k++) {
				System.out.print(path.get(k) + " ");
			}
			System.out.println("   gain is:" + forwardPathsGains.get(j));
		}
	}

	void FindLoop() {
		listStore = new ListStore();
		Queue<ArrayList<Integer>> loopList = new LinkedList<ArrayList<Integer>>();
		for (int i = 1; i < numOfNodes - 1; i++) {
			for (int j = i; j < numOfNodes; j++) {
				if (Data.gains[j][i] != null) {
					ArrayList<Integer> loop = new ArrayList<>();
					loop.add(i);
					loop.add(j);
					loopList.add(loop);
				}
			}
			while (!loopList.isEmpty()) {
				ArrayList<Integer> checkLoop = new ArrayList<>(loopList.peek());
				loopList.remove();
				int lastNode = checkLoop.get(checkLoop.size() - 1);
				int firstNode = checkLoop.get(0);
				if (lastNode == i) {
					int size = checkLoop.size();
					for (int k = 0; k < size / 2; k++) {
						int temp = checkLoop.get(k);
						checkLoop.set(k, checkLoop.get(size - k - 1));
						checkLoop.set(size - k - 1, temp);
					}
					if (!listStore.isExist(checkLoop)) {

						int gain = 1;
						for (int k = 0; k < size - 1; k++) {
							gain *= Data.gains[checkLoop.get(k)][checkLoop.get(k + 1)];
						}
						loops.add(checkLoop);
						loopsGains.add(gain);
						listStore.addToStore(checkLoop);
					}

				} else {
					for (int k = 0; k < numOfNodes; k++) {
						if (Data.gains[k][lastNode] != null) {
							if ((!checkLoop.contains(k) || k == i) && (k >= firstNode)) {
								ArrayList<Integer> aux = new ArrayList<>(checkLoop);
								aux.add(k);
								loopList.add(aux);
							}
						}
					}
				}

			}

		}
	}

	public void printLoop() {
		for (int j = 0; j < loops.size(); j++) {
			ArrayList<Integer> path = loops.get(j);
			System.out.print("loop " + (j + 1) + ":");
			for (int k = 0; k < path.size(); k++) {
				System.out.print(path.get(k) + " ");
			}
			System.out.println("   gain is:" + loopsGains.get(j));
		}
	}

	void findUnTL() {
		listStore = new ListStore();
		for (int i = 0; i < loops.size(); i++) {
			ArrayList<Integer> loop1 = new ArrayList<>(loops.get(i));
			for (int j = i + 1; j < loops.size(); j++) {
				ArrayList<Integer> loop2 = new ArrayList<>(loops.get(j));
				boolean flag = false;
				for (int k = 0; k < loop1.size() && !flag; k++) {
					if (loop2.contains(loop1.get(k))) {
						flag = true;
					}
				}
				if (flag == false) {
					loop2.addAll(loop1);
					untouchedLoops.add(loop2);
					untouchedLoopsGains.add(loopsGains.get(i) * loopsGains.get(j));
					untouchedLoopsCount.add(2);
				}
			}
		}
	}

	void addExtraunTL(Queue<ArrayList<Integer>> uTLlist, Queue<Integer> uTLFainsList, int r) {
		Queue<ArrayList<Integer>> uTLoops1 = new LinkedList<>(uTLlist);
		Queue<ArrayList<Integer>> uTLoops2 = new LinkedList<>();
		Queue<Integer> uTLoops1Gains = new LinkedList<>(uTLFainsList);
		Queue<Integer> uTLoops2Gains = new LinkedList<>();
		int rank = r;
		while (!uTLoops1.isEmpty()) {
			ArrayList<Integer> unTLoop = new ArrayList<>(uTLoops1.peek());
			int unTLoopGain = uTLoops1Gains.peek();
			uTLoops1.remove();
			uTLoops1Gains.remove();
			for (int j = 0; j < loops.size(); j++) {
				ArrayList<Integer> loop = new ArrayList<>(loops.get(j));
				boolean flag = false;
				for (int k = 0; k < loop.size() && !flag; k++) {
					if (unTLoop.contains(loop.get(k))) {
						flag = true;
					}
				}
				if (flag == false) {
					ArrayList<Integer> aux = new ArrayList<>(unTLoop);
					aux.addAll(loop);
					if (!listStore.isExistForLoopComb(aux)) {
						listStore.addToStoreLoopsComb(aux);
						untouchedLoops.add(aux);
						uTLoops2.add(aux);
						Integer totalGain = unTLoopGain * loopsGains.get(j);
						uTLoops2Gains.add(totalGain);
						untouchedLoopsGains.add(totalGain);
						untouchedLoopsCount.add(rank);
					}
				}
			}
		}
		if (!uTLoops2.isEmpty()) {
			addExtraunTL(uTLoops2, uTLoops2Gains, rank + 1);
		}
	}

	public void solve() {
		forwardPaths = new ArrayList<ArrayList<Integer>>();
		loops = new ArrayList<ArrayList<Integer>>();
		untouchedLoops = new ArrayList<ArrayList<Integer>>();
		forwardPathsGains = new ArrayList<Integer>();
		loopsGains = new ArrayList<Integer>();
		untouchedLoopsGains = new ArrayList<Integer>();
		untouchedLoopsCount = new ArrayList<Integer>();
		numOfNodes = Data.numOfNodes;
		FindFP();
		FindLoop();
		findUnTL();
		Queue<ArrayList<Integer>> uTLoops = new LinkedList<>();
		uTLoops.addAll(untouchedLoops);
		Queue<Integer> uTLoopsGains = new LinkedList<>();
		uTLoopsGains.addAll(untouchedLoopsGains);
		addExtraunTL(uTLoops, uTLoopsGains, 3);
		calcOverAllTF();
	}

	public String buildResult() {
		StringBuilder builder = new StringBuilder("<html>");
		builder.append("<center><b>forward paths</b></center><br>");
		for (int j = 0; j < forwardPaths.size(); j++) {
			ArrayList<Integer> path = forwardPaths.get(j);
			builder.append("path " + (j + 1) + ":&nbsp;&nbsp;&nbsp;");
			for (int k = 0; k < path.size(); k++) {
				builder.append(path.get(k) + " ");
			}
			builder.append(" gain is: &nbsp;" + forwardPathsGains.get(j) + "<br>");
		}
		builder.append("<br><br><br>");
		builder.append("<center><b>Loops</b></center><br>");
		for (int j = 0; j < loops.size(); j++) {
			ArrayList<Integer> path = loops.get(j);
			builder.append("loop " + (j + 1) + ":&nbsp;&nbsp;&nbsp;");
			for (int k = 0; k < path.size(); k++) {
				builder.append(path.get(k) + " ");
			}
			builder.append(" gain is:&nbsp;" + loopsGains.get(j) + "<br>");
		}
		builder.append("<br><br><br>");
		builder.append("<center><b>untouched loops</b></center><br>");
		for (int j = 0; j < untouchedLoops.size(); j++) {
			builder.append("untouched loop: " + (j + 1) + " of rank: " + untouchedLoopsCount.get(j));
			builder.append(" &nbsp;&nbsp;&nbsp; gain is:" + untouchedLoopsGains.get(j) + "<br>");
		}
		builder.append("<br><br><br>");
		builder.append("delta = &nbsp;" + delta + "<br> ");
		builder.append("<br><br><br>");
		builder.append("<center><b>delta without forward paths</b></center><br>");
		for (int i = 0; i < deltaFP.size(); i++) {
			builder.append(" delta " + (i + 1) + ":&nbsp;" + deltaFP.get(i) + " <br> ");
		}
		builder.append("<br><br><br>");
		builder.append("Overall Transfer function = " + overAllTF + "<br> ");
		builder.append("</html>");
		return builder.toString();
	}

	public void printUnTLoops() {
		for (int j = 0; j < untouchedLoops.size(); j++) {
			System.out.print("untouched loop: " + (j + 1) + " of rank: " + untouchedLoopsCount.get(j));
			System.out.println("&nbsp&nbsp>>&nbsp gain is:&nbsp" + untouchedLoopsGains.get(j));
		}
	}

	void calcDelta() {
		delta = 0;
		for (int i = 0; i < loopsGains.size(); i++) {
			delta += loopsGains.get(i);
		}
		delta = 1 - delta;
		int sign = 1, sum = 0, rank = 2;
		for (int i = 0; i < untouchedLoopsGains.size(); i++) {
			if (rank == untouchedLoopsCount.get(i)) {
				sum += untouchedLoopsGains.get(i);
			} else {
				delta += sign * sum;
				rank = untouchedLoopsCount.get(i);
				sum = 0;
				sum += untouchedLoopsGains.get(i);
				sign *= -1;
			}
		}
		delta += sign * sum;
	}

	void calcDeltaFP() {
		for (int i = 0; i < forwardPaths.size(); i++) {
			int delFP = 0;
			int sign = 1, sum = 0, rank = 2;
			ArrayList<Integer> fP = new ArrayList<>(forwardPaths.get(i));
			for (int j = 0; j < loops.size(); j++) {
				ArrayList<Integer> loop = new ArrayList<>(loops.get(j));
				if (Collections.disjoint(fP, loop)) {
					delFP += loopsGains.get(j);
				}
			}
			delFP = 1 - delFP;
			for (int j = 0; j < untouchedLoops.size(); j++) {
				ArrayList<Integer> uTLoop = new ArrayList<>(untouchedLoops.get(j));
				if (Collections.disjoint(fP, uTLoop)) {
					if (rank == untouchedLoopsCount.get(j)) {
						sum += untouchedLoopsGains.get(j);
					} else {
						delFP += sign * sum;
						rank = untouchedLoopsCount.get(j);
						sum = 0;
						sum += untouchedLoopsGains.get(j);
						sign *= -1;
					}
				}

			}
			delFP += sign * sum;
			deltaFP.add(delFP);
		}
	}

	void calcOverAllTF() {
		calcDelta();
		calcDeltaFP();
		overAllTF = new Double(0);
		for (int i = 0; i < forwardPaths.size(); i++) {
			overAllTF += forwardPathsGains.get(i) * deltaFP.get(i);
		}
		overAllTF /= delta;
	}
}