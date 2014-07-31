(ns swissknife.core
  (:use [clojure.walk :only [walk]]))


(defn keep-last
  "Returns the last n elements of the sequence"
  [n xs]
  (drop (- (count xs) n) xs))


(defn group
  "A cross between group-by and walk, applies
inner and outer to the grouped collections instead
of the entire map

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


(defn bucket
  "Partitions a collection into fractions of a total, keeps order

by: transforms object to number
fraction: part of the whole in each bucket (defaults to 1/10)
total: the whole which a fraction is compared to

Doesn't realise the sequence if total is provided"

  ([by xs]
     (bucket by 1/10 xs))
  ([by fraction xs]
     (bucket by fraction (reduce + (map by xs)) xs))
  ([by fraction total xs]
     (letfn [(take-fraction [coll]
               (loop [acc [] sum 0 c coll]
                 (cond (empty? c)
                       acc

                       (and (seq c) (empty? acc))
                       (recur (conj acc (first c)) (+ sum (by (first c))) (rest c))

                       (<= (/ (+ sum (by (first c))) total) fraction)
                       (recur (conj acc (first c)) (+ sum (by (first c))) (rest c))

                       :else
                       acc)))]
       (lazy-seq
        (let [f (take-fraction xs)]
          (when (seq f)
            (cons f (bucket by fraction total (nthrest xs (count f))))))))))
