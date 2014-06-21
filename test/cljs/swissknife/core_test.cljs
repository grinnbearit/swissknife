(ns swissknife.core-test
  (:require [swissknife.core :refer [format]]))

;;; format test
(assert (= "pie is 3.142..." (format "%s is %.3f..." "pie" 3.1415)))

(.exit js/phantom)
