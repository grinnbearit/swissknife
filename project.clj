(defproject swissknife "0.9.2"
  :description "general purpose utility functions"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "0.0-2173"]]
  :source-paths ["src/clojure"]
  :test-paths ["test/clojure"]
  :cljsbuild {:builds [{:id "simple"
                        :source-paths ["test/cljs" "src/cljs"]
                        :compiler {:optimizations :simple
                                   :pretty-print true
                                   :static-fns true
                                   :output-to "test/cljs/tests.js"}}]
              :test-commands {"test" ["phantomjs"
                                      "test/cljs/tests.js"
                                      "test/cljs/tests.html"]}}
  :profiles {:dev {:dependencies [[midje "1.8.3"]]}})
