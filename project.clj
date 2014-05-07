(defproject swissknife "0.2.0"
  :description "general purpose utility functions"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]]
  :profiles {:dev {:plugins [[lein-midje "3.1.3"]]
                   :dependencies [[midje "1.6.3"]]}})
