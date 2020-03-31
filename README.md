# N50Calculator
###### Exercise One - Cmd Line Tool

Requirement: Java 8.

Run `javac N50Calculator.java && java N50Calculator -f <FILEPATH>` to execute the program.

Usage:
No arg:
```
$ javac N50Calculator.java && java N50Calculator
Usage: java N50Calculator [-h] -f FILEPATH

Required arguments:
-f, --filepath : path to the Fasta File
Optional arguments:
-h, --help : show this help message and exit
```
File doesn't exist:
```
$ javac N50Calculator.java && java N50Calculator -f 123

********** File not found! *************

Usage: java N50Calculator [-h] -f FILEPATH

Required arguments:
-f, --filepath : path to the Fasta File
Optional arguments:
-h, --help : show this help message and exit
```
Process existing file:
```
$ javac N50Calculator.java && java N50Calculator -f input.fna
3654
```

File validation: file exist, no empty lines, contig only contains ATGC
