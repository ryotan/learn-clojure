(defn whole-numbers [] (iterate inc 1))

(interleave (whole-numbers) ["A", "B", "C", "D", "E"])

(apply str (interpose "," ["apples" "bananas" "grapes"]))

(use '[clojure.string :only (join)])

(join "," ["apples" "bananas" "grapes"])

(join \, (take 10 (whole-numbers)))

(join \, (take 10 (whole-numbers)))
