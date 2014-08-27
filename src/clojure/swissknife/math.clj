(ns swissknife.math)


(defn factorial
  [n]
  (reduce * (bigint 1) (range 1 (inc n))))


(defn factors
  [n]
  (->> (range 1 (inc (Math/sqrt n)))
       (filter #(= 0 (rem n %)))
       (mapcat #(vector % (/ n %)))))


(defn P
  [n k]
  (if (= n k)
    (factorial n)
    (reduce * (bigint 1) (range (- (inc  n) k) (inc n)))))


(defn C
  [n k]
  (/ (P n k) (factorial k)))


(defn geometric-sum
  "geometric sum of the series a^0 + a^1 ... + a^n"
  [a n]
  (/ (- (Math/pow a (inc n)) 1) (- a 1)))
