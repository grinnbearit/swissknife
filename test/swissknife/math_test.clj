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
 "mean"

 (mean [1 2 3 4 5]) => 3
 (mean []) => (throws ArithmeticException))


(facts
 "variance"

 (variance [1 2 3 4 5]) => 2
 (variance [1 2 3 4 5] :mu 3) => 2
 (variance [1 2 3 4 5] :sample? true) => 5/2
 (variance [1 2 3 4 5] :mu 2) => 3)


(facts
 "standard deviation"

 (standard-deviation [1 2 3 4 5]) => (Math/sqrt 2)
 (standard-deviation [1 2 3 4 5] :sigma 4) => 2.0
 (standard-deviation [1 2 3 4 5] :sample? true) => (Math/sqrt 5/2)
 (standard-deviation [1 2 3 4 5] :mu 2) => (Math/sqrt 3))


(facts
 "relative frequencies"

 (relative-frequencies [])
 => {}

 (relative-frequencies [:a :a :b :c :d :d :d])
 => {:a 2/7 :b 1/7 :c 1/7 :d 3/7})
