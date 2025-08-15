 package grep.engine;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import grep.impl.GrepMapper;
import grep.impl.GrepReducer;
import grep.util.Pair;

public class MapReduceEngine {
    private final GrepMapper mapper;
    private final GrepReducer reducer;
    private final boolean useVirtualThreads;

    public MapReduceEngine(GrepMapper mapper, GrepReducer reducer, boolean useVirtualThreads) {
        this.mapper = mapper;
        this.reducer = reducer;
        this.useVirtualThreads = useVirtualThreads;
    }

    public void execute(String pattern, List<String> filePaths, String outputPath)
            throws InterruptedException, ExecutionException {

        ExecutorService executor;
        if (useVirtualThreads) 
            executor = Executors.newVirtualThreadPerTaskExecutor();
        else 
            executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        long start = System.nanoTime();
        List<Future<List<Pair<String, String>>>> futures = new ArrayList<>();
        for (String filePath : filePaths) {
            futures.add(executor.submit(() -> {
                List<String> lines = Files.readAllLines(Paths.get(filePath));
                Thread.sleep(10); 
                return mapper.map(filePath, lines, pattern);
            }));
        }

        List<Pair<String, String>> intermediateKV = new ArrayList<>();
        for (Future<List<Pair<String, String>>> future : futures) {
            intermediateKV.addAll(future.get());
        }

        long end = System.nanoTime(); 
        executor.shutdown();

        reducer.reduce(intermediateKV, outputPath);

        double durationSec = (end - start) / 1_000_000_000.0;
        double filesPerSec = filePaths.size() / durationSec;

        System.out.printf("Processed %d files in %.2f seconds (%.2f files/sec)%n",
                filePaths.size(), durationSec, filesPerSec);
    }
}
