import Gui.Window;
import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import implementaions.MyDWGAlgo;

import java.util.Scanner;

/**
 * This class is the main class for Ex2 - your implementation will be tested using this class.
 */
public class Ex2 {
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraph getGrapg(String json_file) {
        try {
            DirectedWeightedGraph ans = getGrapgAlgo(json_file).getGraph();
            return ans;
        }
        catch (Exception exception){
            return null;
        }
    }
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraphAlgorithms getGrapgAlgo(String json_file) {
        try {
            DirectedWeightedGraphAlgorithms ans = new MyDWGAlgo(json_file);
            return ans;
        }
        catch (Exception exception){
            return null;
        }
    }
    /**
     * This static function will run your GUI using the json fime.
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     *
     */
    public static void runGUI(String json_file) {
        try {
            DirectedWeightedGraphAlgorithms alg = getGrapgAlgo(json_file);
            Window w = new Window(alg);
        }
        catch (Exception exception){
            return;
        }
    }
    public static void main(String args[]){
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter file name with .json at the end ");
            String str = sc.nextLine();
            runGUI("data/" + str);
        }
        catch (Exception exception){
            return;
        }
    }
}