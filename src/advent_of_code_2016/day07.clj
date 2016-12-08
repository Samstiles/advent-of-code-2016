(ns advent-of-code-2016.day07
  (:require [clojure.string :refer [split join]]
            [clojure.set :refer [intersection]]))

;; very little time to work on AoC today so uploading
;; my sloppy solutions w/ lots of duplication :c

;; -- shared fn's

(defn square-bracket-before? [ip i]
  (let [rest-of-str (reverse (subs ip 0 i))]
    (loop [idx 0]
      (if (or (= (nth rest-of-str idx) \])
              (= idx (- (count rest-of-str) 1)))
        false
        (if (= (nth rest-of-str idx) \[)
          true
          (recur (inc idx)))))))

(defn square-bracket-after? [ip i]
  (let [rest-of-str (subs ip i)]
    (loop [idx 0]
      (if (or (= (nth rest-of-str idx) \[)
              (= idx (- (count rest-of-str) 1)))
        false
        (if (= (nth rest-of-str idx) \])
          true
          (recur (inc idx)))))))

;; -- part 1

(defn find-abba-indexes [ip]
  (loop [index 0
         indexes []]
    (if (= index (- (count ip) 3))
      indexes
      (if (and
           (not= (nth ip index) (nth ip (+ index 1)))
           (= (nth ip index)
              (nth ip (+ index 3)))
           (= (nth ip (+ index 1))
              (nth ip (+ index 2))))
        (recur (inc index) (conj indexes index))
        (recur (inc index) indexes)))))

(defn abba-wrapped? [ip abba-index]
  (if (or (= abba-index (- (count ip) 4)) (= abba-index 0))
    false
    (let [char-before (nth ip (- abba-index 1))
          char-after (nth ip (+ abba-index 4))]
      (and (square-bracket-before? ip abba-index)
           (square-bracket-after? ip abba-index)))))

(defn run [input]
  (let [ips (split input #"\n")]
    (reduce (fn [acc ip]
              (let [abba-indexes (find-abba-indexes ip)]
                (if (= (count abba-indexes) 0)
                  acc
                  (let [da-truths (->> (map
                                        #(not (abba-wrapped? ip %))
                                        abba-indexes))]
                    (if (and (not= nil da-truths)
                             (not= 0 (count da-truths))
                             (every? true? da-truths))
                      (inc acc)
                      acc)))))
            0
            ips)))

;; -- part 2

(defn sqr-bracket-after? [ip idx]
  (if (= (count ip) (+ idx 1))
    false
    (reduce
     (fn [acc letter]
       (if (= letter \[)
         (reduced false)
         (if (= letter \])
           (reduced true)
           false)))
     (subs ip idx))))

(defn sqr-bracket-before? [ip idx]
  (if (= idx 0)
    false
    (reduce
     (fn [acc letter]
       (if (= letter \])
         (reduced false)
         (if (= letter \[)
           (reduced true)
           false)))
     (reverse (subs ip 0 (+ idx 1))))))

(defn find-abas [ip]
  (reduce (fn [acc letter]
            (let [{:keys [idx inside-abas outside-abas]} acc]
              (if (= (+ idx 2) (count ip))
                (reduced acc)
                (let [temp-aba (subs ip idx (+ idx 3))]
                  (if (and (= (first temp-aba) (last temp-aba))
                           (not= (second temp-aba) \[)
                           (not= (second temp-aba) \])
                           (not= (first temp-aba) (second temp-aba)))
                    (if (and (sqr-bracket-before? ip idx)
                             (sqr-bracket-after? ip idx))
                      {:idx (inc idx) :outside-abas outside-abas :inside-abas (conj inside-abas temp-aba)}
                      {:idx (inc idx) :outside-abas (conj outside-abas temp-aba) :inside-abas inside-abas})
                    {:idx (inc idx) :inside-abas inside-abas :outside-abas outside-abas})))))
          {:idx 0 :inside-abas #{} :outside-abas #{}}
          ip))

(defn supports-ssl? [ip]
  (let [{:keys [inside-abas outside-abas]} (find-abas ip)
        babzzzz (reduce (fn [acc aba]
                          (let [corresponding-bab (str (second aba) (first aba) (second aba))]
                            (if (contains? outside-abas corresponding-bab)
                              (inc acc)
                              acc)))
                        0
                        inside-abas)]
    (> babzzzz 0)))

(defn run-bonus [input]
  (let [ips (split input #"\n")]
    (count (filter true? (map supports-ssl? ips)))))
