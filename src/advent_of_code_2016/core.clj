(ns advent-of-code-2016.core
  (:require [advent-of-code-2016.day01 :as day01]
            [advent-of-code-2016.day02 :as day02])
  (:gen-class))

(defn -main
  [& args]
  (try
    (let [input (slurp "inputs/02.txt")]
      (day02/run-bonus input))
    (catch Throwable e (println "sad times") (println e))))
