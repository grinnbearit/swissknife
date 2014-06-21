(ns swissknife.threads)


(defn sleep
  [ms]
  (do (Thread/sleep ms)
      ms))
