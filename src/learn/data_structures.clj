;; functions for list.
(peek '(1 2 3))

(pop '(1 2 3))

;; difference of (peek, pop) and (first, rest).
(first '(1 2 3))

(rest '(1 2 3))

(peek '())
(first '())

(pop '())
(rest '())


;; functions for vector.
(peek [1 2 3])
(peek [1 2 '(1 2 3)])

(pop [1 2 3])
(pop [1 2 '(1 2 3)])

(get [:a :b :c] 1)
([:a :b :c] 1)

(get [:a :b :c] 4)
([:a :b :c] 4)

(assoc [0 1 2 3 4] 2 :two)

(subvec [1 2 3 4 5] 1 3)


;; functions for map.
(keys {:sundance "spaniel" :darwin "beagle"})
(vals {:sundance "spaniel" :darwin "beagle"})

(get {:sundance "spaniel" :darwin "beagle"} :darwin)
({:sundance "spaniel" :darwin "beagle"} :darwin)

(get {:sundance "spaniel" :darwin "beagle"} :snoopy :not-found)
({:sundance "spaniel" :darwin "beagle"} :snoopy :not-found)

(:darwin {:sundance "spaniel" :darwin "beagle"} :not-found)
(:snoopy {:sundance "spaniel" :darwin "beagle"} :not-found)

(def score {:stu nil :joey 100})

(:stu score)
(contains? score :stu)

(merge-with
 concat
 {:rubble ["Barney"] :flintstone ["Fred"]}
 {:rubble ["Betty"] :flintstone ["Wilma"]}
 {:rubble ["Bam-Bam"] :flintstone ["Pebbles"]})


;; functions for set.
(use 'clojure.set)

(def languages #{"java" "c" "d" "clojure"})
(def beverages #{"java" "chai" "pop"})

(union languages beverages)
(difference languages beverages)
(intersection languages beverages)
(select #(= 1 (.length %)) languages)

(def compositions
  #{{:name "The Art of the Fugue" :composer "J. S. Bach"}
    {:name "Musical Offering" :composer "J. S. Bach"}
    {:name "Requiem" :composer "Giuseppe Verdi"}
    {:name "Requiem" :composer "W. A. Mozart"}})
(def composers
  #{{:composer "J. S. Bach" :country "Germany"}
    {:composer "W. A. Mozart" :country "Austria"}
    {:composer "Giuseppe Verdi" :country "Italy"}})
(def nations
  #{{:nation "Germany" :language "German"}
    {:nation "Austria" :language "German"}
    {:nation "Italy" :language "Italian"}})

(rename compositions {:name :title})
(select #(= (:name %) "Requiem") compositions)
(project compositions [:name])
(for [m compositions c composers] (concat m c))
(join compositions composers)
(join composers nations {:country :nation})
(project
 (join
  (select #(= (:name %) "Requiem") compositions)
  composers)
 [:country])



