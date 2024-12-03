(ns part1
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(defn parse-tsv
  [filepath]
  (with-open [reader (io/reader filepath)]
    (doall
     (map (fn [line]
            (map #(Integer/parseInt %) (str/split line #" +")))
          (line-seq reader)))))

(defn calculate-total-distance
  [rows]
  (let [columns (apply map vector rows)
        sorted-cols (map #(sort %) columns)]
    (reduce
     (fn [total-dist pair]
       (+ total-dist (Math/abs (- (first pair) (second pair)))))
     0
     (map vector (first sorted-cols) (second sorted-cols)))))

(let [input-file "input"
      rows (parse-tsv input-file)
      total-distance (calculate-total-distance rows)]
  (println total-distance))
