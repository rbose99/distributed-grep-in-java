
package grep.core;

import java.util.List;

public interface Reducer {
    void reduce(List<Pair<String, String>> mappedData, String outputPath);

}
