(defproject util "0.1.0"
  :description "general purpose utility functions"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]]
  :profiles {:dev {:plugins [[lein-midje "3.0.1"]]
                   :dependencies [[midje "1.5.1"]]}})
