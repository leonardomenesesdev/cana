package AV3;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileMergerBruteForce {

    public static long mergeBruteForce(List<File> files, String outputDir) throws IOException {
        if (files.size() == 1) return 0;
        long minCost = Long.MAX_VALUE;

        for (int i = 0; i < files.size(); i++) {
            for (int j = i + 1; j < files.size(); j++) {

                File f1 = files.get(i);
                File f2 = files.get(j);
                long cost = f1.length() + f2.length();

                File merged = new File(outputDir, "merged_brute_temp.txt");
                mergeFiles(f1, f2, merged);

                List<File> newList = new ArrayList<>(files);
                newList.remove(j);
                newList.remove(i);
                newList.add(merged);

                cost += mergeBruteForce(newList, outputDir);

                if (cost < minCost) {
                    minCost = cost;
                }

                merged.delete(); // remove o temporário
            }
        }

        return minCost;
    }

    private static void mergeFiles(File f1, File f2, File merged) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(merged));
             BufferedReader r1 = new BufferedReader(new FileReader(f1));
             BufferedReader r2 = new BufferedReader(new FileReader(f2))) {

            String line;
            while ((line = r1.readLine()) != null) writer.write(line + "\n");
            while ((line = r2.readLine()) != null) writer.write(line + "\n");
        }
    }
}
