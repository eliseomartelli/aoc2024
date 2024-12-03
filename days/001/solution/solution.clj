(ns solution
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(defn parse-line
  [line]
  (mapv #(Integer/parseInt %) (str/split line #"\s+")))

(defn parse-tsv
  [filepath]
  (with-open [reader (io/reader filepath)]
    (doall (map parse-line (line-seq reader)))))

(defn calculate-pair-distance
  [total-dist pair]
  (+ total-dist (Math/abs (- (first pair) (second pair)))))

(defn count-occurrences
  [lst]
  (reduce (fn [acc x] (update acc x (fnil inc 0))) {} lst))

(defn calculate-similarity-score
  [left-list right-list]
  (let [right-counts (count-occurrences right-list)]
    (reduce (fn [score num]
              (+ score (* num (get right-counts num 0))))
            0
            left-list)))

(let [input-file "input"
      rows (parse-tsv input-file)
      left-list (map first rows)
      right-list (map second rows)
      total-distance (calculate-total-distance rows)
      total-similarity (calculate-similarity-score left-list right-list)]
  (println total-distance)
  (println total-similarity))
