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


(defn mean
  [xs]
  (/ (reduce + xs) (count xs)))


(defn variance
  [xs & {:keys [mu sample?]}]
  (letfn [(square [x] (* x x))]
    (/ (->> (repeat (or mu (mean xs)))
            (map (comp square -) xs)
            (reduce +))
       (if sample?
         (dec (count xs))
         (count xs)))))


(defn standard-deviation
  [xs & {:keys [mu sigma sample?]}]
  (Math/sqrt (or sigma (variance xs :mu mu :sample? sample?))))


(defn relative-frequencies
  [points]
  (reduce-kv #(assoc %1 %2 (/ %3 (count points))) {} (frequencies points)))


(defn z-score
  [x mu sigma]
  (/ (- x mu) sigma))


(defn geometric-sum
  "geometric sum of the series a^0 + a^1 ... + a^n"
  [a n]
  (/ (- (Math/pow a (inc n)) 1) (- a 1)))
