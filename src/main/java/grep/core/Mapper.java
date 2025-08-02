
package grep.core;

import java.util.List;

public interface Mapper {
    List<Pair<String, String>> map(String fileName, List<String> lines, String pattern);
}
