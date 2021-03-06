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

(use 'examples.primes)

(def ordinals-and-primes (map vector (iterate inc 1) primes))

(take 10 (drop 10000 ordinals-and-primes))

(def x (for [i (range 1 3)] (do (println i) i)))

(doall x)

(dorun x)

(re-seq #"\w+" "the quick brown fox")

(sort (re-seq #"\w+" "the quick brown fox"))

(drop 2 (re-seq #"\w" "the quick brown fox"))

(map #(.toUpperCase %) (re-seq #"\w" "the quick brown fox"))

(import '(java.io File))

(seq (.listFiles (File. ".")))

(map #(.getName %) (.listFiles (File. ".")))

(count (file-seq (File. ".")))

(use 'clojure.java.io)

(count (file-seq (file ".")))

(defn minutes-to-millis [mins]
  (* mins 1000 60))

(defn recently-modified? [file]
  (> (.lastModified file)
     (- (System/currentTimeMillis) (minutes-to-millis 30))))

(filter recently-modified? (file-seq (file ".")))

(use '[clojure.java.io :only (reader)]
     '[clojure.java.io :only (writer)])

(with-open [rdr (reader "src/examples/utils.clj")]
  (count (line-seq rdr)))

(with-open [rdr (reader "src/examples/utils.clj")]
  (count (filter #(re-find #"\S+" %) (line-seq rdr))))

(defn non-blank? [line]
  (re-find #"\S+" line))

(with-open [rdr (reader "src/examples/utils.clj")]
  (count (filter non-blank? (line-seq rdr))))

(defn non-git? [file]
  (not (.contains (.toString file) ".git")))

(filter non-git? (file-seq (file ".")))

(defn clojure-source? [file]
  (.endsWith (.toString file) ".clj"))

(filter clojure-source? (file-seq (file ".")))


(defn clojure-sloc [base-file]
  (reduce
   +
   (for [file (file-seq base-file)
         :when (and (non-git? file) (clojure-source? file))]
     (with-open [rdr (reader file)]
       (count (filter non-blank? (line-seq rdr)))))))

(clojure-sloc (file "."))

(use '[clojure.xml :only (parse)])

(parse (file "data/sequences/compositions.xml"))

(for [x (xml-seq (parse (file "data/sequences/compositions.xml")))
      :when (= :composition (:tag x))]
  (:composer (:attrs x)))








