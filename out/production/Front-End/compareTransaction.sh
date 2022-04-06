#!/bin/bash

FILES="e_outputs/*.txt"
num=0
for f in $FILES # read expected output files
do
  { # compare the file differences
    diff -u "$f" "outputs/transaction_$((num)).txt"

  } > "transaction_differences/$((num++))_difference.txt" # write the differences

done
