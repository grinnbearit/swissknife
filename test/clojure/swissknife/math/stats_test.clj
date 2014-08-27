(ns swissknife.math.stats-test
  (:use [midje.sweet]
        [swissknife.math.stats]))


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
