/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package QMCLogicMinimizer.minimization;

import java.util.ArrayList;


public class Minimizer {

    private ArrayList<String[]> minimized;

    public Minimizer(ArrayList<String[]> data1) {
        ArrayList<String[]> data2 = new ArrayList<String[]>();
        minimized = new ArrayList<String[]>();
        ////////////////////////////
        // minimalizace z data1 do data2
        ////////////////////////////
        for (;;) {
            if (data1.isEmpty()) {
                break;
            }
            for (int i = 0; i < data1.size() - 1; i++) {
                String[] compTerm = data1.get(i); // clen se kterym se porovnavaji ostatni
                for (int j = i + 1; j < data1.size(); j++) {
                    String[] actlTerm = data1.get(j);
                    String[] compared = comparator(compTerm, actlTerm);
                    if (!"N".equals(compared[0])) {
                        data2.add(compared);
                        actlTerm[actlTerm.length - 1] = "U";
                        compTerm[compTerm.length - 1] = "U";
                        data1.set(i, compTerm);
                        data1.set(j, actlTerm);
                    }
                }
            }
            ////////////////////////////
            // hledani prostych implikantu a jejich kopirovani do minimized
            ////////////////////////////
            for (int i = 0; i < data1.size(); i++) {
                String[] term = data1.get(i);
                if (term[term.length - 1] == null) {
                    minimized.add(term);
                }
            }
            data1.clear(); // vyprazni data1
            if (data2.isEmpty()) {
                break;
            }
            data2 = deleteSameTerms(data2);
            ////////////////////////////
            // minimalizace z data2 do data1
            ////////////////////////////
            for (int i = 0; i < data2.size() - 1; i++) {
                String[] compTerm = data2.get(i); // clen se kterym se porovnavaji ostatni
                for (int j = i + 1; j < data2.size(); j++) {
                    String[] actlTerm = data2.get(j);
                    String[] compared = comparator(compTerm, actlTerm);
                    if (!"N".equals(compared[0])) {
                        data1.add(compared);
                        actlTerm[actlTerm.length - 1] = "U";
                        compTerm[compTerm.length - 1] = "U";
                        data2.set(i, compTerm);
                        data2.set(j, actlTerm);
                    }
                }
            }
            ////////////////////////////
            // hledani prostych implikantu a jejich kopirovani do minimized
            ////////////////////////////
            for (int i = 0; i < data2.size(); i++) {
                String[] term = data2.get(i);
                if (term[term.length - 1] == null) {
                    minimized.add(term);
                }
            }
            data2.clear();
            if (data1.isEmpty()) {
                break;
            }
            data1 = deleteSameTerms(data1);
        }
    }

    private String[] comparator(String[] term1, String[] term2) {
        String[] compared = new String[term1.length];
        int different = 0;
        for (int i = 0; i < term1.length - 1; i++) {
            if (term1[i].equals(term2[i])) {
                compared[i] = term1[i];
            } else {
                different++;
                compared[i] = "-";
            }
            if (different > 1) {
                compared[0] = "N";
                break;
            }
        }
        return compared;
    }

    private ArrayList<String[]> deleteSameTerms(ArrayList<String[]> data) {
        for (int i = 0; i < data.size() - 1; i++) {
            String[] term1 = data.get(i);
            for (int j = i + 1; j < data.size(); j++) {
                String[] term2 = data.get(j);
                int k = 0;
                int l = 0;
                for (; k < term1.length - 1; k++) {
                    if (!term1[k].equals(term2[k])) {
                        break;
                    }
                }
                if (k == term1.length - 1) {
                    data.remove(j);
                    j--;
                }
            }
        }
        return data;
    }

    public ArrayList<String[]> getMinimized() {
        return minimized;
    }
}
