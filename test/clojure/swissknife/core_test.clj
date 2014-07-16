(ns swissknife.core-test
  (:use [midje.sweet]
        [swissknife.core]))


(facts
 (keep-last 3 (range 10))
 => [7 8 9]

 (keep-last 3 [0 1])
 => [0 1])


(facts
 (group even? #(* % %) (partial apply +) (range 10))
 => {false 165                          ; [1 9 25 49 81]
     true 120})                         ; [0 4 16 36 64]
