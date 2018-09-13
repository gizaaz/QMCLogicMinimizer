/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package QMCLogicMinimizer.minimization;

import java.util.ArrayList;

/**
 *
 * @author OK3PJ 16.4.2012
 */
public class PrimeImplicantChart {

    ArrayList<String[]> minSolution = new ArrayList<String[]>();

    public PrimeImplicantChart(ArrayList<String[]> minterms, ArrayList<String[]> primeImplicants) {
        ////////////////////////////
        // sestaveni tabulky pokryti
        ////////////////////////////
        ArrayList<String[]> primeImplChart = new ArrayList<String[]>();
        for (int i = 0; i < minterms.size(); i++) {
            String[] minterm = minterms.get(i);
            String[] minColumn = new String[primeImplicants.size() + 1];
            int cover = 0;
            int j;
            for (j = 0; j < primeImplicants.size(); j++) {
                if (isCover(minterm, primeImplicants.get(j))) {
                    minColumn[j] = "X";
                    cover++;
                }
            }
            minColumn[j] = Integer.toString(cover);
            primeImplChart.add(minColumn);
        }
        int nofCoverage = 1; // pocet pokryti
        ////////////////////////////
        // ziskavej nezbytne cleny dokud nebude tabulka pokryti prazdna
        ////////////////////////////
        while (!primeImplChart.isEmpty()) {
            int size = primeImplChart.size();
            int i = 0;
            findCoverage:
            while (i < size) {
                if (Integer.parseInt(primeImplChart.get(i)[primeImplChart.get(i).length - 1]) == nofCoverage) {
                    rowScanner(primeImplChart, i, primeImplicants);
                    size = primeImplChart.size();
                    i = 0;
                    continue findCoverage;
                }
                i++;
            }
            nofCoverage++;
        }
    }
    ////////////////////////////
    // metoda pro zjistovani pokryti primarniho implikantu a mintermu
    ////////////////////////////

    private boolean isCover(String[] minterm, String[] primeImplicant) {
        for (int i = 0; i < minterm.length - 1; i++) {
            if (!minterm[i].equals(primeImplicant[i]) && !"-".equals(primeImplicant[i])) {
                return false;
            }
        }
        return true;
    }
    ////////////////////////////
    // procedura pro skenovani radku
    ////////////////////////////    

    private void rowScanner(ArrayList<String[]> primeImplChart, int row, ArrayList<String[]> primeImplicants) {
        String[] actRow = primeImplChart.get(row);
        primeImplChart.remove(row);
        boolean intersec = false;
        int index = 0;
        for (int k = 0; k < actRow.length - 1; k++) {
            if ("X".equals(actRow[k])) {
                if (columnScanner(primeImplChart, k)) {
                    intersec = true;
                    minSolution.add(primeImplicants.get(k));
                    break;
                }
                index = k;
            }
        }
        if (intersec == false) {
            minSolution.add(primeImplicants.get(index));
        }
    }
    ////////////////////////////
    // metoda pro zskenovani sloupcu a mazani radku
    ////////////////////////////   

    private boolean columnScanner(ArrayList<String[]> primeImplChart, int index) {
        boolean deleted = false;
        for (int i = primeImplChart.size() - 1; i >= 0; i--) {
            if ("X".equals(primeImplChart.get(i)[index])) {
                primeImplChart.remove(i);
                deleted = true;
            }
        }
        return deleted;
    }
    ////////////////////////////
    // metoda vraci staticke pole s vysledkem
    ////////////////////////////   

    public ArrayList getMinSolution() {
        return minSolution;
    }
}
