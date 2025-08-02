package grep;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import grep.engine.MapReduceEngine;
import grep.impl.GrepMapper;
import grep.impl.GrepReducer;

public class GrepApplication {
    public static void main(String[] args) throws Exception {
        if (args.length < 3) {
            System.out.println("Invalid command");
            return;
        }

        boolean useVirtualThreads = Arrays.asList(args).contains("--virtual");

        String pattern = args[0];
        String outputFile = args[1];
        List<String> inputs = new ArrayList<>(Arrays.asList(args).subList(2, args.length));
        if (!inputs.isEmpty() && "--virtual".equals(inputs.get(inputs.size() - 1))) {
            inputs.remove(inputs.size() - 1);
        }

        MapReduceEngine engine = new MapReduceEngine(new GrepMapper(), new GrepReducer(), useVirtualThreads);
        engine.execute(pattern, inputs, outputFile);

        System.out.println("Search complete. Output written to " + outputFile);
    }
}