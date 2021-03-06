import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Task1 {

    private static ArrayList<ArrayList<Integer>> graph;
    private static ArrayList<Integer> result;
    private static ArrayList<Integer> color;
    private static boolean impossible;

    private static void dfs(int v) {
        if (color.get(v) == 1)
            return;
        color.set(v, 0);
        for (int u : graph.get(v)) {
            if (color.get(u) == 0)
                impossible = true;
            else
                dfs(u);
        }
        color.set(v, 1);
        result.add(v);
    }

    public static ArrayList<Character> solve(int n, String[] names) {
        // build a graph on letters so that
        // a -> b <==> a must go before b
        // then topsort, if no cycles

        graph = new ArrayList<>();
        result = new ArrayList<>();
        color = new ArrayList<>();
        impossible = false;
        int vertexes = 'z' - 'a' + 1;
        for (int v = 0; v < vertexes; v++) {
            graph.add(new ArrayList<>());
            color.add(-1);
        }

        // building a graph from the given strings
        for (int i = 1; i < n; i++) {
            String s1 = names[i - 1];
            String s2 = names[i];
            int t = 0;
            while (t < s1.length() && t < s2.length() && s1.charAt(t) == s2.charAt(t))
                t++;
            if (t == s1.length() && t == s2.length()) {
                throw new IllegalArgumentException("invalid input - cannot have equal strings");
            } else if (t == s2.length()) {
                // s2 is a prefix of s1, but it should go after s1 => ?!
                impossible = true;
            } else if (t == s1.length()) {
                // s1 is prefix of s2, always correct, do nothing
            } else {
                graph.get(s1.charAt(t) - 'a').add(s2.charAt(t) - 'a');
            }
        }

        // topsort
        for (int v = 0; v < vertexes; v++) {
            if (color.get(v) == -1)
                dfs(v);
        }

        if (impossible)
            return null;
        Collections.reverse(result);
        return result.stream().map(v -> (char) (v + 'a')).collect(Collectors.toCollection(ArrayList::new));
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        String[] names = new String[n];
        for (int i = 0; i < n; i++)
            names[i] = sc.next();

        ArrayList<Character> solution = solve(n, names);

        if(solution == null) {
            System.out.println("Impossible");
            return;
        }
        for (char c : solution)
            System.out.print(c + " ");
        System.out.println();
    }
}



