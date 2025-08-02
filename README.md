# distributed-grep-in-java
Distributed Grep using a lightweight MapReduce implementation in Java 24

Supports both platform threads and virtual threads with some simple benchmarks.

Scalable and has been tested with 10,000 log files. To simulate long reads and latency, a Thread.sleep() function was used.

I primarily developed this to observe how virtual threads can be useful for large IO operations as it can be scaled up to millions.

The platform thread implementation used creates around 8 platform threads (depending on number of CPU cores).
The virtual thread implementation created threads equal to the number of tasks for the map() function (in this case, the number of files to read which was 10,000).

## Results
When using 10,000 log files the following results were observed: 

Virtual Threads: Processed 19200 files in 0.46 seconds (41941.44 files/sec)

Platform Threads: Processed 19200 files in 29.37 seconds (653.81 files/sec)

As mentioned earlier, when the number of files to search are in the million and the number of platform threads available are limited, virtual threads come in handy.

## How to Run

### Compile:

```bash
javac -d out src/main/java/grep/**/*.java
```
### Run:

```bash
java -cp out grep.GrepApplication "<search_pattern>" <output_file> <input_files...> [--virtual]
```
For input_files, logs/log*.txt was used to obtain all the logs.



