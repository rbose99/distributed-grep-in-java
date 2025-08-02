
package grep.impl;

import java.util.ArrayList;
import java.util.List;

import grep.core.Mapper;
import grep.core.Pair;


public class GrepMapper implements Mapper {
    @Override
    public List<Pair<String, String>> map(String fileName, List<String> lines, String pattern) {
        List<Pair<String, String>> result = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.contains(pattern)) {
                result.add(new Pair<>(fileName + ":" + (i + 1), line));
            }
        }
        return result;
    }


}
