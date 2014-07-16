(ns swissknife.core
  (:use [clojure.walk :only [walk]]))


(defn keep-last
  "Returns the last n elements of the sequence"
  [n xs]
  (drop (- (count xs) n) xs))


(defn group
  "A cross between group-by and walk

by: the grouping function
inner: transform on each collected object
outer: run on each grouped collection"

  [by inner outer xs]
  (letfn [(reducer [acc o]
            (update-in acc [(by o)] conj (inner o)))

          (walker [[k v]]
            [k (outer v)])]

    (->> (reduce reducer {} xs)
         (walk walker identity))))
