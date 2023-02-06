package Problems.WordNets;
/******************************************************************************
 *  Compilation:  javac WordNet.java
 *  Execution:    none
 *  Dependencies: SAP.java, algs4.jar
 * <p>
 *  An immutable data type that represents digraph of WordNet,
 *  which is used in semantic lexicon for the English language,
 *  that computational linguists and cognitive scientists use extensively.
 *  The WordNet digraph is a rooted DAG: it is acyclic
 *  and has one vertex—the root—that is an ancestor of every other vertex.
 * <p>
 *  For use on Coursera, Algorithms Part II programming assignment.
 *
 ******************************************************************************/
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedEulerianCycle;
import edu.princeton.cs.algs4.In;

import java.util.HashMap;
import java.util.HashSet;

public class WordNet {

    private final Digraph G;
    private final HashMap<String, HashSet<Integer>> synsetKeysMapping;
    private final HashMap<Integer, String> keysSynsetMapping;
    private final SAP sap;

    /**
     * Takes the name of the two input files
     *
     * @param synsets   input file name for synsets
     * @param hypernyms input file name for hypernyms
     */
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) throw new IllegalArgumentException("Argument cannot be null");
        this.synsetKeysMapping = new HashMap<>();
        this.keysSynsetMapping = new HashMap<>();
        int numKeys = this.processSynsets(synsets);
        this.G = new Digraph(numKeys);
        this.createDigraph(hypernyms);

        int numRoots = 0;
        for (int i = 0; i < this.G.V(); i++)
            if (this.G.outdegree(i) == 0) numRoots++;

        DirectedEulerianCycle dec = new DirectedEulerianCycle(this.G);
        if (dec.hasEulerianCycle() || numRoots > 1)
            throw new IllegalArgumentException("Input to the constructor does not correspond to a rooted DAG");

        this.sap = new SAP(this.G);
    }

    /**
     * unit testing of this class
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
    }

    private int processSynsets(String synsetsFileName) {
        int numKeys = 0;
        In in = new In(synsetsFileName);
        while (!in.isEmpty()) {
            String[] line = in.readLine().split(",");
            int id = Integer.parseInt(line[0]);
            
            numKeys++;
            String[] synsets = line[1].split(" ");
            this.keysSynsetMapping.put(id, line[1]);
            for (String s : synsets) {
                if (!this.synsetKeysMapping.containsKey(s)) this.synsetKeysMapping.put(s, new HashSet<>());
                this.synsetKeysMapping.get(s).add(id);
            }
        }
        return numKeys;
    }

    private void createDigraph(String hypernymsFileName) {
        In in = new In(hypernymsFileName);
        while (!in.isEmpty()) {
            String[] line = in.readLine().split(",");
            int id = Integer.parseInt(line[0]);
            for (int i = 1; i < line.length; i++)
                this.G.addEdge(id, Integer.parseInt(line[i]));
        }
    }

    /**
     * @return all WordNet nouns
     */
    public Iterable<String> nouns() {
        return (Iterable<String>) this.synsetKeysMapping.keySet();
    }

    /**
     * @param word queried noun
     * @return is the word a WordNet noun?
     */
    public boolean isNoun(String word) {
        if (word == null) throw new IllegalArgumentException("Argument cannot be null");
        return this.synsetKeysMapping.containsKey(word);
    }

    /**
     * @param nounA wordnet noun A
     * @param nounB wordnet noun B
     * @return distance between nounA and nounB
     */
    public int distance(String nounA, String nounB) {
        this.checkNounArguments(nounA, nounB);
        return this.sap.length(this.synsetKeysMapping.get(nounA), this.synsetKeysMapping.get(nounB));
    }

    /**
     * @param nounA wordnet noun A
     * @param nounB wordnet noun B
     * @return the shortest ancestral path for two nouns that have a common ancestor
     */
    public String sap(String nounA, String nounB) {
        this.checkNounArguments(nounA, nounB);
        int ancestor = this.sap.ancestor(this.synsetKeysMapping.get(nounA), this.synsetKeysMapping.get(nounB));

        return this.keysSynsetMapping.get(ancestor);
    }

    private void checkNounArguments(String nounA, String nounB) {
        if (nounA == null || nounB == null) throw new IllegalArgumentException("Argument cannot be null");
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException("Argument(s) not a WordNet noun");
    }
}