(ns util.core)


(defn keep-last
  "Returns the last n elements of the sequence"
  [n xs]
  (drop (- (count xs) n) xs))


(defn queue
  "Returns a clojure.lang.PersistentQueue initialized with xs"
  [& xs]
  (reduce conj (clojure.lang.PersistentQueue/EMPTY) xs))
