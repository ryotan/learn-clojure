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

(every? odd? [1 3 5])

(some odd? [2 4 6 7])

(some identity [nil nil 3 nil])

(not-every? even? (whole-numbers))

(not-any? even? (whole-numbers))

(map #(format "<p>%s</p>" %) ["the" "quick" "brown" "fox"])

(map #(format "<%s>%s</%s>" %1 %2 %1) ["h1" "h2" "h3" "1"] ["the" "quick" "brown" "fox"])

(reduce + (range 1 11))

(reduce * (range 1 11))

(sort [42 1 7 11])

(sort-by #(.toString %) [42 1 7 11])

(sort > [42 1 7 11])

(sort-by :grade > [{:grade 83} {:grade 90} {:grade 77}])

(for [word ["the" "quick" "brown" "fox"]]
  (format "<p>%s</p>" word))

(take 50 (for [n (whole-numbers) :when (even? n)] n))

(for [file "ABCDEFGH"
      rank (range 1 9)]
  (format "%c%d" file rank))


