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
  n: number of buckets
  total: the whole which a fraction is compared to"

  ([by xs]
   (bucket by 10 xs))
  ([by n xs]
   (bucket by n (reduce + (map by xs)) xs))
  ([by n total xs]
   (letfn [(take-fraction [coll]
             (loop [acc [] sum 0 c coll]
               (cond (empty? c)
                     acc

                     (< sum (/ total n))
                     (recur (conj acc (first c)) (+ sum (by (first c))) (rest c))

                     :else
                     acc)))]
     (lazy-seq
      (if (and (pos? total) (pos? n))
        (let [f (take-fraction xs)]
          (cons f (bucket by (dec n) (- total (/ total n)) (nthrest xs (count f))))))))))


(defn map-keys
  "Returns a new map with keys transformed by f

  If 2 or more keys are transformed to the same result, only one of the values is retained"
  [f m]
  (letfn [(walker [[k v]]
            [(f k) v])]

    (walk walker identity m)))


(defn map-values
  "Returns a new map with values transformed by f"
  [f m]
  (letfn [(walker [[k v]]
            [k (f v)])]

    (walk walker identity m)))


(defn filter-keys
  "Returns a new map with keys that satisfy pred"
  [pred m]
  (into {} (filter (comp pred first) m)))


(defn filter-values
  "Returns a new map with values that satisfy pred"
  [pred m]
  (into {} (filter (comp pred second) m)))
