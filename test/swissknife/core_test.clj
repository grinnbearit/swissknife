(ns swissknife.core-test
  (:require [midje.sweet :refer :all]
            [swissknife.core :refer :all]
            [clojure.string :as str]))


(facts
 "keep-last"

 (keep-last 3 (range 10))
 => [7 8 9]

 (keep-last 3 [0 1])
 => [0 1])


(facts
 "group"

 (group even? #(* % %) (partial apply +) (range 10))
 => {false 165                          ; [1 9 25 49 81]
     true 120})                         ; [0 4 16 36 64]


(facts
 "bucket"

 (bucket identity 3 (range 10))
 => [[0 1 2 3 4 5] [6 7 8] [9]]         ; [15 21 9]

 (bucket #(* % %) 3 (range 10))
 => [[0 1 2 3 4 5 6 7] [8 9] []])       ; [140 145 0]


(facts
 "map-keys"

 (map-keys inc {1 :a 2 :b})
 => {2 :a 3 :b}

 (map-keys #(rem % 3) {1 :a 3 :b 5 :c 6 :d})
 => {0 :d 1 :a 2 :c})


(facts
 "map-values"

 (map-values reverse {:a [1 2 3] :b [4 5 6]})
 => {:a [3 2 1] :b [6 5 4]})


(facts
 "filter-keys"

 (filter-keys even? {1 :a 2 :b 3 :c 4 :d})
 => {2 :b 4 :d})


(facts
 "filter-values"

 (filter-values even? {:a 1 :b 2 :c 3 :d 4})
 => {:b 2 :d 4})


(facts
 "min-by"

 (min-by #(* % %) [-3 4 5 2])
 => 2

 (min-by #(* % %) > [-3 4 5 2])
 => 5

 (min-by identity [])
 => nil)


(facts
 "max-by"

 (max-by #(* % %) [-3 4 5 2])
 => 5

 (max-by #(* % %) > [-3 4 5 2])
 => 2

 (max-by identity [])
 => nil)
