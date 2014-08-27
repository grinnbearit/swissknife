(ns swissknife.math.stats)


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
