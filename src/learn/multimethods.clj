(require '[clojure.string :as str])
(defmethod my-print java.util.Collection [c]
  (.write *out* "(")
  (.write *out* (str/join " " c))
  (.write *out* ")"))

(defmethod my-print clojure.lang.IPersistentVector [v]
  (.write *out* "[")
  (.write *out* (str/join " " v))
  (.write *out* "]"))

(prefer-method my-print clojure.lang.IPersistentVector java.util.Collection)

(my-println (take 6 (cycle [1 2 3])))
(my-println [1 2 3])
