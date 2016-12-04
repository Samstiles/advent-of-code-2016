(ns advent-of-code-2016.day04
  (:require [clojure.string :refer [index-of replace trim-newline join split]])
  (:refer-clojure :exclude [replace]))

;; -- part 1

(defn run [input]
  (let [parts (split input #"\n")]
    (println
     (reduce (fn [acc line]
               (println line)
               (let [sector-id (Integer. (re-find #"\d+" line))
                     lettaz (-> line
                                (subs 0 (- (index-of line "[") 4))
                                (replace #"-" "")
                                (trim-newline))
                     checksum (subs line
                                    (- (index-of line "]") 5)
                                    (- (count line) 1))
                     thing (->> lettaz
                                (sort)
                                (frequencies)
                                (sort-by val >)
                                (take 5)
                                (keys)
                                (join))]
                 (if (= checksum thing)
                   (+ acc sector-id)
                   acc))) 0 parts))))

;; -- part 2

;; Source: https://clojurebridge.github.io/community-docs/docs/exercises/caesar-cipher/
(defn caesar-cipher [words offset]
  "Assumes offset >=0, words entirely lowercase English characters or spaces"
  (let [alphabet-chars (map char "abcdefghijklmnopqrstuvwxyz")
        alphabet-shifted (->> (cycle alphabet-chars) (take 100) (drop offset))
        shifted-map (-> (zipmap alphabet-chars alphabet-shifted)
                        (assoc \space \space))]
    (apply str (map shifted-map (map char words)))))

(defn run-bonus [input]
  (let [parts (split input #"\n")]
    (doseq [enc parts]
      (let [sector-id (Integer. (re-find #"\d+" enc))
            lettaz (-> enc
                       (subs 0 (- (index-of enc "[") 4))
                       (replace #"-" " ")
                       (trim-newline))
            cipher-offset (mod sector-id 26)
            dec (caesar-cipher lettaz cipher-offset)]
        (when (and (.contains dec "storage") (.contains dec "object"))
          (println "Sector ID of north pole storage: " sector-id)))
      parts)))
