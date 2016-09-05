(ns swissknife.crypto-test
  (:use [midje.sweet]
        [swissknife.crypto]))


(facts
 (decrypt (encrypt "data" "key") "key")
 => "data"

 (decrypt (encrypt "data" "key") "wrong-key")
 => nil)
