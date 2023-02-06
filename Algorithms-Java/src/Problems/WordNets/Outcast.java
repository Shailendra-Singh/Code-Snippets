package Problems.WordNets;
/******************************************************************************
 *  Compilation:  javac Outcast.java
 *  Execution:    none
 *  Dependencies: WordNet.java, (algs4.jar)
 * <p>
 *  Given a list of WordNet nouns x1, x2, ..., xn, selects a noun that is the least related to the others
 * <p>
 *  For use on Coursera, Algorithms Part II programming assignment.
 *
 ******************************************************************************/
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {

    private final WordNet wordNet;

    /**
     * @param wordnet takes a WordNet object
     */
    public Outcast(WordNet wordnet) {
        this.wordNet = wordnet;
    }

    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }

    /**
     * @param nouns an array of WordNet nouns
     * @return an outcast
     */
    public String outcast(String[] nouns) {
        int x = 0;
        int dt = 0;
        for (int i = 0; i < nouns.length; i++) {
            int di = 0;
            for (int j = 0; j < nouns.length; j++) {
                if (i == j) continue; // skip self
                di += this.wordNet.distance(nouns[i], nouns[j]);
            }
            if (di > dt) {
                dt = di;
                x = i;
            }
        }
        return nouns[x];
    }
}