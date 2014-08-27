(ns swissknife.math-test
  (:use [midje.sweet]
        [swissknife.math]))


(facts
 "factorial"

 (factorial 0) => 1
 (factorial 1) => 1
 (factorial 5) => 120
 (type (factorial 0)) => clojure.lang.BigInt)


(facts
 "permutations"

 (P 5 1) => 5
 (P 5 2) => 20
 (P 5 3) => 60
 (P 5 4) => 120
 (P 5 5) => 120
 (type (P 5 1)) => clojure.lang.BigInt)


(facts
 "combinations"

 (C 5 1) => 5
 (C 5 2) => 10
 (C 5 3) => 10
 (C 5 4) => 5
 (C 5 5) => 1
 (type (C 5 1)) => clojure.lang.BigInt)


(facts
 "geometric sum"

 (geometric-sum 2 5)
 => 63.0)
