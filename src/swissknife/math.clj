(ns swissknife.math)


(defn factorial
  [n]
  (reduce * (bigint 1) (range 1 (inc n))))


(defn P
  [n k]
  (if (= n k)
    (factorial n)
    (reduce * (bigint 1) (range (- (inc  n) k) (inc n)))))


(defn C
  [n k]
  (/ (P n k) (factorial k)))
