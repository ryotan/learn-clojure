(defn whole-numbers [] (iterate inc 1))

(interleave (whole-numbers) ["A", "B", "C", "D", "E"])

(apply str (interpose "," ["apples" "bananas" "grapes"]))

(use '[clojure.string :only (join)])

(join "," ["apples" "bananas" "grapes"])

(join \, (take 10 (whole-numbers)))

(apply str (take 10 (interpose \, (whole-numbers))))

(take 10 (filter even? (whole-numbers)))

(filter even? (take 10 (whole-numbers)))

(take-while (complement #{\a \e \i \o \u}) "the-quick-brown-fox")

(drop-while (complement #{\a \e \i \o \u}) "the-quick-brown-fox")

(split-at 5 (range 10))

(split-with #(<= % 10) (range 0 20 2))
