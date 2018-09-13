/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package QMCLogicMinimizer.table;

/**
 *
 * @author PJ
 */
public class TableModelMaker {

    public String[] columnsHeaders(int nofInpVar, int nofOutVar) {
        int numberOfColumns = nofInpVar + nofOutVar;
        int counter = 0;
        int counter2 = 0;
        char inpCar = 65;
        String[] columns;
        columns = new String[numberOfColumns];
        while (counter < nofInpVar) {
            columns[counter] = Character.toString(inpCar);
            counter++;
            inpCar++;
        }
        while (counter < numberOfColumns) {
            columns[counter] = "f" + counter2;
            counter++;
            counter2++;
        }
        return columns;
    }

    public String[][] rowsData(int nofInpVar, int nofOutVar, int nofRows) {
        String[][] rows;
        rows = new String[nofRows][nofInpVar + nofOutVar];
        int colCounter;
        int rowCounter;
        if (nofRows == Math.pow(2, nofInpVar)) {
            double nofZeros;
            double wrtCycle;
            int rowNum = 0;
            int colNum = 0;
            for (int exp = (nofInpVar - 1); exp >= 0; exp--) {
                nofZeros = Math.pow(2, exp);
                wrtCycle = nofRows / Math.pow(2, exp + 1);
                while (wrtCycle > 0) {
                    for (double z = nofZeros; z > 0; z--) {
                        rows[rowNum][colNum] = "0";
                        rowNum++;
                    }
                    for (double o = nofZeros; o > 0; o--) {
                        rows[rowNum][colNum] = "1";
                        rowNum++;
                    }
                    wrtCycle--;
                }
                rowNum = 0;
                colNum++;
            }
        } else {
            for (colCounter = 0; colCounter < nofInpVar; colCounter++) {
                for (rowCounter = 0; rowCounter < nofRows; rowCounter++) {
                    rows[rowCounter][colCounter] = "0";
                }
            }
        }
        for (colCounter = nofInpVar; colCounter < (nofInpVar + nofOutVar); colCounter++) {
            for (rowCounter = 0; rowCounter < nofRows; rowCounter++) {
                rows[rowCounter][colCounter] = "0";
            }
        }
        return rows;
    }
}
