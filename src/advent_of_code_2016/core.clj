(ns advent-of-code-2016.core
  (:require [advent-of-code-2016.day01 :as day01])
  (:gen-class))

(defn -main
  [& args]
  (try
    (let [input (clojure.string/split (slurp "inputs/01.txt") #", ")]
      (day01/run-bonus input))
    (catch Throwable e (println "sad times") (println e))))
