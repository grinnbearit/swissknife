(ns util.core)


(defn keep-last
  "Returns the last n elements of the sequence"
  [n xs]
  (drop (- (count xs) n) xs))
