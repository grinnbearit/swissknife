(ns swissknife.core
  (:require [goog.string.format]))

;;; http://stackoverflow.com/questions/22743115/how-to-define-a-format-function-for-all-namespaces-in-clojurescript
(defn format
  [fmt & args]
  (apply goog.string/format fmt args))
